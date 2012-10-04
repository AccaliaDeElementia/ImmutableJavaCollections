import java.util.*;

//Based off of Eric Lippert's C# Immutable Queue
// http://blogs.msdn.com/b/ericlippert/archive/2007/12/10/immutability-in-c-part-four-an-immutable-queue.aspx
public final class ImmutableStack<E> implements ImmutableCollection<E> {

    private final class ImmutableStackIterator<E> implements Iterator<E> {
        private ImmutableStack<E> collection;
        private ImmutableStackIterator(ImmutableStack<E> c) {
            collection = c;
        }
        public boolean hasNext() {
            return ! collection.isEmpty();
        }
        public E next() {
            E e = collection.peek();
            collection = collection.pop();
            return e;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private final ImmutableStack<E> tail;
    private final E data;
    private final int length;
    public ImmutableStack () {
        tail = null;
        data = null;
        length = 0;
    }
    public ImmutableStack (E initial) {
        tail = new ImmutableStack<E>();
        data = initial;
        length = 1;
    }
    public ImmutableStack (Iterable<? extends E> initial) {
        ImmutableStack<E> tmp = new ImmutableStack<E>();
        tmp = tmp.addAll(initial);
        data = tmp.peek();
        length = tmp.length;
        tail = tmp.pop();
    }
    private ImmutableStack (ImmutableStack<E> c, E e) {
        tail = c;
        data = e;
        length = c.size() + 1;
    }
    public ImmutableStack<E> push (E e) {
        return new ImmutableStack<E> (this, e);
    }

    public ImmutableStack<E> pop () {
        if (null == tail) {
            throw new EmptyStackException();
        }
        return tail;
    }

    public E peek () {
        if (null == tail) {
            throw new EmptyStackException();
        }
        return data;
    }

    public ImmutableStack<E> add (E e) {
        return push(e);
    }

    public ImmutableStack<E> addAll (Iterable<? extends E> c) {
        ImmutableStack<E> val = this;
        for(E e: c) {
            val = val.push(e);
        }
        return val;
    }

    public boolean isEmpty() {
        return null == tail;
    }

    public int size() {
        return length;
    }

    public Iterator<E> iterator() {
        return new ImmutableStackIterator<E>(this);
    }
    public Object[] toArray() {
        return toArray(new Object[length]);
    }

    //This is annoying, but I can't constrain T to be a supertype of E due to
    //  the way Java's generics work.
    @SuppressWarnings("unchecked") 
    public <T> T[] toArray(T[] a) {
        if (a.length < length) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), length);
        }
        int i = 0;
        for (E e : this) {
            a[i] = (T) e;
            i += 1;
        }
        return a;
    }

}
