/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.DeadlineDao;
import db.KurssiDao;
import domain.Deadline;
import domain.Kurssi;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import javafx.stage.Stage;

/**
 *
 * @author Saku
 */
public class KurssiNappi {

    private Button nappi;
    private DeadlineDao dDao;
    private KurssiDao kDao;
    private String nimi;

    public KurssiNappi(String nimi, DeadlineDao d, KurssiDao k, Scene aScene, Stage ikkuna) {
        nappi = new Button(nimi);
        dDao = d;
        kDao = k;
        this.nimi = nimi;

        nappi.setOnAction(event -> {
            List<Deadline> deadlineLista = new ArrayList();
            try {
                deadlineLista = dDao.findAllKurssista(nimi);
            } catch (SQLException ex) {
                Logger.getLogger(KurssiNappi.class.getName()).log(Level.SEVERE, null, ex);
            }

            /* for (int i = 0; i < deadlineLista.size(); i++) {
                Deadline l = deadlineLista.get(i);
                String[] paivamaara = l.getDate().split(".");
                
                        
                SimpleDateFormat deadlinenPaivamaara = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            }*/
            BorderPane deadLinePane = new BorderPane();
            deadLinePane.setPrefSize(300, 500);
            
            VBox vBox = new VBox();
            VBox takana = new VBox();
            VBox edessa = new VBox();

            VBox oikeaReuna = new VBox();
            deadLinePane.setLeft(vBox);
            deadLinePane.setRight(oikeaReuna);

            Label edessaLabel = new Label("tulevat Deadlinet:");
            Label takanaLabel = new Label("menneet Deadlinet:");

            edessa.getChildren().add(edessaLabel);
            takana.getChildren().add(takanaLabel);
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

                MerkkaaTehdyksiNappi tee = new MerkkaaTehdyksiNappi(deadlineLista.get(i), this.nappi, dDao);
                rivi.getChildren().addAll(deadlinenNimi, paiva, aika, tehty, tee.getNappi());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));

                if (deadlineLista.get(i).getPaivamaaraJaKello().before(new Date())) {
                    takana.getChildren().add(rivi);
                } else {
                    edessa.getChildren().add(rivi);
                }
                //vBox.getChildren().add(rivi);

                // oikeaReuna.getChildren().add(tee.getNappi());
            }
            Button takaisinPoistosta = new Button("takaisin");
            Button takaisin = new Button("takaisin");
            HBox ylareuna = new HBox();

            takaisin.setOnAction(juttu -> {
                ikkuna.setScene(aScene);
            });

            Button lisaaDeadline = new Button("lisää deadline");
            Button poistaDeadline = new Button("poista deadline");
            ylareuna.getChildren().addAll(takaisin, lisaaDeadline, poistaDeadline);
            deadLinePane.setTop(ylareuna);

            Scene kurssitDeadlineScene = new Scene(deadLinePane);

