import java.util.LinkedList;
import java.util.Queue;

public class QueueChainManager extends ChainManager  {
    Queue<Chain> chainQueue = new LinkedList<>();

    @Override
    public void add(Chain chain) {
        chainQueue.add(chain);
        this.updateMax(chain.length());
    }

    @Override
    public Chain next() {
        incrementNumNexts();
        return chainQueue.remove();
        
    }

    @Override
    public boolean isEmpty() {
        return chainQueue.isEmpty();
    }
}