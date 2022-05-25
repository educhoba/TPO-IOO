package Model;
public class Coordenada {

    /**
     * Default constructor
     */
    public Coordenada() {
    }

    private float xCoordenada;
    private float yCoordenada;

    public Coordenada(float x, float y, AreaJuego areaJuego) {
        this.xCoordenada = x;
        this.yCoordenada = y;
    }

    public float getX() {
        return this.xCoordenada;
    }

    public float getY() {
        return this.yCoordenada;
    }

    public void moverY(float distance) {
       this.yCoordenada+=distance;
    }

    public void moverX(float distance) {
        this.xCoordenada+=distance;
    }

}