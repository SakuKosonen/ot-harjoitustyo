/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

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
import domain.Deadline;
import domain.Kurssi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Saku
 */
public class Main extends Application {

    @Override
    public void start(Stage ikkuna) throws ClassNotFoundException, SQLException {
        
       
        
        ikkuna.setTitle("Hallinta sovellus");
        ikkuna.show();
        
        

        Database pase = new Database("jdbc:sqlite:testi3.db");
        
        //pase.init(); Jos Sovellus ei toimi
        
        KurssiDao kurssit = new KurssiDao(pase);
        DeadlineDao deadlinet = new DeadlineDao(pase);

        BorderPane kurssitPane = new BorderPane();
        kurssitPane.setPrefSize(300, 500);

        VBox kurssitOikeaBox = new VBox();
        kurssitPane.setRight(kurssitOikeaBox);
        

        Button kaikkiDeadlinetNappi = new Button("Kaikki deadlinet");
        Button otNappi = new Button("ot");
        Button takaisinKurssi = new Button("Takaisin");
        //kurssitPane.setTop(takaisinKurssi);
        
        Button lisaaKurssi = new Button("Lisää kurssi");
        Button kurssitNappi = new Button("Kurssit");
        Button poistaKursseja = new Button("Poista kurssi");

        //kurssitVasenBox.getChildren().add(otNappi);
        kurssitOikeaBox.getChildren().add(takaisinKurssi);
        kurssitOikeaBox.getChildren().add(lisaaKurssi);
        kurssitOikeaBox.getChildren().add(poistaKursseja);
        
        BorderPane ekaPane = new BorderPane();
        
        ekaPane.setPrefSize(300, 500);
        
        
        FlowPane aloitusRuutuPane = new FlowPane();
        
        
        
        ekaPane.setCenter(aloitusRuutuPane);
        aloitusRuutuPane.getChildren().add(kurssitNappi);
        aloitusRuutuPane.getChildren().add(kaikkiDeadlinetNappi);
        BorderPane lisaaKurssiPane = new BorderPane();
        
        Scene nakyma = new Scene(ekaPane);
        Scene kurssitScene = new Scene(kurssitPane);

        TextField kurssinNimiKentta = new TextField();
        Button takaisinKurssienLisaamisesta = new Button("Takaisin");
        Button lisaaKurssiLopullisestiNappi = new Button("Lisää kurssi");

        HBox komponenttiryhma = new HBox();
        komponenttiryhma.setSpacing(20);
        komponenttiryhma.getChildren().addAll(kurssinNimiKentta, lisaaKurssiLopullisestiNappi, takaisinKurssienLisaamisesta);

        Scene lisaaKurssiScene = new Scene(komponenttiryhma);
        
        kaikkiDeadlinetNappi.setOnAction(valuutta -> {
            List<Deadline> deadlineLista = new ArrayList();
            
            try {
                deadlineLista = deadlinet.findAll();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            BorderPane deadLinePane = new BorderPane();
            deadLinePane.setPrefSize(300,500);
            VBox vBox = new VBox();
            VBox oikeaReuna = new VBox();
            VBox edessa = new VBox();
            edessa.getChildren().add(new Label("Tulevat Deadlinet:"));
            VBox takana = new VBox();
            takana.getChildren().add(new Label("Menneet deadlinet:"));
            deadLinePane.setLeft(vBox);
            deadLinePane.setRight(oikeaReuna);
            vBox.getChildren().addAll(edessa, takana);
            
            for (int i = 0; i < deadlineLista.size(); i++) {
                HBox rivi = new HBox();
                rivi.setSpacing(20);
                Label deadlinenNimi = new Label(deadlineLista.get(i).getNimi());
                if (deadlineLista.get(i).onkoPakollinen()) {
                    deadlinenNimi.setTextFill(Color.web("#ffaa00", 0.8));
                } else {
                    deadlinenNimi.setTextFill(Color.web("#3700ff", 0.8));
                }

                Label tehty = new Label("tehty");

                if (deadlineLista.get(i).onkoTehty() == false) {
                    tehty.setText("ei tehty");
                    tehty.setTextFill(Color.web("#ff0000", 0.8));
                } else {
                    tehty.setTextFill(Color.web("#11ff00", 0.8));
                }

                Label paiva = new Label(deadlineLista.get(i).getDate());

                Label aika = new Label(deadlineLista.get(i).getAika());
                
                MerkkaaTehdyksiNappi tee = new MerkkaaTehdyksiNappi(deadlineLista.get(i), kaikkiDeadlinetNappi, deadlinet);
                
                rivi.getChildren().addAll(deadlinenNimi, paiva, aika, tehty, tee.getNappi());
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));

                if (deadlineLista.get(i).getPaivamaaraJaKello().before(new Date())) {
                    takana.getChildren().add(rivi);
                } else {
                    edessa.getChildren().add(rivi);
                }
                
               // vBox.getChildren().add(rivi);
                
            }
            Button takaisinKaikistaDeadlineista = new Button("takaisin");
            
            takaisinKaikistaDeadlineista.setOnAction(make -> {
                takaisinKurssi.fire();
            });
            
            oikeaReuna.getChildren().add(takaisinKaikistaDeadlineista);
            Scene kaikkiDeadlinetScene = new Scene(deadLinePane);
            ikkuna.setScene(kaikkiDeadlinetScene);
            
        });
        
        
        takaisinKurssienLisaamisesta.setOnAction((event) -> {
            kurssitNappi.fire();
        });
        
