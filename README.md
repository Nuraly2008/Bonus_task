# Binary Search Tree vs Hash Table: Performance Comparison

## 1. Introduction

In this assignment, I implemented a Binary Search Tree (BST) and a Hash Table in Java and compared their performance.

The main goal was to see how fast both data structures work when inserting, searching for, and deleting elements.

I tested them with 100, 1,000, and 10,000 integers. I also compared their time complexity and tried to understand why one structure can be faster than the other.

## 2. Project Structure

The project contains these files:

- `src/BinarySearchTree.java` – implementation of the Binary Search Tree.
- `src/HashTable.java` – implementation of the Hash Table.
- `src/Main.java` – runs the performance tests.
- `results/benchmark-output.txt` – stores the test results.
- `README.md` – explanation of the project and comparison results.

I implemented both data structures myself using nodes and arrays instead of Java's built-in TreeSet or HashSet.

## 3. How the Data Structures Work

### Binary Search Tree (BST)

A Binary Search Tree stores values in nodes. Each node can have a left child and a right child.

Values smaller than the current node go to the left, while larger values go to the right.

I implemented three main operations:

- **Insert:** Adds a new value to the correct position in the tree.
- **Search:** Looks for a value by moving left or right through the nodes.
- **Delete:** Removes a value from the tree. If the node has two children, the program uses its in-order successor to replace it.

My BST does not allow duplicate values.

One important thing about this implementation is that the tree is not balanced automatically. Because of this, the order in which elements are inserted can affect its performance.

For example, inserting sorted numbers can make the tree very deep, which makes operations slower.

### Hash Table

The Hash Table stores integers in an array of buckets.

To find the correct bucket, it calculates an index using a hash function.

I used separate chaining to handle collisions. This means that if multiple values have the same bucket index, they are stored in a linked list.

The main operations are:

- **Insert:** Calculates the bucket index and adds a value if it does not already exist.
- **Search:** Finds the correct bucket and checks whether the value exists.
- **Delete:** Finds and removes a value from its bucket.

The Hash Table starts with 16 buckets. When the load factor would go above 0.75, the number of buckets is doubled.

This helps reduce collisions when more elements are added.

Like the BST, my Hash Table does not store duplicate values.

## 4. Time Complexity Analysis

I compared the best, average, and worst cases for each operation.

| Data Structure | Operation | Best Case | Average Case | Worst Case |
|---|---|---|---|---|
| BST | Insert | O(1) | O(log n) | O(n) |
| BST | Search | O(1) | O(log n) | O(n) |
| BST | Delete | O(1) | O(log n) | O(n) |
| Hash Table | Insert | O(1) | O(1) amortized | O(n) |
| Hash Table | Search | O(1) | O(1) | O(n) |
| Hash Table | Delete | O(1) | O(1) | O(n) |

Here, n represents the number of stored elements.

### BST Complexity

The performance of the BST depends on its height.

If the tree has a good shape, searching, inserting, and deleting usually take O(log n) time.

However, if the tree becomes unbalanced, the worst-case complexity can be O(n).

For example, searching for the root value takes O(1), but finding a value at the bottom of a very deep tree takes much longer.

The average O(log n) case assumes a reasonably random insertion order.

### Hash Table Complexity

The Hash Table is usually faster for finding individual values because it uses a hash function to locate the correct bucket.

Insertion, searching, and deletion normally take O(1) time on average when collisions are limited.

However, if many values are stored in the same bucket, the operations can become O(n).

Resizing can also take O(n) for a single insertion, but insertion is still O(1) amortized on average.

## 5. Performance Testing

I tested both structures using three different input sizes:

- 100 elements
- 1,000 elements
- 10,000 elements

I used the same values for both structures to make the comparison fair.

First, the program creates unique integers and shuffles them. Then it inserts all values, searches for them, and finally deletes them.

I used `System.nanoTime()` to measure the execution time.

The program runs three warm-up tests and then ten measured tests. The final result is the average of the measured runs.

The results are displayed in milliseconds.

Each time in the table represents the total time for all operations of that type, not the time for just one element.

I ran the program in IntelliJ IDEA on macOS using Java 21.

### Test Results

| Elements | Structure | Insert (ms) | Search (ms) | Delete (ms) |
|---:|---|---:|---:|---:|
| 100 | BST | 0.0163 | 0.0099 | 0.0103 |
| 100 | Hash Table | 0.0336 | 0.0039 | 0.0089 |
| 1,000 | BST | 0.1129 | 0.0936 | 0.1039 |
| 1,000 | Hash Table | 0.1352 | 0.0348 | 0.0425 |
| 10,000 | BST | 1.4772 | 0.9871 | 1.3761 |
| 10,000 | Hash Table | 0.4138 | 0.0978 | 0.0978 |

The program finished successfully without errors.

The benchmark output is also stored in `results/benchmark-output.txt`.

## 6. Comparison of Results

### Test with 100 Elements

With 100 elements, both structures worked very fast.

The BST was faster at insertion. It took 0.0163 ms, while the Hash Table took 0.0336 ms.

However, the Hash Table performed better in searching and deletion.

At this size, all the times were very small, so the difference was not very important in practice.

### Test with 1,000 Elements

When I increased the number of elements to 1,000, I started to notice a bigger difference.

The BST was still slightly faster at insertion, with 0.1129 ms compared to 0.1352 ms for the Hash Table.

But searching was faster in the Hash Table. It took 0.0348 ms, while the BST needed 0.0936 ms.

Deletion was also faster in the Hash Table, taking 0.0425 ms compared to 0.1039 ms.

### Test with 10,000 Elements

The difference became much clearer with 10,000 elements.

The BST needed 1.4772 ms for insertion, while the Hash Table needed only 0.4138 ms.

Searching took 0.9871 ms in the BST and 0.0978 ms in the Hash Table.

Deletion also showed a big difference: 1.3761 ms for the BST and 0.0978 ms for the Hash Table.

For this input size, the Hash Table was faster in all three operations.

### Why Are the Results Different?

The main reason is how these data structures find their elements.

The BST needs to compare values and move through nodes. If the tree becomes deeper, it takes more steps to reach the correct value.

The Hash Table calculates an index and usually goes directly to the correct bucket. This means it often needs fewer steps.

Another reason is the input data. In my tests, I used integers from 0 to n - 1, which work well with the hash function used in this project.

For these values, the final Hash Table has very few collision problems.

However, Hash Tables are not always faster. If there are many collisions, their performance can decrease.

The results can also change depending on the computer, Java runtime, and other programs running at the same time.

## 7. Conclusion

After comparing both data structures, I noticed that the Hash Table was generally faster, especially when the number of elements increased.

For 100 and 1,000 elements, the BST was slightly faster at insertion. But when I tested 10,000 elements, the Hash Table performed better in insertion, searching, and deletion.

I think the biggest advantage of a Hash Table is that it can find a specific value quickly without going through many nodes.

On the other hand, a BST is useful when values need to stay in sorted order. It can also be useful for operations such as finding values within a range.

The disadvantage of my BST implementation is that it does not balance itself. If elements are inserted in a bad order, performance can become much worse.

Overall, this assignment helped me understand how the choice of a data structure affects program performance.

I also learned that time complexity is important, but testing with real data is useful because actual execution times depend on more than just Big O notation.

## 8. How to Run the Project

1. Open the project in IntelliJ IDEA.
2. Make sure Java 21 is selected as the project SDK.
3. Open `src/Main.java`.
4. Click the Run button next to the main method.
5. Check the console output.

The program will display the insertion, search, and deletion times for BST and Hash Table using 100, 1,000, and 10,000 elements.