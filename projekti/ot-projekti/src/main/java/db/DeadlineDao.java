/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.Deadline;
import domain.Kurssi;
import java.util.Collections;

/**
 *
 * @author Saku
 */
public class DeadlineDao implements Dao<Deadline, Integer> {

    private Database database;

    public DeadlineDao(Database database) {
        this.database = database;
    }

    @Override
    public Deadline findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Deadlinet WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        //java.sql.Date jotain = new java.sql.Date(deadline.getDate().getTime());
        //java.util.Date jotain1 = new java.util.Date(rs.getDate("paivamaara").getTime());
        String jotain1 = rs.getString("paivamaara");

        Deadline deadline = new Deadline(rs.getString("nimi"), rs.getBoolean("pakollisuus"), jotain1, rs.getString("aika"), rs.getString("kurssi"));
        if (rs.getBoolean("tehty")) {
            deadline.tehty();
        }

        stmt.close();
        rs.close();

        conn.close();

        return deadline;
    }

    public List<Deadline> findAllKurssista(String kurssi) throws SQLException {
        List<Deadline> deadlinet = new ArrayList<>();
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Deadlinet WHERE KurssiId = (SELECT KurssiId from Kurssit where nimi = ?) ");
        stmt.setString(1, kurssi);
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            String jotain1 = rs.getString("paivamaara");
            Deadline deadline = new Deadline(rs.getString("nimi"), rs.getBoolean("pakollisuus"), jotain1, rs.getString("aika"), rs.getString("kurssi"));
            if (rs.getBoolean("tehty")) {
                deadline.tehty();
            }
            deadlinet.add(deadline);
        }
        
        stmt.close();
        connection.close();
        
        
        Collections.sort(deadlinet);
        return deadlinet;
    }   
    
    
    @Override
    public List<Deadline> findAll() throws SQLException {
        List<Deadline> deadlinet = new ArrayList<>();
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Deadlinet");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            String jotain1 = rs.getString("paivamaara");
            Deadline deadline = new Deadline(rs.getString("nimi"), rs.getBoolean("pakollisuus"), jotain1, rs.getString("aika"), rs.getString("kurssi"));
            if (rs.getBoolean("tehty")) {
                deadline.tehty();
            }
            deadlinet.add(deadline);
        }

        stmt.close();
        rs.close();

        connection.close();
        
        Collections.sort(deadlinet);
        return deadlinet;
    }

    public void delete(Integer key) throws SQLException {

        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Deadlinet WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();

    }

    public void saveJee(Deadline deadline) throws SQLException {

        //java.sql.Date jotain = new java.sql.Date(deadline.getDate().getTime());
        String jotain = deadline.getDate();
        Connection conn = database.getConnection();
        //PreparedStatement stmt = conn.prepareStatement("INSERT INTO Deadlinet"
        //       + " (FOREIGN KEY, nimi, pakollisuus, tehty, paivamaara, aika)"
        //        + " VALUES ( ?, ?, ?, ?, ?, ?)");

        /* INSERT INTO bar (description, foo_id) VALUES
        ( 'testing',     SELECT id from foo WHERE type='blue' ),
        ( 'another row', SELECT id from foo WHERE type='red'  ); */
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Deadlinet "
                + "(KurssiId, nimi, pakollisuus, tehty, paivamaara, aika, kurssi)"
                + " VALUES ((SELECT KurssiId from Kurssit where nimi = ?), ?, ?, ?, ?, ?, ?)");

        //stmt.setInt(1, kurssi.getId());
        stmt.setString(1, deadline.getKurssi());
        stmt.setString(2, deadline.getNimi());
        stmt.setBoolean(3, deadline.onkoPakollinen());
        stmt.setBoolean(4, deadline.onkoTehty());
        stmt.setString(5, deadline.getDate());
        stmt.setString(6, deadline.getAika());
        stmt.setString(7, deadline.getKurssi());
        stmt.executeUpdate();
        stmt.close();

        conn.close();

    }

    @Override
    public Deadline saveOrUpdate(Deadline object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(Deadline deadline) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Deadlinet SET"
                + " tehty = ? WHERE nimi = ? AND kurssi = ?");
        stmt.setBoolean(1, deadline.onkoTehty());
        stmt.setString(2, deadline.getNimi());
        stmt.setString(3, deadline.getKurssi());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

    }
        
   
        
    
   /* public String findKurssinNimi() throws SQLException {
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT nimi FROM Deadlinet WHERE id = (SELECT )");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
    } */
    
    public void deleteNimella(String nimi, String kurssinNimi) throws SQLException {

        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Deadlinet WHERE nimi = ? AND kurssi = ?");

        stmt.setString(1, nimi);
        stmt.setString(2, kurssinNimi);
        stmt.executeUpdate();
        stmt.close();
        conn.close();

    }
    
    public void deleteAllNimella(String kurssinNimi) throws SQLException {
        Connection conn = this.database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Deadlinet WHERE kurssi = ?");

        stmt.setString(1, kurssinNimi);
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
}