        lisaaKurssiLopullisestiNappi.setOnAction((event) -> {
            try {
                kurssit.save(new Kurssi(kurssinNimiKentta.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
          /*  VBox kurssitVasenBox = new VBox();
            kurssitPane.setLeft(kurssitVasenBox);

            List<Kurssi> kurssiLista = new ArrayList();
            try {
                kurssiLista = kurssit.findAll();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 0; i < kurssiLista.size(); i++) {
                Button uusiKurssiNappi = new Button(kurssiLista.get(i).getNimi());
                kurssitVasenBox.getChildren().add(uusiKurssiNappi);

            }
            
            ikkuna.setScene(kurssitScene);*/
          kurssitNappi.fire();
            
        });

        Button takaisinPoistosta = new Button("takaisin");

        takaisinPoistosta.setOnAction((event) -> {
           /* 
            VBox kurssitVasenBox = new VBox();
            kurssitPane.setLeft(kurssitVasenBox);

            List<Kurssi> kurssiLista = new ArrayList();
            try {
                kurssiLista = kurssit.findAll();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = 0; i < kurssiLista.size(); i++) {
                Button uusiKurssiNappi = new Button(kurssiLista.get(i).getNimi());
                kurssitVasenBox.getChildren().add(uusiKurssiNappi);

            }
            
            ikkuna.setScene(kurssitScene); */
           kurssitNappi.fire();
        });

        BorderPane poistaKurssejaPane = new BorderPane();
        
        

        poistaKurssejaPane.setTop(takaisinPoistosta);

        Scene poistaKurssejaScene = new Scene(poistaKurssejaPane);

        poistaKursseja.setOnAction((event) -> {

            VBox poistaKurssitVasenBox = new VBox();
            VBox poistaKurssitOikeaBox = new VBox();
            poistaKurssejaPane.setLeft(poistaKurssitVasenBox);
            poistaKurssejaPane.setRight(poistaKurssitOikeaBox);

            List<Kurssi> kurssiLista = new ArrayList();
            try {
                kurssiLista = kurssit.findAll();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 0; i < kurssiLista.size(); i++) {
                PoistoNappi poistoNappi = new PoistoNappi(kurssiLista.get(i).getNimi(), kurssit, takaisinPoistosta, deadlinet);
                
                Label kurssiLabel = new Label(kurssiLista.get(i).getNimi());
                
                HBox rivi = new HBox();
                rivi.setAlignment(Pos.BOTTOM_RIGHT);
                rivi.setSpacing(50);
                rivi.getChildren().addAll(kurssiLabel, poistoNappi.getNappi());
                poistaKurssitVasenBox.getChildren().add(rivi);
                //poistaKurssitOikeaBox.getChildren().add(poistoNappi.getNappi());
            }

            ikkuna.setScene(poistaKurssejaScene);
        });

        lisaaKurssi.setOnAction((event) -> {
            ikkuna.setScene(lisaaKurssiScene);
        });

        takaisinKurssi.setOnAction((event) -> {
            ikkuna.setScene(nakyma);
        });

        kurssitNappi.setOnAction((event) -> {

            VBox kurssitVasenBox = new VBox();
            kurssitPane.setLeft(kurssitVasenBox);

            List<Kurssi> kurssiLista = new ArrayList();
            try {
                kurssiLista = kurssit.findAll();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 0; i < kurssiLista.size(); i++) {
                KurssiNappi uusiKurssiNappi = new KurssiNappi(kurssiLista.get(i).getNimi(), deadlinet, kurssit, kurssitScene, ikkuna);
                kurssitVasenBox.getChildren().add(uusiKurssiNappi.getNappi());

            }

            ikkuna.setScene(kurssitScene);
            
        });

        /*  poistaKursseja.setOnAction((event) -> {

            VBox kurssitVasenBox = new VBox();
            kurssitPane.setLeft(kurssitVasenBox);

            List<Kurssi> kurssiLista = new ArrayList();
            try {
                kurssiLista = kurssit.findAll();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 0; i < kurssiLista.size(); i++) {
                Button uusiKurssiNappi = new Button(kurssiLista.get(i).getNimi());
                kurssitVasenBox.getChildren().add(uusiKurssiNappi);

            }

            ikkuna.setScene(kurssitScene);
        }); */
        ikkuna.setScene(nakyma);
        ikkuna.show();

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        /*   Date paiva = new Date(2019, 11, 16, 20, 20);
         Date nykyaika = new Date(2019, 11, 15, 20, 0);
         Deadline homma = new Deadline("homma", true, paiva);
         int aika = homma.aikaMinuutteina(nykyaika);
         System.out.println(aika);
         System.out.println(homma.aikaHienosti(nykyaika));*/
        
        launch(Main.class);
        /*
        Database pase = new Database("jdbc:sqlite:testi3.db");

        //pase.init();
        
        KurssiDao kurssit = new KurssiDao(pase);
        DeadlineDao deadlinet = new DeadlineDao(pase);
        
        //Kurssi kurssi = new Kurssi("SiisTamaKurssi");
        Deadline deadline = new Deadline("SiisTamanKurssinDeadline2", true, "06:09:1996", "16:30", "SiisTamaKurssi");
        
       // kurssit.save(kurssi);
        deadlinet.saveJee(deadline);
        
       /* List<Kurssi> kurssiLista = kurssit.findAll();
        
        List<Deadline> deadlineLista = deadlinet.findAll();
        
        for (int i = 0; i < kurssiLista.size(); i++) {
            System.out.println(kurssiLista.get(i).getNimi());
        }
        
        System.out.println("----");
        
        for (int i = 0; i < deadlineLista.size(); i++) {
            System.out.println(deadlineLista.get(i).getNimi());
        }
         */
 /*       
List<Deadline> deadlineLista2 = deadlinet.findAllKurssista(deadline.getKurssi());
        
        for (int i = 0; i < deadlineLista2.size(); i++) {
            System.out.println(deadlineLista2.get(i).getNimi());
            System.out.println(deadlineLista2.get(i).getKurssi()); 
        }
         */
     /*
         SimpleDateFormat d1 = new SimpleDateFormat("22-12-2020-20-30");
         SimpleDateFormat d2 = new SimpleDateFormat("22-12-2019-20-30");
         SimpleDateFormat d3 = new SimpleDateFormat("22-12-2018-20-30");
         SimpleDateFormat d4 = new SimpleDateFormat("22-12-2019-20-40");
         SimpleDateFormat d5 = new SimpleDateFormat("22-12-2019-20-30");
         
        
         
        String dateFormat = "yyyy-MM-dd HH:mm";
        Date f1 = new Date(); 
        Date f2 = new Date(); 
        Date f3 = new Date(); 
        Date f4 = new Date(); 
        Date f5 = new Date(); 
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        f1 = sdf.parse("2020-12-27 23:24");
        f2 = sdf.parse("2019-09-27 23:24");
        f3 = sdf.parse("2018-09-27 23:24");
        f4 = sdf.parse("2018-04-27 23:24");
        f5 = sdf.parse("2015-09-27 23:21");
        
        ArrayList<Date> asdl = new ArrayList();
        
        asdl.add(f4);
        asdl.add(f3);
        asdl.add(f5);
        asdl.add(f1);
        asdl.add(f2);
        
        for (int i = 0; i < asdl.size(); i++) {
            System.out.println(asdl.get(i));
        }
        
        System.out.println("------");
        Collections.sort(asdl);
        
         for (int i = 0; i < asdl.size(); i++) {
            System.out.println(asdl.get(i));
        }
         
       */ 
        
       /* String qq = "20.12.2012";
        String qqq = "20:30";
        
        String[] ww = qq.split("\\.");
        String[] www = qqq.split(":");
        
        String printti = www[1];
        
         for (String a : ww) 
            System.out.println(a);
        
        System.out.println(printti);
        System.out.println("mitvid");
     */
        
    }

}
