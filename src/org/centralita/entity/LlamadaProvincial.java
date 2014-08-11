package org.centralita.entity;

/**
 *
 * @author Diego Ivan Perez Michel (2013-1488)
 *
 */
public class LlamadaProvincial extends Llamada {

    /*
     * Precios de las llamadas en funcion de la franja horaria
     */
    private final int precio1 = 20;
    private final int precio2 = 25;
    private final int precio3 = 30;

    private int franja;

    /**
     * Constructor por defecto
     */
    public LlamadaProvincial()
    {

    }

    /**
     * Inicializa los atributos de la llamada
     *
     * @param origen
     * @param destino
     * @param duracion
     */
    public LlamadaProvincial(String origen, String destino, double duracion)
    {
        super(origen, destino, duracion);
    }

    public LlamadaProvincial(String origen, String destino, double duracion, int franja)
    {
        this(origen,destino,duracion);
        this.franja = franja;
    }

    /**
     * @return coste de la llamada, retorna 0 en caso de que la franja horaria
     * no sea valida
     */
    @Override
    public double calcularPrecio()
    {

        double total = 0;

        switch (franja)
        {
            case 1:
                total = precio1 * getDuracion();
                break;
            case 2:
                total = precio2 * getDuracion();
                break;
            case 3:
                total = precio3 * getDuracion();
        }

        return total;
    }

    public int getFranja()
    {
        return franja;
    }

    public void setFranja(int franja)
    {
        this.franja = franja;
    }
}
