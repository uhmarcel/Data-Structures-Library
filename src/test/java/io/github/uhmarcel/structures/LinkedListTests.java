package io.github.uhmarcel.structures;

import io.github.uhmarcel.*;
import org.junit.jupiter.api.Nested;

@Nested
public class LinkedListTests {

    @Nested
    class CollectionTests extends AbstractCollectionTests {
        @Override
        public <T> H_Collection<T> getCollection() {
            return new H_LinkedList<>();
        }
    }

    @Nested
    class ListTests extends AbstractListTests {
        @Override
        public <T> H_List<T> getList() {
            return new H_LinkedList<>();
        }
    }

    @Nested
    class QueueTests extends AbstractQueueTests {
        @Override
        public <T> H_Queue<T> getQueue() {
            return new H_LinkedList<>();
        }
    }

    @Nested
    class StackTests extends AbstractStackTests {
        @Override
        public <T> H_Stack<T> getStack() {
            return new H_LinkedList<>();
        }
    }

    @Nested
    class DequeTests extends AbstractDequeTests {
        @Override
        public <T> H_Deque<T> getDeque() {
            return new H_LinkedList<>();
        }
    }

}