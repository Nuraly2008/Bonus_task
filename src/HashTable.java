
public class HashTable {
    private static class Node {
        int value;
        Node next;

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node[] buckets = new Node[16];
    private int size = 0;

    public void insert(int value) {
        if (search(value)) {
            return;
        }
        if ((size + 1) * 4 > buckets.length * 3) {
            resize();
        }
        int index = Math.floorMod(value, buckets.length);
        buckets[index] = new Node(value, buckets[index]);
        size++;
    }

    public boolean search(int value) {
        int index = Math.floorMod(value, buckets.length);
        Node current = buckets[index];
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void delete(int value) {
        int index = Math.floorMod(value, buckets.length);
        Node current = buckets[index];
        Node previous = null;

        while (current != null) {
            if (current.value == value) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    private void resize() {
        Node[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];

        for (Node head : oldBuckets) {
            Node current = head;
            while (current != null) {
                Node next = current.next;
                int index = Math.floorMod(
                        current.value, buckets.length);
                current.next = buckets[index];
                buckets[index] = current;
                current = next;
            }
        }
    }
}
