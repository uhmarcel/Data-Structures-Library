package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractListTests {

    public abstract <T> H_List<T> getList();

    @Test
    public void whenGetAtValidIndex_expectItemReturned() {
        H_List<String> l = getList();

        l.add("first");
        l.add("second");
        l.add("third");

        Assertions.assertEquals("first", l.get(0));
        Assertions.assertEquals("second", l.get(1));
        Assertions.assertEquals("third", l.get(2));
    }

    @Test
    public void whenGetAtIndexLessThanZero_expectIndexOutOfBoundsException() {
        H_List<String> l = getList();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.get(-1));
    }

    @Test
    public void whenGetAtIndexAboveSize_expectIndexOutOfBoundsException() {
        H_List<String> l = getList();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.get(1));
    }

    @Test
    public void whenSetAtValidIndex_expectItemUpdated() {
        H_List<Integer> l = getList();

        l.add(1);
        l.add(2);
        l.add(3);

        Assertions.assertArrayEquals(new Integer[] { 1, 2, 3 }, l.toArray());

        l.set(1, 9);

        Assertions.assertArrayEquals(new Integer[] { 1, 9, 3 }, l.toArray());
    }

    @Test
    public void whenSetAtIndexLessThanZero_expectIndexOutOfBoundsException() {
        H_List<Integer> l = getList();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.set(-1, 42));
    }

    @Test
    public void whenSetAtIndexLargerThanSize_expectIndexOutOfBoundsException() {
        H_List<Integer> l = getList();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.set(1, 42));
    }


    @Test
    public void whenAddingElementAtIndexZero_expectElementAdded() {
        H_List<Character> l = getList();

        l.add('a');
        l.add('b');
        l.add('c');

        Assertions.assertArrayEquals(new Character[] { 'a', 'b', 'c' }, l.toArray());

        l.add(0, 'd');

        Assertions.assertArrayEquals(new Character[] { 'd', 'a', 'b', 'c', }, l.toArray());
    }

    @Test
    public void whenAddingElementAtIndexInBetween_expectElementAdded() {
        H_List<Character> l = getList();

        l.add('a');
        l.add('b');
        l.add('c');

        Assertions.assertArrayEquals(new Character[] { 'a', 'b', 'c' }, l.toArray());

        l.add(1, 'd');

        Assertions.assertArrayEquals(new Character[] { 'a', 'd', 'b', 'c', }, l.toArray());
    }

    @Test
    public void whenAddingElementAtLastIndex_expectElementAdded() {
        H_List<Character> l = getList();

        l.add('a');
        l.add('b');
        l.add('c');

        Assertions.assertArrayEquals(new Character[] { 'a', 'b', 'c' }, l.toArray());

        l.add(3, 'd');

        Assertions.assertArrayEquals(new Character[] { 'a', 'b', 'c', 'd' }, l.toArray());
    }

    @Test
    public void whenAddingElementAtIndexAboveListSize_expectIndexOutOfBoundsException() {
        H_List<Character> l = getList();

        l.add('a');
        l.add('b');
        l.add('c');

        Assertions.assertArrayEquals(new Character[] { 'a', 'b', 'c' }, l.toArray());

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.add(4, 'd'));
    }

    @Test
    public void whenAddingElementAtNegativeIndex_expectIndexOutOfBoundsException() {
        H_List<Character> l = getList();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.add(-1, 'a'));
    }

    @Test
    public void whenRemoveAtValidIndex_expectElementRemoved() {
        H_List<Character> l = getList();

        l.add('x');
        l.add('y');
        l.add('z');

        Assertions.assertArrayEquals(new Character[] { 'x', 'y', 'z' }, l.toArray());

        char removed = l.remove(1);

        Assertions.assertEquals('y', removed);
        Assertions.assertArrayEquals(new Character[] { 'x', 'z' }, l.toArray());
    }

    @Test
    public void whenRemoveAtIndexLessThanZero_expectIndexOutOfBoundsException() {
        H_List<Character> l = getList();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.remove(-1));
    }

    @Test
    public void whenRemoveAtIndexAboveSize_expectIndexOutOfBoundsException() {
        H_List<Character> l = getList();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.remove(1));
    }

    @Test
    public void givenElementExists_whenCallingIndexOf_expectIndexReturned() {
        H_List<String> l = getList();

        l.add("data-1");
        l.add("target");
        l.add("data-2");

        Assertions.assertEquals(1, l.indexOf("target"));
    }

    @Test
    public void givenElementDoesNotExist_whenCallingIndexOf_expectNegativeOneReturned() {
        H_List<String> l = getList();

        l.add("data-1");
        l.add("data-2");

        Assertions.assertEquals(-1, l.indexOf("target"));
    }

}
