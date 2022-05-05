package dk.sdu.mmmi.swe.gtg.common.data;

public class GameKeys {

    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int ENTER = 4;
    public static final int ESCAPE = 5;
    public static final int SPACE = 6;
    public static final int SHIFT = 7;
    public static final int W = 9;
    public static final int A = 10;
    public static final int S = 11;
    public static final int D = 12;
    public static final int M = 13;
    public static final int P = 14;
    private static final int NUM_KEYS = 15;
    private static boolean[] keys;
    private static boolean[] pkeys;

    public GameKeys() {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];

    }

    public void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            pkeys[i] = keys[i];
        }
    }

    public void setKey(int k, boolean b) {
        keys[k] = b;
    }

    public boolean isDown(int k) {
        return keys[k];
    }

    public boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }

}
