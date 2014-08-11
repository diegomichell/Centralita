/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centralita.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import org.centralita.entity.LlamadaLocal;
import org.centralita.entity.LlamadaProvincial;

/**
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
@XmlRootElement(namespace = "org.centralita.model")
public class DataStore implements Store {

    private ArrayList<LlamadaLocal> llamadasLocales;
    private ArrayList<LlamadaProvincial> llamadasProvinciales;
    private static DataStore dataStore;
    public static final String DATA_FILE = "datastore.xml";
    
    private DataStore()
    {
        llamadasLocales = new ArrayList<>();
        llamadasProvinciales = new ArrayList<>();
    }

    public static DataStore getInstance()
    {
        if (dataStore == null)
        {
            dataStore = new DataStore();
        }

        return dataStore;
    }

    public ArrayList<LlamadaLocal> getLlamadasLocales()
    {
        return llamadasLocales;
    }

    public void setLlamadasLocales(ArrayList<LlamadaLocal> llamadasLocales)
    {
        this.llamadasLocales = llamadasLocales;
    }

    public ArrayList<LlamadaProvincial> getLlamadasProvinciales()
    {
        return llamadasProvinciales;
    }

    public void setLlamadasProvinciales(ArrayList<LlamadaProvincial> llamadasProvinciales)
    {
        this.llamadasProvinciales = llamadasProvinciales;
    }
    
    @Override
    public void limpiarHistorial()
    {
        String empty = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<ns2:dataStore xmlns:ns2=\"org.centralita.model\"/>";
        
        try(BufferedWriter buff = new BufferedWriter(new FileWriter(DATA_FILE)))
        {
           
            buff.write(empty);
        } catch (IOException ex)
        {
            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
