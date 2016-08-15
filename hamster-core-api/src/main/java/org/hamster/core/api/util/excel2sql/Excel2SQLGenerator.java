/**
 * 
 */
package org.hamster.core.api.util.excel2sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;

/**
 * To generate test SQLs from Excel
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class Excel2SQLGenerator {

    /**
     * Line separator for generated SQL
     */
    public static final String LINE_SEP = "\r\n";

    public static final String INSERT_TEMPLATE = "INSERT INTO {0} ({1}) VALUES ({2});";

    public static final String COMMENT_TEMPLATE = "-- table {0} rows count {1}, Date {2}";

    /**
     * In Excel, row starts with ## will be ignored.
     */
    public static final String IGNORED_ROW = "##";

    /**
     * to generate the result into a single file or stores each sheet into separated files
     */
    @Getter
    @Setter
    private boolean singleFile = false;

    /**
     * output directory if singleFile is false, otherwise this should be a file
     */
    @Getter
    private File output;

    /**
     * generate SQL from excel file
     * 
     * @param excelFile
     * @param output
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void generate(File excelFile, File output) throws IOException, IllegalAccessException, InstantiationException {
        this.output = output;
        validate(output);

        XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(excelFile));
        int sheetNumber = book.getNumberOfSheets();

        Map<String, List<String>> allSqls = Maps.newLinkedHashMap();
        for (int i = 0; i < sheetNumber; i++) {
            XSSFSheet sheet = book.getSheetAt(i);
            String name = sheet.getSheetName();

            if ("Index".equals(name)) {
                System.out.println("Ignore Sheet : " + name);
                continue;
            }

            allSqls.put(name, getSqlsFromSheet(sheet));
        }

        this.writeToFile(allSqls);

        IOUtils.closeQuietly(book);
    }

    protected void validate(File output) {
        if (singleFile && output.isDirectory()) {
            throw new IllegalArgumentException("output must be a file!" + output.getAbsolutePath());
        } else if (!singleFile && output.isFile()) {
            throw new IllegalArgumentException("output must be a directory!" + output.getAbsolutePath());
        }
    }

    protected List<String> getSqlsFromSheet(XSSFSheet sheet) {
        String name = sheet.getSheetName();
        int rownum = 0;
        XSSFRow headerRow = sheet.getRow(rownum++);
        XSSFRow row = null;
        List<List<String>> rowsValues = Lists.newArrayList();

        List<String> fields = getFields(headerRow);

        while ((row = sheet.getRow(rownum++)) != null) {
            // parse comment
            if (row.getCell(0) != null && row.getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String cellStr = row.getCell(0).getStringCellValue();
                if (!isCommentLine(cellStr)) {
                    rowsValues.add(Lists.asList(cellStr, new String[] {}));
                    continue;
                }
            }

            buildValues(fields, row, rowsValues);
        }

        List<String> sqls = Lists.newArrayList();
        sqls.add(MessageFormat.format(COMMENT_TEMPLATE, name, String.valueOf(rowsValues.size()), new Date()));
        for (List<String> values : rowsValues) {
            if (values.size() == 1 && isCommentLine(values.get(0))) {
                sqls.add("-- " + values.get(0));
            } else {
                sqls.add(MessageFormat.format(INSERT_TEMPLATE, name, StringUtils.join(fields, ", "), StringUtils.join(values, ", ")));
            }
        }
        sqls.add("");
        return sqls;
    }

    protected void buildValues(List<String> headers, XSSFRow row, List<List<String>> rowsValues) {
        List<String> values = Lists.newArrayList();

        for (int j = 0; j < headers.size(); j++) {
            values.add(getCellValue(row.getCell(j)));
        }

        if (StringUtils.isBlank(StringUtils.join(values, ", ")) || values.size() < headers.size()) {
            return;
        }
        rowsValues.add(values);
    }

    protected List<String> getFields(XSSFRow headerRow) {
        List<String> fields = Lists.newArrayList();
        Iterator<Cell> cellIter = headerRow.cellIterator();
        while (cellIter.hasNext()) {
            Cell next = cellIter.next();
            fields.add(next.getStringCellValue());
        }
        return fields;
    }

    protected void writeToFile(Map<String, List<String>> allSqls) throws IOException {

        FileOutputStream target = null;
        if (!singleFile) {
            if (!output.exists()) {
                output.mkdirs();
            }
        } else {
            target = new FileOutputStream(output);
        }
        int index = 0;
        for (Map.Entry<String, List<String>> entry : allSqls.entrySet()) {
            String name = entry.getKey();
            if (!singleFile) {
                File f = new File(output, String.format("%03d", index++) + "-" + name + ".sql");
                System.out.println(f.getAbsolutePath());
                f.createNewFile();
                target = new FileOutputStream(f);
            }
            String ss = StringUtils.join(entry.getValue(), LINE_SEP);
            IOUtils.write(ss, target);
        }
    }

    protected static boolean isCommentLine(String firstCellStr) {
        return StringUtils.indexOf(firstCellStr, IGNORED_ROW) == 0;
    }

    protected static String escapeSql(String str) {
        return StringUtils.replace(str, "'", "''");
    }

    protected static String getCellValue(Cell cell) {
        return CellType.valueOf(cell == null ? XSSFCell.CELL_TYPE_BLANK : cell.getCellType()).getValue(cell);
    }

    protected static enum CellType {

        STRING(XSSFCell.CELL_TYPE_STRING), FORMULA(XSSFCell.CELL_TYPE_FORMULA), ERROR(XSSFCell.CELL_TYPE_ERROR), BOOLEAN(XSSFCell.CELL_TYPE_BOOLEAN) {
            @Override
            public String getValue(Cell cell) {
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            }
        },
        NUMERIC(XSSFCell.CELL_TYPE_NUMERIC) {
            @Override
            public String getValue(Cell cell) {
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    String strValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    return "'" + strValue + "'";
                } else {
                    return Double.toString(cell.getNumericCellValue()).replaceAll("\\.0$", "");
                }
            }
        },
        BLANK(XSSFCell.CELL_TYPE_BLANK) {
            public String getValue(Cell cell) {
                return "NULL";
            }
        },
        DEFAULT(Integer.MIN_VALUE);

        private final int type;

        CellType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public String getValue(Cell cell) {
            return "'" + escapeSql(cell.getStringCellValue()) + "'";
        }

        public static final CellType valueOf(int cellType) {
            for (CellType ct : CellType.values()) {
                if (ct.getType() == cellType) {
                    return ct;
                }
            }
            return DEFAULT;
        }
    }

}
