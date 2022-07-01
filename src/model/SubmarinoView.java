package model;

public class SubmarinoView {
	
	// DEBERIA EXTENDER DE OBJETO JUEGO VIEW

	public SubmarinoView(ObjetoJuego oj) {
    	this.xCoordenada = oj.getCoordenada().getX();
    	this.yCoordenada = oj.getCoordenada().getY();
    }

	protected float xCoordenada;
    protected float yCoordenada;
	
    public float getX() {
        return this.xCoordenada;
    }

    public float getY() {
        return this.yCoordenada;
    }
}