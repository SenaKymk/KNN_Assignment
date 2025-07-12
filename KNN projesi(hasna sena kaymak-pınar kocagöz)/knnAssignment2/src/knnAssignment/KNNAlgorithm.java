package knnAssignment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KNNAlgorithm {

    public static int classify(List<DataPoint> dataPoints, DataPoint query, int k) {
        List<Neighbor> neighbors = new ArrayList<>();

        // Tüm veri noktaları ile sorgu arasındaki mesafeyi hesapla
        for (DataPoint point : dataPoints) {
            double distance = calculateDistance(point, query);
            neighbors.add(new Neighbor(point.getLabel(), distance));
        }

        // Komşuları mesafeye göre sırala
        neighbors.sort(Comparator.comparingDouble(Neighbor::getDistance));

        // İlk k komşuyu seç
        int[] classCounts = new int[2]; // Örnekte 2 sınıf (0 ve 1) var
        for (int i = 0; i < k; i++) {
            classCounts[neighbors.get(i).getLabel()]++;
        }

        // Çoğunluk sınıfını döndür
        return classCounts[1] > classCounts[0] ? 1 : 0;
    }

    private static double calculateDistance(DataPoint p1, DataPoint p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.getFeatures().length; i++) {
            sum += Math.pow(p1.getFeatures()[i] - p2.getFeatures()[i], 2);
        }
        return Math.sqrt(sum);
    }

    // Komşuları temsil eden iç sınıf
    private static class Neighbor {
        private final int label;
        private final double distance;

        public Neighbor(int label, double distance) {
            this.label = label;
            this.distance = distance;
        }

        public int getLabel() {
            return label;
        }

        public double getDistance() {
            return distance;
        }
    }
}
