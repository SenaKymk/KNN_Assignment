package knnAssignment;
import java.util.List;

public class KNNMain {
    public static void main(String[] args) {
        String filePath = "DATASET.csv";

        // CSV Dosyasını Yükleme
        List<DataPoint> dataPoints = CSVLoader.loadCSV(filePath);
        System.out.println("Yüklenen Veri Noktaları:");
        for (DataPoint dp : dataPoints) {
            System.out.println(dp);
        }

        // CSV'nin Ham İçeriğini Görüntüleme
        System.out.println("\nCSV Dosyası İçeriği:");
        CSVReader.printCSV(filePath);
        KNNController controller = new KNNController();

        // Arayüzü başlat
        new KNNGUI(controller);
    }
}

