package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static io.github.uhmarcel.TestUtils.expectCollectionContents;

@Nested
public abstract class AbstractCollectionTests {

    public abstract <T> H_Collection<T> getCollection();

    @Test
    public void givenCollection_whenAddingElements_expectElementsAdded() {
        H_Collection<Character> c = getCollection();

        expectCollectionContents(new Character[] {}, c);

        c.add('a');
        c.add('b');
        c.add('c');

        expectCollectionContents(new Character[] { 'a', 'b', 'c' }, c);
    }

    @Test
    public void givenCollection_whenRemovingElements_expectElementsRemoved() {
        H_Collection<Character> c = getCollection();
        c.add('a');
        c.add('b');
        c.add('c');

        expectCollectionContents(new Character[] { 'a', 'b', 'c' }, c);

        c.remove('a');
        c.remove('c');

        expectCollectionContents(new Character[] { 'b' }, c);
    }

    @Test
    public void givenCollectionIncludingElement_whenCallingContains_expectTrue() {
        H_Collection<Character> c = getCollection();
        c.add('b');
        c.add('c');

        Assertions.assertTrue(c.contains('b'));
    }

    @Test
    public void givenCollectionNotIncludingElement_whenCallingContains_expectFalse() {
        H_Collection<Character> c = getCollection();
        c.add('a');
        c.add('c');

        Assertions.assertFalse(c.contains('b'));
    }

    @Test
    public void givenCollectionWithVariousSizes_whenCallingSize_expectProperSize() {
        H_Collection<Integer> c = getCollection();

        Assertions.assertEquals(0, c.size());

        for (int i = 1; i <= 100; i++) {
            c.add(i);

            Assertions.assertEquals(i, c.size());
        }

        for (int i = 100; i > 0; i--) {
            c.remove(i);

            Assertions.assertEquals(i - 1, c.size());
        }
    }

    @Test
    public void givenEmptyCollection_whenCallingIsEmpty_expectTrue() {
        H_Collection<Integer> c = getCollection();

        Assertions.assertTrue(c.isEmpty());
    }

    @Test
    public void givenNonEmptyCollection_whenCallingIsEmpty_expectFalse() {
        H_Collection<Integer> c = getCollection();
        c.add(1);

        Assertions.assertFalse(c.isEmpty());
    }

    @Test
    public void givenCollection_whenCallingToArray_expectCorrectArray() {
        H_Collection<Integer> c = getCollection();

        c.add(0);
        c.add(1);
        c.add(7);
        c.add(-9);
        c.add(9);
        c.add(2);

        Integer[] expected = new Integer[] { 0, 1, 7, -9, 9, 2 };

        expectCollectionContents(expected, c);
    }

    @Test
    public void givenNonEmptyCollection_whenCallingClear_expectEmptyCollection() {
        H_Collection<String> c = getCollection();
        c.add("one");
        c.add("two");

        expectCollectionContents(new String[] { "one", "two" }, c);
        Assertions.assertFalse(c.isEmpty());

        c.clear();

        expectCollectionContents(new String[] {}, c);
        Assertions.assertTrue(c.isEmpty());
    }

    @Test
    public void givenNonEmptyCollection_whenIterating_expectItems() {
        H_Collection<Integer> c = getCollection();

        c.add(1);
        c.add(2);
        c.add(3);

        expectCollectionContents(new Integer[] { 1, 2, 3 }, c);

        Iterator<Integer> iter = c.iterator();

        Assertions.assertTrue(iter.hasNext());
        Assertions.assertEquals(1, iter.next());
        Assertions.assertTrue(iter.hasNext());
        Assertions.assertEquals(2, iter.next());
        Assertions.assertTrue(iter.hasNext());
        Assertions.assertEquals(3, iter.next());
        Assertions.assertFalse(iter.hasNext());

        expectCollectionContents(new Integer[] { 1, 2, 3 }, c); // Expect no changes
    }

}