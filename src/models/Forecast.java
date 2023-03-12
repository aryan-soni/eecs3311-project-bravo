package models;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.List;

// Notes: Size limit on the dataset, possibly filter this with IO
public class Forecast {
    public int numOfMonths;
    public CSVLoader loader = new CSVLoader();
    public ArffSaver saver = new ArffSaver();
    public WekaForecaster forecaster = new WekaForecaster();
    public PrintStream stream = new PrintStream("./src/temp.txt");

    public Forecast() throws Exception {
        // Parse the CSV
        loader.setSource(new File("src/data.csv")); // Our input file
        Instances data = loader.getDataSet();

        // Save it to format .arff for Weka
        saver.setInstances(data);
        saver.setFile(new File("src/data.arff"));
        saver.writeBatch();

        // Load .arff file
        Instances dataset = new Instances(new BufferedReader(new FileReader("src/data.arff")));

        // Initialize forecaster
        forecaster.setFieldsToForecast("VALUE"); // Predicted Value
        forecaster.setOverlayFields("REF_DATE"); // Overlay Variable
        forecaster.setBaseForecaster(new GaussianProcesses()); // This can change depending on the model we want
        forecaster.buildForecaster(dataset, stream);
        forecaster.primeForecaster(dataset);

        // Run the forecast
        List<List<NumericPrediction>> forecast = forecaster.forecast(numOfMonths, dataset, stream);

        // Output the forecast
        for (int i = 0; i < numOfMonths; i++) {
            List<NumericPrediction> prediction = forecast.get(i);
            NumericPrediction predictionData = prediction.get(0);
            System.out.println(predictionData.predicted()); // TODO CHANGE OUTPUT HERE WITH CONTROLLER
        }
    }
}
