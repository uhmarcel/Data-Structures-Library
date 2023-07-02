package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public abstract class AbstractPriorityQueueTests {

    public abstract <T> H_PriorityQueue<T> getPriorityQueue();

    public abstract <T> H_PriorityQueue<T> getPriorityQueue(Comparator<? super T> comparator);

    @Nested
    class QueueTests extends AbstractQueueTests {
        @Override
        public <T> H_Queue<T> getQueue() {
            return getPriorityQueue();
        }
    }

    @Test
    public void givenDistinctItems_expectItemsQueuedInSortedOrder() {
        H_PriorityQueue<Integer> pq = getPriorityQueue();

        pq.offer(4);
        pq.offer(1);
        pq.offer(3);

        Assertions.assertEquals(1, pq.poll());
        Assertions.assertEquals(3, pq.poll());
        Assertions.assertEquals(4, pq.poll());
    }

    @Test
    public void givenDuplicateItems_expectItemsQueuedInOrder() {
        H_PriorityQueue<Character> pq = getPriorityQueue();

        pq.offer('C');
        pq.offer('A');
        pq.offer('A');
        pq.offer('B');

        Assertions.assertEquals('A', pq.poll());
        Assertions.assertEquals('A', pq.poll());
        Assertions.assertEquals('B', pq.poll());
        Assertions.assertEquals('C', pq.poll());
    }

    @Test
    public void givenCustomComparator_expectItemsQueuedInOrder() {
        H_PriorityQueue<Integer> pq = getPriorityQueue(Comparator.reverseOrder());

        pq.offer(4);
        pq.offer(1);
        pq.offer(3);
        pq.offer(2);


        Assertions.assertEquals(4, pq.poll());
        Assertions.assertEquals(3, pq.poll());
        Assertions.assertEquals(2, pq.poll());
        Assertions.assertEquals(1, pq.poll());
    }

}
