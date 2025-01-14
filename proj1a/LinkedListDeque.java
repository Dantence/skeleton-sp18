/**
 * @author Dantence
 * @description:
 * @date 2023/1/27
 */

/**
 * @projectName: proj1a
 * @package: PACKAGE_NAME
 * @className: LinkedListDeque
 * @author: Dantence
 * @description: TODO
 * @date: 2023/1/27 14:25
 * @version: 1.0
 */
public class LinkedListDeque<T> {

    private class Node {
        Node(T item) {
            this.item = item;
        }

        Node() {

        }

        private T item;
        private Node next;
        private Node prev;
    }

    private Node head;

    private Node tail;

    private int size;

    public LinkedListDeque() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    public void addFirst(T item) {
        Node node = new Node(item);
        node.prev = head;
        head.next.prev = node;
        node.next = head.next;
        head.next = node;
        size += 1;
    }

    public void addLast(T item) {
        Node node = new Node(item);
        node.next = tail;
        tail.prev.next = node;
        node.prev = tail.prev;
        tail.prev = node;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        Node p = head.next;
        while (p != tail) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if (size > 0) {
            Node removedNode = head.next;
            head.next.next.prev = head;
            head.next = head.next.next;
            removedNode.prev = null;
            removedNode.next = null;
            size -= 1;
            return removedNode.item;
        }
        return null;
    }

    public T removeLast() {
        if (size > 0) {
            Node removedNode = tail.prev;
            tail.prev.prev.next = tail;
            tail.prev = tail.prev.prev;
            removedNode.prev = null;
            removedNode.next = null;
            size -= 1;
            return removedNode.item;
        }
        return null;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node p = head.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private T traverseRecursive(Node head, int now, int index) {
        if (now == index) {
            return head.next.item;
        }
        return traverseRecursive(head.next, now + 1, index);
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return traverseRecursive(head, 0, index);
    }
}
