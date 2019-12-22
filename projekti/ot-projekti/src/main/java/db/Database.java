/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Saku
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String database;

    public Database(String database) throws ClassNotFoundException {
        this.database = database;
        //this.init();
    }

    /**
     * Gets connection to database.
     *
     * @return Connection
     * @throws SQLException
     */
    /* public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {

        }
        return DriverManager.getConnection(database);
    }*/
    public Connection getConnection() throws SQLException {
        //String dbUrl = System.getenv("org.sqlite.JDBC");

        return DriverManager.getConnection(database);
    }

    /**
     * Makes new database if old one doesn't exist.
     */
    public void init() {
        List<String> commands = this.sqliteCommands();

        // "try with resources" close the resources in the end
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            for (String com : commands) {
                System.out.println("Running command >> " + com);
                st.executeUpdate(com);
            }

        } catch (Throwable t) {
            // if db is initialized -> failed to run commands and stop
            System.out.println("Error >> " + t.getMessage());
        }
    }

    /* * Creates table if making new database;
     *
     * @return List that goes to database. */
    private List<String> sqliteCommands() {
        ArrayList<String> list = new ArrayList<>();

        /* list.add("CREATE TABLE Kurssit ("
                + "id integer PRIMARY KEY, "
                + "nimi varchar(50));");

        list.add("CREATE TABLE Deadlinet ("
                + "id integer PRIMARY KEY, "
                + "FOREIGN KEY (Kurssi.id) REFERENCES Kurssit(Kurssi.id), "
                + "nimi varchar(50), "
                + "pakollisuus Boolean, "
                + "tehty boolean, "
                + "paivamaara Date), "
                + "aika String);"); */
        list.add("CREATE TABLE Kurssit (KurssiId Integer PRIMARY KEY, nimi varchar(50));\n"
                + "INSERT INTO Kurssit VALUES(1,'testi');\n"
                + "CREATE TABLE Deadlinet (DeadlineId integer PRIMARY KEY, KurssiId integer, nimi varchar(50), pakollisuus boolean, tehty boolean, paivamaara varchar(20), aika varchar(5), kurssi varchar(50), FOREIGN KEY (KurssiId) REFERENCES Kurssit(KurssiId));");
        return list;
    }
}
