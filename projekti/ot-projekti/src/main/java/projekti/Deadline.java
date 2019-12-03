/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Saku
 */
public class Deadline {
    
    int id;
    String nimi;
    Date paivamaara;
    boolean pakollisuus;
    boolean tehty;
    String aika;

    public Deadline(String nimi, boolean pakollinen, Date paivamaara, String aika) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.pakollisuus = pakollinen;
        this.tehty = false;
        this.aika = aika;

    }
    
    public Deadline(String nimi, boolean pakollinen, Date paivamaara, int tunnit, int minuutit) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.pakollisuus = pakollinen;
        this.tehty = false;
        this.aika = tunnit + ":" + minuutit;

    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int jotain) {
        id = jotain;
    }
    
    public Date getDate() {
        return this.paivamaara;
    }

    public String getNimi() {
        return this.nimi;
    }

    public boolean onkoTehty() {
        
        return tehty;
    }

    public boolean onkoPakollinen() {
        
        return pakollisuus;
    }

    public void tehty() {
        tehty = true;
    }

    public int aikaMinuutteina(Date nykyaika) {
        long eroMillisekunteina = Math.abs(paivamaara.getTime() - nykyaika.getTime());
        long ero = TimeUnit.MINUTES.convert(eroMillisekunteina, TimeUnit.MILLISECONDS);
        int eroInttina = (int)ero;
        return eroInttina;
    }

    public String aikaHienosti(Date nykyaika) {
        int aika = aikaMinuutteina(nykyaika);
        
        return aika / 24 / 60 + ":" + aika / 60 % 24 + ':' + aika % 60;

    }
    
    public String getAika() {
        return aika;
    }
    

}
