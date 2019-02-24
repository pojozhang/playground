package playground.redis;

import io.lettuce.core.KeyScanCursor;
import io.lettuce.core.ScanArgs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScanTest extends BaseRedisTest {

    @Test
    void scan() {
        final int count = 500;
        generateKeys("key", count);
        assertEquals(count, syncCommand.keys("key**").size());

        KeyScanCursor<String> cursor = null;
        ScanArgs scanArgs = ScanArgs.Builder.limit(50);
        do {
            if (cursor == null) {
                cursor = syncCommand.scan(scanArgs);
            } else {
                cursor = syncCommand.scan(cursor, scanArgs);
            }
            if (!cursor.getKeys().isEmpty()) {
                syncCommand.del(cursor.getKeys().toArray(new String[0]));
            }
        } while (!cursor.isFinished());

        assertEquals(0, syncCommand.keys("key**").size());
    }

    private void generateKeys(String prefix, int count) {
        for (int i = 0; i < count; i++) {
            syncCommand.set(prefix + i, "value");
        }
    }
}
