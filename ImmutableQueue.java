import java.util.*;

public final class ImmutableQueue<E> implements ImmutableCollection<E> {

    private final Quelette<E> right;
    private final ImmutableQueue<Quelette<E>> mid;
    private final Quelette<E> left;
    public final int length;
    public final boolean empty;

    public ImmutableQueue () {
        right = new Zero();
        mid = null;
        left = new Zero();
        length = 0;
        empty = true;
    }
    private ImmutableQueue (Quelette<E> right, ImmutableQueue<Quelette<E>> mid, Quelette<E> left, int length) {
        this.right = right;
        this.mid = mid;
        this.left = left;
        this.length = length;
        empty = false;
    }

    public int size() { return length; }
    
    public boolean isEmpty() { return empty; }

    public ImmutableQueue<E> pushRight(E e) {
        Quelette<E> right = this.right;
        ImmutableQueue<Quelette<E>> mid = this.mid;
        Quelette<E> lleft = this.left;
        if (right.isFull()) {
            if (null == mid) {
                mid = new ImmutableQueue<Quelette<E>>();
            }
            mid = mid.pushRight(right);
            right = new One(e);
        } else {
            right = right.pushRight(e);
        }
        return new ImmutableQueue<E>(right, mid, left, length + 1);
    }

    public ImmutableQueue<E> pushLeft(E e) {
        Quelette<E> right = this.right;
        ImmutableQueue<Quelette<E>> mid = this.mid;
        Quelette<E> left = this.left;
        if (left.isFull()) {
            if (null == mid) {
                mid = new ImmutableQueue<Quelette<E>>();
            }
            mid = mid.pushLeft(left);
            left = new One(e);
        } else {
            left = left.pushLeft(e);
        }
        return new ImmutableQueue<E>(right, mid, left, length + 1);
    }

    public E peekRight() {
        if (empty) {
            throw new EmptyQueueException();
        }
        if (right.size() == 0) {
            if (null != mid) {
                return mid.peekRight().peekRight();
            }
            return left.peekRight();
        } else {
            return right.peekRight();
        }
    }

    public E peekLeft() {
        if (empty) {
            throw new EmptyQueueException();
        }
        if (left.size() == 0) {
            if (null != mid) {
                return mid.peekLeft().peekLeft();
            }
            return right.peekLeft();
        } else {
            return left.peekLeft();
        }
    }

    public ImmutableQueue<E> popRight() {
        if (empty) {
            throw new EmptyQueueException();
        }
        Quelette<E> right = this.right;
        ImmutableQueue<Quelette<E>> mid = this.mid;
        Quelette<E> left = this.left;
        if (right.size() == 0) {
            if (null == mid) {
                left = left.popRight();
            } else {
                right = mid.peekRight();
                mid = mid.popRight();
                right = right.popRight();
                if (mid.size() == 0) {
                    mid = null;
                }
            }
        } else {
            right = right.popRight();
        }
        return new ImmutableQueue<E> (right, mid, left, length - 1);
     }

    public ImmutableQueue<E> popLeft() {
        if (empty) {
            throw new EmptyQueueException();
        }
        Quelette<E> right = this.right;
        ImmutableQueue<Quelette<E>> mid = this.mid;
        Quelette<E> left = this.left;
        if (left.size() == 0) {
            if (null == mid) {
                right = right.popLeft();
            } else {
                left = mid.peekLeft();
                mid = mid.popLeft();
                left = left.popLeft();
                if (mid.size() == 0) {
                    mid = null;
                }
            }
        } else {
            left = left.popLeft();
        }
        return new ImmutableQueue<E> (right, mid, left, length - 1);
     }

    public Iterator<E> iterator() { return new InnutableQueueIterator; }

    private final class ImmutableQueueIterator implements Iterator<E> {
        private ImmutableQueue<E> queue
        ImmutableQueueIterator(InnutableQueue<E> queue){
            this.queue = queue;
        }
        public boolean hasNext() { return ! queue.empty }
        public E next() {
            E e = queue.peekRight();
            queue = queue.popRight();
            return e;
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }

    private interface Quelette<E> {
        public int size ();
        public boolean isFull();
        public E peekLeft();
        public E peekRight();
        public Quelette<E> pushLeft(E e);
        public Quelette<E> pushRight(E e);
        public Quelette<E> popLeft();
        public Quelette<E> popRight();
    }
    private final class Zero implements Quelette<E> {
        public Zero () {}
        public int size() { return 0; }
        public boolean isFull() { return false; }
        public E peekLeft() { throw new EmptyQueueException(); }
        public E peekRight() { throw new EmptyQueueException(); }
        public Quelette<E> pushLeft(E e) { return new One(e); }
        public Quelette<E> pushRight(E e) { return new One(e); }
        public Quelette<E> popLeft() { throw new EmptyQueueException(); }
        public Quelette<E> popRight() { throw new EmptyQueueException(); }
    }
    private final class One implements Quelette<E> {
        private final E v1;
        public One (E v1) { this.v1 = v1; }
        public int size () { return 1; }
        public boolean isFull() { return false; }
        public E peekRight() { return v1; }
        public E peekLeft() { return v1; }
        public Quelette<E> pushRight(E e) { return new Two(e, v1); }
        public Quelette<E> pushLeft(E e) { return new Two(v1, e); }
        public Quelette<E> popRight() { return new Zero(); }
        public Quelette<E> popLeft() { return new Zero(); }
    }
    private final class Two implements Quelette<E> {
        private final E v1;
        private final E v2;
        public Two(E v1, E v2) { 
            this.v1 = v1;
            this.v2 = v2;
        }
        public int size () { return 2; }
        public boolean isFull() { return false; }
        public E peekRight() { return v1; }
        public E peekLeft() { return v2; }
        public Quelette<E> pushRight(E e) { return new Three(e, v1, v2); }
        public Quelette<E> pushLeft(E e) { return new Three(v1, v2, e); }
        public Quelette<E> popRight() { return new One(v2); }
        public Quelette<E> popLeft() { return new One(v1); }
    }
    private final class Three implements Quelette<E> {
        private final E v1;
        private final E v2;
        private final E v3;
        public Three(E v1, E v2, E v3) { 
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
        }
        public int size () { return 3; }
        public boolean isFull() { return false; }
        public E peekRight() { return v1; }
        public E peekLeft() { return v3; }
        public Quelette<E> pushRight(E e) { return new Four(e, v1, v2, v3); }
        public Quelette<E> pushLeft(E e) { return new Four(v1, v2, v3, e); }
        public Quelette<E> popRight() { return new Two(v2, v3); }
        public Quelette<E> popLeft() { return new Two(v1, v2); }
    }
    private final class Four implements Quelette<E> {
        private final E v1;
        private final E v2;
        private final E v3;
        private final E v4;
        public Four(E v1, E v2, E v3, E v4) { 
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.v4 = v4;
        }
        public int size () { return 4; }
        public boolean isFull() { return true; }
        public E peekRight() { return v1; }
        public E peekLeft() { return v4; }
        public Quelette<E> pushRight(E e) { throw new EmptyQueueException(); }
        public Quelette<E> pushLeft(E e) { throw new EmptyQueueException(); }
        public Quelette<E> popRight() { return new Three(v2, v3, v4); }
        public Quelette<E> popLeft() { return new Three(v1, v2, v3); }
    }
}
