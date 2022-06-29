package model;

abstract class ObjetoJuegoView {

	public ObjetoJuegoView(ObjetoJuego oj) {
    	this.xCoordenada = oj.getCoordenada().getX();
    	this.yCoordenada = oj.getCoordenada().getY();
    	this.id = oj.id;
    }

	protected int id;
	protected float xCoordenada;
    protected float yCoordenada;
	
    public float getX() {
        return this.xCoordenada;
    }

    public float getY() {
        return this.yCoordenada;
    }
}