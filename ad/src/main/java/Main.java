import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        long averageHeapTime = 0;
        long averageListTime = 0;
        int repeats = 1000000;

        FileWriter outputFile;

        try {
            outputFile = new FileWriter(new File("adoutput.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Excel CSV writer
        CSVWriter writer = new CSVWriter(outputFile, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");

        String[] header = {"n", "average heap time", "average list time"};
        writer.writeNext(header);

        for (double n = 100; n <= 1000; n += 100) {
            final int finalN = (int) n;

            averageHeapTime = Util.average(() -> {
                List<Integer> permutation = Util.permutation(finalN, true, false);

                Queue<Integer> heap = new Heap<>(Integer::compare);

                heap.addAll(permutation);

                return Util.time(() -> {
                    heap.poll();
                });
            }, repeats);

            averageListTime = Util.average(() -> {
                List<Integer> permutation = Util.permutation(finalN, true, false);

                return Util.time(() -> {
                    findMin(permutation);
                });
            }, repeats);

            String[] data = {"" + finalN, "" + averageHeapTime, "" + averageListTime};
            writer.writeNext(data);

            System.out.println(finalN);
        }

        System.gc();

        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static int findMin(List<Integer> permutation) {
        int min = permutation.get(0);
        for (int i = 1; i < permutation.size(); i++) {
            if (permutation.get(i) < min) {
                min = permutation.get(i);
            }
        }
        return min;
    }

}
