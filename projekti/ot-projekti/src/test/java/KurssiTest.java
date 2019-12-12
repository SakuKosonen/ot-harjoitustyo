
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import projekti.Deadline;
import projekti.Kurssi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Saku
 */
public class KurssiTest {
    
    public KurssiTest() {       
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
    
    @Test
    public void kurssiToimii() {
        Kurssi kurssi = new Kurssi("testikurssi");
        Date paiva = new Date(2019, 11, 16);
        String kello = "23:59";
        Deadline homma = new Deadline("homma", true, paiva, kello);
        kurssi.lisaaDeadline(homma);
        
        assertEquals("testikurssi",kurssi.getNimi());
        kurssi.setId(0);
        assertEquals(0,kurssi.getId());
        boolean testi = kurssi.getDeadlinet().contains(homma);
        
        assertEquals(true, testi);
        
        kurssi.poistaDeadline(homma);
        
        testi = kurssi.getDeadlinet().contains(homma);
        
        assertEquals(false, testi);
        
        
        
    } 
    
}
