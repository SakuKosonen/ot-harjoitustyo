/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import db.Database;
import db.DeadlineDao;
import db.KurssiDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

/**
 *
 * @author Saku
 */
public class Main extends Application {

    @Override
    public void start(Stage ikkuna) {
        ikkuna.setTitle("Hallinta sovellus");
        ikkuna.show();

        Button kaikkiDeadlinetNappi = new Button("Kaikki honmat");
        Button otNappi = new Button("ot");
        Button takaisinKurssi = new Button("Takaisin");
        Button lisaaKurssi = new Button("Lisää kurssi");
        Button kurssitNappi = new Button("Kurssit");        

        BorderPane kurssitPane = new BorderPane();
        VBox kurssitVasenBox = new VBox();
        VBox kurssitOikeaBox = new VBox();
        kurssitPane.setRight(kurssitOikeaBox);
        kurssitPane.setLeft(kurssitVasenBox);

        kurssitVasenBox.getChildren().add(otNappi);

        kurssitOikeaBox.getChildren().add(takaisinKurssi);
        kurssitOikeaBox.getChildren().add(lisaaKurssi);

        FlowPane aloitusRuutuPane = new FlowPane();
        aloitusRuutuPane.getChildren().add(kurssitNappi);
        aloitusRuutuPane.getChildren().add(kaikkiDeadlinetNappi);

        Scene nakyma = new Scene(aloitusRuutuPane);
        Scene kurssitScene = new Scene(kurssitPane);

        kurssitNappi.setOnAction((event) -> {
            ikkuna.setScene(kurssitScene);
        });

        ikkuna.setScene(nakyma);
        ikkuna.show();
        
        

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        /*   Date paiva = new Date(2019, 11, 16, 20, 20);
         Date nykyaika = new Date(2019, 11, 15, 20, 0);
         Deadline homma = new Deadline("homma", true, paiva);
         int aika = homma.aikaMinuutteina(nykyaika);
         System.out.println(aika);
         System.out.println(homma.aikaHienosti(nykyaika));*/
        //launch(Main.class);
        
        Database pase = new Database("jdbc:sqlite:Tietokanta.db");

        
        
        KurssiDao kurssit = new KurssiDao(pase);
        
       // Kurssi kurssi = new Kurssi("testiKurssi1234");
       // kurssit.save(kurssi);
        
        List<Kurssi> lista = kurssit.findAll();
        
        
        
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).getNimi());
        }
        
        DeadlineDao deadlinet = new DeadlineDao(pase);
        
        Date paivamaara = new Date(11122019);
        
        Deadline deadline = new Deadline("testiDeadline", true, paivamaara, "20:30");
        
        deadlinet.saveJee(deadline, "testiKurssi1234");
        //int jonne = kurssit.findOneIntNimella("testiKurssi1234");
        System.out.println("----");
        
        List<Deadline> lista2 = deadlinet.findAll();
        
        for (int i = 0; i < lista2.size(); i++) {
            System.out.println(lista2.get(i).getNimi());
        }
        
        
        //System.out.println(jonne);
        
        
        
        //Kurssi kurssi123 = kurssit.findOneNimella("testiKurssi22");
       
        //System.out.println(kurssi123.getNimi());
        
        //kurssit.deleteNimella("testiKurssi1234");
        
        
    }

}
