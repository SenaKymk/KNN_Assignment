package knnAssignment;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;

public class KNNController {
    private List<DataPoint> dataPoints;

    public KNNController() {
        this.dataPoints = new ArrayList<>();
    }
    public void removeDataPoint(int index) {
        if (index >= 0 && index < dataPoints.size()) {
            dataPoints.remove(index);
        }
    }
    public void clearDataPoints() {
        dataPoints.clear();
    }
    public void updateDataPointsFromTable(DefaultTableModel tableModel) {
        dataPoints.clear(); // Eski verileri temizle
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int columnCount = tableModel.getColumnCount() - 1; // Sınıf sütununu çıkar
            double[] features = new double[columnCount];
            for (int j = 0; j < columnCount; j++) {
                features[j] = Double.parseDouble(tableModel.getValueAt(i, j).toString());
            }
            int label = Integer.parseInt(tableModel.getValueAt(i, columnCount).toString());
            dataPoints.add(new DataPoint(features, label));
        }
    }


    public void loadDataFromCSV(String filePath) {
        dataPoints = CSVLoader.loadCSV(filePath);
    }

    public void addDataPoint(double[] features, int label) {
        dataPoints.add(new DataPoint(features, label));
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public int classify(double[] features, int k) {
        DataPoint query = new DataPoint(features, -1);
        return KNNAlgorithm.classify(dataPoints, query, k);
    }
}
