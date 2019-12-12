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
import projekti.Deadline;

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

 /*  @Test
    public void aikaLasketaanOikein() {
         Date paiva = new Date(2019, 11, 16, 20, 20);
         Date nykyaika = new Date(2019, 11, 15, 20, 0);
         Deadline homma = new Deadline("homma", true, paiva);
         int aika = homma.aikaMinuutteina(nykyaika);
         
         String aikaHienosti = homma.aikaHienosti(nykyaika);
         
         assertEquals(1460,aika);
         assertEquals("1:0:20", aikaHienosti);
         
    } */
    
    @Test
    public void deadlineMerkattuTehdyksi() {
        Date paiva = new Date(2019, 11, 16);
        String kello = "23:59";
        Deadline homma = new Deadline("homma", true, paiva, kello);
        boolean eiTehty = homma.onkoTehty();
        homma.tehty();
        boolean tehty = homma.onkoTehty();
        
        assertEquals(false,eiTehty);
        assertEquals(true, tehty);
        
    } 
    
    
    
    
}
