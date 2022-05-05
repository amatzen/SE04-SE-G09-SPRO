package dk.sdu.mmmi.swe.gtg.enemyai;

import com.badlogic.gdx.math.Vector2;

public class Node {

    private Node parent;
    private Vector2 state;
    private float cost;

    public Node() {
        this.parent = null;
        this.state = null;
        this.cost = 0;
    }

    public Node(Node parent, Vector2 state, float cost) {
        this.parent = parent;
        this.state = state;
        this.cost = cost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Vector2 getState() {
        return state;
    }

    public void setState(Vector2 state) {
        this.state = state;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        return ((int) state.y) * 1000 + ((int) state.x);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Node)) return false;

        return hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return state.toString();
    }

    public String getKey() {
        return (int) this.getState().x + "," + (int) this.getState().y;
    }
}
