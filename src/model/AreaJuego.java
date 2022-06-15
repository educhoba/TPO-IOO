package model;

public class AreaJuego {
	
    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;
    private Estado estadoJuego;
    enum Estado{
    	INICIO,
    	CORRIENDO,
    	PAUSADO,
    	MENU,
    	SALIR
    }

    public AreaJuego(int xMax , int xMin, int yMax, int yMin) {
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
        estadoJuego = Estado.INICIO;
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
        return this.estadoJuego == Estado.INICIO;
    }
    public boolean estaCorriendo() {
        return this.estadoJuego == Estado.CORRIENDO;
    }
    public boolean estaPausado() {
        return this.estadoJuego == Estado.PAUSADO;
    }
    public boolean estaMenuAbierto() {
        return this.estadoJuego == Estado.MENU;
    }
    public boolean estaSaliendo() {
        return this.estadoJuego == Estado.SALIR;
    }
    public void pausarJuego(){
    	this.estadoJuego = Estado.PAUSADO;
    }
    public void iniciarOReanudarJuego(){
    	this.estadoJuego = Estado.CORRIENDO;
    }
    public void abrirMenu() {
        this.estadoJuego = Estado.MENU;
    }
    public void irAlInicio() {
        this.estadoJuego = Estado.INICIO;
    }
    public void finalizarEjecucion() {
        this.estadoJuego = Estado.SALIR;
    }
}