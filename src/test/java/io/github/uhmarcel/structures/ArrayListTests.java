package io.github.uhmarcel.structures;

import io.github.uhmarcel.AbstractListTests;
import io.github.uhmarcel.H_List;
import org.junit.jupiter.api.Nested;


public class ArrayListTests {

    @Nested
    class ListTests extends AbstractListTests {
        @Override
        public <T> H_List<T> getList() {
            return new H_ArrayList<>();
        }
    }

}