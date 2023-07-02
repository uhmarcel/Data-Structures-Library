package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import java.util.Arrays;

public class TestUtils {

    public static <T> void expectCollectionContents(T[] contents, H_Collection<T> c) {
        Object[] expected = Arrays.copyOf(contents, contents.length);
        Object[] actual = c.toArray();

        Arrays.sort(expected);
        Arrays.sort(actual);

        Assertions.assertArrayEquals(expected, actual);
    }

}
