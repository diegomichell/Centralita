/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centralita.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.centralita.entity.Llamada;
import org.centralita.entity.LlamadaLocal;
import org.centralita.entity.LlamadaProvincial;
import org.centralita.util.Util;

/**
 * FXML Controller class
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class EfectuarLlamadaDialogController implements Initializable {

    private Llamada llamada;
    private String llamadaType;
    @FXML
    private ComboBox<String> box;
    @FXML
    private TextField numOrigenField;
    @FXML
    private TextField numDestinoField;
    @FXML
    private Slider duracionSlider;
    @FXML
    private Label message;
    private Stage stage;

    @FXML
    private Label franjaLabel;
    @FXML
    private ComboBox<String> franjaBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        configureBox();

        Tooltip tip = new Tooltip("Introduzca un numero con el formato\n ###-###-####");
        numOrigenField.setTooltip(tip);
        numDestinoField.setTooltip(tip);
    }

    private void configureBox()
    {
        box.setItems(FXCollections.observableArrayList("Local", "Provincial"));

        franjaBox.getItems().addAll("Dia", "Tarde", "Noche");
        franjaLabel.setVisible(false);
        franjaBox.setVisible(false);

        box.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            switch (newValue)
            {
                case "Local":
                    franjaLabel.setVisible(false);
                    franjaBox.setVisible(false);
                    break;
                case "Provincial":
                    franjaLabel.setVisible(true);
                    franjaBox.setVisible(true);
                    break;
            }
        });
    }

    @FXML
    private void efectuarLlamada(ActionEvent event)
    {
        String numOrigen = numOrigenField.getText();
        String numDestino = numDestinoField.getText();

        if (box.getSelectionModel().getSelectedItem() != null)
        {
            Llamada cllamada = (box.getSelectionModel().getSelectedItem().equals("Local")) ? new LlamadaLocal() : new LlamadaProvincial();

            if (numOrigen.isEmpty())
            {
                message.setText("Debe introducir su numero");
            } else if (numDestino.isEmpty())
            {
                message.setText("Debe introducir el numero que desea marcar");
            } else if (!(numDestino.matches(Util.PHONE_REGEX) && numOrigen.matches(Util.PHONE_REGEX)))
            {
                message.setText("Escriba un numero valido");
            } else
            {
                cllamada.setNumOrigen(numOrigen);
                cllamada.setNumDestino(numDestino);
                cllamada.setDuracion(duracionSlider.getValue());
                
                if (cllamada instanceof LlamadaProvincial)
                {
                    if (franjaBox.getSelectionModel().getSelectedItem() != null)
                    {
                        ((LlamadaProvincial) cllamada).setFranja((franjaBox.getSelectionModel().getSelectedIndex() + 1));
                        this.llamada = cllamada;
                        stage.close();
                    } else
                    {
                        message.setText("Debe seleccionar la franja horaria");
                    }
                } else
                {
                    this.llamada = cllamada;
                    stage.close();
                }
            }

        } else
        {
            message.setText("Debe seleccionar un tipo de llamada");
        }

    }

    public Llamada getLlamada()
    {
        return llamada;
    }

    public String getLlamadaType()
    {
        return llamadaType;
    }

    public void setLlamadaType(String llamadaType)
    {
        this.llamadaType = llamadaType;

        if (llamadaType.equals("Locales"))
        {
            box.getSelectionModel().selectFirst();
        } else
        {
            box.getSelectionModel().selectLast();
        }
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
}
