/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.DeadlineDao;
import domain.Deadline;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

/**
 *
 * @author Saku
 */
public class MerkkaaTehdyksiNappi {

    private Button nappi;
    private DeadlineDao dao;
    private Deadline deadline;
    private Button jokuNappi;

    public MerkkaaTehdyksiNappi(Deadline deadline, Button jokuNappi, DeadlineDao D) {
        this.deadline = deadline;
        this.jokuNappi = jokuNappi;
        this.dao = D;

        if (deadline.onkoTehty()) {
            this.nappi = new Button("X");
        } else {
            int ascii =  0x2713;      
            String sign = Character.toString((char)ascii);
            this.nappi = new Button(sign);
        }

        nappi.setOnAction(event -> {
            if(deadline.onkoTehty()) {
                deadline.eiTehty();
            } else {
                deadline.tehty();
            }
            try {
                dao.update(deadline);
            } catch (SQLException ex) {
                Logger.getLogger(MerkkaaTehdyksiNappi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            jokuNappi.fire();
        });

    }
    
    public Button getNappi() {
        return nappi;
    } 
    
}
