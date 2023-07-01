package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractQueueTests {

    public abstract <T> H_Queue<T> getQueue();

    @Test
    public void givenEmptyQueue_whenOfferElement_expectSizeToBeOne() {
        H_Queue<Integer> q = getQueue();

        q.offer(42);

        Assertions.assertEquals(1, q.size());
    }

    @Test
    public void givenNonEmptyQueue_whenPoll_expectHeadElementReturnedAndSizeDecreased() {
        H_Queue<Character> q = getQueue();

        q.offer('a');
        q.offer('b');

        Assertions.assertEquals(2, q.size());
        Assertions.assertEquals('a', q.poll());
        Assertions.assertEquals(1, q.size());
        Assertions.assertEquals('b', q.poll());
        Assertions.assertEquals(0, q.size());
    }

    @Test
    public void givenEmptyQueue_whenPoll_expectNullReturned() {
        H_Queue<Character> q = getQueue();

        Assertions.assertNull(q.poll());
    }

    @Test
    public void givenNonEmptyQueue_whenPeek_expectHeadElementReturnedAndSameSize() {
        H_Queue<Character> q = getQueue();

        q.offer('a');

        Assertions.assertEquals('a', q.peek());
        Assertions.assertEquals(1, q.size());

        q.offer('b');

        Assertions.assertEquals('a', q.peek());
        Assertions.assertEquals(2, q.size());

        q.poll();

        Assertions.assertEquals('b', q.peek());
        Assertions.assertEquals(1, q.size());

        q.offer('z');

        Assertions.assertEquals('b', q.peek());
        Assertions.assertEquals(2, q.size());
    }

    @Test
    public void givenEmptyQueue_whenPeek_expectNull() {
        H_Queue<Character> q = getQueue();

        Assertions.assertNull(q.peek());
    }

    @Test
    public void givenEmptyQueue_whenIsEmpty_expectTrue() {
        H_Queue<Integer> q = getQueue();

        Assertions.assertTrue(q.isEmpty());
    }

    @Test
    public void givenNonEmptyQueue_whenIsEmpty_expectFalse() {
        H_Queue<Integer> q = getQueue();

        q.offer(42);

        Assertions.assertFalse(q.isEmpty());
    }

}
