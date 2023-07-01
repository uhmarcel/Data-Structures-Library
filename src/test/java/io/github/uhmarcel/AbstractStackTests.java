package io.github.uhmarcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractStackTests {

    public abstract <T> H_Stack<T> getStack();

    @Test
    public void givenEmptyStack_whenPushElement_expectSizeToBeOne() {
        H_Stack<Integer> s = getStack();

        s.push(42);

        Assertions.assertEquals(1, s.size());
    }

    @Test
    public void givenNonEmptyStack_whenPop_expectTailElementReturnedAndSizeDecreased() {
        H_Stack<Character> s = getStack();

        s.push('a');
        s.push('b');

        Assertions.assertEquals(2, s.size());
        Assertions.assertEquals('b', s.pop());
        Assertions.assertEquals(1, s.size());
        Assertions.assertEquals('a', s.pop());
        Assertions.assertEquals(0, s.size());
    }

    @Test
    public void givenEmptyStack_whenPoll_expectNullReturned() {
        H_Stack<Character> s = getStack();

        Assertions.assertNull(s.pop());
    }

    @Test
    public void givenNonEmptyStack_whenPeek_expectLastElementReturnedAndSameSize() {
        H_Stack<Character> s = getStack();

        s.push('a');

        Assertions.assertEquals('a', s.peek());
        Assertions.assertEquals(1, s.size());

        s.push('b');

        Assertions.assertEquals('b', s.peek());
        Assertions.assertEquals(2, s.size());

        s.pop();

        Assertions.assertEquals('a', s.peek());
        Assertions.assertEquals(1, s.size());

        s.push('z');

        Assertions.assertEquals('z', s.peek());
        Assertions.assertEquals(2, s.size());
    }

    @Test
    public void givenEmptyStack_whenPeek_expectNull() {
        H_Stack<Character> s = getStack();

        Assertions.assertNull(s.peek());
    }

    @Test
    public void givenEmptyStack_whenIsEmpty_expectTrue() {
        H_Stack<Integer> s = getStack();

        Assertions.assertTrue(s.isEmpty());
    }

    @Test
    public void givenNonEmptyStack_whenIsEmpty_expectFalse() {
        H_Stack<Integer> s = getStack();

        s.push(42);

        Assertions.assertFalse(s.isEmpty());
    }

}
