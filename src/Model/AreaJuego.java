package Model;

public class AreaJuego {

    /**
     * Default constructor
     */
    public AreaJuego() {
    }

    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;
    private int estadoJuego; //estatico?
    private final int ESTADO_PAUSADO = 0;
    private final int ESTADO_CORRIENDO = 1;

    public AreaJuego(int xMax , int xMin, int yMax, int yMin) {
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
    }

    public int getXMax() {
        return this.xMax;
    }

    public int getXMin() {
        return this.xMin;
    }

    public int getYMax() {
        return this.yMax;
    }

    public int getYMin() {
        return this.yMin;
    }

    public boolean estaCorriendo() {
        return this.estadoJuego == ESTADO_CORRIENDO;
    }
    public boolean estaPausado() {
        return this.estadoJuego == ESTADO_PAUSADO;
    }
    public void pausarJuego(){
    	this.estadoJuego = ESTADO_PAUSADO;
    }
    public void iniciarOReanudarJuego(){
    	this.estadoJuego = ESTADO_CORRIENDO;
    }
}