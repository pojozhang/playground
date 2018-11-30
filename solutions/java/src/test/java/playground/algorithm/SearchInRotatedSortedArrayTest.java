import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import playground.algorithm.SearchInRotatedSortedArray;

class SearchInRotatedSortedArrayTest {

    private SearchInRotatedSortedArray solution = new SearchInRotatedSortedArray();

    @Test
    void case_1() {
        assertEquals(4, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
    }

    @Test
    void case_2() {
        assertEquals(-1, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
    }

    @Test
    void case_3() {
        assertEquals(2, solution.search(new int[]{1, 2, 5}, 5));
    }

    @Test
    void case_4() {
        assertEquals(0, solution.search(new int[]{5, 1, 3}, 5));
    }

    @Test
    void case_5() {
        assertEquals(1, solution.search(new int[]{3, 1}, 1));
    }
}