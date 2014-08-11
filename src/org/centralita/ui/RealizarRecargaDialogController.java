/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centralita.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.centralita.controller.RecargasHandler;
import org.centralita.entity.Recarga;
import org.centralita.util.Util;

/**
 * FXML Controller class
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class RealizarRecargaDialogController implements Initializable {

    private Stage stage;
    @FXML
    private TextField numField;
    @FXML
    private Slider montoSlider;
    @FXML
    private Label message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    @FXML
    private void efectuarRecarga(ActionEvent event)
    {
        String phone = numField.getText();

        if (!(phone == null || phone.equals("")))
        {
            if (phone.matches(Util.PHONE_REGEX))
            {
                RecargasHandler handler = RecargasHandler.getInstance();
                handler.registrarRecarga(new Recarga(phone, (int) montoSlider.getValue()));
                
                stage.close();
            } else
            {
                message.setText("Debe introducir un\n numero valido");
            }
        }
        else
        {
            message.setText("Introduzca su numero");
        }

    }
    
    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

}