            lisaaDeadline.setOnAction(tapahtuma -> {
                TextField nimiKentta = new TextField();
                nimiKentta.setPromptText("nimi");
                HBox paivamaaraBox = new HBox();

                LimitedTextField paivamaaraKentta1 = new LimitedTextField();
                paivamaaraKentta1.setPrefWidth(30);
                paivamaaraKentta1.setMaxWidth(30);
                paivamaaraKentta1.setMaxLength(2);
                Label piste = new Label(":");
                Label piste2 = new Label(":");
                LimitedTextField paivamaaraKentta2 = new LimitedTextField();
                paivamaaraKentta2.setMaxLength(2);
                paivamaaraKentta2.setPrefWidth(30);
                paivamaaraKentta2.setMaxWidth(30);
                LimitedTextField paivamaaraKentta3 = new LimitedTextField();
                paivamaaraKentta3.setMaxLength(4);
                paivamaaraKentta3.setPrefWidth(50);
                paivamaaraKentta3.setMaxWidth(50);
                paivamaaraBox.getChildren().addAll(paivamaaraKentta1, piste, paivamaaraKentta2, piste2, paivamaaraKentta3);
                HBox aikaBox = new HBox();

                LimitedTextField aikaKentta1 = new LimitedTextField();
                aikaKentta1.setMaxLength(2);
                aikaKentta1.setPrefWidth(30);
                aikaKentta1.setMaxWidth(30);
                LimitedTextField aikaKentta2 = new LimitedTextField();
                aikaKentta2.setPrefWidth(30);
                aikaKentta2.setMaxWidth(30);
                Label kaksoispiste = new Label(":");
                aikaKentta2.setMaxLength(2);
                aikaBox.getChildren().addAll(aikaKentta1, kaksoispiste, aikaKentta2);
                CheckBox checkBox1 = new CheckBox("Onko Pakollinen");
                Button takaisinDeadlinejenLisaamisesta = new Button("Takaisin");
                Button lisaaDeadlineLopullisestiNappi = new Button("Lisää deadline");

                HBox komponenttiryhma = new HBox();
                komponenttiryhma.setSpacing(20);
                komponenttiryhma.getChildren().addAll(nimiKentta, paivamaaraBox, aikaBox, checkBox1, lisaaDeadlineLopullisestiNappi, takaisinDeadlinejenLisaamisesta);

                lisaaDeadlineLopullisestiNappi.setOnAction(honma -> {
                    boolean jotain = false;
                    if (checkBox1.isSelected()) {
                        jotain = true;
                    }
                    String paivamaara = paivamaaraKentta1.getText() + "." + paivamaaraKentta2.getText() + "." + paivamaaraKentta3.getText();
                    String aika = aikaKentta1.getText() + ":" + aikaKentta2.getText();
                    Deadline uusiDeadline = new Deadline(nimiKentta.getText(), jotain, paivamaara, aika, this.nimi);
                    try {
                        d.saveJee(uusiDeadline);
                    } catch (SQLException ex) {
                        Logger.getLogger(KurssiNappi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.nappi.fire();
                    /*
                    HBox rivi = new HBox();
                    rivi.setSpacing(20);
                    Label deadlinenNimi = new Label(uusiDeadline.getNimi());
                    if (uusiDeadline.onkoPakollinen()) {
                        deadlinenNimi.setTextFill(Color.web("#ffaa00", 0.8));
                    } else {
                        deadlinenNimi.setTextFill(Color.web("#3700ff", 0.8));
                    }

                    Label tehty = new Label("tehty");

                    if (uusiDeadline.onkoTehty() == false) {
                        tehty.setText("ei tehty");
                        tehty.setTextFill(Color.web("#ff0000", 0.8));
                    } else {
                        tehty.setTextFill(Color.web("#11ff00", 0.8));
                    }

                    Label uusiPaiva = new Label(uusiDeadline.getDate());

                    Label uusiAika = new Label(uusiDeadline.getAika());
                    MerkkaaTehdyksiNappi tee = new MerkkaaTehdyksiNappi(uusiDeadline, this.nappi, dDao);
                    rivi.getChildren().addAll(deadlinenNimi, uusiPaiva, uusiAika, tehty, tee.getNappi());

                   
                    
                    if (uusiDeadline.getPaivamaaraJaKello().before(new Date())) {
                    takana.getChildren().add(rivi);
                } else {
                    edessa.getChildren().add(rivi);
                }

                    ikkuna.setScene(kurssitDeadlineScene);
*/
                });

                takaisinDeadlinejenLisaamisesta.setOnAction(jutskaputska -> {
                    ikkuna.setScene(kurssitDeadlineScene);
                });

                Scene lisaaDeadlineScene = new Scene(komponenttiryhma);
                ikkuna.setScene(lisaaDeadlineScene);
            });
            BorderPane poistaDeadlinejaPane = new BorderPane();
            Button takaisinDeadlinejenPoistamisesta = new Button("takaisin");

            takaisinDeadlinejenPoistamisesta.setOnAction(jutskaputskatsuska -> {
                takaisinPoistosta.fire();
            });

            poistaDeadlinejaPane.setTop(takaisinDeadlinejenPoistamisesta);

            poistaDeadline.setOnAction(jutskeliini -> {
                VBox poistaKurssitVasenBox = new VBox();
                VBox poistaKurssitOikeaBox = new VBox();
                poistaDeadlinejaPane.setLeft(poistaKurssitVasenBox);
                poistaDeadlinejaPane.setRight(poistaKurssitOikeaBox);

                List<Deadline> deadlineLista2 = new ArrayList();
                try {
                    deadlineLista2 = dDao.findAllKurssista(nimi);
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int i = 0; i < deadlineLista2.size(); i++) {
                    DeadlinenPoistoNappi poistoNappi = new DeadlinenPoistoNappi(deadlineLista2.get(i).getNimi(), deadlineLista2.get(i).getKurssi(), dDao, takaisinPoistosta);

                    Label kurssiLabel = new Label(deadlineLista2.get(i).getNimi());

                    HBox rivi = new HBox();
                    rivi.setAlignment(Pos.BOTTOM_RIGHT);
                    rivi.setSpacing(50);
                    rivi.getChildren().addAll(kurssiLabel, poistoNappi.getNappi());
                    poistaKurssitVasenBox.getChildren().add(rivi);
                    //poistaKurssitOikeaBox.getChildren().add(poistoNappi.getNappi());
                }
                Scene poistaDeadlinejaScene = new Scene(poistaDeadlinejaPane);
                ikkuna.setScene(poistaDeadlinejaScene);
            });

            //oikeaReuna.getChildren().add(lisaaDeadline);
            //oikeaReuna.getChildren().add(poistaDeadline);
            ikkuna.setScene(kurssitDeadlineScene);

            takaisinPoistosta.setOnAction(takaisinPoistostaEvene -> {
                this.nappi.fire();
            });

        });

    }

    public List<Deadline> getDeadlinet() throws SQLException {
        return dDao.findAllKurssista(nimi);
    }

    public Button getNappi() {
        return this.nappi;
    }

    public String getKurssi() {
        return this.nimi;
    }

}
