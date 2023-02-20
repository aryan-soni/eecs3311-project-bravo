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

    public static void main(String args[]) {
        // For testing program

        // Creates local date objects and converts them to string using the
        // csvDateConverter method
        LocalDate inDate1 = LocalDate.of(1999, 12, 5);
        LocalDate inDate2 = LocalDate.of(2001, 6, 7);
        String convertedDateOne = csvDateConverter(inDate1);
        String convertedDateTwo = csvDateConverter(inDate2);

        // Creates a form object to test the program
        Form testForm = new Form(true, true, "Alberta", "Ontario", "Calgary", "Toronto", true, inDate1,
                inDate2);

        // Creates two arraylists to store the results from the database
        ArrayList<QueryResult> databaseResultOne = new ArrayList<QueryResult>();
        ArrayList<QueryResult> databaseResultTwo = new ArrayList<QueryResult>();

        // Creates a SQLRealEstateDate object so we can call the method
        SQLRealEstateData caller = new SQLRealEstateData();

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm.inputLocation.isLocationOneACity == true) {
            String locationOne = (testForm.inputLocation.cityNameOne + ", " + testForm.inputLocation.provinceNameOne);
            databaseResultOne = caller.returnData(locationOne, convertedDateOne, convertedDateTwo);
        } else {
            String locationOne = testForm.inputLocation.provinceNameOne;
            databaseResultOne = caller.returnData(locationOne, convertedDateOne, convertedDateTwo);
        }

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm.inputLocation.isLocationTwoACity == true) {
            String locationTwo = (testForm.inputLocation.cityNameTwo + ", " + testForm.inputLocation.provinceNameTwo);
            databaseResultTwo = caller.returnData(locationTwo, convertedDateOne, convertedDateTwo);
        } else {
            String locationTwo = testForm.inputLocation.provinceNameTwo;
            databaseResultTwo = caller.returnData(locationTwo, convertedDateOne, convertedDateTwo);
        }

        // Prints the results of the first location
        for (int i = 0; i < databaseResultOne.size(); i++) {
            System.out.println(databaseResultOne.get(i).location);
            System.out.println(databaseResultOne.get(i).NHPIIndexValue);
            System.out.println(databaseResultOne.get(i).dataDate);
        }

        // Prints the results of the second location
        System.out.println("This is the second line");

        for (int i = 0; i < databaseResultTwo.size(); i++) {
            System.out.println(databaseResultTwo.get(i).location);
            System.out.println(databaseResultTwo.get(i).NHPIIndexValue);
            System.out.println(databaseResultTwo.get(i).dataDate);
        }

        // Creates a form object to test the program
        Form testForm2 = new Form(false, false, "Alberta", "Ontario", "", "", true, inDate1,
                inDate2);

        // Creates two arraylists to store the results from the database
        ArrayList<QueryResult> databaseResultThree = new ArrayList<QueryResult>();
        ArrayList<QueryResult> databaseResultFour = new ArrayList<QueryResult>();

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm2.inputLocation.isLocationOneACity == true) {
            String locationOne = (testForm2.inputLocation.cityNameOne + ", " + testForm2.inputLocation.provinceNameOne);
            databaseResultThree = caller.returnData(locationOne, convertedDateOne, convertedDateTwo);
        } else {
            String locationOne = testForm.inputLocation.provinceNameOne;
            databaseResultThree = caller.returnData(locationOne, convertedDateOne, convertedDateTwo);
        }

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm2.inputLocation.isLocationTwoACity == true) {
            String locationTwo = (testForm2.inputLocation.cityNameTwo + ", " + testForm2.inputLocation.provinceNameTwo);
            databaseResultFour = caller.returnData(locationTwo, convertedDateOne, convertedDateTwo);
        } else {
            String locationTwo = testForm2.inputLocation.provinceNameTwo;
            databaseResultFour = caller.returnData(locationTwo, convertedDateOne, convertedDateTwo);
        }

        // Prints the results of the first location
        for (int i = 0; i < databaseResultThree.size(); i++) {
            System.out.println(databaseResultThree.get(i).location);
            System.out.println(databaseResultThree.get(i).NHPIIndexValue);
            System.out.println(databaseResultThree.get(i).dataDate);
        }

        // Prints the results of the second location
        System.out.println("This is the second line");

        for (int i = 0; i < databaseResultFour.size(); i++) {
            System.out.println(databaseResultFour.get(i).location);
            System.out.println(databaseResultFour.get(i).NHPIIndexValue);
            System.out.println(databaseResultFour.get(i).dataDate);
        }

    }

    // Converts the date object to a string that can be used to make SQL requests
    public static String csvDateConverter(LocalDate inDate) {
        DateTimeFormatter csvConverter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedDate = inDate.format(csvConverter);
        return formattedDate;
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

            // If the connection is made correctly we return the connection object
            System.out.println("Connection has been established");
            return connectionToDB;

        } catch (Exception nameOfException) {
            System.out.println("Something went wrong, exception: " + nameOfException);
        }

        // So if a connection is established successfully we already return the
        // connection object, however if there is an error in connection then we return
        // null, and handle the situation from the method call location
        return null;
    }

    public ArrayList<QueryResult> returnData(String inLocation, String inDate1, String inDate2) {
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
            preppedSQLQuery.setString(1, inLocation);
            preppedSQLQuery.setString(2, inDate1);
            preppedSQLQuery.setString(3, inDate2);
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
}