package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;

public class SQLRealEstateData implements RealEstateData {

    // Converts the date object to a string that can be used to make SQL requests
    public static String csvDateConverter(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public Connection connectToDB() throws Exception {
        try {
            Properties secretStorage = new Properties();
            /* 
             * Make sure you're in the eecs3311-project-bravo directory
             * Create a .env file in the models directory
             * Add this the .env file: PASSWORD=password_here
             */
            FileInputStream secretFile = new FileInputStream("./src/models/.env");
            secretStorage.load(secretFile);
            secretFile.close();

            // Puts the driver in a string
            String driver = "com.mysql.cj.jdbc.Driver";

            // Puts the location of the database in a string
            String DBLocation = "jdbc:mysql://localhost:3306/realestatedatabase";

            // Sets the username, changes based on what you set it too, but if you dont it
            // defaults to root
            String user = "root";

            // This is the password, depends on what you set it to
            String pass = secretStorage.getProperty("PASSWORD");

            // Loads the driver
            Class.forName(driver);

            // Makes the connection to the database by taking in the location, username and
            // password
            Connection connectionToDB = DriverManager.getConnection(DBLocation, user, pass);

            return connectionToDB;

        } catch (Exception nameOfException) {
            System.out.println("Something went wrong, exception: " + nameOfException);
        }

        // So if a connection is established successfully we already return the
        // connection object, however if there is an error in connection then we return
        // null, and handle the situation from the method call location
        return null;
    }

    /* TODO: Add code that returns the appropiate set of queries based on granularity 
     * Pass in Form object for simplicity if that's easier
    */
    public ArrayList<QueryResult> returnData(String location, String dateA, String dateB) {
        // Creates an arraylist of query result objects used to store our queryresuts
        ArrayList<QueryResult> queryResults = new ArrayList<QueryResult>();

        // Makes a request if the user look at a city
        try {
            // Calls the connectToDB method to make a connection to the database
            Connection connectionToDB = connectToDB();

            // Sets up the SQL query string
            String sqlQuery = "SELECT Ref_Date, NHPIValue, Geo " + "FROM Data " + "WHERE Geo = ? "
                    + "AND Ref_Date BETWEEN ? AND ? " + "AND New_House_Pricing_Indexes = ?";

            // Prepares our SQL query before making it
            PreparedStatement preppedSQLQuery = connectionToDB.prepareStatement(sqlQuery);
            // Fills in the question marks in our SQL query with the values needed
            preppedSQLQuery.setString(1, location);
            preppedSQLQuery.setString(2, dateA);
            preppedSQLQuery.setString(3, dateB);
            preppedSQLQuery.setString(4, "House only");

            // Exectues query and then stores the results in the result set object
            ResultSet result = preppedSQLQuery.executeQuery();

            // Goes through the SQL query and adds it to our QueryResult object, then adds
            // the object to the list
            while (result.next()) {
                QueryResult queryResultObject = new QueryResult(result.getString("Geo"),
                        result.getDouble("NHPIValue"), result.getString("Ref_Date"));
                queryResults.add(queryResultObject);
            }
            // Returns the arraylist of our query results
            return queryResults;
        } catch (Exception nameOfException) {
            System.out.println("Something went wrong, exception: " + nameOfException);
        }
        // If there is an error in the query then we return null
        return null;
    }

    public static void main(String args[]) {
        // For testing program

        // Creates local date objects and converts them to string using the
        // csvDateConverter method
        LocalDate dateA = LocalDate.of(1999, 12, 5);
        LocalDate dateB = LocalDate.of(2001, 6, 7);
        String convertedDateA = csvDateConverter(dateA);
        String convertedDateB = csvDateConverter(dateB);

        // Creates a form object to test the program
        Form testForm = new Form(true, true, "Alberta", "Ontario", "Calgary", "Toronto", true, dateA,
                dateB);

        // Creates two arraylists to store the results from the database
        ArrayList<QueryResult> databaseResultOne = new ArrayList<QueryResult>();
        ArrayList<QueryResult> databaseResultTwo = new ArrayList<QueryResult>();

        // Creates a SQLRealEstateDate object so we can call the method
        SQLRealEstateData caller = new SQLRealEstateData();

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm.inputLocation.isGeoACity) {
            String locationOne = (testForm.inputLocation.cityA + ", " + testForm.inputLocation.provinceA);
            databaseResultOne = caller.returnData(locationOne, convertedDateA, convertedDateB);
        } else {
            String locationOne = testForm.inputLocation.provinceA;
            databaseResultOne = caller.returnData(locationOne, convertedDateA, convertedDateB);
        }

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm.inputLocation.isGeoBCity) {
            String locationTwo = (testForm.inputLocation.cityB + ", " + testForm.inputLocation.provinceB);
            databaseResultTwo = caller.returnData(locationTwo, convertedDateA, convertedDateB);
        } else {
            String locationTwo = testForm.inputLocation.provinceB;
            databaseResultTwo = caller.returnData(locationTwo, convertedDateA, convertedDateB);
        }

        // Prints the results of the first location
        for (int i = 0; i < databaseResultOne.size(); i++) {
            System.out.println(databaseResultOne.get(i).location);
            System.out.println(databaseResultOne.get(i).NHPIIndexValue);
            System.out.println(databaseResultOne.get(i).date);
        }

        // Prints the results of the second location
        System.out.println("This is the second line");

        for (int i = 0; i < databaseResultTwo.size(); i++) {
            System.out.println(databaseResultTwo.get(i).location);
            System.out.println(databaseResultTwo.get(i).NHPIIndexValue);
            System.out.println(databaseResultTwo.get(i).date);
        }

        // Creates a form object to test the program
        Form testForm2 = new Form(false, false, "Alberta", "Ontario", "", "", true, dateA,
                dateB);

        // Creates two arraylists to store the results from the database
        ArrayList<QueryResult> databaseResultThree = new ArrayList<QueryResult>();
        ArrayList<QueryResult> databaseResultFour = new ArrayList<QueryResult>();

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm2.inputLocation.isGeoACity == true) {
            String locationOne = (testForm2.inputLocation.cityA + ", " + testForm2.inputLocation.cityB);
            databaseResultThree = caller.returnData(locationOne, convertedDateA, convertedDateB);
        } else {
            String locationOne = testForm.inputLocation.cityB;
            databaseResultThree = caller.returnData(locationOne, convertedDateA, convertedDateB);
        }

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm2.inputLocation.isGeoBCity == true) {
            String locationTwo = (testForm2.inputLocation.cityB + ", " + testForm2.inputLocation.provinceB);
            databaseResultFour = caller.returnData(locationTwo, convertedDateA, convertedDateB);
        } else {
            String locationTwo = testForm2.inputLocation.provinceB;
            databaseResultFour = caller.returnData(locationTwo, convertedDateA, convertedDateB);
        }

        // Prints the results of the first location
        for (int i = 0; i < databaseResultThree.size(); i++) {
            System.out.println(databaseResultThree.get(i).location);
            System.out.println(databaseResultThree.get(i).NHPIIndexValue);
            System.out.println(databaseResultThree.get(i).date);
        }

        // Prints the results of the second location
        System.out.println("This is the second line");

        for (int i = 0; i < databaseResultFour.size(); i++) {
            System.out.println(databaseResultFour.get(i).location);
            System.out.println(databaseResultFour.get(i).NHPIIndexValue);
            System.out.println(databaseResultFour.get(i).date);
        }

    }
}