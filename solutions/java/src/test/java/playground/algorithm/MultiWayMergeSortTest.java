package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MultiWayMergeSortTest {

    private MultiWayMergeSort solution = new MultiWayMergeSort();

    @Test
    void case_1() throws IOException {
        File input = new File(Thread.currentThread().getContextClassLoader().getResource("unsorted_sample.txt").getPath());
        File output = new File("result_" + UUID.randomUUID().toString().substring(0, 6));
        File expected = new File(Thread.currentThread().getContextClassLoader().getResource("sorted_sample.txt").getPath());

        solution.sort(input, output, 1000);

        assertThat(output).hasSameTextualContentAs(expected);

        output.delete();
    }
}