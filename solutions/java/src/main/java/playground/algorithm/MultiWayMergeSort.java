package playground.algorithm;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MultiWayMergeSort {

    public void sort(File input, File output, int bufferSize) throws IOException {
        List<File> sortedFiles = split(input, bufferSize);
        mergeSort(sortedFiles, output);
        sortedFiles.forEach(File::delete);
    }

    private List<File> split(File input, int bufferSize) throws IOException {
        List<File> files = new ArrayList<>();
        try (BufferedReader bufferedReader = IOUtils.buffer(new FileReader(input))) {
            String line;
            File file = null;
            List<Integer> list = null;
            for (int row = 0; (line = bufferedReader.readLine()) != null; ) {
                if (row == 0) {
                    file = new File("tmp_" + UUID.randomUUID().toString().substring(0, 6));
                    file.createNewFile();
                    files.add(file);
                    list = new ArrayList<>(bufferSize);
                }
                list.add(Integer.parseInt(line, 10));
                row++;
                if (row == bufferSize) {
                    Collections.sort(list);
                    PrintWriter printWriter = new PrintWriter(file);
                    IOUtils.writeLines(list, System.lineSeparator(), printWriter);
                    printWriter.close();
                    row = 0;
                }
            }
        }
        return files;
    }

    private void mergeSort(List<File> sortedFiles, File output) throws IOException {
        PriorityQueue<Item> priorityQueue = new PriorityQueue<>();
        List<BufferedReader> readers = getReaders(sortedFiles);
        for (BufferedReader reader : readers) {
            priorityQueue.add(Item.next(reader));
        }

        try (PrintStream printStream = new PrintStream(output)) {
            while (!priorityQueue.isEmpty()) {
                Item item = priorityQueue.poll();
                Item next = item.next();
                if (next != null) {
                    priorityQueue.add(next);
                }
                printStream.println(item.value);
            }
        }

        for (BufferedReader reader : readers) {
            reader.close();
        }
    }

    private List<BufferedReader> getReaders(List<File> files) {
        return files.stream().map(file -> {
            try {
                return new BufferedReader(new FileReader(file));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }).collect(Collectors.toList());
    }

    private static class Item implements Comparable<Item> {

        private Integer value;
        private BufferedReader reader;

        private static Item next(BufferedReader reader) throws IOException {
            return new Item(null, reader).next();
        }

        private Item(Integer value, BufferedReader reader) {
            this.value = value;
            this.reader = reader;
        }

        public Item next() throws IOException {
            String line = reader.readLine();
            if (line == null) {
                return null;
            }
            if (line.isEmpty() || line.trim().isEmpty()) {
                return next();
            }
            return new Item(Integer.parseInt(line, 10), reader);
        }

        @Override
        public int compareTo(Item o) {
            return value - o.value;
        }
    }
}
