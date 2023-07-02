package io.github.uhmarcel;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.uhmarcel.TestUtils.expectCollectionContents;

public abstract class AbstractSetTests {

    public abstract <T> H_Set<T> getSet();

    @Nested
    class CollectionTests extends AbstractCollectionTests {
        @Override
        public <T> H_Collection<T> getCollection() {
            return getSet();
        }
    }

    @Test
    public void whenAddingDistinctElements_expectElementToBeAdded() {
        H_Set<Integer> s = getSet();

        s.add(1);
        s.add(2);
        s.add(3);

        expectCollectionContents(new Integer[] { 1, 2, 3 }, s);
    }

    @Test
    public void whenAddingDuplicateElements_expectUniqueElementsAdded() {
        H_Set<Character> s = getSet();

        s.add('a');
        s.add('b');
        s.add('b');
        s.add('b');

        expectCollectionContents(new Character[] { 'a', 'b' }, s);
    }

}
