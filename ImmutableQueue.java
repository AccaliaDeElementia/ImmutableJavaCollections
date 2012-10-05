import java.util.*;

/**
* An implementation of an Immutable Queue
*
* @author Accalia de Elementia <accalia.de.elementia@gmail.com>
*
* @version 0.5.0
*/
public final class ImmutableQueue<E> implements ImmutableCollection<E> {

    private final Quelette<E> right;
    private final ImmutableQueue<Quelette<E>> mid;
    private final Quelette<E> left;

    /**
    * The size of the queue.
    *
    * @since 0.5.0
    */
    public final int length;

    /**
    * True if queue contains no data, false otherwise.
    * 
    * @since 0.5.0
    */
    public final boolean empty;

    /**
    * Create a new, empty, ImmutableQueue.
    * 
    * @since 0.5.0
    */
    public ImmutableQueue () {
        right = new Zero();
        mid = null;
        left = new Zero();
        length = 0;
        empty = true;
    }

    /**
    * Create a new ImmutableQueue with data.
    *
    * *PRIVATE* constructor.
    *
    * @param right The right Quelette
    * @param mid The middle ImmutableQueue of Quelettes
    * @param left The left Quelette
    * @param length The size of the queue
    *
    * @since 0.5.0
    */
    private ImmutableQueue (Quelette<E> right, ImmutableQueue<Quelette<E>> mid, Quelette<E> left, int length) {
        //TODO: Get better way of determining size of the queue
        this.right = right;
        this.mid = mid;
        this.left = left;
        this.length = length;
        empty = false;
    }

    /**
    * Returns the size of the queue.
    *
    * @see #length
    *
    * @since 0.5.0
    */
    public int size() { return length; }
    
    /**
    * Returns true if the queue contains no data, false otherwise.
    *
    * @see #empty
    * 
    * @since 0.5.0
    */
    public boolean isEmpty() { return empty; }

    /**
    * Push a data value to the right end of the queue.
    *
    * @param e Data value to push
    * @return A new ImmutableQueue with updated state
    *
    * @since 0.1.0
    */
    public ImmutableQueue<E> pushRight(E e) {
        Quelette<E> right = this.right;
        ImmutableQueue<Quelette<E>> mid = this.mid;
        Quelette<E> lleft = this.left;
        if (right.isFull()) {
            if (null == mid) {
                //TODO: make sure that the left side is also full before pushing down into the mid
                mid = new ImmutableQueue<Quelette<E>>();
            }
            mid = mid.pushRight(right);
            right = new One(e);
        } else {
            right = right.pushRight(e);
        }
        return new ImmutableQueue<E>(right, mid, left, length + 1);
    }

    /**
    * Push a data value to the left end of the queue.
    *
    * @param e Data value to push
    * @return A new ImmutableQueue with updated state
    *
    * @since 0.1.0
    */
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

    /**
    * Alias for pushLeft
    *
    * @param e Data value to push
    * @return A new ImmutableQueue with updated state
    *
    * @see #pushLeft
    *
    * @since 0.5.0
    */
    public ImmutableQueue<E> push(E e) { return pushLeft(e); }

    /**
    * Alias for pushLeft
    *
    * @param e Data value to push
    * @return A new ImmutableQueue with updated state
    *
    * @see #pushLeft
    *
    * @since 0.5.0
    */
    public ImmutableQueue<E> add(E e) { return pushLeft(e); }

    /**
    * Look at the first element on the right.
    *
    * @return The rightmost data value
    * @throws EmptyQueueException if there is no data stored
    *
    * @since 0.2.0
    */
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

    
    /**
    * Look at the first element on the left.
    *
    * @return The leftmost data value
    * @throws EmptyQueueException if there is no data stored
    *
    * @since 0.2.0
    */
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
    
    /**
    * Alias for peekRight..
    *
    * @return The rightmost data value
    * @throws EmptyQueueException if there is no data stored
    *
    * @see #peekRight
    *
    * @since 0.5.0
    */
    public E peek() { return peekRight(); }

    /**
    * Remove the rightmost element.
    *
    * @return a new ImmutableQueue with updated state
    * @throws EmptyQueueException if there is no data stored
    *
    * @since 0.3.0
    */
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

