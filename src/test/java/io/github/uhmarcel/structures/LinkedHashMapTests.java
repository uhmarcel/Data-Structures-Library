package io.github.uhmarcel.structures;

import io.github.uhmarcel.AbstractMapTests;
import io.github.uhmarcel.H_Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class LinkedHashMapTests {

    @Nested
    class MapTests extends AbstractMapTests {
        @Override
        public <K,V> H_Map<K,V> getMap() {
            return new H_LinkedHashMap<>();
        }
    }

    @Test
    public void givenNonEmptyMap_whenGetKeySet_expectKeysOrderedByInsertion() {
        H_Map<Integer, String> m = new H_LinkedHashMap<>();

        m.put(42, "c49d");
        m.put(11, "dce4");
        m.put(9, "c188");
        m.put(27, "a881");
        m.put(1, "c49d");

        Iterator<Integer> iter = m.keySet().iterator();

        Assertions.assertEquals(42, iter.next());
        Assertions.assertEquals(11, iter.next());
        Assertions.assertEquals(9, iter.next());
        Assertions.assertEquals(27, iter.next());
        Assertions.assertEquals(1, iter.next());
        Assertions.assertFalse(iter.hasNext());
    }

    @Test
    public void givenNonEmptyMap_whenGetValues_expectValuesOrderedByInsertion() {
        H_Map<Integer, String> m = new H_LinkedHashMap<>();

        m.put(42, "c49d");
        m.put(11, "dce4");
        m.put(9, "c188");
        m.put(27, "a881");
        m.put(1, "c49d");

        Iterator<String> iter = m.values().iterator();

        Assertions.assertEquals("c49d", iter.next());
        Assertions.assertEquals("dce4", iter.next());
        Assertions.assertEquals("c188", iter.next());
        Assertions.assertEquals("a881", iter.next());
        Assertions.assertEquals("c49d", iter.next());
        Assertions.assertFalse(iter.hasNext());
    }

}
