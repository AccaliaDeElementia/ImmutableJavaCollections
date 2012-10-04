import java.util.*;

public final class ImmutableStack<E> implements ImmutableCollection<E> {
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
        return null;
    }
}
