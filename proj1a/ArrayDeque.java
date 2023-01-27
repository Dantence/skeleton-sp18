/**
 * @author Dantence
 * @description:
 * @date 2023/1/27
 */

/**
 * @projectName: proj1a
 * @package: PACKAGE_NAME
 * @className: ArrayDeque
 * @author: Dantence
 * @description: TODO
 * @date: 2023/1/27 15:24
 * @version: 1.0
 */
public class ArrayDeque<T> {

    private T[] arr;
    private int size;
    private int capacity;
    private int head;
    private int tail;
    private final int threshold = 8;
    private final double lowestUsingRate = 0.25;

    public ArrayDeque() {
        capacity = threshold;
        arr = (T[]) new Object[capacity];
        size = 0;
        head = capacity / 2 - 1;
        tail = capacity / 2;
    }

    private void resizeLarger() {
        T[] newArr = (T[]) new Object[capacity * 2];
        int index = 1;

        int i;
        if (head == capacity - 1) {
            i = 0;
        } else {
            i = head + 1;
        }
        while (i != tail) {
            newArr[index] = arr[i];
            i += 1;
            index += 1;
            if (i >= capacity) {
                i = 0;
            }
        }
        arr = newArr;
        head = 0;
        tail = size + 1;
        capacity *= 2;
    }

    private void resizeSmaller() {
        T[] newArr = (T[]) new Object[capacity / 2];
        int index = 1;

        int i;
        if (head == capacity - 1) {
            i = 0;
        } else {
            i = head + 1;
        }
        while (i != tail) {
            newArr[index] = arr[i];
            i += 1;
            index += 1;
            if (i >= capacity) {
                i = 0;
            }
        }
        arr = newArr;
        head = 0;
        tail = size + 1;
        capacity /= 2;
    }

    public void addFirst(T item) {
        if (size >= capacity - 1) {
            resizeLarger();
        }
        arr[head] = item;
        head -= 1;
        if (head < 0) {
            head = capacity - 1;
        }
        size += 1;
    }

    public void addLast(T item) {
        if (size >= capacity) {
            resizeLarger();
        }
        arr[tail] = item;
        tail += 1;
        if (tail >= capacity) {
            tail = 0;
        }
        size += 1;

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int i;
        if (head == capacity - 1) {
            i = 0;
        } else {
            i = head + 1;
        }
        while (i != tail) {
            i += 1;
            if (i >= capacity) {
                i = 0;
            }
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        head += 1;
        if (head >= capacity) {
            head = 0;
        }
        T item = arr[head];
        size -= 1;
        if (size / (double) capacity <= lowestUsingRate && capacity > threshold) {
            resizeSmaller();
        }
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }

        tail -= 1;
        if (tail <= 0) {
            tail = capacity - 1;
        }
        T item = arr[tail];
        size -= 1;
        if (size / (double) capacity <= lowestUsingRate && capacity > threshold) {
            resizeSmaller();
        }
        return item;
    }

    public T get(int index) {
        int cnt = 0;

        int i;
        if (head == capacity - 1) {
            i = 0;
        } else {
            i = head + 1;
        }
        while (i != tail) {
            if (cnt == index) {
                return arr[i];
            }
            cnt += 1;
            i += 1;
            if (i >= capacity) {
                i = 0;
            }
        }
        return null;
    }
}
