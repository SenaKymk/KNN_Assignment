# 👥 Gender Prediction Using KNN in Java

This is a Java-based desktop application that predicts gender (Male/Female) using the **K-Nearest Neighbors (KNN)** algorithm. The project includes a graphical user interface (GUI) and supports CSV-based dataset loading.

## 🧠 Algorithm

The application uses the **K-Nearest Neighbors (KNN)** algorithm, a widely used supervised machine learning method for classification problems. It predicts the label of a new instance by analyzing the 'k' closest data points from the training set and performing a majority vote.

---

## 📁 Project Structure

| File               | Description                                           |
|--------------------|-------------------------------------------------------|
| `CSVLoader.java`   | Handles loading of CSV files from the file system     |
| `CSVReader.java`   | Parses the CSV content into structured data points    |
| `DataPoint.java`   | Represents a single data instance with features/label |
| `KNNAlgorithm.java`| Implements the core KNN logic                         |
| `KNNController.java`| Manages interaction between UI and algorithm         |
| `KNNGUI.java`      | Graphical user interface implementation               |
| `KNNMain.java`     | Main class to run the application                     |

---

## ⚙️ How It Works

1. Load a CSV dataset that includes labeled examples (e.g., height, weight, shoe size, and gender).
2. The system converts each row into a `DataPoint` object.
3. When a user inputs new feature values, the algorithm:
   - Calculates the distance to every training data point
   - Selects the k nearest neighbors
   - Predicts the most frequent gender label among them
4. The predicted gender is displayed on the GUI.

---

## 🖥️ Features

- Easy-to-use Java Swing GUI
- Load datasets in `.csv` format
- Adjust `k` value for KNN
- Predict gender instantly

---

## 📝 Example CSV Format

```csv
Height,Weight,ShoeSize,Gender
180,80,44,Male
165,55,38,Female
170,65,40,Male
