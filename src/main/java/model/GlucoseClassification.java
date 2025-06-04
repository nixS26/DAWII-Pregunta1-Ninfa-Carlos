package model;

public enum GlucoseClassification {
    NORMAL(0, "Normal"),
    PREDIABETES(1, "Prediabetes"),
    DIABETES(2, "Diabetes");

    private final int value;
    private final String description;

    GlucoseClassification(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static GlucoseClassification classify(int glucoseLevel) {
        if (glucoseLevel < 100) {
            return NORMAL;
        } else if (glucoseLevel <= 125) {
            return PREDIABETES;
        } else {
            return DIABETES;
        }
    }
}
