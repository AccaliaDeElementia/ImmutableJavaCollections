import org.junit.Test;
import org.junit.Assert;

import java.util.EmptyStackException;

public class ImmutableStackTest {
    private static class A {}
    private static class B extends A {}
    static final A dasA = new A();
    static final ImmutableStack<A> Empty = new ImmutableStack<A>();
    static final ImmutableStack<A> OneStack = new ImmutableStack<A>(dasA);
    static ImmutableStack<A> dasStack;
    @Test
    public void EmptyStackIsSizeZero () {
        Assert.assertEquals(0, Empty.size());
    }

    @Test(expected = EmptyStackException.class)
    public void EmptyStackCannotPeek () {
        Empty.peek();
    }

    @Test(expected = EmptyStackException.class)
    public void EmptyStackCannotPop () {
        Empty.pop();
    }

    @Test
    public void EmptyStackIsEmpty () {
        Assert.assertTrue(Empty.isEmpty());
    }

    @Test
    public void OneStackIsSizeOne () {
        Assert.assertEquals(1, OneStack.size());
    }

    @Test
    public void OneStackCanPeek () {
        Assert.assertSame(dasA, OneStack.peek());
    }

    @Test
    public void OneStackPopsToEmptyStack () {
        dasStack = OneStack.pop();
        Assert.assertTrue(dasStack.isEmpty());
    }

    @Test
    public void OneStackStackIsNotEmpty () {
        Assert.assertTrue(!OneStack.isEmpty());
    }

    @Test
    public void PushDoesNotChangeBaseStack () {
        dasStack = Empty;
        dasStack = dasStack.push(new A());
        Assert.assertNotSame(dasStack, Empty);
    }

    @Test
    public void PushIncrementsSize () {
        dasStack = Empty;
        dasStack = dasStack.push(new A());
        Assert.assertEquals(1, dasStack.size());
    }

    @Test
    public void PushMakesNotEmptyStack () {
        dasStack = Empty;
        dasStack = dasStack.push(new A());
        Assert.assertTrue(!dasStack.isEmpty());
    }

    @Test
    public void PopFromOneStackGivesEmptyStack () {
    }

}
