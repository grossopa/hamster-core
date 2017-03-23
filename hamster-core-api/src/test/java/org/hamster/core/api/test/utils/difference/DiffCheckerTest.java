/**
 * 
 */
package org.hamster.core.api.test.utils.difference;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.difference.DiffChecker;
import org.hamster.core.api.util.difference.DiffCheckerException;
import org.hamster.core.api.util.difference.comparator.PropertyComparator;
import org.hamster.core.api.util.difference.comparator.defaults.AbstractPropertyComparator;
import org.hamster.core.api.util.difference.model.DiffObjectVO;
import org.hamster.core.api.util.difference.model.DiffResultVO;
import org.hamster.core.api.util.difference.model.DiffType;
import org.hamster.core.api.util.difference.model.DiffVO;
import org.hamster.core.api.util.difference.transformer.IdInvoker;
import org.hamster.core.api.util.difference.transformer.defaults.DefaultPropertyInvoker;
import org.hamster.core.api.util.difference.walker.Walker;
import org.hamster.core.test.helper.Asserts;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DiffCheckerTest {

    @Test
    public void testSimple() throws DiffCheckerException {
        //@formatter:off
        List<Dog> list1 = Lists.newArrayList(
            new Dog("AAA001", "Jone", 35),
            new Dog("AAA002", "Jack Jones", 44),
            new Dog("AAA003", "DDYYDYD", 61)
        );
        
        //AAA002 DELETED
        List<Dog> list2 = Lists.newArrayList(
                new Dog("AAA001", "Jone", 35),
                new Dog("AAA003", null, 61),
                new Dog("AAA004", "NEWVAL2", 100),
                new Dog(null, "NEWITEM1", 3),
                new Dog(null, "NEWITEM2", 5)
            );
        //@formatter:on

        DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        DiffResultVO<String, Dog> diffResult = checker.check(Dog.class, list1, list2);
        List<DiffObjectVO> diffResultList = Lists.newArrayList();
        diffResultList.addAll(diffResult.getResults().get(DiffType.ADD));
        diffResultList.addAll(diffResult.getResults().get(DiffType.REMOVAL));
        diffResultList.addAll(diffResult.getResults().get(DiffType.CHANGE));

        Asserts.assertEquals(5, diffResultList.size());
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        for (DiffObjectVO object : diffResultList) {
            Asserts.assertNotEquals("AAA001", object.getId());
            
            for (Map.Entry<String, DiffVO> entry : object.getPropertyList().entrySet()) {
                if ( entry.getValue().getProperty() == null) {
                    System.out.println(entry.getValue());
                    System.out.println(object.getPropertyList());
                }
                Asserts.assertEquals(entry.getKey(), entry.getValue().getProperty());
            }

            if (object.getType() == DiffType.ADD) {
                if ("AAA004".equals(object.getId())) {
                    Asserts.assertNull(object.getPropertyList().get("name").getOldValue());
                    Asserts.assertEquals("NEWVAL2", object.getPropertyList().get("name").getNewValue());
                    Asserts.assertNull(object.getPropertyList().get("length").getOldValue());
                    Asserts.assertEquals(100, object.getPropertyList().get("length").getNewValue());
                    count1++;
                } else if (object.getId() == null) {
                    Asserts.assertNull(object.getPropertyList().get("name").getOldValue());
                    Object newValue = object.getPropertyList().get("name").getNewValue();
                    Asserts.assertTrue(newValue.equals("NEWITEM1") || newValue.equals("NEWITEM2"));
                    count2++;
                }
            } else if (object.getType() == DiffType.REMOVAL) {
                Asserts.assertEquals("AAA002", object.getId());
                count3++;
            } else if (object.getType() == DiffType.CHANGE) {
                Asserts.assertEquals("AAA003", object.getId());
                Asserts.assertEquals(1, object.getPropertyList().size());
                Asserts.assertEquals("DDYYDYD", object.getPropertyList().get("name").getOldValue());
                Asserts.assertEquals(null, object.getPropertyList().get("name").getNewValue());
                count4++;
            }
        }

        Asserts.assertEquals(1, count1);
        Asserts.assertEquals(2, count2);
        Asserts.assertEquals(1, count3);
        Asserts.assertEquals(1, count4);
    }

    @Test
    public void testMergerSimple() throws DiffCheckerException {
        //@formatter:off
        List<Dog> list1 = Lists.newArrayList(
            new Dog("AAA002", "Jack Jones", 44),
            new Dog("AAA003", "DDYYDYD", 61)
        );
        
        List<Dog> list2 = Lists.newArrayList(
                new Dog("AAA003", null, 61),
                new Dog("AAA004", "NEWVAL233", 100),
                new Dog(null, "NEWITEM1", 3),
                new Dog(null, "NEWITEM2", 5)
            );
        
        List<Dog> expectedList = Lists.newArrayList(
                new Dog("AAA003", null, 61),
                new Dog("AAA004", "NEWVAL233", 100),
                new Dog(null, "NEWITEM1", 3),
                new Dog(null, "NEWITEM2", 5)
            );
        
        DiffResultVO<String, Dog> diffResultVO = new DiffResultVO<String, Dog>();
        
        diffResultVO.setAddedColl(Lists.newArrayList(
                new Dog("AAA004", "NEWVAL233", 100),
                new Dog(null, "NEWITEM1", 3),
                new Dog(null, "NEWITEM2", 5)));
        
        diffResultVO.setRemovedColl(ImmutableMap.of("AAA002",  new Dog("AAA002", "Jack Jones", 44)));
        
        diffResultVO.setChangedColl(ImmutableMap.of("AAA003", (Pair<Dog, Dog>) ImmutablePair.<Dog, Dog>of(list1.get(1), new Dog("AAA003", null, 61))));
        
        diffResultVO.setProperties(Sets.newHashSet("id", "name", "length"));
        Map<DiffType, List<DiffObjectVO>> diffResults = Maps.newHashMap();
        diffResults.put(DiffType.ADD, Lists.newArrayList(
            new DiffObjectVO(DiffType.ADD, "AAA004", ImmutableMap.<String, DiffVO>of(
                "id", new DiffVO("id", null, "AAA004"),
                "name", new DiffVO("name", null, "NEWVAL233"),
                "length", new DiffVO("length", null, 100)
            )),
            new DiffObjectVO(DiffType.ADD, null, ImmutableMap.<String, DiffVO>of(
                "id", new DiffVO("id", null, null),
                "name", new DiffVO("name", null, "NEWITEM1"),
                "length", new DiffVO("length", null, 3)
            )),
            new DiffObjectVO(DiffType.ADD, null, ImmutableMap.<String, DiffVO>of(
                "id", new DiffVO("id", null, null),
                "name", new DiffVO("name", null, "NEWITEM2"),
                "length", new DiffVO("length", null, 5)
            ))
        ));
        diffResults.put(DiffType.REMOVAL, Lists.newArrayList(
            new DiffObjectVO(DiffType.REMOVAL, "AAA002", ImmutableMap.<String, DiffVO>of(
                "id", new DiffVO("id", "AAA002", null),
                "name", new DiffVO("name", "Jack Jones", null),
                "length", new DiffVO("length", 44, null)
            ))
        ));
        diffResults.put(DiffType.CHANGE, Lists.newArrayList(
                new DiffObjectVO(DiffType.CHANGE, "AAA003", ImmutableMap.<String, DiffVO>of(
                    "name", new DiffVO("name", "DDYYDYD", null)
                ))
            ));
        //@formatter:on
        diffResultVO.setResults(diffResults);

        DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        checker.merge(Dog.class, list1, list2, diffResultVO);

        Asserts.assertArrayEquals(expectedList.toArray(), list1.toArray());
    }
    
    /**
     * Ensure configuration is not changed for {@link DiffChecker}
     * @throws DiffCheckerException 
     */
    @Test
    public void testConfigEmpty() throws DiffCheckerException {
        List<Dog> list1 = Lists.newArrayList();
        
        List<Dog> list2 = Lists.newArrayList(new Dog("aabb", "a dog", 1));
        final DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        
        Function<Void, Void> doCheck = new Function<Void, Void>() {

            @Override
            public Void apply(Void input) {
                Asserts.assertNull(checker.getIdProperty());
                Asserts.assertNull(checker.getIdInvoker());
                Asserts.assertNull(checker.getMerger());
                Asserts.assertNull(checker.getProperties());
                Asserts.assertNull(checker.getPropertyComparators());
                Asserts.assertNull(checker.getPropertyInvoker());
                Asserts.assertNull(checker.getWalker());
                return null;
            }
            
        };
        doCheck.apply(null);
        
        checker.check(Dog.class, list1, list2);
        
        doCheck.apply(null);
    }
    
    @Test
    public void testConfigIdProperty() throws DiffCheckerException {
        List<Dog> list1 = Lists.newArrayList(new Dog("1", "Jone", 1));
        List<Dog> list2 = Lists.newArrayList(new Dog("1", "Home", 2));
        
        DiffChecker<Integer, Dog> checker = new DiffChecker<Integer, Dog>();
        checker.setIdProperty("length");
        DiffResultVO<Integer, Dog> result = checker.check(Dog.class, list1, list2);
        
        Asserts.assertEquals(1, result.getAddedColl().size());
        Asserts.assertEquals(DiffType.ADD, result.getResults().get(DiffType.ADD).get(0).getType());
    }
    
    @Test
    public void testConfigIdInvoker() throws DiffCheckerException {
        List<Dog> list1 = Lists.newArrayList(new Dog("1", "Jone", 1));
        List<Dog> list2 = Lists.newArrayList(new Dog("44aav", "What about your name", 35));
        
        final Object id = new Object();
        
        DiffChecker<Object, Dog> checker = new DiffChecker<Object, Dog>();
        checker.setIdInvoker(new IdInvoker<Object, Dog>() {

            @Override
            public Object invoke(Map<String, Method> methods, Dog object) {
                return id;
            }
            
        });
        
        DiffResultVO<Object, Dog> result = checker.check(Dog.class, list1, list2);
        Asserts.assertEquals(1, result.getChangedColl().size());
        Asserts.assertEquals(0, result.getAddedColl().size());
        Asserts.assertEquals(0, result.getRemovedColl().size());
        Asserts.assertEquals(DiffType.CHANGE, result.getResults().get(DiffType.CHANGE).get(0).getType());
    }
    
    @Test
    public void testConfigExcludeProperties() throws DiffCheckerException {
        List<Dog> list1 = Lists.newArrayList(new Dog("1", "Jone", 1));
        List<Dog> list2 = Lists.newArrayList(new Dog("1", "What about your name", 1));
        
        DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        checker.setExclude(true);
        checker.setProperties(new String[]{"name"});
        
        DiffResultVO<String, Dog> result = checker.check(Dog.class, list1, list2);
        
        Asserts.assertTrue(result.getAddedColl().isEmpty());
        Asserts.assertTrue(result.getRemovedColl().isEmpty());
        Asserts.assertTrue(result.getChangedColl().isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.ADD).isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.CHANGE).isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.REMOVAL).isEmpty());
        Asserts.assertEquals(2, result.getProperties().size());
    }
    
    @Test
    public void testConfigIncludeProperties() throws DiffCheckerException {
        List<Dog> list1 = Lists.newArrayList(new Dog("1", "Jone", 1));
        List<Dog> list2 = Lists.newArrayList(new Dog("1", "What about your name", 1));
        
        DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        checker.setExclude(false);
        checker.setProperties(new String[]{"length"});
        
        DiffResultVO<String, Dog> result = checker.check(Dog.class, list1, list2);
        
        Asserts.assertTrue(result.getAddedColl().isEmpty());
        Asserts.assertTrue(result.getRemovedColl().isEmpty());
        Asserts.assertTrue(result.getChangedColl().isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.ADD).isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.CHANGE).isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.REMOVAL).isEmpty());
        // id is included
        Asserts.assertEquals(2, result.getProperties().size());
    }
    
    @Test
    public void testConfigPropertyInvoker() throws DiffCheckerException {
        List<Dog> list1 = Lists.newArrayList(new Dog("1", "Jone", 3));
        List<Dog> list2 = Lists.newArrayList(new Dog("1", "What about your name", 3));
        
        final Object nameValue = new Object();
        DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        checker.setPropertyInvoker(new DefaultPropertyInvoker<Dog>() {

            @Override
            public Object invoke(String property, Method method, Dog object) {
                if ("name".equals(property)) {
                    return nameValue;
                }
                return super.invoke(property, method, object);
            }
            
        });
        
        DiffResultVO<String, Dog> result = checker.check(Dog.class, list1, list2);
        
        Asserts.assertTrue(result.getAddedColl().isEmpty());
        Asserts.assertTrue(result.getRemovedColl().isEmpty());
        Asserts.assertTrue(result.getChangedColl().isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.ADD).isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.CHANGE).isEmpty());
        Asserts.assertTrue(result.getResults().get(DiffType.REMOVAL).isEmpty());
        Asserts.assertEquals(3, result.getProperties().size());
    }
    
    @Test
    public void testConfigPropertyComparators() throws DiffCheckerException {
        List<Dog> list1 = Lists.newArrayList(new Dog("1", "Jone", 3));
        List<Dog> list2 = Lists.newArrayList(new Dog("1", "Jone", 3));
        
        DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        checker.setPropertyComparators(Lists.<PropertyComparator>newArrayList(new AbstractPropertyComparator() {

            @Override
            public boolean canCompare(String property, Method getterMethod) {
                return true;
            }

            @Override
            protected int doCompare(Object o1, Object o2) {
                return -1;
            }
            
        }));
        
        DiffResultVO<String, Dog> result = checker.check(Dog.class, list1, list2);
        
        Asserts.assertTrue(result.getAddedColl().isEmpty());
        Asserts.assertTrue(result.getRemovedColl().isEmpty());
        Asserts.assertEquals(1, result.getChangedColl().size());
        Asserts.assertTrue(result.getResults().get(DiffType.ADD).isEmpty());
        Asserts.assertEquals(1, result.getResults().get(DiffType.CHANGE).size());
        Asserts.assertEquals(3, result.getResults().get(DiffType.CHANGE).get(0).getPropertyList().size());
        Asserts.assertTrue(result.getResults().get(DiffType.REMOVAL).isEmpty());
        Asserts.assertEquals(3, result.getProperties().size());
    }
    
    @Test
    public void testConfigWalker() throws DiffCheckerException {
        List<Dog> list1 = Lists.newArrayList(
                new Dog("1", "Jone", 3),
                new Dog("2", "Jone", 3),
                new Dog("3", "Jone", 3)
                );
        List<Dog> list2 = Lists.newArrayList(
                new Dog("1", "Jone", 3)
                );
        
        DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        checker.setWalker(new Walker<String, Dog>() {

            @Override
            public Collection<Dog> walkForAdded(Map<String, Dog> sourceMap, Collection<Dog> targetColl, Map<String, Method> methods) {
                List<Dog> result = Lists.newArrayList();
                for (int i = 0; i < 10; i++) {
                    result.add(new Dog("New" + i, "Pipi", 10));
                }
                return result;
            }

            @Override
            public Map<String, Dog> walkForRemoved(Map<String, Dog> sourceMap, Collection<Dog> targetColl, Map<String, Method> methods) {
                return Maps.newHashMap();
            }

            @Override
            public Set<String> walkProperty(Dog source, Dog target, Map<String, Method> methods) {
                return Sets.newHashSet();
            }

        });
        DiffResultVO<String, Dog> result = checker.check(Dog.class, list1, list2);
        
        Asserts.assertEquals(10, result.getAddedColl().size());
        Asserts.assertTrue(result.getRemovedColl().isEmpty());
        Asserts.assertEquals(0, result.getChangedColl().size());
    }
    
    @Test
    public void testSimpleCollection() {
        Dog d = new Dog("1", "1", 1);
        List<Person> p1 = Lists.newArrayList(
                new Person(1l, "John", 300d, true, d, Lists.newArrayList("Snow1", "Snow2"), null)
                );
        List<Person> p2 = Lists.newArrayList(
                new Person(1l, "John", 300d, true, d, Lists.newArrayList("Snow2", "Snow1"), null)
                );
        
        DiffChecker<Long, Person> checker = new DiffChecker<Long, Person>();
        DiffResultVO<Long, Person> result = checker.check(Person.class, p1, p2);
        Asserts.assertFalse(result.hasChange());
    }
    
    @Test
    public void testChildrenDifference() {
        Dog d1 = new Dog("1", "1", 1);
        Dog d2 = new Dog("2", "2", 2);
        Dog d11 = new Dog("1", "2", 3);
        
        List<Person> p1 = Lists.newArrayList(
                new Person(1l, "John", 300d, true, d1, Lists.newArrayList("Snow1", "Snow2"), 
                        Lists.newArrayList(d1, d2))
                );
        List<Person> p2 = Lists.newArrayList(
                new Person(1l, "John", 300d, true, d1, Lists.newArrayList("Snow1", "Snow2"), 
                        Lists.newArrayList(d11, d2))
                );
        DiffChecker<Long, Person> checker = new DiffChecker<Long, Person>();
        DiffResultVO<Long, Person> result = checker.check(Person.class, p1, p2);
        Assert.assertTrue(result.hasChange());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Person {
        private Long id;
        private String name;
        private Double money;
        private boolean male;
        private Dog dog;
        private List<String> alias;
        private List<Dog> dogs;
    }

    @Data
    @AllArgsConstructor
    public static class Dog {
        private String id;
        private String name;
        private int length;
    }
    
}
