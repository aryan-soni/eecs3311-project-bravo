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

    public ArrayList<QueryResult> returnData(String location, String dateA, String dateB, Boolean goingByMonths) {
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

            // If going by months enter this body
            if (goingByMonths) {
                // Goes through the SQL query and adds it to our QueryResult object, then adds
                // the object to the list
                while (result.next()) {
                    QueryResult queryResultObject = new QueryResult(result.getString("Geo"),
                            result.getDouble("NHPIValue"), result.getString("Ref_Date"));
                    queryResults.add(queryResultObject);
                }
            } else {
                // So on your end if we are going by years make sure that the start date has
                // January as the month and the end date has December

                // Creates a double that will be used to store the yearly value
                Double yearlyIndexVal = 0.0;

                // Goes through the our SQL query and calcualtes the yearly index values and
                // then adds them to the list
                while (result.next()) {
                    // Takes the month off our date string in order to help with calculating yearly
                    // indexes
                    String currentMonth = result.getString("Ref_Date").substring(5, 7);

                    // Conditional statement that handles the cases in which the current month is
                    // Jan, Dec and the rest of the months
                    if (currentMonth.equals("01")) {

                        // When the current month is January, set the index value to what January is, it
                        // "resets" the count for the year
                        yearlyIndexVal = result.getDouble("NHPIValue");

                    } else if (currentMonth.equals("12")) {

                        // If the month is December, then we would have all the values needed to
                        // calculate the yearly index value, so we add deceembers index and then divide
                        // it by 12 to get the yearly index value
                        yearlyIndexVal = (yearlyIndexVal + result.getDouble("NHPIValue")) / 12;

                        // Uses math.round to round our yearly index value to 1 decimal place
                        Double roundedYearlyIndexVal = Math.round(yearlyIndexVal * 10.0) / 10.0;

                        // Gets the current year from the date string
                        String getCurrentYear = result.getString("Ref_Date").substring(0, 4);

                        // Creates the query result object for the year and adds it to our array list
                        QueryResult queryResultObject = new QueryResult(result.getString("Geo"), roundedYearlyIndexVal,
                                getCurrentYear);

                        // Add the query result object to our array list
                        queryResults.add(queryResultObject);

                    } else {
                        // Just adds the index value to the yearly index value, as we do not have to do
                        // any other specfic behvaiours in the months that are not Jan or Dec
                        yearlyIndexVal = yearlyIndexVal + result.getDouble("NHPIValue");
                    }
                }
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
            databaseResultOne = caller.returnData(locationOne, convertedDateA, convertedDateB,
                    testForm.tSeries.isGranularByMonths);
        } else {
            String locationOne = testForm.inputLocation.provinceA;
            databaseResultOne = caller.returnData(locationOne, convertedDateA, convertedDateB,
                    testForm.tSeries.isGranularByMonths);
        }

        // Handles if location 2 is a city or a province and then calls the method
        // accordingly
        if (testForm.inputLocation.isGeoBCity) {
            String locationTwo = (testForm.inputLocation.cityB + ", " + testForm.inputLocation.provinceB);
            databaseResultTwo = caller.returnData(locationTwo, convertedDateA, convertedDateB,
                    testForm.tSeries.isGranularByMonths);
        } else {
            String locationTwo = testForm.inputLocation.provinceB;
            databaseResultTwo = caller.returnData(locationTwo, convertedDateA, convertedDateB,
                    testForm.tSeries.isGranularByMonths);
        }

        System.out.println("\n------------First Test Prints:---------------");
        // Prints the results of the first location
        for (int i = 0; i < databaseResultOne.size(); i++) {
            System.out.println(databaseResultOne.get(i).location);
            System.out.println(databaseResultOne.get(i).NHPIIndexValue);
            System.out.println(databaseResultOne.get(i).date);
        }

        // Prints the results of the second location
        System.out.println("\n----------------This is the second line----------------\n");

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
            String locationOne = (testForm2.inputLocation.cityA + ", " + testForm2.inputLocation.provinceA);
            databaseResultThree = caller.returnData(locationOne, convertedDateA, convertedDateB,
                    testForm2.tSeries.isGranularByMonths);
        } else {
            String locationOne = testForm.inputLocation.provinceA;
            databaseResultThree = caller.returnData(locationOne, convertedDateA, convertedDateB,
                    testForm2.tSeries.isGranularByMonths);
        }

        // Handles if location 2 is a city or a province and then calls the method
        // accordingly
        if (testForm2.inputLocation.isGeoBCity == true) {
            String locationTwo = (testForm2.inputLocation.cityB + ", " + testForm2.inputLocation.provinceB);
            databaseResultFour = caller.returnData(locationTwo, convertedDateA, convertedDateB,
                    testForm2.tSeries.isGranularByMonths);
        } else {
            String locationTwo = testForm2.inputLocation.provinceB;
            databaseResultFour = caller.returnData(locationTwo, convertedDateA, convertedDateB,
                    testForm2.tSeries.isGranularByMonths);
        }

        System.out.println("\n------------Second Test Prints:---------------\n");

        // Prints the results of the first location
        for (int i = 0; i < databaseResultThree.size(); i++) {
            System.out.println(databaseResultThree.get(i).location);
            System.out.println(databaseResultThree.get(i).NHPIIndexValue);
            System.out.println(databaseResultThree.get(i).date);
        }

        // Prints the results of the second location
        System.out.println("\n----------------This is the second line----------------\n");

        for (int i = 0; i < databaseResultFour.size(); i++) {
            System.out.println(databaseResultFour.get(i).location);
            System.out.println(databaseResultFour.get(i).NHPIIndexValue);
            System.out.println(databaseResultFour.get(i).date);
        }

        // Creates a form object to test the program
        Form testForm3 = new Form(false, false, "Alberta", "Ontario", "", "", false, dateA,
                dateB);

        // Creates two arraylists to store the results from the database
        ArrayList<QueryResult> databaseResultFive = new ArrayList<QueryResult>();
        ArrayList<QueryResult> databaseResultSix = new ArrayList<QueryResult>();

        // Handles if location 1 is a city or a province and then calls the method
        // accordingly
        if (testForm3.inputLocation.isGeoACity == true) {
            String locationOne = (testForm3.inputLocation.cityA + ", " + testForm3.inputLocation.provinceA);
            databaseResultFive = caller.returnData(locationOne, convertedDateA, convertedDateB,
                    testForm3.tSeries.isGranularByMonths);
        } else {
            String locationOne = testForm.inputLocation.provinceA;
            databaseResultFive = caller.returnData(locationOne, convertedDateA, convertedDateB,
                    testForm3.tSeries.isGranularByMonths);
        }

        // Handles if location 2 is a city or a province and then calls the method
        // accordingly
        if (testForm3.inputLocation.isGeoBCity == true) {
            String locationTwo = (testForm3.inputLocation.cityB + ", " + testForm3.inputLocation.provinceB);
            databaseResultSix = caller.returnData(locationTwo, convertedDateA, convertedDateB,
                    testForm3.tSeries.isGranularByMonths);
        } else {
            String locationTwo = testForm3.inputLocation.provinceB;
            databaseResultSix = caller.returnData(locationTwo, convertedDateA, convertedDateB,
                    testForm3.tSeries.isGranularByMonths);
        }

        System.out.println("\n------------Third Test Prints:---------------\n");

        // Prints the results of the first location
        for (int i = 0; i < databaseResultFive.size(); i++) {
            System.out.println(databaseResultFive.get(i).location);
            System.out.println(databaseResultFive.get(i).NHPIIndexValue);
            System.out.println(databaseResultFive.get(i).date);
        }

        // Prints the results of the second location
        System.out.println("\n----------------This is the second line----------------\n");

        for (int i = 0; i < databaseResultSix.size(); i++) {
            System.out.println(databaseResultSix.get(i).location);
            System.out.println(databaseResultSix.get(i).NHPIIndexValue);
            System.out.println(databaseResultSix.get(i).date);
        }

    }
}