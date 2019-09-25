package playground.codingdojo;

public class FizzBuzz {

    private final int number;

    public FizzBuzz(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        String result = "";
        if (number % 3 == 0) {
            result += "fizz";
        }
        if (number % 5 == 0) {
            result += "buzz";
        }
        return result.length() > 0 ? result : String.valueOf(number);
    }
}
