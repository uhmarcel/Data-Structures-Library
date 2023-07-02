package io.github.uhmarcel.structures;

import io.github.uhmarcel.AbstractSetTests;
import io.github.uhmarcel.H_Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class LinkedHashSetTests {

    @Nested
    class SetTests extends AbstractSetTests {
        @Override
        public <T> H_Set<T> getSet() {
            return new H_LinkedHashSet<>();
        }
    }

    @Test
    public void shouldMaintainInsertionOrder() {
        H_LinkedHashSet<Integer> s = new H_LinkedHashSet<>();

        s.add(4);
        s.add(1);
        s.add(9);
        s.add(-9);
        s.add(2);

        Iterator<Integer> iter = s.iterator();

        Assertions.assertEquals(4, iter.next());
        Assertions.assertEquals(1, iter.next());
        Assertions.assertEquals(9, iter.next());
        Assertions.assertEquals(-9, iter.next());
        Assertions.assertEquals(2, iter.next());
        Assertions.assertFalse(iter.hasNext());
    }

}
