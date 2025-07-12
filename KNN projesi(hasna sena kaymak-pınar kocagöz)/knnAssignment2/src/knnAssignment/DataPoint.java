package knnAssignment;
public class DataPoint {
    private double[] features; // Özellikler (f1, f2, ...)
    private int label; // Sınıf (0: Sağlıklı, 1: Hasta)

    public DataPoint(double[] features, int label) {
        this.features = features;
        this.label = label;
    }

    public double[] getFeatures() {
        return features;
    }

    public int getLabel() {
        return label;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Features: ");
        for (double feature : features) {
            sb.append(feature).append(" ");
        }
        sb.append("Class: ").append(label);
        return sb.toString();
    }
}

