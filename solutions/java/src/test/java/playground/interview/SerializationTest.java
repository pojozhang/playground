package playground.interview;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SerializationTest {

    @Test
    void serialize_and_deserialize() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(new Pair("k", "v"));

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream in = new ObjectInputStream(inputStream);
        Pair pair = (Pair) in.readObject();

        assertEquals("k", pair.key);
        assertNull(pair.value);
    }

    public static class Pair implements Serializable {

        private static final long serialVersionUID = -6789819854401951780L;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String key;
        private transient String value;
    }
}
