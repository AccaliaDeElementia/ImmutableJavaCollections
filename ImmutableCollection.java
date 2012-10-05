import java.util.Iterator;
public interface ImmutableCollection<E> extends Iterable<E> {
    public int hashCode();
    public boolean isEmpty();
    public int size();
    //public Object[] toArray();
    //public <T> T[] toArray(T[] a);
    //public <T extends E> ImmutableCollection<T> add(T t);
    //public ImmutableCollection<E> addAll(Iterable<? extends E> e);
}
