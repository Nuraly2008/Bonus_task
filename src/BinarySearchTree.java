
public class BinarySearchTree {
    private Node root;

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    public void insert(int value) {
        root = insertNode(root, value);
    }

    private Node insertNode(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insertNode(node.left, value);
        } else if (value > node.value) {
            node.right = insertNode(node.right, value);
        }
        return node;
    }

    public boolean search(int value) {
        Node current = root;
        while (current != null) {
            if (value == current.value) {
                return true;
            }
            current = value < current.value
                    ? current.left : current.right;
        }
        return false;
    }

    public void delete(int value) {
        root = deleteNode(root, value);
    }

    private Node deleteNode(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = deleteNode(node.left, value);
        } else if (value > node.value) {
            node.right = deleteNode(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.value = successor.value;
            node.right = deleteNode(node.right, successor.value);
        }
        return node;
    }
}
