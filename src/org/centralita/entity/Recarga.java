/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centralita.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centralita.util.Util;

/**
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class Recarga implements Serializable {

    private String numero;
    private int monto;
    private String fecha;

    public Recarga()
    {
        fecha = Util.formatDate(new Date());
    }

    public Recarga(String numero, int monto)
    {
        this.numero = numero;
        this.monto = monto;
        this.fecha = Util.formatDate(new Date());
    }

    public String getNumero()
    {
        return numero;
    }

    public void setNumero(String numero)
    {
        this.numero = numero;
    }

    public void setMonto(int monto)
    {
        this.monto = monto;
    }

    public int getMonto()
    {
        return monto;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = Util.formatDate(fecha);
    }

    public String getFecha()
    {
        return fecha;
    }

    @Override
    public String toString()
    {
        return "Recarga{" + "numero=" + numero + ", monto=" + monto + ", fecha=" + fecha + '}';
    }
}
