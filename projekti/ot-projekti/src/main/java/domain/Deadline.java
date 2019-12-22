/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saku
 */
public class Deadline implements Comparable<Deadline> {

    int id;
    String nimi;
    String paivamaara;
    boolean pakollisuus;
    boolean tehty;
    String aika;
    String kurssi;
    Date date;

    public Deadline(String nimi, boolean pakollinen, String paivamaara, String aika, String kurssi) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.pakollisuus = pakollinen;
        this.tehty = false;
        this.aika = aika;
        this.kurssi = kurssi;
        this.date = getPaivamaaraJaKello();

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Deadline other = (Deadline) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    /* public Deadline(String nimi, boolean pakollinen, Date paivamaara, int tunnit, int minuutit) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.pakollisuus = pakollinen;
        this.tehty = false;
        this.aika = tunnit + ":" + minuutit;

    }
     */
    public int getId() {
        return id;
    }

    public void setId(int jotain) {
        id = jotain;
    }

    public String getDate() {
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
    
    public void eiTehty() {
        tehty = false;
    }

    public String getKurssi() {
        return this.kurssi;
    }

    /*public int aikaMinuutteina(Date nykyaika) {
        long eroMillisekunteina = Math.abs(paivamaara.getTime() - nykyaika.getTime());
        long ero = TimeUnit.MINUTES.convert(eroMillisekunteina, TimeUnit.MILLISECONDS);
        int eroInttina = (int)ero;
        return eroInttina;
    } */

 /*public String aikaHienosti(Date nykyaika) {
        int aika = aikaMinuutteina(nykyaika);
        
        return aika / 24 / 60 + ":" + aika / 60 % 24 + ':' + aika % 60;

    }*/
    public String getAika() {
        return aika;
    }

    public Date getPaivamaaraJaKello() {
        
        String[] pl;
        
        if (paivamaara.contains(":")) {
            pl = paivamaara.split(":");

        } else {
            pl = paivamaara.split("\\.");
        }

        String[] kl = aika.split(":");

        int paiva = Integer.parseInt(pl[0]);
        int kuukaus = Integer.parseInt(pl[1]);
        int vuosi = Integer.parseInt(pl[2]);
        int tunti = Integer.parseInt(kl[0]);
        int minuutti = Integer.parseInt(kl[1]);

        String dateFormat = "yyyy-MM-dd HH:mm";
        String tama = vuosi + "-" + kuukaus + "-" + paiva + " " + tunti + ":" + minuutti;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date f1 = new Date();
        try {
            f1 = sdf.parse(tama);
        } catch (ParseException ex) {
            Logger.getLogger(Deadline.class.getName()).log(Level.SEVERE, null, ex);
        }

        return f1;
    }

    @Override
    public int compareTo(Deadline o) {
        return getPaivamaaraJaKello().compareTo(o.getPaivamaaraJaKello());

    }
}
