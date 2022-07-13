package negocio.gameObjetcsViews;

import java.awt.Dimension;

import negocio.gameObjects.ObjetoJuego;
import utilidades.Vector2D;

abstract class ObjetoJuegoView {
    
    protected Vector2D coordenada;
    protected Dimension dimension;

	protected ObjetoJuegoView(ObjetoJuego oj) 
	{
		this.coordenada = oj.getCoordenada().getVectorCoordenada();
		this.dimension = oj.getDimension();
    }
	
    public int getX() {
        return this.coordenada.getX();
    }

    public int getY() {
        return this.coordenada.getY();
    }
    
    public int getLargo() {
		return dimension.width;
	}


	public int getAlto() {
		return dimension.height;
	}
    
}