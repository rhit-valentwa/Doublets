import java.util.Stack;

public class StackChainManager extends ChainManager {
    Stack<Chain> stack = new Stack<Chain>();

    @Override
    public void add(Chain chain) {
        stack.add(chain);
        this.updateMax(chain.length());   
    }

    @Override
    public Chain next() {
        incrementNumNexts();
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}