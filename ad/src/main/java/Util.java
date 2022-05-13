import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class Util {

    public static long average(Callable<Long> callable, int n) {
        long time = 0;
        for (int i = 0; i < n; i++) {
            try {
                time += callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return time / n;
    }

    public static long time(Runnable runnable) {
        long before = System.nanoTime();

        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long after = System.nanoTime();

        return after - before;
    }

    public static List<Integer> permutation(int n, boolean shuffle, boolean reverse) {
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        if (shuffle) {
            Collections.shuffle(list);
        }

        if (reverse) {
            Collections.reverse(list);
        }

        return list;
    }

    public static List<Integer> permutationWithInterval(int n, int a, int b) {
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            list.add((int) (Math.random() * (b - a) + a));
        }

        return list;
    }

}
