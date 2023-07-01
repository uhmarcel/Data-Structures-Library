package io.github.uhmarcel.structures;

import io.github.uhmarcel.AbstractCollectionTests;
import io.github.uhmarcel.AbstractListTests;
import io.github.uhmarcel.H_Collection;
import io.github.uhmarcel.H_List;
import org.junit.jupiter.api.Nested;

@Nested
public class ArrayListTests {

    @Nested
    class CollectionTests extends AbstractCollectionTests {
        @Override
        public <T> H_Collection<T> getCollection() {
            return new H_ArrayList<>();
        }
    }

    @Nested
    class ListTests extends AbstractListTests {
        @Override
        public <T> H_List<T> getList() {
            return new H_ArrayList<>();
        }
    }

}