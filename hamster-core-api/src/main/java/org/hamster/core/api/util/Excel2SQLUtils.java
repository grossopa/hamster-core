/**
 * 
 */
package org.hamster.core.api.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hamster.mobile.constant.AppConsts;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">grossopaforever@gmail.com</a>
 * @version May 30, 2014 9:05:08 AM
 */
public class Excel2SQLUtils {

    public static final String LINE_SEP = "\r\n";

    public static final String EXCEL_FILE = "scripts/sql/import-data.xlsx";

    public static final String OUTPUT_SQL = "src/test/resources/data/import-data.sql";

    public static final String INSERT_TEMPLATE = "INSERT INTO {0} ({1}) VALUES ({2});";

    public static final String COMMENT_TEMPLATE = "-- table {0} rows count {1}, Date {2}";

    public static final List<Class<? extends Handler>> handlers = Lists.newArrayList();

    static {
        handlers.add(MessageValueHandler.class);
        handlers.add(GeneralValueHandler.class);
    }

    /**
     * @param args
     * @throws IOException
     * @throws
     */
    public static final void main(String[] args) throws Exception {
        System.out.println("Opening " + new File(EXCEL_FILE).getAbsolutePath());

        XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(new File(EXCEL_FILE)));
        int sheetNumber = book.getNumberOfSheets();
        Date curDate = new Date();
        List<String> sqls = Lists.newArrayList();
        for (int i = 0; i < sheetNumber; i++) {
            XSSFSheet sheet = book.getSheetAt(i);
            String name = sheet.getSheetName();

            if (name.startsWith("Sheet")) {
                System.out.println("Ignore Sheet : " + name);
                continue;
            }

            System.out.println("Find Sheet : " + name);

            int rownum = 0;
            XSSFRow headerRow = sheet.getRow(rownum++);

            Handler handler = null;
            for (int j = 0; j < handlers.size(); j++) {
                Handler h = handlers.get(j).newInstance();
                if (h.affected(name)) {
                    handler = h;
                    break;
                }
            }

            List<String> fields = Lists.newArrayList();
            Iterator<Cell> cellIter = headerRow.cellIterator();
            while (cellIter.hasNext()) {
                Cell next = cellIter.next();
                fields.add(next.getStringCellValue());
            }

            XSSFRow row = null;
            List<List<String>> rowsValues = Lists.newArrayList();

            while ((row = sheet.getRow(rownum++)) != null) {
                // parse comment
                if (row.getCell(0) != null && row.getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    String cellStr = row.getCell(0).getStringCellValue();
                    if (cellStr != null && cellStr.startsWith("##")) {
                        rowsValues.add(Lists.asList(cellStr, new String[] {}));
                        continue;
                    }
                }

                handler.buildValues(fields, row, rowsValues);
            }
            sqls.add(MessageFormat.format(COMMENT_TEMPLATE, name, String.valueOf(rowsValues.size()), curDate.toString()));
            for (List<String> values : rowsValues) {
                if (values.size() == 1 && values.get(0).startsWith("##")) {
                    sqls.add("-- " + values.get(0));
                } else {
                    sqls.add(MessageFormat.format(INSERT_TEMPLATE, name, StringUtils.join(fields, ", "),
                            StringUtils.join(values, ", ")));
                }
            }
            sqls.add("");
        }
        File sqlFile = new File(OUTPUT_SQL);
        if (sqlFile.exists() && !sqlFile.canWrite()) {
            sqlFile.delete();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFile));
        writer.write(StringUtils.join(sqls, LINE_SEP));
        writer.close();
        System.out.println("Finished. " + sqlFile.getAbsolutePath());
    }

    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.replace(str, "'", "''");
    }

    public static String getValue(Cell cell) {
        if (cell == null) {
            return "NULL";
        } else {
            switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
            case XSSFCell.CELL_TYPE_FORMULA:
            case XSSFCell.CELL_TYPE_ERROR:
                return "'" + escapeSql(cell.getStringCellValue().replaceAll("\n", "")) + "'";
            case XSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case XSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    String strValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    return "'" + strValue + "'";
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case XSSFCell.CELL_TYPE_BLANK:
                return "NULL";
            default:
                return "'" + escapeSql(cell.getStringCellValue().replaceAll("\n", "")) + "'";
            }
        }
    }
}

class GeneralValueHandler implements Handler {

    @Override
    public Boolean affected(String table) {
        return true;
    }

    @Override
    public void buildValues(List<String> headers, XSSFRow row, List<List<String>> rowsValues) {
        List<String> values = Lists.newArrayList();

        for (int j = 0; j < headers.size(); j++) {
            String strlist = getValue(row.getCell(j));
            values.add(strlist);
        }

        if (StringUtils.isBlank(StringUtils.join(values, ", ")) || values.size() < headers.size()) {
            return;
        }
        rowsValues.add(values);
    }

    @Override
    public String getValue(Cell cell) {
        return Excel2SQLUtils.getValue(cell);
    }
}

class MessageValueHandler extends GeneralValueHandler {

    private List<String> headers_copy = null;

    @Override
    public Boolean affected(String table) {
        if ((AppConsts.DB_PREFIX + "sys_message").equalsIgnoreCase(table)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void buildValues(List<String> headers, XSSFRow row, List<List<String>> rowsValues) {
        Set<String> locales = Sets.newHashSet();

        if (headers_copy == null) {
            headers_copy = Lists.newArrayList(headers);
            Iterator<String> headerIter = headers.iterator();
            while (headerIter.hasNext()) {
                String h = headerIter.next();
                if (h.startsWith("message_")) {
                    headerIter.remove();
                }
            }

            headers.add("locale");
            headers.add("message");
            
        }

        for (String header : headers_copy) {
            if (header.startsWith("message_")) {
                locales.add(header.substring("message_".length()));
            }
        }
        int index = 100000;
        for (String locale : locales) {
            List<String> values = Lists.newArrayList();

            String message = "";

            for (int j = 0; j < headers_copy.size(); j++) {
                String h = headers_copy.get(j);
                if (h.startsWith("message_")) {
                    if (h.substring("message_".length()).equals(locale)) {
                        message = getValue(row.getCell(j));
                    }
                } else if ("id".equalsIgnoreCase(h)) {
                    String idStr = getValue(row.getCell(j));
                    idStr = String.valueOf((int) Double.parseDouble(idStr) + index);
                    values.add(idStr);
                } else {
                    String str = getValue(row.getCell(j));
                    values.add(str);
                }
            }

            values.add("'" + locale + "'");
            values.add(message);

            if (StringUtils.isBlank(StringUtils.join(values, ", "))) {
                return;
            }

            rowsValues.add(values);
            index += 100000;
        }
    }

}

interface Handler {
    Boolean affected(String table);

    String getValue(Cell cell);

    void buildValues(List<String> headerCell, XSSFRow row, List<List<String>> rowsValues);
}
