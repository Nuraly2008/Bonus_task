
import java.util.Random;

public class Main {
    private static final int[] SIZES = {100, 1000, 10000};
    private static final int RUNS = 10;

    public static void main(String[] args) {
        System.out.printf("%-8s %-12s %14s %14s %14s%n",
                "Elements", "Structure", "Insert (ms)",
                "Search (ms)", "Delete (ms)");

        for (int n : SIZES) {
            int[] values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = i;
            }
            shuffle(values, new Random(42 + n));

            int[] searchOrder = values.clone();
            int[] deleteOrder = values.clone();
            shuffle(searchOrder, new Random(123 + n));
            shuffle(deleteOrder, new Random(456 + n));

            for (int warmup = 0; warmup < 3; warmup++) {
                testBST(values, searchOrder, deleteOrder);
                testHashTable(values, searchOrder, deleteOrder);
            }

            long[] bstTotal = new long[3];
            long[] hashTotal = new long[3];

            for (int run = 0; run < RUNS; run++) {
                long[] bst;
                long[] hash;

                if (run % 2 == 0) {
                    bst = testBST(values, searchOrder, deleteOrder);
                    hash = testHashTable(values, searchOrder, deleteOrder);
                } else {
                    hash = testHashTable(values, searchOrder, deleteOrder);
                    bst = testBST(values, searchOrder, deleteOrder);
                }

                for (int i = 0; i < 3; i++) {
                    bstTotal[i] += bst[i];
                    hashTotal[i] += hash[i];
                }
            }

            printTimes(n, "BST", bstTotal);
            printTimes(n, "Hash Table", hashTotal);
            System.out.println();
        }
    }

    private static long[] testBST(
            int[] values, int[] searchOrder, int[] deleteOrder) {

        BinarySearchTree tree = new BinarySearchTree();

        long start = System.nanoTime();
        for (int value : values) {
            tree.insert(value);
        }
        long insertTime = System.nanoTime() - start;

        start = System.nanoTime();
        int found = 0;
        for (int value : searchOrder) {
            if (tree.search(value)) {
                found++;
            }
        }
        long searchTime = System.nanoTime() - start;

        if (found != values.length) {
            throw new IllegalStateException("BST search failed");
        }

        start = System.nanoTime();
        for (int value : deleteOrder) {
            tree.delete(value);
        }
        long deleteTime = System.nanoTime() - start;

        if (tree.search(values[0])) {
            throw new IllegalStateException("BST delete failed");
        }

        return new long[]{insertTime, searchTime, deleteTime};
    }

    private static long[] testHashTable(
            int[] values, int[] searchOrder, int[] deleteOrder) {

        HashTable table = new HashTable();

        long start = System.nanoTime();
        for (int value : values) {
            table.insert(value);
        }
        long insertTime = System.nanoTime() - start;

        start = System.nanoTime();
        int found = 0;
        for (int value : searchOrder) {
            if (table.search(value)) {
                found++;
            }
        }
        long searchTime = System.nanoTime() - start;

        if (found != values.length) {
            throw new IllegalStateException(
                    "Hash Table search failed");
        }

        start = System.nanoTime();
        for (int value : deleteOrder) {
            table.delete(value);
        }
        long deleteTime = System.nanoTime() - start;

        if (table.search(values[0])) {
            throw new IllegalStateException(
                    "Hash Table delete failed");
        }

        return new long[]{insertTime, searchTime, deleteTime};
    }

    private static void shuffle(int[] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static void printTimes(
            int n, String structure, long[] total) {

        double factor = RUNS * 1_000_000.0;

        System.out.printf(
                "%-8d %-12s %14.4f %14.4f %14.4f%n",
                n, structure,
                total[0] / factor,
                total[1] / factor,
                total[2] / factor);
    }
}

