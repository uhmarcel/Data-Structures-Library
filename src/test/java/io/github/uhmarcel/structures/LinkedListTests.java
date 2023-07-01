package io.github.uhmarcel.structures;

import io.github.uhmarcel.AbstractCollectionTests;
import io.github.uhmarcel.AbstractListTests;
import io.github.uhmarcel.H_Collection;
import io.github.uhmarcel.H_List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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


}