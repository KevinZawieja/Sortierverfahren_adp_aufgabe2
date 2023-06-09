package sorting; 

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

/**
 * Eine Klasse zum Produzieren von Integer-Listen.
 * 
 * @author Lars Hamann, Axel Schmolitzky
 */
public class ListProducer {
    private long seed = 198794;

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public List<Integer> createRandomIntegerList(int numElements) {
        SplittableRandom splittableRandom = new SplittableRandom(seed);

        ArrayList<Integer> list = new ArrayList<>(numElements);

        for (int i = 0; i < numElements; ++i) {
            list.add(splittableRandom.nextInt(0, numElements - 1));
        }

        return list;
    }


    public List<Integer> createOrderedIntegerList(int numElements) {
        ArrayList<Integer> list = new ArrayList<>(numElements);

        for (int i = 0; i < numElements; ++i) {
            list.add(i);
        }

        return list;
    }

    public List<Integer> createReverseOrderedIntegerList(int numElements) {
        ArrayList<Integer> list = new ArrayList<>(numElements);

        for (int i = numElements - 1; i >= 0; --i) {
            list.add(i);
        }

        return list;
    }

    public List<Integer> createSameIntegerList(int numElements) {
        ArrayList<Integer> list = new ArrayList<>(numElements);

        for (int i = 0; i < numElements; ++i) {
            list.add(0);
        }

        return list;
    }
}
