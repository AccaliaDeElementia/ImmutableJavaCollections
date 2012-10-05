import org.junit.Test;
import org.junit.Assert;

import java.util.*;

public class ImmutableQueueTest {
    private static class A {}
    private static class B extends A {}

    static final A dasA = new A();
    static final ImmutableQueue<A> Empty = new ImmutableQueue<A>();
    static ImmutableQueue<A> dasQ;
    @Test
    public void EmptyQueueIsSizeZero() {
        Assert.assertEquals(0, Empty.size());
        Assert.assertEquals(0, Empty.length);
    }

    @Test
    public void EmptyQueueIsEmpty () {
        Assert.assertEquals(true, Empty.isEmpty());
        Assert.assertEquals(true, Empty.empty);
    }

    @Test
    public void EmptyQueueCanPushRight() {
        ImmutableQueue<A> q = Empty.pushRight(dasA);
        Assert.assertSame(dasA, q.peekRight());
    }
     
    @Test
    public void EmptyQueueCanPushLeft() {
        ImmutableQueue<A> q = Empty.pushLeft(dasA);
        Assert.assertSame(dasA, q.peekLeft());
    }

    @Test(expected = EmptyQueueException.class)
    public void CannotPeekRightOnEmptyQueue () {
        Empty.peekRight();
    }
 
    @Test(expected = EmptyQueueException.class)
    public void CannotPeekLeftOnEmptyQueue () {
        Empty.peekLeft();
    }
 
    @Test(expected = EmptyQueueException.class)
    public void CannotPopRightOnEmptyQueue () {
        Empty.popRight();
    }
 
    @Test(expected = EmptyQueueException.class)
    public void CannotPopLeftOnEmptyQueue () {
        Empty.popLeft();
    }
    
    @Test
    public void CanPeekLeftOnQueueWithOnlyRightBranch() {
        ImmutableQueue<A> q = Empty.pushRight(dasA);
        Assert.assertSame(dasA, q.peekLeft());
    }

    @Test
    public void CanPeekRightOnQueueWithOnlyLeftBranch() {
        ImmutableQueue<A> q = Empty.pushLeft(dasA);
        Assert.assertSame(dasA, q.peekRight());
    }
    
    @Test
    public void PushRightUntilFilloverIntoMiddle () {
        A[] as = new A[5];
        dasQ = Empty;
        for( int i = 0; i < as.length; i += 1) {
            as[i] = new A();
            dasQ = dasQ.pushRight(as[i]);
        }
        Assert.assertEquals(5, dasQ.length);
    }

    @Test
    public void PushLeftUntilFilloverIntoMiddle () {
        A[] as = new A[5];
        dasQ = Empty;
        for( int i = 0; i < as.length; i += 1) {
            as[i] = new A();
            dasQ = dasQ.pushLeft(as[i]);
        }
        Assert.assertEquals(5, dasQ.length);
    }

    @Test
    public void PopLeftFromRightFilledWithMidData () {
        ImmutableQueue<Integer> q = new ImmutableQueue<Integer>();
        for (int i = 0; i < 1000; i += 1) {
            q = q.pushRight(i);
        }
        for (int i = 0; i < 1000; i += 1) {
            Assert.assertEquals(i, (int)q.peekLeft());
            q = q.popLeft();
        }
    }

    @Test
    public void PopRightFromRightFilledWithMidData () {
        ImmutableQueue<Integer> q = new ImmutableQueue<Integer>();
        for (int i = 0; i < 1000; i += 1) {
            q = q.pushRight(i);
        }
        for (int i = 999; i >= 0; i -= 1) {
            Assert.assertEquals(i, (int)q.peekRight());
            q = q.popRight();
        }
    }
    @Test
    public void PopRightFromLeftFilledWithMidData () {
        ImmutableQueue<Integer> q = new ImmutableQueue<Integer>();
        for (int i = 0; i < 1000; i += 1) {
            q = q.pushLeft(i);
        }
        for (int i = 0; i < 1000; i += 1) {
            Assert.assertEquals(i, (int)q.peekRight());
            q = q.popRight();
        }
    }

    @Test
    public void PopLeftFromLeftFilledWithMidData () {
        ImmutableQueue<Integer> q = new ImmutableQueue<Integer>();
        for (int i = 0; i < 1000; i += 1) {
            q = q.pushLeft(i);
        }
        for (int i = 999; i >= 0; i -= 1) {
            Assert.assertEquals(i, (int)q.peekLeft());
            q = q.popLeft();
        }
    }
}
