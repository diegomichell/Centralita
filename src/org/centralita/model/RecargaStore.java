/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.centralita.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import org.centralita.entity.Recarga;

/**
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
@XmlRootElement(namespace = "org.centralita.model")
public class RecargaStore implements Store {

    public static final String DATA_FILE = "recargastore.xml";
    private static RecargaStore recargaStore;
    private HashSet<Recarga> recargas;
    
    private RecargaStore()
    {
        recargas = new HashSet<>();
    }
    
    public static RecargaStore getInstance()
    {
        if(recargaStore == null)
        {
            recargaStore = new RecargaStore();
        }
        
        return recargaStore;
    }
    
    @Override
    public void limpiarHistorial()
    {
        String empty = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<ns2:recargaStore xmlns:ns2=\"org.centralita.model\"/>";
        
        try(BufferedWriter buff = new BufferedWriter(new FileWriter(DATA_FILE)))
        {
           
            buff.write(empty);
        } catch (IOException ex)
        {
            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public HashSet<Recarga> getRecargas()
    {
        return recargas;
    }

    public void setRecargas(HashSet<Recarga> recargas)
    {
        this.recargas = recargas;
    }
}
