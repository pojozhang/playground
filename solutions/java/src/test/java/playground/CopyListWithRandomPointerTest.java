package playground;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CopyListWithRandomPointerTest {

    private CopyListWithRandomPointer solution = new CopyListWithRandomPointer();

    @Test
    void case_1() {
        CopyListWithRandomPointer.Node[] nodes = new CopyListWithRandomPointer.Node[5];
        nodes[0] = new CopyListWithRandomPointer.Node(7);
        nodes[1] = new CopyListWithRandomPointer.Node(13);
        nodes[2] = new CopyListWithRandomPointer.Node(11);
        nodes[3] = new CopyListWithRandomPointer.Node(10);
        nodes[4] = new CopyListWithRandomPointer.Node(1);
        nodes[0].next = nodes[1];
        nodes[1].next = nodes[2];
        nodes[2].next = nodes[3];
        nodes[3].next = nodes[4];
        nodes[4].next = null;
        nodes[0].random = null;
        nodes[1].random = nodes[0];
        nodes[2].random = nodes[4];
        nodes[3].random = nodes[2];
        nodes[4].random = nodes[0];

        CopyListWithRandomPointer.Node copied = solution.copyRandomList(nodes[0]);

        assertThat(copied.val).isEqualTo(7);
        assertThat(copied.random).isNull();
        assertThat(copied.next.val).isEqualTo(13);
        assertThat(copied.next.random).isEqualTo(7);
        assertThat(copied.next.next.val).isEqualTo(11);
        assertThat(copied.next.next.random).isEqualTo(1);
        assertThat(copied.next.next.next.val).isEqualTo(10);
        assertThat(copied.next.next.next.random).isEqualTo(11);
        assertThat(copied.next.next.next.next.val).isEqualTo(1);
        assertThat(copied.next.next.next.next.random).isEqualTo(7);
        assertThat(copied.next.next.next.next.next).isNull();
    }

}