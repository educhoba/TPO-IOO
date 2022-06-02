package Model;

public class AreaJuego {
	
    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;
    private int estadoJuego; //estatico?
    private final int ESTADO_INICIO = 0;
    private final int ESTADO_CORRIENDO = 1;
    private final int ESTADO_PAUSADO = 2;
    private final int ESTADO_MENU = 3;

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

    public boolean estaEnInicio() {
        return this.estadoJuego == ESTADO_INICIO;
    }
    public boolean estaCorriendo() {
        return this.estadoJuego == ESTADO_CORRIENDO;
    }
    public boolean estaPausado() {
        return this.estadoJuego == ESTADO_PAUSADO;
    }
    public boolean estaMenuAbierto() {
        return this.estadoJuego == ESTADO_MENU;
    }
    public void pausarJuego(){
    	this.estadoJuego = ESTADO_PAUSADO;
    }
    public void iniciarOReanudarJuego(){
    	this.estadoJuego = ESTADO_CORRIENDO;
    }
    public void abrirMenu() {
        this.estadoJuego = ESTADO_MENU;
    }
    public void irAlInicio() {
        this.estadoJuego = ESTADO_INICIO;
    }
}