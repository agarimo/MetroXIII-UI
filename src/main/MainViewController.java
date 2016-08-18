/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import metronomo.Compas;
import metronomo.Metronomo;

/**
 *
 * @author Agarimo
 */
public class MainViewController extends Metronomo implements Initializable {

    @FXML
    private Button uiStart;

    @FXML
    private Button uiStop;

    @FXML
    private ImageView uiImage;

    @FXML
    private Label labelTempo;

    @FXML
    private Slider uiTempo;

    @FXML
    private ComboBox uiCompas;

    @FXML
    private Circle circulo1;

    @FXML
    private Circle circulo2;

    @FXML
    private Circle circulo3;

    ObservableList<Compas> comboCompas;

    Image img0;
    Image img1;
    Image img2;
    Image img3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        img0 = new Image("/resources/3by4_0.png");
        img1 = new Image("/resources/3by4_1.png");
        img2 = new Image("/resources/3by4_2.png");
        img3 = new Image("/resources/3by4_3.png");
        uiImage.setImage(img0);
        circulo1.setFill(Color.GREY);
        circulo2.setFill(Color.GREY);
        circulo3.setFill(Color.GREY);
        initializeCompas();
        initializeTempo();
    }

    private void initializeCompas() {
        comboCompas = FXCollections.observableArrayList();
        comboCompas.addAll(Arrays.asList(Compas.values()));
        uiCompas.setItems(comboCompas);
        uiCompas.getSelectionModel().select(Compas.c3by4);
    }

    private void initializeTempo() {
        labelTempo.setText("TEMPO : " + super.getTempo());
        uiTempo.setValue(super.getTempo());

        uiTempo.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelTempo.setText("TEMPO : " + newValue.intValue());
            super.setTempo(newValue.intValue());
        });
    }

    @Override
    protected void updateUI(int estado, long timeLapse) {

        switch (estado) {
            case 1:
                this.uiImage.setImage(img1);
                circulo1.setFill(Color.RED);
                circulo2.setFill(Color.GREY);
                circulo3.setFill(Color.GREY);
                break;
            case 2:
                this.uiImage.setImage(img2);
                circulo1.setFill(Color.GREY);
                circulo2.setFill(Color.RED);
                circulo3.setFill(Color.GREY);
                break;
            case 3:
                this.uiImage.setImage(img3);
                circulo1.setFill(Color.GREEN);
                circulo2.setFill(Color.GREEN);
                circulo3.setFill(Color.GREEN);
                break;
        }
    }

    @FXML
    void onStart(ActionEvent event) {
        super.start();
    }

    @FXML
    void onStop(ActionEvent event) {
        super.stop();
        uiImage.setImage(img0);
        circulo1.setFill(Color.GREY);
        circulo2.setFill(Color.GREY);
        circulo3.setFill(Color.GREY);
    }

    @FXML
    void onCompas(ActionEvent event) {
        super.setCompas((Compas) uiCompas.getSelectionModel().getSelectedItem());
    }

}
