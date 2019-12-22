/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.DeadlineDao;
import db.KurssiDao;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

/**
 *
 * @author Saku
 */
public class DeadlinenPoistoNappi {
    private Button poista;
    private String poistettavaDeadline;
    private String kurssi;
    private DeadlineDao dao;
    
    public DeadlinenPoistoNappi(String poistettava, String kurssi, DeadlineDao K, Button button) {
        this.dao = K;
        this.poistettavaDeadline = poistettava;
        this.poista = new Button("poista");
        this.kurssi = kurssi;
        
          poista.setOnAction(event -> {
            try {
                dao.deleteNimella(poistettava, kurssi);
            } catch (SQLException ex) {
                Logger.getLogger(PoistoNappi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            button.fire();
            
        });
        
    }
    
    public Button getNappi() {
        return poista;
    }
}
