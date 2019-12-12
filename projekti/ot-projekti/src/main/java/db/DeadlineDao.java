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
import projekti.Deadline;
import projekti.Kurssi;

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
        java.util.Date jotain1 = new java.util.Date(rs.getDate("paivamaara").getTime());

        Deadline deadline = new Deadline(rs.getString("nimi"), rs.getBoolean("pakollisuus"), jotain1, rs.getString("aika"));
        if (rs.getBoolean("tehty")) {
            deadline.tehty();
        }

        stmt.close();
        rs.close();

        conn.close();

        return deadline;
    }

    @Override
    public List<Deadline> findAll() throws SQLException {
        List<Deadline> deadlinet = new ArrayList<>();
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Deadlinet");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            
            java.util.Date jotain1 = new java.util.Date(rs.getDate("paivamaara").getTime());
            Deadline deadline = new Deadline(rs.getString("nimi"), rs.getBoolean("pakollisuus"), jotain1, rs.getString("aika"));
            if (rs.getBoolean("tehty")) {
                deadline.tehty();
            }
            deadlinet.add(deadline);
        }

        stmt.close();
        rs.close();

        connection.close();
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
    
    
    public void saveJee(Deadline deadline, String kurssi) throws SQLException {

        java.sql.Date jotain = new java.sql.Date(deadline.getDate().getTime());

        Connection conn = database.getConnection();
        //PreparedStatement stmt = conn.prepareStatement("INSERT INTO Deadlinet"
         //       + " (FOREIGN KEY, nimi, pakollisuus, tehty, paivamaara, aika)"
        //        + " VALUES ( ?, ?, ?, ?, ?, ?)");
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Deadlinet"
        + "(KurssiId, nimi, pakollisuus, tehty, paivamaara, aika)"
            + " VALUES ((SELECT KurssiId from Kurssit where nimi = '?'), ?, ?, ?, ?, '?')");
        
        //stmt.setInt(1, kurssi.getId());
        stmt.setString(1, kurssi);
        stmt.setString(2, deadline.getNimi());
        stmt.setBoolean(3, deadline.onkoPakollinen());
        stmt.setBoolean(4, deadline.onkoTehty());
        stmt.setDate(5, jotain);        
        stmt.setString(6, deadline.getAika());

        stmt.executeUpdate();
        stmt.close();
        
        conn.close();

    }

    @Override
    public Deadline saveOrUpdate(Deadline object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void update(Deadline deadline) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Deadlinet SET"
                + " tehty = ? WHERE id = ?");
        stmt.setBoolean(1, deadline.onkoTehty());
        stmt.setInt(2, deadline.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        
    }

}
