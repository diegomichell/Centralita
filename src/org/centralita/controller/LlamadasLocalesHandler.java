package org.centralita.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.centralita.entity.LlamadaLocal;
import org.centralita.model.DataStore;
import static org.centralita.model.DataStore.DATA_FILE;

/**
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class LlamadasLocalesHandler {

    private JAXBContext context;
    private static LlamadasLocalesHandler handler;
    private DataStore dataStore;

    private LlamadasLocalesHandler()
    {
        try
        {
            context = JAXBContext.newInstance(DataStore.class);
        } catch (JAXBException ex)
        {
            Logger.getLogger(LlamadasLocalesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static LlamadasLocalesHandler getInstance()
    {
        if (handler == null)
        {
            handler = new LlamadasLocalesHandler();
        }

        return handler;
    }

    public void registrarLlamada(LlamadaLocal llamada)
    {
        try
        {
            Unmarshaller um = context.createUnmarshaller();
            dataStore = (DataStore) um.unmarshal(new FileReader(DATA_FILE));
        } catch (JAXBException jbe)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, jbe.getMessage());
        } catch (FileNotFoundException e)
        {
            dataStore = DataStore.getInstance();
        }

        dataStore.getLlamadasLocales().add(llamada);

        try
        {
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(dataStore, new File(DATA_FILE));
        } catch (JAXBException e)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ocurrio un error mientras se guardaba el Map hacia el DataStore", e);
        }
    }

    public ArrayList<LlamadaLocal> obtenerLlamadas()
    {
        try
        {
            Unmarshaller um = context.createUnmarshaller();
            dataStore = (DataStore) um.unmarshal(new FileReader(DATA_FILE));
        } catch (JAXBException jbe)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, jbe.getMessage());
        } catch (FileNotFoundException e)
        {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "No existe el archivo datastore.xml");
        }

        return dataStore.getLlamadasLocales();
    }
    
    public void borrarLlamadas()
    {
        dataStore.getLlamadasLocales().clear();
    }

}
