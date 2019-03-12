package playground.design.produce_consume;

public class Producer<T> {

    private Channel<T> channel;

    public Producer(Channel<T> channel) {
        this.channel = channel;
    }

    public void produce(T item) throws InterruptedException {
        this.channel.put(item);
    }
}
