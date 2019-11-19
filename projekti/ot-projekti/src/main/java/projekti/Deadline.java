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

    String nimi;
    Date paivamaara;
    boolean pakollisuus;
    boolean tehty;

    public Deadline(String nimi, boolean pakollinen, int vuosi, int kuukaus, int paiva, int tunnit, int minuutit) {
        this.nimi = nimi;
        this.paivamaara = new Date(vuosi, kuukaus, paiva, tunnit, minuutit);
        this.pakollisuus = pakollinen;
        this.tehty = false;

    }
    
    public Deadline(String nimi, boolean pakollinen, Date paivamaara) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.pakollisuus = pakollinen;
        this.tehty = false;

    }

    public Date getDate() {
        return this.paivamaara;
    }

    public String getNimi() {
        return this.nimi;
    }

    public boolean onkoTehty() {
        if (tehty == true) {
            return true;
        }
        return false;
    }

    public boolean onkoPakollinen() {
        if (pakollisuus == true) {
            return true;
        }
        return false;
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
    

}
