import java.util.Iterator;

public abstract class AbstractImmutableStack<E> implements ImmutableCollection<E> {
    private final class ImmutableStackIterator<E> implements Iterator<E> {
        private AbstractImmutableStack<E> collection;
        ImmutableStackIterator (AbstractImmutableStack<E> c) {
            collection = c;
        }

        public boolean hasNext () {
            return ! collection.isEmpty();
        }

        public E next () {
            E val = collection.peek();
            collection = collection.pop();
            return val;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public abstract AbstractImmutableStack<E> push (E e);

    public abstract AbstractImmutableStack<E> pop ();

    public abstract E peek ();

    public Iterator<E> iterator () {
        return new ImmutableStackIterator<E>(this);
    }

    public int hashCode () {
        return ((Object)this).hashCode();
    }

    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    @SuppressWarnings("unchecked") // I know what I'm doing... I think...
    public <T> T[] toArray(T[] a) {
        if (a.length < size()) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size());
        }
        int i = 0;
        for (E v: this) {
            a[i] = (T) v;
            i += 1;
        }
        return a;
    }
}
