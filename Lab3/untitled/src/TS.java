import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TS {
    private int size;
    private int maxSize;
    private Pair[] dict;

    public TS(int maxSize) {
        this.size = 0;
        this.maxSize = maxSize;
        this.dict = new Pair[maxSize];
    }

    public int size() {
        return this.size;
    }

    public Pair get(int index) {
        return this.dict[index];
    }

    public void setSecond(int index, int second) {
        this.dict[index] = new Pair(this.dict[index].getFirst(), second);
    }

    public Pair find(String atom) {
        for(int i = 0; i < this.size; i++){
            if (this.dict[i].getFirst().equals(atom))
                return this.dict[i];
        }
        return null;
    }


    private void resize() {
        Pair[] newDict = new Pair[2 * this.maxSize];
        if (this.maxSize >= 0)
            System.arraycopy(this.dict, 0, newDict, 0, this.maxSize);
        this.maxSize = 2 * this.maxSize;
        this.dict = newDict;
    }

    void add(Pair element) {
        if (size == maxSize) {
            resize();
        }

        int index = Arrays.binarySearch(dict, 0, size, element, Comparator.comparing(Pair::getFirst));

        if (index < 0) {
            // If not found, calculate the insertion point to get the correct index
            index = -(index + 1);
        }

        // Shift elements to make space for the new element
        System.arraycopy(dict, index, dict, index + 1, size - index);
        // Insert the new element
        dict[index] = element;
        size++;

        // Update the indices
        for (int i = 0; i < size; i++) {
            dict[i] = new Pair(dict[i].getFirst(), i);
        }

    }
    public void sort(Comparator<Pair> comparing) {
        List<Pair> list = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            list.add(this.dict[i]);
        }
        list.sort(comparing);
        for (int i = 0; i < this.size; i++) {
            this.dict[i] = list.get(i);
        }
    }

    public static class Pair {
        private final String first;
        private final int second ;

        Pair(String first, int second) {
            this.first = first;
            this.second = second;
        }

        public String getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
    }
}
