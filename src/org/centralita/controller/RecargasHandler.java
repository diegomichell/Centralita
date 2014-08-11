package org.centralita.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.centralita.entity.Recarga;
import org.centralita.model.RecargaStore;
import static org.centralita.model.RecargaStore.DATA_FILE;

/**
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class RecargasHandler {

    private JAXBContext context;
    private static RecargasHandler handler;
    private RecargaStore recargaStore;

    private RecargasHandler()
    {
        try
        {
            context = JAXBContext.newInstance(RecargaStore.class);
        } catch (JAXBException ex)
        {
            Logger.getLogger(LlamadasLocalesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static RecargasHandler getInstance()
    {
        if (handler == null)
        {
            handler = new RecargasHandler();
        }

        return handler;
    }

    public void registrarRecarga(Recarga recarga)
    {

        try
        {
            Unmarshaller um = context.createUnmarshaller();
            recargaStore = (RecargaStore) um.unmarshal(new FileReader(DATA_FILE));
        } catch (JAXBException jbe)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, jbe.getMessage());
        } catch (FileNotFoundException e)
        {
            recargaStore = RecargaStore.getInstance();
        }

        for (Iterator<Recarga> it = recargaStore.getRecargas().iterator(); it.hasNext();)
        {
            Recarga r = it.next();
            if (r.getNumero().equals(recarga.getNumero()))
            {
                int tempMonto = r.getMonto();
                it.remove();
                recarga.setMonto(tempMonto + recarga.getMonto());
            }
        }

        recargaStore.getRecargas().add(recarga);

        try
        {
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(recargaStore, new File(DATA_FILE));
        } catch (JAXBException e)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ocurrio un error mientras se guardaba el Map hacia el DataStore", e);
        }
    }

    public HashSet<Recarga> obtenerRecargas()
    {
        try
        {
            Unmarshaller um = context.createUnmarshaller();
            recargaStore = (RecargaStore) um.unmarshal(new FileReader(DATA_FILE));
        } catch (JAXBException jbe)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, jbe.getMessage());
        } catch (FileNotFoundException e)
        {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "No existe el archivo recargastore.xml");
        }

        return recargaStore.getRecargas();
    }

    public void borrarRecargas()
    {
        RecargaStore.getInstance().limpiarHistorial();
    }
    
    public boolean recargastoreExist()
    {
        return new File(DATA_FILE).exists();
    }

}
