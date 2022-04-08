package dk.sdu.mmmi.swe.gtg.common.rb;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

public class RedBlacktree<T> extends BaseBinaryTree<T> implements Iterable<T> {

    private Comparator<T> comparator;

    public RedBlacktree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private void rotateRight(Node<T> node) {
        Node<T> parent = node.getParent();
        Node<T> left = node.getLeft();

        node.setLeft(left.getRight());

        if (left.getRight() != null) {
            left.getRight().setParent(node);
        }

        left.setRight(node);
        node.setParent(left);

        replaceParentsChild(parent, node, left);
    }

    private void replaceParentsChild(Node<T> parent, Node<T> oldChild, Node<T> newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.getLeft() == oldChild) {
            parent.setLeft(newChild);
        } else if (parent.getRight() == oldChild) {
            parent.setRight(newChild);
        } else {
            throw new IllegalStateException("Child not found");
        }

        if (newChild != null) {
            newChild.setParent(parent);
        }
    }

    private void rotateLeft(Node<T> node) {
        Node<T> parent = node.getParent();
        Node<T> right = node.getRight();

        node.setRight(right.getLeft());

        if (right.getLeft() != null) {
            right.getLeft().setParent(node);
        }

        right.setLeft(node);
        node.setParent(right);

        replaceParentsChild(parent, node, right);
    }

    public void insert(T value) {
        Node<T> node = new Node<T>(value);
        insert(node);
    }

    public void insert(Node<T> node) {
        Node<T> current = root;
        Node<T> parent = null;

        while (current != null) {
            parent = current;

            if (comparator.compare(node.getData(), current.getData()) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        node.setParent(parent);

        if (parent == null) {
            setRoot(node);
        } else if (comparator.compare(node.getData(), parent.getData()) < 0) {
            parent.setLeft(node);
        } else {
            parent.setRight(node);
        }

        insertCase1(node);
    }

    private void insertCase1(Node<T> node) {
        if (node.getParent() == null) {
            node.setColor(Color.BLACK);
        } else {
            insertCase2(node);
        }
    }

    private void insertCase2(Node<T> node) {
        if (node.getParent().getColor() == Color.BLACK) {
            return;
        } else {
            insertCase3(node);
        }
    }

    private void insertCase3(Node<T> node) {
        Node<T> uncle = node.getUncle();
        Node<T> grandparent = node.getGrandparent();

        if (uncle != null && uncle.getColor() == Color.RED) {
            node.getParent().setColor(Color.BLACK);
            uncle.setColor(Color.BLACK);
            grandparent.setColor(Color.RED);
            insertCase1(grandparent);
        } else {
            insertCase4(node);
        }
    }

    private void insertCase4(Node<T> node) {
        Node<T> grandparent = node.getGrandparent();
        Node<T> parent = node.getParent();

        if (node == parent.getRight() && parent == grandparent.getLeft()) {
            rotateLeft(parent);
            node = node.getLeft();
        } else if (node == parent.getLeft() && parent == grandparent.getRight()) {
            rotateRight(parent);
            node = node.getRight();
        }

        insertCase5(node);
    }

    private void insertCase5(Node<T> node) {
        Node<T> grandparent = node.getGrandparent();
        Node<T> parent = node.getParent();

        parent.setColor(Color.BLACK);
        grandparent.setColor(Color.RED);

        if (node == parent.getLeft() && parent == grandparent.getLeft()) {
            rotateRight(grandparent);
        } else {
            rotateLeft(grandparent);
        }
    }

    public Node<T> search(T value) {
        return search(root, value);
    }

    public Node<T> search(Node<T> tree, T value) {
        Node<T> node = tree;

        while (node != null) {
            int compare = comparator.compare(value, node.getData());

            if (compare == 0) {
                return node;
            } else if (compare < 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        return null;
    }

    private static class NilNode extends Node {
        private NilNode(Object data) {
            super(null);
            this.setColor(Color.BLACK);
        }
    }

    


    private void replaceNode(Node<T> node) {
        Node<T> parent = node.getParent();

        if (node.getLeft() == null) {
            replaceParentsChild(parent, node, node.getRight());
        } else {
            replaceParentsChild(parent, node, node.getLeft());
        }
    }

    private Node<T> getSuccessor(Node<T> node) {
        return getMinimum(node.getRight());
    }

    private Node<T> getMinimum(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    @Override
    public Iterator<T> iterator() {
        return new RBIterator<>(this);
    }

    class RBIterator<T> implements Iterator<T> {
        Stack<Node<T>> stack = new Stack<>();

        public RBIterator(RedBlacktree<T> tree) {
            pushToLeft(tree.getRoot());
        }

        private void pushToLeft(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            Node<T> node = stack.pop();
            pushToLeft(node.getRight());
            return node.getData();
        }
    }
}