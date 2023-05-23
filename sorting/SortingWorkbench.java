package sorting;

import sorting.sorter.*;
import sorting.config.*;
import sorting.events.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Das Herzstück des Projekts. Hier werden Sortieralgorithmen
 * konfigurierbar ausgeführt (<code>executeWorkbench</code>). 
 *
 * Die Workbench wir mit einer Liste von <code>Sorter</code>n initialisiert.
 * Jeder Sorter steht für einen Sortieralgorithmus.
 *
 * Die Ausführung kann über die <code>WorkbenchConfig</code> dahingehend konfiguriert werden,
 * dass eine bestimme Anzahl an Läufen durchgeführt werden, die bei einer Grundzahl an zu
 * sortierenden Elementen starten und pro Schritt um eine definiert Anzahl an
 * Elementen erhöht werden.
 *
 * Bei jedem Schritt werden alle übergebenen Sortieralgorithmen für drei Listen ausgeführt:
 * 1. eine Liste mit Pseudozufallszahlen
 * 2. eine aufsteigend sortierte Liste
 * 3. eine absteigend sortierte Liste
 *
 * Die Ergebnisse eines Laufs können über einen Listener während der Ausführung ausgewertet werden.
 * 
 * @author Lars Hamann, Axel Schmolitzky
 */
public class SortingWorkbench { 

    /**
     * Liste der auszuführenden Sortieralgorithmen
     */
    private final List<Sorter> sorters;


    /**
     * Konfiguration der Workbench (Anzahl Läufe, Startanzahl an Elementen, ...)
     */
    private final WorkbenchConfig config;

    /**
     * Liste der Fortschritts-Listener
     */
    private final List<WorkbenchProgressListener> progressListener = new ArrayList<>();

    /**
     * Erzeugt eine neuen <code>SortingWorkbench</code> mit den auszuführenden
     * Sortieralgorithmen <code>sorters</code> und den Einstellungen in <code>config</code>.
     * @param sorters Die auszuführenden <Code>Sorter</Code>
     * @param config Einstellungen zu Ausführung
     */
    public SortingWorkbench(List<Sorter> sorters, WorkbenchConfig config) {
        this.sorters = sorters;
        this.config = config;
    }

    public void addProgressListener(WorkbenchProgressListener l) {
        this.progressListener.add(l);
    }

    public void removeProgressListener(WorkbenchProgressListener l) {
        this.progressListener.remove(l);
    }

    private void fireWorkbenchProgress(ProgressEvent event) {
        for (WorkbenchProgressListener l : this.progressListener) {
            l.reportProgress(event);
        }
    }

    public WorkbenchConfig getConfig() {
        return config;
    }

    public void executeWorkbench() {

        List<Integer> randomElements;
        List<Integer> orderedElements;
        List<Integer> reverseOrderedElements;
        List<Integer> sameElements;
        ListAdapter<Integer> toSort;

        ListProducer lp = new ListProducer();

        while (!this.getConfig().isFinished()) {
            RunInfo runConfig = this.config.getNextRunConfig();

            randomElements = lp.createRandomIntegerList(runConfig.getNumElements());
            orderedElements = lp.createOrderedIntegerList(runConfig.getNumElements());
            reverseOrderedElements = lp.createReverseOrderedIntegerList(runConfig.getNumElements());
            sameElements = lp.createSameIntegerList(runConfig.getNumElements());

            for (Sorter sorter : sorters) {
                toSort = new ListAdapter<>(new ArrayList<>(randomElements),
                                           runConfig.getRandomMetricsFor(sorter)); 
                sorter.sort(toSort);
                assert ListValidator.validateOrder(toSort.getList()) : "Randomized list was not sorted!";

                toSort = new ListAdapter<>(new ArrayList<>(orderedElements),
                                           runConfig.getOrderedMetricsFor(sorter)); 
                sorter.sort(toSort);
                assert ListValidator.validateOrder(toSort.getList()) : "Ordered list was not sorted!";

                toSort = new ListAdapter<>(new ArrayList<>(reverseOrderedElements),
                                           runConfig.getReverseOrderedMetricsFor(sorter)); 
                sorter.sort(toSort);
                assert ListValidator.validateOrder(toSort.getList()) : "Reverse ordered list was not sorted!";

                toSort = new ListAdapter<>(new ArrayList<>(sameElements),
                        runConfig.getSameMetricsFor(sorter));
                sorter.sort(toSort);
                assert ListValidator.validateOrder(toSort.getList()) : "Same elements list was not sorted!";
            }

            this.fireWorkbenchProgress(new ProgressEvent(runConfig, "Run %05d with %d elements finished!".formatted(this.getConfig().getCurrentRun(), runConfig.getNumElements())));
        }
    }


