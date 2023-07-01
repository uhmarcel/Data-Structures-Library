package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractDequeTests {

    public abstract <T> H_Deque<T> getDeque();

    @Test
    public void whenOfferFirst_expectElementAddedToHead() {
        H_Deque<Character> d = getDeque();

        d.offerFirst('a');

        Assertions.assertEquals('a', d.peekFirst());
        Assertions.assertEquals('a', d.peekLast());
        Assertions.assertEquals(1, d.size());

        d.offerFirst('b');

        Assertions.assertEquals('b', d.peekFirst());
        Assertions.assertEquals('a', d.peekLast());
        Assertions.assertEquals(2, d.size());
    }

    @Test
    public void whenOfferLast_expectElementAddedToTail() {
        H_Deque<Character> d = getDeque();

        d.offerLast('a');

        Assertions.assertEquals('a', d.peekFirst());
        Assertions.assertEquals('a', d.peekLast());
        Assertions.assertEquals(1, d.size());

        d.offerLast('b');

        Assertions.assertEquals('a', d.peekFirst());
        Assertions.assertEquals('b', d.peekLast());
        Assertions.assertEquals(2, d.size());
    }

    @Test
    public void whenPollFirst_expectHeadElementReturned() {
        H_Deque<Character> d = getDeque();

        d.offerLast('a');
        d.offerLast('b');

        Assertions.assertEquals('a', d.pollFirst());
        Assertions.assertEquals(1, d.size());

        Assertions.assertEquals('b', d.pollFirst());
        Assertions.assertEquals(0, d.size());
    }

    @Test
    public void whenPollLast_expectTailElementReturned() {
        H_Deque<Character> d = getDeque();

        d.offerLast('a');
        d.offerLast('b');

        Assertions.assertEquals('b', d.pollLast());
        Assertions.assertEquals(1, d.size());

        Assertions.assertEquals('a', d.pollLast());
        Assertions.assertEquals(0, d.size());
    }

    @Test
    public void givenEmptyDeque_whenIsEmpty_expectTrue() {
        H_Deque<Integer> s = getDeque();

        Assertions.assertTrue(s.isEmpty());
    }

    @Test
    public void givenNonEmptyDeque_whenIsEmpty_expectFalse() {
        H_Deque<Integer> s = getDeque();

        s.offerFirst(42);

        Assertions.assertFalse(s.isEmpty());
    }

}
