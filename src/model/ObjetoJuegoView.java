package model;

abstract class ObjetoJuegoView {
	
	protected int id; // NO UTILIZAMOS EL ID
	protected float xCoordenada;
    protected float yCoordenada;

	public ObjetoJuegoView(ObjetoJuego oj) {
    	this.xCoordenada = oj.getCoordenada().getX();
    	this.yCoordenada = oj.getCoordenada().getY();
    	this.id = oj.id;
    }
	
    public float getX() {
        return this.xCoordenada;
    }

    public float getY() {
        return this.yCoordenada;
    }
}