import org.junit.Test;
import org.junit.Assert;

import java.util.*;

public class ImmutableStackTest {
    private static class A {}
    private static class B extends A {}

    static final A[] arrA = new A[] { 
        new A(), new A(), new A(), new A(), new A(), 
        new A(), new A(), new A(), new A(), new A()}; 
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
    public void ConstructorIsCovariant () {
        B dasB = new B();
        dasStack = new ImmutableStack<A>(dasB);
        Assert.assertSame(dasB, dasStack.peek());
    }
    
    @Test
    public void IteratorNotHasNextOnEmpty () {
        Iterator<A> itr = Empty.iterator();
        Assert.assertTrue(!itr.hasNext());
    }

    @Test(expected = EmptyStackException.class)
    public void IteratorNextThrowsErrorOnEmpty () {
        Iterator<A> itr = Empty.iterator();
        itr.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void IteratorThrowsErrorForRemove() {
        Iterator<A> itr = OneStack.iterator();
        itr.remove();
    }

    @Test
    public void IteratorHasNextForOneStack() {
        Iterator<A> itr = OneStack.iterator();
        Assert.assertTrue(itr.hasNext());
    }
    @Test
    public void IteratorNextWorksForOneStack() {
        Iterator<A> itr = OneStack.iterator();
        Assert.assertSame(dasA, itr.next());
    }
    @Test(expected = UnsupportedOperationException.class)
    public void IteratorRemoveFailsForOneStack() {
        Iterator<A> itr = OneStack.iterator();
        itr.remove();
    }

    @Test 
    public void IterableConstructorCreatesCorrectLength () {
        dasStack = new ImmutableStack<A>(Arrays.asList(arrA));
        Assert.assertEquals(arrA.length, dasStack.size());
    }

    @Test
    public void IterableConstructorCreatesCorrectorder () {
        dasStack = new ImmutableStack<A>(Arrays.asList(arrA));
        int i = arrA.length - 1;
        for (A a: dasStack) {
            Assert.assertSame(arrA[i], a);
            i -= 1;
        }
    }

    @Test
    public void ToArrayObjectWorksMakesInputReversedArray(){
        dasStack = new ImmutableStack<A>(Arrays.asList(arrA));
        Object[] dasArr = dasStack.toArray();
        for(int i = arrA.length - 1, j = 0; i >= 0; i -= 1, j += 1) {
            Assert.assertSame(arrA[i], dasArr[j]);
        }
        
    }
}
