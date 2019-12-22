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
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 *
 * @author Saku
 */
public class PoistoNappi {
    private Button poista;
    private String poistettavaKurssi;
    private KurssiDao dao;
    private DeadlineDao dDao;
    
    public PoistoNappi(String poistettava, KurssiDao K, Button button, DeadlineDao D){
        this.poista = new Button("poista");
        this.poistettavaKurssi = poistettava;
        this.dao = K;
        this.dDao = D;
        poista.setOnAction(event -> {
            try {
                dao.deleteNimella(poistettava);
                dDao.deleteAllNimella(poistettava);
            } catch (SQLException ex) {
                Logger.getLogger(PoistoNappi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            button.fire();
            
        });
    }
    
    public String getPoistettava() {
        return this.poistettavaKurssi;
    }
    
    public Button getNappi(){
        return this.poista;
    }
    
    
}
