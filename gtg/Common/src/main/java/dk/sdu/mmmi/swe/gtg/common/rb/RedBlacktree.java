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

    public void delete(T value) {
        delete(
            search(value)
        );
    }

    public void delete(Node<T> node) {

        if (node == null) {
            return;
        }

        Node<T> movedUpNode;
        Color deletedNodeColor;

        if (node.getLeft() == null || node.getRight() == null) {
            movedUpNode = deletedNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.getColor();
        } else {
            Node<T> inOrderSuccessor = getMinimum(node.getRight());

            node.setData(inOrderSuccessor.getData());

            movedUpNode = inOrderSuccessor;
            deletedNodeColor = inOrderSuccessor.getColor();
        }

        if (deletedNodeColor == Color.BLACK) {
            fixRedBlackPropertiesAfterDelete(movedUpNode);

            if (movedUpNode.getClass() == NilNode.class) {
                replaceParentsChild(movedUpNode.getParent(), movedUpNode, null);
            }
        }

    }

    private static class NilNode extends Node {
        private NilNode(Object data) {
            super(null);
            this.setColor(Color.BLACK);
        }
    }


    private void fixRedBlackPropertiesAfterDeleteX(Node<T> movedUpNode) {
        while (movedUpNode != root && movedUpNode.getColor() == Color.BLACK) {
            if (movedUpNode == movedUpNode.getParent().getLeft()) {
                Node<T> sibling = movedUpNode.getParent().getRight();

                if (sibling.getColor() == Color.RED) {
                    sibling.setColor(Color.BLACK);
                    movedUpNode.getParent().setColor(Color.RED);
                    rotateLeft(movedUpNode.getParent());
                    sibling = movedUpNode.getParent().getRight();
                }

                if (sibling.getLeft().getColor() == Color.BLACK &&
                    sibling.getRight().getColor() == Color.BLACK) {
                    sibling.setColor(Color.RED);
                    movedUpNode = movedUpNode.getParent();
                } else {
                    if (sibling.getRight().getColor() == Color.BLACK) {
                        sibling.getLeft().setColor(Color.BLACK);
                        sibling.setColor(Color.RED);
                        rotateRight(sibling);
                        sibling = movedUpNode.getParent().getRight();
                    }

                    sibling.setColor(movedUpNode.getParent().getColor());
                    movedUpNode.getParent().setColor(Color.BLACK);
                    sibling.getRight().setColor(Color.BLACK);
                    rotateLeft(movedUpNode.getParent());
                    movedUpNode = root;
                }
            } else {
                Node<T> sibling = movedUpNode.getParent().getLeft();

                if (sibling.getColor() == Color.RED) {
                    sibling.setColor(Color.BLACK);
                    movedUpNode.getParent().setColor(Color.RED);
                    rotateRight(movedUpNode.getParent());
                    sibling = movedUpNode.getParent().getLeft();
                }

                if (sibling.getRight().getColor() == Color.BLACK &&
                    sibling.getLeft().getColor() == Color.BLACK) {
                    sibling.setColor(Color.RED);
                    movedUpNode = movedUpNode.getParent();
                } else {
                    if (sibling.getLeft().getColor() == Color.BLACK) {
                        sibling.getRight().setColor(Color.BLACK);
                        sibling.setColor(Color.RED);
                        rotateLeft(sibling);
                        sibling = movedUpNode.getParent().getLeft();
                    } else {
                        sibling.setColor(movedUpNode.getParent().getColor());
                        movedUpNode.getParent().setColor(Color.BLACK);
                        sibling.getLeft().setColor(Color.BLACK);
                        rotateRight(movedUpNode.getParent());
                        movedUpNode = root;
                    }
                }
            }
        }
    }

    private void fixRedBlackPropertiesAfterDelete(Node<T> x) {
        Node<T> s;
        while (x != root && x.getColor() == Color.BLACK) {
            if (x == x.getParent().getLeft()) {
                s = x.getParent().getRight();
                if (s.getColor() == Color.RED) {
                    s.setColor(Color.BLACK);
                    x.getParent().setColor(Color.RED);
                    rotateLeft(x.getParent());
                    s = x.getParent().getRight();
                }

                if (s.getLeft().getColor() == Color.BLACK && s.getRight().getColor() == Color.BLACK) {
                    s.setColor(Color.RED);
                    x = x.getParent();
                } else {
                    if (s.getRight().getColor() == Color.BLACK) {
                        s.getLeft().setColor(Color.BLACK);
                        s.setColor(Color.RED);
                        rotateRight(s);
                        s = x.getParent().getRight();
                    }

                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(Color.BLACK);
                    s.getRight().setColor(Color.BLACK);
                    rotateLeft(x.getParent());
                    x = root;
                }
            } else {
                s = x.getParent().getLeft();
                if (s.getColor() == Color.RED) {
                    s.setColor(Color.BLACK);
                    x.getParent().setColor(Color.RED);
                    rotateRight(x.getParent());
                    s = x.getParent().getLeft();
                }

                if (s.getRight().getColor() == Color.BLACK && s.getLeft().getColor() == Color.BLACK) {
                    s.setColor(Color.RED);
                    x = x.getParent();
                } else {
                    if (s.getLeft().getColor() == Color.BLACK) {
                        s.getRight().setColor(Color.BLACK);
                        s.setColor(Color.RED);
                        rotateLeft(s);
                        s = x.getParent().getLeft();
                    }

                    s.setColor(s.getParent().getColor());
                    x.getParent().setColor(Color.BLACK);
                    s.getLeft().setColor(Color.BLACK);
                    rotateRight(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(Color.BLACK);
    }

    private Node<T> deletedNodeWithZeroOrOneChild(Node<T> node) {
        if (node.getLeft() != null) {
            replaceParentsChild(node.getParent(), node, node.getLeft());
            return node.getLeft();
        } else if (node.getRight() != null) {
            replaceParentsChild(node.getParent(), node, node.getRight());
            return node.getRight();
        } else {
            Node<T> newChild = node.getColor() == Color.BLACK ? new NilNode(null) : null;
            replaceParentsChild(node.getParent(), node, newChild);
            return newChild;
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