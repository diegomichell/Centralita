package org.centralita.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.centralita.controller.LlamadasLocalesHandler;
import org.centralita.controller.LlamadasProvincialesHandler;
import org.centralita.controller.RecargasHandler;
import org.centralita.entity.Llamada;
import org.centralita.entity.LlamadaLocal;
import org.centralita.entity.LlamadaProvincial;
import org.centralita.model.DataStore;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class mainWindowController implements Initializable {

    @FXML
    private ListView<String> listView;
    @FXML
    private TableView<Llamada> tableView;
    private ObservableList<String> listItems;
    private Map<String, List<Llamada>> llamadas;
    private ObservableList currentLlamadas;

    @FXML
    private TextField cantidadLlamadasField;
    @FXML
    private TextField totalFacturadoField;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        configureList();
        configureTable();
    }

    @FXML
    protected void exitApp(ActionEvent event)
    {
        Platform.exit();
    }

    private void configureList()
    {
        listView.setCellFactory((ListView<String> cb) -> new CellWithIcon());
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        llamadas = new HashMap();

        llamadas.put("Locales", new ArrayList<>(LlamadasLocalesHandler.getInstance().obtenerLlamadas()));
        llamadas.put("Provinciales", new ArrayList<>(LlamadasProvincialesHandler.getInstance().obtenerLlamadas()));

        listItems = FXCollections.observableArrayList(llamadas.keySet());
        listView.setItems(listItems);

        listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            if (newValue != null)
            {

                switch (newValue)
                {
                    case "Locales":

                        currentLlamadas = FXCollections.observableArrayList(llamadas.get(newValue));
                        tableView.setItems(FXCollections.observableArrayList(currentLlamadas));

                        if (!currentLlamadas.isEmpty())
                        {
                            cantidadLlamadasField.setText(String.valueOf(currentLlamadas.size()));
                            totalFacturadoField.setText(String.valueOf(calcularFactura(currentLlamadas)) + " $RD");
                        } else
                        {
                            cantidadLlamadasField.clear();
                            totalFacturadoField.clear();
                            tableView.setPlaceholder(new Label("No se han efectuado llamadas locales"));
                        }

                        break;
                    case "Provinciales":

                        currentLlamadas = FXCollections.observableArrayList(llamadas.get(newValue));

                        tableView.setItems(currentLlamadas);

                        if (!currentLlamadas.isEmpty())
                        {
                            cantidadLlamadasField.setText(String.valueOf(currentLlamadas.size()));
                            totalFacturadoField.setText(String.valueOf(calcularFactura(currentLlamadas)) + " $RD");
                        } else
                        {
                            cantidadLlamadasField.clear();
                            totalFacturadoField.clear();
                            tableView.setPlaceholder(new Label("No se han efectuado llamadas provinciales"));
                        }

                        break;
                }
            }
        });

        /* SE CONFIGURA EL MENU CONTEXTUAL PARA LA LISTVIEW DE LLAMADAS*/
        MenuItem mitem = new MenuItem("Efectuar llamada");
        mitem.setOnAction(e -> efectuarLlamada(e));

        listView.setContextMenu(new ContextMenu(mitem));
    }

    private void configureTable()
    {
        tableView.setPlaceholder(new Label("No se ha seleccionado ningun listado de llamadas"));

        ((TableColumn) tableView.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("numOrigen"));
        ((TableColumn) tableView.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("numDestino"));
        ((TableColumn) ((TableColumn) tableView.getColumns().get(2)).getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("duracion"));
        ((TableColumn) ((TableColumn) tableView.getColumns().get(2)).getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("costo"));
    }

    @FXML
    private void efectuarLlamada(ActionEvent e)
    {
        String selectedItem = listView.getSelectionModel().getSelectedItems().get(0);

        Stage stage = new Stage();
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EfectuarLlamadaDialog.fxml"));
            AnchorPane dialog = loader.load();
            stage.setScene(new Scene(dialog, 399, 300));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            EfectuarLlamadaDialogController controller = loader.getController();
            controller.setStage(stage);

            if (selectedItem != null)
            {
                controller.setLlamadaType(selectedItem);
            }

            stage.showAndWait();

            if (controller.getLlamada() != null)
            {
                if (controller.getLlamada() instanceof LlamadaLocal)
                {
                    LlamadaLocal llamada = (LlamadaLocal) controller.getLlamada();
                    llamadas.get("Locales").add(llamada);

                    tableView.getItems().add(llamada);

                    LlamadasLocalesHandler.getInstance().registrarLlamada(llamada);
                } else
                {
                    LlamadaProvincial llamada = (LlamadaProvincial) controller.getLlamada();
                    llamadas.get("Provinciales").add(llamada);

                    tableView.getItems().add(llamada);

                    LlamadasProvincialesHandler.getInstance().registrarLlamada(llamada);
                }
            }

        } catch (IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void borrarHistorial(ActionEvent e)
    {
        if (!llamadas.get("Provinciales").isEmpty() || !llamadas.get("Locales").isEmpty())
        {
            DataStore.getInstance().limpiarHistorial();
            LlamadasLocalesHandler.getInstance().borrarLlamadas();
            LlamadasProvincialesHandler.getInstance().borrarLlamadas();
        } else
        {
            Dialogs.create().title("Limpiando Historial").masthead("No se pudo borrar el historial").message("El historial esta vacio").showWarning();
            return;
        }

        if (currentLlamadas != null)
        {
            currentLlamadas.clear();
        }

        if (llamadas != null)
        {
            llamadas.get("Locales").clear();
            llamadas.get("Provinciales").clear();
        }

        cantidadLlamadasField.clear();
        totalFacturadoField.clear();

        Dialogs.create().title("Limpiando Historial").masthead("Limpiando Historial").message("Acaba de borrar todo el historial de llamadas.").showInformation();
    }

    class CellWithIcon extends ListCell<String> {

        @Override
        public void updateItem(String item, boolean empty)
        {
            super.updateItem(item, empty);
            if (item != null)
            {
                switch (item)
                {
                    case "Provinciales":
                        setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/llamadaProvincial.png"))));
                        break;
                    case "Locales":
                        setGraphic(new ImageView(new Image(getClass().getResourceAsStream("icons/llamadaLocal.png"))));
                        break;
                }

                setText(item);
            }
        }

    }

    public double calcularFactura(List<? extends Llamada> llamadas)
    {
        double factura = 0;

        for (Llamada llamada : llamadas)
        {
            factura += llamada.calcularPrecio();
        }

        return factura;
    }

    @FXML
    private void openRecargaDialog(ActionEvent event)
    {

        Stage stage = new Stage();

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RealizarRecargaDialog.fxml"));
            AnchorPane dialog = loader.load();
            stage.setScene(new Scene(dialog, 300, 200));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            RealizarRecargaDialogController controller = loader.getController();
            controller.setStage(stage);

            stage.showAndWait();

        } catch (IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void verHistorialRecargas(ActionEvent event)
    {
        Stage stage = new Stage();

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("VerHistorialRecarga.fxml"));
            Scene scene = new Scene(root, 377, 400);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mostrarAcercaDialog(ActionEvent event)
    {
        Stage stage = new Stage();
        try
        {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("HelpDialog.fxml"))));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (IOException ex)
        {
            Logger.getLogger(mainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void borrarHistorialRecargas(ActionEvent event)
    {
        if (!RecargasHandler.getInstance().obtenerRecargas().isEmpty())
        {
            RecargasHandler.getInstance().borrarRecargas();
            Dialogs.create().title("Limpiando Historial").masthead("Limpiando Historial").message("Acaba de borrar todo el historial de recargas.").showInformation();
        } else
        {
            Dialogs.create().title("Limpiando Historial").masthead("No se pudo borrar el historial").message("No se han efectuado recargas").showWarning();

        }
    }
}
