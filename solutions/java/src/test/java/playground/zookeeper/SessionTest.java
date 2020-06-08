package playground.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest extends ZooKeeperTest {

    @Test
    void should_connect_to_zookeeper() throws IOException, InterruptedException {
        CountDownLatch connection = new CountDownLatch(1);

        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, 3000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connection.countDown();
            }
        });

        connection.await(10, TimeUnit.SECONDS);
        assertThat(zooKeeper.getState()).isEqualTo(ZooKeeper.States.CONNECTED);
    }

    @Test
    void should_not_connect_to_zookeeper_when_password_is_wrong() throws IOException, InterruptedException {
        CountDownLatch connection = new CountDownLatch(1);

        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, 3000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.Expired) {
                connection.countDown();
            }
        }, 1L, "password".getBytes());

        connection.await(10, TimeUnit.SECONDS);
        assertThat(zooKeeper.getState()).isEqualTo(ZooKeeper.States.CLOSED);
    }

    @Test
    void should_reconnect_to_zookeeper_when_password_is_correct() throws IOException, InterruptedException {
        CountDownLatch connection = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, 3000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connection.countDown();
            }
        });
        connection.await(10, TimeUnit.SECONDS);
        assertThat(zooKeeper.getState()).isEqualTo(ZooKeeper.States.CONNECTED);
        long sessionId = zooKeeper.getSessionId();
        byte[] sessionPasswd = zooKeeper.getSessionPasswd();

        CountDownLatch reconnection = new CountDownLatch(1);
        zooKeeper = new ZooKeeper(CONNECT_STRING, 3000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                reconnection.countDown();
            }
        }, sessionId, sessionPasswd);
        reconnection.await(10, TimeUnit.SECONDS);
        assertThat(zooKeeper.getState()).isEqualTo(ZooKeeper.States.CONNECTED);
    }
}