    public static void visual_ausfuehrung(VisualTagList tagList, Sorter sorter){
        sorter.sort(tagList);
        for (int i = 0; i < tagList.size(); i++) {
            Tag tag = tagList.get(i);
            System.out.println(tag);
        }
    }

    public static void visual_prep(){
        VisualTagList tagList = new VisualTagList(30);
        //VisualTagList tagList2 = new VisualTagList(200);
        Sorter sorter_Selection = new SelectionSorter();
        Sorter sorter_Insertion = new InsertionSorter();
        Sorter sorter_Bubble = new BubbleSorter();
        Sorter sorter_quickFirst = new QuickSorter(QuickSorter.MedianStrategy.FIRST);
        Sorter sorter_quickMiddle = new QuickSorter(QuickSorter.MedianStrategy.MIDDLE);
        Sorter sorter_quickMedian = new QuickSorter(QuickSorter.MedianStrategy.MEDIAN_OF_THREE);
        Sorter sorter_quickInsertionMiddle = new QuickInsertionSorter(QuickInsertionSorter.MedianStrategy.MIDDLE, 20);
        Sorter sorter_quickInsertionMiddle2 = new QuickInsertionSorter(QuickInsertionSorter.MedianStrategy.MIDDLE, 5);
        Sorter sorter_merge = new MergeSorter();
        visual_ausfuehrung(tagList,sorter_Bubble);
        //visual_ausfuehrung(tagList2,sorter_quickMiddle);
    }

    /**
     * Beispielausführung einer WorkBench.
     * Kann an die eigenen Bedürfnisse angepasst werden.
     * @param args Startargumente. Bisher nicht verwendet.
     */
    public static void main(String[] args) {
        visual_prep();

        List<Sorter> sorter = new ArrayList<>();
        sorter.add(new SelectionSorter());
        sorter.add(new InsertionSorter());
        sorter.add(new BubbleSorter());
        sorter.add(new MergeSorter());
        sorter.add(new QuickSorter(QuickSorter.MedianStrategy.FIRST));
        sorter.add(new QuickSorter(QuickSorter.MedianStrategy.MIDDLE));
        sorter.add(new QuickSorter(QuickSorter.MedianStrategy.MEDIAN_OF_THREE));
        sorter.add(new QuickInsertionSorter(QuickInsertionSorter.MedianStrategy.MIDDLE, 200));
        List<Sorter> sorterQuick = new ArrayList<>();
        sorterQuick.add(new QuickSorter(QuickSorter.MedianStrategy.FIRST));
        sorterQuick.add(new QuickSorter(QuickSorter.MedianStrategy.MIDDLE));
        sorterQuick.add(new QuickSorter(QuickSorter.MedianStrategy.MEDIAN_OF_THREE));

        WorkbenchConfig config = new WorkbenchConfig(sorter,10, 1000, 1000);

        SortingWorkbench wb = new SortingWorkbench(sorter, config);


        wb.addProgressListener(event -> {
            System.out.println(event.getMessage());

            for (Sorter s : sorter) {
                SortingMetrics metRandom = event.getRunConfig().getRandomMetricsFor(s);
                SortingMetrics metOrdered = event.getRunConfig().getOrderedMetricsFor(s);
                SortingMetrics metReverse = event.getRunConfig().getReverseOrderedMetricsFor(s);
                SortingMetrics metEqual = event.getRunConfig().getSameMetricsFor(s);


                System.out.printf("  Results for %s:\n", s.getName());
                System.out.printf("    Swaps random: %d, ordered: %d, reverse: %d, equal:  %d\n", metRandom.getNumMoves(), metOrdered.getNumMoves(), metReverse.getNumMoves(), metEqual.getNumMoves());
                System.out.printf("    Compares random: %d, ordered: %d, reverse: %d, equal:  %d\n", metRandom.getNumCompares(), metOrdered.getNumCompares(), metReverse.getNumCompares(), metEqual.getNumCompares());
            }

        });

        wb.executeWorkbench();
    }
}
