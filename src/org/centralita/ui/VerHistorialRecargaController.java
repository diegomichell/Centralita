/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centralita.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.centralita.controller.RecargasHandler;
import org.centralita.entity.Recarga;

/**
 * FXML Controller class
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class VerHistorialRecargaController implements Initializable {

    @FXML
    private TableView<Recarga> tableView;
    private ObservableList<Recarga> recargas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        configureTable();
    }

    public void configureTable()
    {

        if (RecargasHandler.getInstance().recargastoreExist())
        {
            recargas = FXCollections.observableArrayList(RecargasHandler.getInstance().obtenerRecargas());

            if (!recargas.isEmpty())
            {
                tableView.setItems(recargas);
                
                ((TableColumn) tableView.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("numero"));
                ((TableColumn) tableView.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("monto"));
                ((TableColumn) tableView.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("fecha"));

            } else
            {
                tableView.setPlaceholder(new Label("No se han realizado recargas"));
            }
        } else
        {
            tableView.setPlaceholder(new Label("No se han realizado recargas"));
        }

    }

}
