/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;

/**
 *
 * @author Saku
 */
public class Kurssi {

    String nimi;
    ArrayList<Deadline> deadlinet;

    public Kurssi(String nimi) {
        this.nimi = nimi;
        this.deadlinet = new ArrayList();
    }

    public String getNimi() {
        return this.nimi;
    }
    
    public void lisaaDeadline(Deadline homma) {
        deadlinet.add(homma);
    }
    
    public void poistaDeadline(Deadline homma) {
        if(deadlinet.contains(homma)) {
            deadlinet.remove(homma);
        }
    }
    
    public ArrayList<Deadline> getDeadlinet() {
        return deadlinet;
    }

}
