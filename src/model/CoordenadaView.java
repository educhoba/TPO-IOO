package model;

public class CoordenadaView {

    public CoordenadaView(Coordenada c) {
    	this.xCoordenada = c.getX();
    	this.yCoordenada = c.getY();
    }

    private float xCoordenada;
    private float yCoordenada;
	
	

    public float getX() {
        return this.xCoordenada;
    }

    public float getY() {
        return this.yCoordenada;
    }
}