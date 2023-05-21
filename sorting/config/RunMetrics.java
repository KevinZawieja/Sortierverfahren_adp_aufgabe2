package sorting.config;
import sorting.sorter.Sorter;

/**
 * @author Lars Hamann
 * @version 2023
 */
public class RunMetrics {  

    private final Sorter sorter;

    private final SortingMetrics randomMetrics = new SortingMetrics(SortingMetrics.ListType.RANDOM);

    private final SortingMetrics orderedMetrics = new SortingMetrics(SortingMetrics.ListType.ORDERED);

    private final SortingMetrics reverseOrderedMetrics = new SortingMetrics(SortingMetrics.ListType.REVERSE_ORDERED);

    private final SortingMetrics sameMetrics = new SortingMetrics(SortingMetrics.ListType.SAME);

    public RunMetrics(Sorter sorter) {
        this.sorter = sorter;
    }

    public Sorter getSorter() {
        return sorter;
    }

    public SortingMetrics getRandomMetrics() {
        return this.randomMetrics;
    }

    public SortingMetrics getOrderedMetrics() {
        return this.orderedMetrics;
    }

    public SortingMetrics getReverseOrderedMetrics() {
        return this.reverseOrderedMetrics;
    }

    public SortingMetrics getSameMetrics() {
        return this.sameMetrics;
    }
}
