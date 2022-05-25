package Model;
public abstract class ObjetosJuego {

    /**
     * Default constructor
     */
    public ObjetosJuego() {
    }
    
    protected float velocidad;
    protected static float velocidadMultiplicador;
    protected int altura;
    protected int largo;
    protected Coordenada coordenada;
    protected AreaJuego areaJuego;

    public Coordenada getCoordenada() {
        return this.coordenada;
    }

}