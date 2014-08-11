package org.centralita.entity;

/**
 *
 * @author Diego Ivan Perez Michel (2013-1488)
 *
 */
public class LlamadaLocal extends Llamada {

    private final int PRECIO = 15;

    /**
     * Constructor por defecto
     */
    public LlamadaLocal()
    {

    }

    /**
     * Inicializa los atributos de la llamada
     *
     * @param origen
     * @param destino
     * @param duracion
     */
    public LlamadaLocal(String origen, String destino, double duracion)
    {
        super(origen, destino, duracion);
    }

    @Override
    public double calcularPrecio()
    {
        return getDuracion() * PRECIO;
    }

}
