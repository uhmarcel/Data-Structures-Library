package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractListTests {

    public abstract <T> H_List<T> getList();

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

        l.

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> l.add(-1, 'a'));
    }

}
