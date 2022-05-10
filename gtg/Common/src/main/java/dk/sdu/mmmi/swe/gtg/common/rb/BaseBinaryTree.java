package dk.sdu.mmmi.swe.gtg.common.rb;

public class BaseBinaryTree<T> {
    protected Node<T> root;

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }
}
