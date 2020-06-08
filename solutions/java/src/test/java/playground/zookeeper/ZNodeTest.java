package playground.zookeeper;

import org.apache.zookeeper.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ZNodeTest extends ZooKeeperTest {

    @Test
    void should_not_create_znode_if_already_exists() throws IOException, KeeperException, InterruptedException {
        CountDownLatch connection = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, 3000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connection.countDown();
            }
        });

        zooKeeper.create("/path", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        assertThatThrownBy(() -> zooKeeper.create("/path", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL))
                .isInstanceOf(KeeperException.NodeExistsException.class);
    }
}
