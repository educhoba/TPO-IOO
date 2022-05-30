package Model;
public class Coordenada {

    /**
     * Default constructor
     */
    public Coordenada() {
    }

    private float xCoordenada;
    private float yCoordenada;
    private AreaJuego areaJuego;

    public Coordenada(float x, float y, AreaJuego aj) {
        this.xCoordenada = x;
        this.yCoordenada = y;
        this.areaJuego = aj;
    }

    public float getX() {
        return this.xCoordenada;
    }

    public float getY() {
        return this.yCoordenada;
    }

    public void moverY(float distance) {
    	float yFinal = this.yCoordenada+distance;
    	
    	if(yFinal > (float)areaJuego.getYMax())
    		this.yCoordenada = (float)areaJuego.getYMax();
    	else if(yFinal < (float)areaJuego.getYMin())
    		this.yCoordenada = (float)areaJuego.getYMin();
    	else
    		this.yCoordenada = yFinal;
    }

    public void moverX(float distance) {
        //TODO chequear maximos con area de juego
        this.xCoordenada+=distance;
    }

}