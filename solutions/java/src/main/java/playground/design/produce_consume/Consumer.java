package playground.design.produce_consume;

public class Consumer<T> {

    private Channel<T> channel;

    public Consumer(Channel<T> channel) {
        this.channel = channel;
    }

    public T consume() throws InterruptedException {
        return this.channel.take();
    }
}
