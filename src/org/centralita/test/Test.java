/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centralita.test;

import java.util.ArrayList;
import org.centralita.entity.Llamada;
import org.centralita.entity.LlamadaLocal;
import org.centralita.entity.LlamadaProvincial;
import org.centralita.controller.LlamadasLocalesHandler;
import org.centralita.controller.LlamadasProvincialesHandler;
import org.centralita.controller.RecargasHandler;
import org.centralita.entity.Recarga;

/**
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class Test {

    public void testLlamada()
    {
        LlamadasLocalesHandler h = LlamadasLocalesHandler.getInstance();
        h.registrarLlamada(new LlamadaLocal("809-750-3664", "1234", 20));

        LlamadasProvincialesHandler p = LlamadasProvincialesHandler.getInstance();
        p.registrarLlamada(new LlamadaProvincial("809-956-1664", "1234", 20, 1));

        ArrayList<Llamada> list = new ArrayList<>();
        list.addAll(p.obtenerLlamadas());
        list.addAll(h.obtenerLlamadas());
    }

    public void testRecarga()
    {
        RecargasHandler h = RecargasHandler.getInstance();
        Recarga r = new Recarga();
        r.setNumero("809-525-1929");
        r.setMonto(15);
        
        h.registrarRecarga(r);

        System.out.println(h.obtenerRecargas());
    }

}
