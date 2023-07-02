package io.github.uhmarcel.structures;

import io.github.uhmarcel.AbstractSetTests;
import io.github.uhmarcel.H_Set;
import org.junit.jupiter.api.Nested;


public class HashSetTests {

    @Nested
    class SetTests extends AbstractSetTests {
        @Override
        public <T> H_Set<T> getSet() {
            return new H_HashSet<>();
        }
    }

}
