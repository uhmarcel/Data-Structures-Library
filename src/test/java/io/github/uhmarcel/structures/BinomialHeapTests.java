package io.github.uhmarcel.structures;

import io.github.uhmarcel.AbstractPriorityQueueTests;
import io.github.uhmarcel.H_PriorityQueue;
import org.junit.jupiter.api.Nested;

import java.util.Comparator;

public class BinomialHeapTests {

    @Nested
    class PriorityQueueTests extends AbstractPriorityQueueTests {
        @Override
        public <T> H_PriorityQueue<T> getPriorityQueue() {
            return new H_BinomialHeap<>();
        }

        @Override
        public <T> H_PriorityQueue<T> getPriorityQueue(Comparator<? super T> comparator) {
            return new H_BinomialHeap<>(comparator);
        }
    }

}
