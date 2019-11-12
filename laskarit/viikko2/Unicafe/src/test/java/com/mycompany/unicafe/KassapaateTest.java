/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Saku
 */
public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti kortti;

    public KassapaateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Kassapaate uus = new Kassapaate();
        kassa = uus;
        kortti = new Maksukortti(2000);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void uudessaKassassaKaikkiKunnossa() {

        assertEquals(100000, kassa.kassassaRahaa());

        assertEquals(0, kassa.edullisiaLounaitaMyyty() + kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kassanSaldoKasvaaOikeinJaVaihtoRahatPalautetaan() {

        int takaisinE = kassa.syoEdullisesti(300);
        int takaisinM = kassa.syoMaukkaasti(500);

        assertEquals(60, takaisinE);
        assertEquals(100, takaisinM);
        int takaisinFE = kassa.syoEdullisesti(100);
        int takaisinFM = kassa.syoMaukkaasti(100);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty() + kassa.edullisiaLounaitaMyyty());
        assertEquals(100000 + 240 + 400, kassa.kassassaRahaa());
        assertEquals(100, takaisinFE);
        assertEquals(100, takaisinFM);

    }
    
    @Test
    public void kassaToimiiKorttimaksuissa() {
        
        boolean ihankoE = kassa.syoEdullisesti(kortti);
        boolean ihankoM = kassa.syoMaukkaasti(kortti);
        assertEquals(2000-240-400, kortti.saldo());
        assertEquals(true, ihankoE);
        assertEquals(true, ihankoM);
        
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(2,kassa.maukkaitaLounaitaMyyty()+kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kassaToimiiSpugemaksuissa() {
        Maksukortti spuge = new Maksukortti(10);
        boolean ihankoSE = kassa.syoEdullisesti(spuge);
        boolean ihankoSM = kassa.syoMaukkaasti(spuge);
        assertEquals(10,spuge.saldo());
        assertEquals(false, ihankoSE);
        assertEquals(false, ihankoSM);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void lataaminenToimii() {
        Maksukortti spuge = new Maksukortti(10);
        kassa.lataaRahaaKortille(spuge, 20);
        kassa.lataaRahaaKortille(spuge, -100000);
        assertEquals(100020,kassa.kassassaRahaa());
        assertEquals(10+20,spuge.saldo());
        
    }
    

}
