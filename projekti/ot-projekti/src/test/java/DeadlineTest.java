/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.Deadline;

/**
 *
 * @author Saku
 */
public class DeadlineTest {
    
    public DeadlineTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        
    }

 /* @Test
    public void aikaLasketaanOikein() {
         Date nyt = new Date();
         
         Deadline homma = new Deadline("homma", true, paiva);
         int aika = homma.aikaMinuutteina(nykyaika);
         
         String aikaHienosti = homma.aikaHienosti(nykyaika);
         
         assertEquals(1460,aika);
         assertEquals("1:0:20", aikaHienosti);
         
    } */
    
    @Test
    public void deadlineMerkattuTehdyksi() {
        String paiva = "22.12.2019";
        String kello = "23:59";
        Deadline homma = new Deadline("homma", true, paiva, kello, "kurssi");
        boolean eiTehty = homma.onkoTehty();
        homma.tehty();
        boolean tehty = homma.onkoTehty();
        
        assertEquals(false,eiTehty);
        assertEquals(true, tehty);
        
    } 
    
    
    
    
}
