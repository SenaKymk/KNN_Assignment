package knnAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {
    public static List<DataPoint> loadCSV(String filePath) {
        List<DataPoint> dataPoints = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // İlk satırı (başlık) atlamak için

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // İlk satırı atla
                }

                // Satırı virgülle ayır
                String[] values = line.split(",");

                // Özellikleri ve sınıfı ayır
                double[] features = new double[values.length - 1];
                for (int i = 0; i < values.length - 1; i++) {
                    features[i] = Double.parseDouble(values[i].trim());
                }
                int label = Integer.parseInt(values[values.length - 1].trim());

                // Veri noktasını listeye ekle
                dataPoints.add(new DataPoint(features, label));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataPoints;
    }
}
