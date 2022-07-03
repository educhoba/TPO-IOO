package model;

abstract class ObjetoJuegoView {
	
	protected int xCoordenada;
    protected int yCoordenada;

	public ObjetoJuegoView(ObjetoJuego oj) {
    	this.xCoordenada = (int)oj.getCoordenada().getX();
    	this.yCoordenada = (int)oj.getCoordenada().getY();
    }
	
    public int getX() {
        return this.xCoordenada;
    }

    public int getY() {
        return this.yCoordenada;
    }
    
}