package org.centralita.entity;

import java.io.Serializable;

/**
 *
 * @author Diego Ivan Perez Michel (2013-1488)
 *
 */
public abstract class Llamada implements Serializable {

    public abstract double calcularPrecio();

    private String numOrigen;
    private String numDestino;
    private double duracion;
    private double costo;

    /**
     * Constructor por defecto
     */
    public Llamada()
    {
        
    }

    public Llamada(String numOrigen, String numDestino, double duracion)
    {
        this.numOrigen = numOrigen;
        this.numDestino = numDestino;
        this.duracion = duracion;
    }

    public String getNumOrigen()
    {
        return numOrigen;
    }

    public void setNumOrigen(String numOrigen)
    {
        this.numOrigen = numOrigen;
    }

    public String getNumDestino()
    {
        return numDestino;
    }

    public void setNumDestino(String numDestino)
    {
        this.numDestino = numDestino;
    }

    public double getDuracion()
    {
        return duracion;
    }

    public void setDuracion(double duracion)
    {
        this.duracion = duracion;
    }

    public double getCosto()
    {
        return calcularPrecio();
    }

    public void setCosto(double costo)
    {
        this.costo = costo;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "{" + "numOrigen=" + numOrigen + ", numDestino=" + numDestino + ", duracion=" + duracion + '}';
    }
}
