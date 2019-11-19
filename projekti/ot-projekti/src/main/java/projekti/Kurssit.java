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
public class Kurssit {
    ArrayList<Kurssi> kurssit;
    
    public Kurssit() {
        kurssit = new ArrayList();
    }
    
    public void lisaaKurssi(Kurssi kurssi) {
        kurssit.add(kurssi);
        
    }
    
    public void poistaKurssi(Kurssi kurssi) {
        kurssit.remove(kurssi);
    }
    
    public ArrayList<Kurssi> listaKursseista() {
        return kurssit;
    }
    
}
