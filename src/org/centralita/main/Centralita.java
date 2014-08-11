/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centralita.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class Centralita extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {

        Parent root = FXMLLoader.load(getClass().getResource("../ui/mainWindow.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Bienvenido");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../ui/icons/llamada.png")));
        stage.setScene(scene);
        
        stage.show();
        
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
