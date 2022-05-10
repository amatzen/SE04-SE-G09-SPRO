package dk.sdu.mmmi.swe.gtg.common.rb;

public class Node<T> {
    private T data;

    private Node<T> left;
    private Node<T> right;
    private Node<T> parent;

    private Color color;

    public Node(T data) {
        this.data = data;
        this.color = Color.RED;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Node<T> getUncle() {
        if (parent == null) {
            return null;
        }
        if (parent.getParent() == null) {
            return null;
        }
        if (parent.getParent().getLeft() == parent) {
            return parent.getParent().getRight();
        }
        return parent.getParent().getLeft();
    }

    public Node<T> getGrandparent() {
        if (parent == null) {
            return null;
        }
        return parent.getParent();
    }

    public Node<T> getSibling() {
        if (parent == null) {
            return null;
        }
        if (parent.getLeft() == this) {
            return parent.getRight();
        }
        return parent.getLeft();
    }
}
