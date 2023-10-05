import java.util.Iterator;
import java.util.LinkedHashSet;

public class Chain implements Iterable<String>{
    LinkedHashSet<String> chainMap;

    public Chain() {
        chainMap = new LinkedHashSet<String>();   
    }

    public Chain(LinkedHashSet<String> input){
        chainMap = input;
    }

    @SuppressWarnings("unchecked")
    public Chain addLast(String str) {
        LinkedHashSet<String> newMap = (LinkedHashSet<String>) chainMap.clone();
        newMap.add(str);
        return new Chain(newMap);
    }

    public String getLast() {
        String last = null;
        Iterator<String> chainIterator = chainMap.iterator();
        while(chainIterator.hasNext()){
            last = chainIterator.next();
        }
        return last;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("[");
        for (Object obj: chainMap) {
            output.append(obj);
            output.append(" ");
        }

        // remove unneeded whitespace from the end of the output
        if (output.length() > 2)
            output.setLength(output.length() - 1);

        output.append("]");
        return output.toString();
    }

	public int length() {
		int len = 0;
        Iterator<String> chainIterator = chainMap.iterator();
        while(chainIterator.hasNext()){
            chainIterator.next();
            len++;
        }
        return len;
	}

    @Override
    public Iterator<String> iterator() {
        return chainMap.iterator();
    }

    public boolean contains(String string) {
        Iterator<String> chainIterator = chainMap.iterator();
        while(chainIterator.hasNext()){
            if(chainIterator.next().equals(string))
                return true;
        }
        return false;
    }
}