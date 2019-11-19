/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.Date;

/**
 *
 * @author Saku
 */
public class Main {
     public static void main(String[] args) {
         
         Date paiva = new Date(2019, 11, 16, 20, 20);
         Date nykyaika = new Date(2019, 11, 15, 20, 0);
         Deadline homma = new Deadline("homma", true, paiva);
         int aika = homma.aikaMinuutteina(nykyaika);
         System.out.println(aika);
         System.out.println(homma.aikaHienosti(nykyaika));
         
     }
}
