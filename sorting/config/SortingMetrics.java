package sorting.config;
 
/**
 * @author Lars Hamann
 * @version 2023
 */
public class SortingMetrics {
    public enum ListType {
        RANDOM,
        ORDERED,
        REVERSE_ORDERED,
        SAME
    }

    private final ListType listType;

    private int numCompares = 0;

    private int numMoves = 0;

    public SortingMetrics(ListType listType) {
        this.listType = listType;
    }

    public ListType getListType() {
        return listType;
    }

    public int getNumCompares() {
        return numCompares;
    }

    public int getNumMoves() {
        return numMoves;
    }

    public void incrementCompares() {
        this.numCompares++;
    }

    public void incrementMoves() {
        this.numMoves++;
    }
}
