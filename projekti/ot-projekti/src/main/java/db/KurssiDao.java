/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import projekti.Kurssi;

/**
 *
 * @author Saku
 */
public class KurssiDao implements Dao<Kurssi, Integer> {

    private Database database;

    public KurssiDao(Database database) {
        this.database = database;
    }

    @Override
    public Kurssi findOne(Integer key) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssit WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Kurssi kurssi = new Kurssi(rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return kurssi;

    }

    @Override
    public Kurssi saveOrUpdate(Kurssi object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kurssit WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Override
    public List<Kurssi> findAll() throws SQLException {
        List<Kurssi> kurssit = new ArrayList<>();
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kurssit");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Kurssi kurssi = new Kurssi(rs.getString("nimi"));
                //kurssi.setId(rs.getInt("KurssiId"));
                kurssit.add(kurssi);
             }
            rs.close();
            stmt.close();
        }
                
        return kurssit;
    }

    public void save(Kurssi kurssi) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kurssit"
                + " (nimi)"
                + " VALUES (?)");
        //stmt.setInt(1, kurssi.getId());
        stmt.setString(1, kurssi.getNimi());

        stmt.executeUpdate();
        stmt.close();

    }

    public Kurssi findOneNimella(String key) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssit WHERE nimi = ?");
        stmt.setString(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Kurssi kurssi = new Kurssi(rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return kurssi;

    }

    public void deleteAll() throws SQLException {
        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE * FROM Kurssit");

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

   
    public void deleteNimella(String key) throws SQLException {
        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kurssit WHERE nimi = ?");

        stmt.setString(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

     public int findOneIntNimella(String key) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssit WHERE nimi = ?");
        stmt.setString(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return 0;
        }

        int id = rs.getInt("Id");

        stmt.close();
        rs.close();

        conn.close();

        return id;

    }
    
}
