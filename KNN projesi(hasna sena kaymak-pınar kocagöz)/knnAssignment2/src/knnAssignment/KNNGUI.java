package knnAssignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KNNGUI extends JFrame {
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextField queryField;
    private JTextField kValueField;
    private JTextArea resultArea;
    private KNNController controller;

    public KNNGUI(KNNController controller) {
        this.controller = controller;

        setTitle("KNN Sınıflandırıcı");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tablo
        tableModel = new DefaultTableModel(new Object[]{"boy(cm)", "kilo(kg)", "BMI", "omuz genişliği(cm)","bel çevresi","ses frekansı(hz)","el boyutu(cm)","ayak numarası" ,"cinsiyet"}, 0);
        dataTable = new JTable(tableModel);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);

        // Kontrol paneli
        JPanel controlPanel = new JPanel(new GridLayout(2, 1));
        JPanel addPanel = new JPanel(new FlowLayout());
        JButton loadCSVButton = new JButton("CSV Yükle");
        JButton addSampleButton = new JButton("Örnek Ekle");
        JButton addFeatureButton = new JButton("Öznitelik Ekle");
        addPanel.add(loadCSVButton);
        addPanel.add(addSampleButton);
        addPanel.add(addFeatureButton);
        controlPanel.add(addPanel);

        JPanel classifyPanel = new JPanel(new FlowLayout());
        classifyPanel.add(new JLabel("Sorgu:"));
        queryField = new JTextField(15);
        classifyPanel.add(queryField);
        classifyPanel.add(new JLabel("k:"));
        kValueField = new JTextField(5);
        classifyPanel.add(kValueField);
        JButton classifyButton = new JButton("Sınıflandır");
        classifyPanel.add(classifyButton);
        controlPanel.add(classifyPanel);
        JButton newQueryButton = new JButton("Yeni Sorgu");
        classifyPanel.add(newQueryButton);
        newQueryButton.addActionListener(e -> {
            // Sorgu giriş alanını temizle
            queryField.setText("");

            // k değeri giriş alanını temizle
            kValueField.setText("");

            // Sonuç alanını temizle
            resultArea.setText("");

            // Bilgilendirme mesajı (isteğe bağlı)
            JOptionPane.showMessageDialog(this, "Sorgu alanı temizlendi. Yeni sorgu yapabilirsiniz.", 
                                          "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
        });
        JButton clearDataButton = new JButton("Veriyi Temizle");
        addPanel.add(clearDataButton);
        clearDataButton.addActionListener(e -> {
            // Kullanıcıya onay kutusu göster
            int confirmation = JOptionPane.showConfirmDialog(this, 
                                "Tüm veriyi silmek istediğinize emin misiniz?", 
                                "Veriyi Temizle", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                // Tablo modelini temizle
                tableModel.setRowCount(0);

                // Controller'daki veri listesini temizle
                controller.clearDataPoints();

                // Kullanıcıya bilgi mesajı göster
                JOptionPane.showMessageDialog(this, "Tüm veri başarıyla silindi.", 
                                              "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        add(controlPanel, BorderLayout.SOUTH);
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // Tablo ve sağ alan için JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(dataTable), resultScrollPane);
        splitPane.setResizeWeight(0.8); // Sol bölme (tablo) için daha fazla alan
        add(splitPane, BorderLayout.CENTER);
        // Buton işlevleri
        loadCSVButton.addActionListener(e -> loadCSV());
        addSampleButton.addActionListener(e -> addSample());
        addFeatureButton.addActionListener(e -> addFeatureColumn());
        classifyButton.addActionListener(e -> classify());

        setVisible(true);
    }

    private void addFeatureColumn() {
        // Kullanıcıdan sütun adı almak için bir giriş kutusu oluştur
        String newFeatureName = JOptionPane.showInputDialog(this, "Yeni öznitelik adı girin:", 
                                                            "Öznitelik Ekle", JOptionPane.PLAIN_MESSAGE);
        // Kullanıcı bir ad girdiyse işlemi gerçekleştir
        if (newFeatureName != null && !newFeatureName.trim().isEmpty()) {
            // Sınıf sütununun indeksini al
            int classColumnIndex = tableModel.getColumnCount() - 1;

            // Yeni sütunu eklemeden önce mevcut verileri geçici olarak sakla
            int rowCount = tableModel.getRowCount();
            Object[][] tempData = new Object[rowCount][tableModel.getColumnCount() + 1];
            String[] tempColumnNames = new String[tableModel.getColumnCount() + 1];

            // Geçici sütun isimlerini ve verileri doldur
            for (int col = 0; col < classColumnIndex; col++) {
                tempColumnNames[col] = tableModel.getColumnName(col);
                for (int row = 0; row < rowCount; row++) {
                    tempData[row][col] = tableModel.getValueAt(row, col);
                }
            }
            // Yeni sütun adını ekle
            tempColumnNames[classColumnIndex] = newFeatureName;
            for (int row = 0; row < rowCount; row++) {
                tempData[row][classColumnIndex] = ""; // Yeni sütun için boş değer
            }

            // Sınıf sütununu ekle
            tempColumnNames[classColumnIndex + 1] = tableModel.getColumnName(classColumnIndex);
            for (int row = 0; row < rowCount; row++) {
                tempData[row][classColumnIndex + 1] = tableModel.getValueAt(row, classColumnIndex);
            }

            // Modeli sıfırla ve yeni sütunları ekle
            tableModel.setColumnCount(0);
            for (String columnName : tempColumnNames) {
                tableModel.addColumn(columnName);
            }
            for (Object[] rowData : tempData) {
                tableModel.addRow(rowData);
            }
        } else {
            // Kullanıcı boş bıraktıysa veya iptal ettiyse uyarı göster
            JOptionPane.showMessageDialog(this, "Öznitelik adı boş bırakılamaz!", 
                                          "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void loadCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            controller.loadDataFromCSV(fileChooser.getSelectedFile().getPath());
            updateTable();
        }
    }

    private void addSample() {
        JDialog dialog = new JDialog(this, "Yeni Veri Ekle", true);
        dialog.setLayout(new GridLayout(tableModel.getColumnCount() + 1, 2));
        dialog.setSize(400, 200);

        JTextField[] textFields = new JTextField[tableModel.getColumnCount()];
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            dialog.add(new JLabel(tableModel.getColumnName(i)));
            textFields[i] = new JTextField();
            dialog.add(textFields[i]);
        }

        JButton addButton = new JButton("Ekle");
        dialog.add(addButton);
        JButton cancelButton = new JButton("Kapat");
        dialog.add(cancelButton);

        addButton.addActionListener(e -> {
            Object[] newRow = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < textFields.length; i++) {
                newRow[i] = textFields[i].getText();
            }
            tableModel.addRow(newRow);
            dialog.dispose();
            updateTable(); // Tabloyu güncelle
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }private void classify() {
        try {
            String[] queryValues = queryField.getText().split(",");
            double[] features = new double[queryValues.length];

            for (int i = 0; i < queryValues.length; i++) {
                features[i] = Double.parseDouble(queryValues[i].trim());
            }

            int featureCount = tableModel.getColumnCount() - 1; // Sınıf sütununu çıkar

            if (features.length != featureCount) {
                throw new IllegalArgumentException("Sorgu için yanlış öznitelik sayısı girdiniz!");
            }

            int k = Integer.parseInt(kValueField.getText());

            int result = controller.classify(features, k);

            resultArea.setText("Sonuç: " + (result == 1 ? "Kadın" : "Erkek"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçerli bir giriş yapın! (Örn: 170,70,20.5)", "Hata", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Tabloyu temizle
        List<DataPoint> dataPoints = controller.getDataPoints();

        for (DataPoint dp : dataPoints) {
            Object[] row = new Object[dp.getFeatures().length + 1];
            for (int i = 0; i < dp.getFeatures().length; i++) {
                row[i] = dp.getFeatures()[i];
            }
            row[dp.getFeatures().length] = dp.getLabel();
            tableModel.addRow(row);
        }

        controller.updateDataPointsFromTable(tableModel); // Veri listesini güncelle
    }


    }

