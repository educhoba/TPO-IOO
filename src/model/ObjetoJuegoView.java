package model;

abstract class ObjetoJuegoView {
	
	protected float xCoordenada;
    protected float yCoordenada;

	public ObjetoJuegoView(ObjetoJuego oj) {
    	this.xCoordenada = oj.getCoordenada().getX();
    	this.yCoordenada = oj.getCoordenada().getY();
    }
	
    public float getX() {
        return this.xCoordenada;
    }

    public float getY() {
        return this.yCoordenada;
    }
}