    /**
    * Remove the leftmost element.
    *
    * @return a new ImmutableQueue with updated state
    * @throws EmptyQueueException if there is no data stored
    *
    * @since 0.3.0
    */
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

    /**
    * Alias for popRight.
    *
    * @return a new ImmutableQueue with updated state
    * @throws EmptyQueueException if there is no data stored
    *
    * @see #popRight
    *
    * @since 0.3.0
    */
    public ImmutableQueue<E> pop() { return popRight(); }

    /**
    * Add a lot of items to the queue.
    *
    * Inserts in iteration order through calls to {@link #add}
    *
    * @param c An iterable object containingobjects to add.
    * @return A new ImmutableQueue with updated state
    *
    * @see #add
    *
    * @since 0.5.0
    */
    public ImmutableQueue<E> addAll (Iterable<? extends E> c) {
        ImmutableQueue<E> q = this;
        for(E e: c) {
            q = q.add(e);
        }
        return q;
    }

    /**
    * Create an iterator to iterate over the items in the collection.
    *
    * @return an ImmutableQueueIterator
    *
    * @since 0.4.0
    */
    public Iterator<E> iterator() { return new ImmutableQueueIterator(this); }

    /**
    * An iterator for ImmutableQueues
    *
    * @author Accalia de Elementia <accalia.de.elementia@gmail.com>
    * @version 1.0.0
    */
    private final class ImmutableQueueIterator implements Iterator<E> {
        private ImmutableQueue<E> queue;

        /**
        * Store the collection to iterate.
        * 
        * @since 1.0.0
        */
        ImmutableQueueIterator(ImmutableQueue<E> queue){
            this.queue = queue;
        }

        /**
        * @return true if the queue still has data, false otherwise.
        *
        * @since 1.0.0
        */
        public boolean hasNext() { return ! queue.empty; }

        /**
        * @return the next data value in the queue.
        * @throws EmptyQueueException if the queue has no data
        *
        * @since 1.0.0
        */
        public E next() {
            E e = queue.peek();
            queue = queue.pop();
            return e;
        }
        /**
        * Not supported.
        *
        * @throws UnsupportedOperationException unconditionally
        *
        * @since 1.0.0
        */
        public void remove() { throw new UnsupportedOperationException(); }
    }

    /**
    * Interface for finite sized quelettes to be used to build a big queue.
    *
    * @author Accalia de Elementia <accalia.de.elementia@gmail.com>
    * @version 1.0.0
    */
    private interface Quelette<E> {
        /**
        * @return the capacity of the Quelette.
        *
        * @since 1.0.0
        */
        public int size ();
        /**
        * @return True if the Quelette cannot be pushed to.
        *
        * @since 1.0.0
        */
        public boolean isFull();
        /**
        * Look at the left value of the Quelette.
        *
        * @return The leftmost value in the quelette.
        * @throws EmptyQueueException if has no data
        *
        * @since 1.0.0
        */
        public E peekLeft();
        /**
        * Look at the right value of the Quelette
        *
        * @return The rightmost value in the Quelette
        * @throws EmptyQueueException if has no data
        *
        * @since 1.0.0
        */
        public E peekRight();
        /**
        * Push value to the left of the Quelette.
        *
        * @return A new Quelette with updated state.
        * @throws EmptyQueueException if Quelette is full. This should be changed to better exception.
        *
        * @since 1.0.0
        */
        public Quelette<E> pushLeft(E e);
        /**
        * Push falue to the right of the queue.
        *
        * @return A new Quelette with updated state.
        * @throws EmptyQueueException if Quelette is full. This should be changed to better exception.
        *
        * @since 1.0.0
        */
        public Quelette<E> pushRight(E e);
        /**
        * Remove leftmost value of the Quelette
        *
        * @return A new quelette with updated state.
        * @throws EmptyQueueException if has no data.
        *
        * @since 1.0.0
        */
        public Quelette<E> popLeft();
        /**
        * Remove rightmost value of the Quelette.
        *
        * @return A new Quelette with updated state.
        * @throws EmptyQueueException if has no ata
        *
        * @since 1.0.0
        */
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
