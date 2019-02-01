package playground.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseRedisTest {

    private RedisClient client;
    private StatefulRedisConnection<String, String> connection;
    RedisCommands<String, String> syncCommand;

    @BeforeAll
    void init() {
        initConnection();
    }

    private void initConnection() {
        client = RedisClient.create("redis://localhost:6379/0");
        connection = client.connect();
        syncCommand = connection.sync();
    }

    @AfterAll
    void cleanUp() {
        if (connection != null) {
            connection.close();
        }
        if (client != null) {
            client.shutdown();
        }
    }
}
