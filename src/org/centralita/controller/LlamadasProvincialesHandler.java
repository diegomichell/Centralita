package org.centralita.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.centralita.entity.LlamadaProvincial;
import org.centralita.model.DataStore;
import static org.centralita.model.DataStore.DATA_FILE;

/**
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class LlamadasProvincialesHandler {

    private JAXBContext context;
    private static LlamadasProvincialesHandler handler;
    private DataStore dataStore;

    private LlamadasProvincialesHandler()
    {
        try
        {
            context = JAXBContext.newInstance(DataStore.class);
        } catch (JAXBException ex)
        {
            Logger.getLogger(LlamadasProvincialesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static LlamadasProvincialesHandler getInstance()
    {
        if (handler == null)
        {
            handler = new LlamadasProvincialesHandler();
        }

        return handler;
    }

    public void registrarLlamada(LlamadaProvincial llamada)
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

        dataStore.getLlamadasProvinciales().add(llamada);

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

    public ArrayList<LlamadaProvincial> obtenerLlamadas()
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

        return dataStore.getLlamadasProvinciales();
    }

    public void borrarLlamadas()
    {
        dataStore.getLlamadasProvinciales().clear();
    }
}
