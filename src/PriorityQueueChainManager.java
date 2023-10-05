import java.util.PriorityQueue;

public class PriorityQueueChainManager extends ChainManager{
    String ending_word;

    public PriorityQueueChainManager(String end) {
        ending_word = end;
    }
    // priority queue to find better solutions faster
    PriorityQueue<Entry> entryQueue = new PriorityQueue<>();

    @Override
    public void add(Chain chain) {
        int priority = distance(chain.getLast(), ending_word);
        entryQueue.add(new Entry(chain, priority));
        this.updateMax(chain.length());
    }

    // calculates how close one word is to another
    public int distance(String s1, String s2) {
        int d = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
                d++;
        }
        return d;
    }

    @Override
    public Chain next() {
        incrementNumNexts();
        Entry lastEntry = entryQueue.remove();
        return lastEntry.getChain();
    }

    @Override
    public boolean isEmpty() {
        return entryQueue.isEmpty();
    }

    // inner class that lets us compare how close different chains are for the sake of priority
    class Entry implements Comparable<Object>{
        private Chain chain;
        private int comparisonVal;

        public Entry(Chain chain, int priority){
            this.chain = chain;
            this.comparisonVal = priority + chain.length();
        }

        // compares by summing distance from end word and chain length
        @Override
        public int compareTo(Object o) {
            Entry other = (PriorityQueueChainManager.Entry) o;
            return Integer.compare(this.comparisonVal, other.getComparisonVal());
        }

        // getters
        public int getComparisonVal(){
            return this.comparisonVal;
        }

        public Chain getChain(){
            return this.chain;
        }
    }
}