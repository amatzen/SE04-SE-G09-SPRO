import java.util.*;

public class Heap<T> implements Queue<T> {
    private Comparator<T> comparator;

    private ArrayList<T> heap;

    public Heap(Comparator<T> comparator) {
        heap = new ArrayList<>();
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        for (T t : heap) {
            if (t.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return this.heap.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.heap.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return a;
    }

    @Override
    public boolean add(T t) {
        heapInsert(heap, t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        // Remove the first occurrence of o and heapify the heap
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i).equals(o)) {
                heap.set(i, heap.get(heap.size() - 1));
                heap.remove(heap.size() - 1);
                heapify(heap, i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return heap.contains(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            heapInsert(heap, t);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        heap.clear();
    }

    @Override
    public boolean offer(T t) {
        heapInsert(heap, t);
        return true;
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return extractMin(heap);
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return extractMin(heap);
    }

    @Override
    public T element() {
        T t = peek();
        if (t == null) {
            throw new NoSuchElementException();
        } else {
            return t;
        }
    }

    @Override
    public T peek() {
        if (heap.size() <= 0) {
            return null;
        }
        return heap.get(0);
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private void heapify(List<T> list, int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;

        if (l < list.size() && comparator.compare(list.get(l), list.get(i)) < 0) {
            smallest = l;
        }

        if (r < list.size() && comparator.compare(list.get(r), list.get(smallest)) < 0) {
            smallest = r;
        }

        if (smallest != i) {
            T temp = list.get(i);
            list.set(i, list.get(smallest));
            list.set(smallest, temp);
            heapify(list, smallest);
        }
    }

    private void heapInsert(List<T> list, T t) {
        list.add(t);
        int i = list.size() - 1;
        while (i > 0 && comparator.compare(list.get(i), list.get(parent(i))) < 0) {
            T temp = list.get(i);
            list.set(i, list.get(parent(i)));
            list.set(parent(i), temp);
            i = parent(i);
        }
    }

    private T extractMin(List<T> list) {
        T t = list.get(0);
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);
        heapify(list, 0);
        return t;
    }
}
