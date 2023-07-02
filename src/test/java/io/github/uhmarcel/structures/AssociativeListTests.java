package io.github.uhmarcel.structures;

import io.github.uhmarcel.AbstractMapTests;
import io.github.uhmarcel.H_Map;
import org.junit.jupiter.api.Nested;

public class AssociativeListTests {

    @Nested
    class MapTests extends AbstractMapTests {
        @Override
        public <K,V> H_Map<K,V> getMap() {
            return new H_AssociativeList<>();
        }
    }

}
