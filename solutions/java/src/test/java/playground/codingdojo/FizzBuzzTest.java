package playground.codingdojo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzTest {

    @Test
    void should_show_fizz_when_number_is_divisible_by_3() {
        assertThat(new FizzBuzz(3).toString()).isEqualTo("fizz");
    }

    @Test
    void should_show_buzz_when_number_is_divisible_by_5() {
        assertThat(new FizzBuzz(5).toString()).isEqualTo("buzz");
    }

    @Test
    void should_show_raw_value_when_number_is_not_divisible_by_3_or_5() {
        assertThat(new FizzBuzz(1).toString()).isEqualTo("1");
    }

    @Test
    void should_show_fizzbuzz_when_number_is_divisible_by_3_and_5() {
        assertThat(new FizzBuzz(15).toString()).isEqualTo("fizzbuzz");
    }
}
