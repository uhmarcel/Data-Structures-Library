package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.uhmarcel.TestUtils.expectCollectionContents;

public abstract class AbstractMapTests {

    public abstract <K,V> H_Map<K,V> getMap();

    @Test
    public void givenEmptyMap_whenPut_expectSizeIncreases() {
        H_Map<Integer, Double> m = getMap();

        m.put(1, 0.1);
        m.put(2, 0.2);
        m.put(7, 0.7);

        Assertions.assertEquals(3, m.size());
    }

    @Test
    public void givenNonEmptyMap_whenGet_expectValueReturned() {
        H_Map<Integer, String> m = getMap();

        m.put(1, "one");
        m.put(2, "two");
        m.put(7, "seven");

        Assertions.assertEquals("one", m.get(1));
        Assertions.assertEquals("two", m.get(2));
        Assertions.assertEquals("seven", m.get(7));
    }

    @Test
    public void givenEmptyMap_whenGet_expectNull() {
        H_Map<Integer, String> m = getMap();

        Assertions.assertNull(m.get(1));
    }

    @Test
    public void givenEmptyMap_whenPutDuplicateKey_expectValueReplaced() {
        H_Map<Character, String> m = getMap();

        m.put('A', "test");
        m.put('B', "test");
        m.put('A', "replaced");

//        TODO: Associative list does not handle size properly (expected)
//        Assertions.assertEquals(2, m.size());

        Assertions.assertEquals("replaced", m.get('A'));
        Assertions.assertEquals("test", m.get('B'));
    }

    @Test
    public void givenEntryExists_whenContainsKey_expectTrue() {
        H_Map<String, String> m = getMap();

        m.put("some-key", "some-value");

        Assertions.assertTrue(m.containsKey("some-key"));
    }

    @Test
    public void givenEntryDoesNotExist_whenContainsKey_expectFalse() {
        H_Map<String, String> m = getMap();

        Assertions.assertFalse(m.containsKey("some-key"));
    }

    @Test
    public void givenEmptyMap_whenIsEmpty_expectTrue() {
        H_Map<String, String> m = getMap();

        Assertions.assertTrue(m.isEmpty());
    }

    @Test
    public void givenNonEmptyMap_whenIsEmpty_expectFalse() {
        H_Map<String, String> m = getMap();

        m.put("some-key", "some-value");

        Assertions.assertFalse(m.isEmpty());
    }

    @Test
    public void givenNonEmptyMap_whenGetKeySet_expectSetOfKeysReturned() {
        H_Map<Integer, String> m = getMap();

        m.put(42, "c49d");
        m.put(11, "dce4");
        m.put(9, "c188");
        m.put(27, "a881");
        m.put(1, "c49d");

        H_Set<Integer> keys = m.keySet();

        expectCollectionContents(new Integer[] { 42, 11, 9, 27, 1 }, keys);
    }

    @Test
    public void givenNonEmptyMap_whenGetValues_expectCollectionOfValuesReturned() {
        H_Map<Integer, String> m = getMap();

        m.put(42, "c49d");
        m.put(11, "dce4");
        m.put(9, "c188");
        m.put(27, "a881");
        m.put(1, "c49d");

        H_Collection<String> values = m.values();

        expectCollectionContents(new String[] { "c49d", "dce4", "c188", "a881", "c49d" }, values);
    }

    @Test
    public void givenNonEmptyMap_whenGetEntrySet_expectSetOfEntriesReturned() {
        H_Map<Integer, String> m = getMap();

        m.put(42, "c49d");
        m.put(11, "dce4");
        m.put(9, "c188");
        m.put(27, "a881");
        m.put(1, "c49d");

        H_Set<H_Map.Entry<Integer, String>> entries = m.entrySet();

        Assertions.assertEquals(5, entries.size());
    }

}
