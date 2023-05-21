package sorting.events;

import sorting.config.RunInfo;

/**
 * Event mit Informationen über einen einzelnen
 * Durchlauf der Workbench mit einer Anzahl von Elementen
 * mit allen Sortern.
 * 
 * @author Lars Hamann
 * @version 2023
 */
public class ProgressEvent {
    private final String message;

    private final RunInfo runConfig;

    public ProgressEvent(RunInfo runConfig, String message) {
        this.message   = message;
        this.runConfig = runConfig;
    }

    public String getMessage() {
        return message;
    }

    public RunInfo getRunConfig() {
        return runConfig;
    }
}
