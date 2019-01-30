package playground.redis;

import io.lettuce.core.MapScanCursor;
import io.lettuce.core.ScanArgs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScanTest extends BaseRedisTest {

    @Test
    void scan() {
        final String bigKey = "big-key";
        generateBigKey(bigKey, 500);

        MapScanCursor<String, String> cursor = null;
        ScanArgs scanArgs = ScanArgs.Builder.limit(50);
        do {
            if (cursor == null) {
                cursor = syncCommand.hscan(bigKey, scanArgs);
            } else {
                cursor = syncCommand.hscan(bigKey, cursor, scanArgs);
            }
            syncCommand.hdel(bigKey, cursor.getMap().keySet().toArray(new String[0]));
        } while (!cursor.isFinished());

        assertEquals(0, syncCommand.hgetall(bigKey).size());
    }

    private void generateBigKey(String key, int count) {
        for (int i = 0; i < count; i++) {
            syncCommand.hset(key, "key" + i, "value");
        }
    }
}
