package domain.gameObjetcsViews;

import java.awt.Dimension;

import domain.gameObjects.CargaProfundidad;
import utilidades.Vector2D;

public class CargaProfundidadView extends ObjetoJuegoView{

	private boolean exploto;
	private boolean soltada;
	private Vector2D vExplosion;
	private Dimension dExplosion;
	
	public CargaProfundidadView(CargaProfundidad cp) {
    	super(cp);
    	exploto = cp.estaExplotada();
    	soltada = cp.estaSoltada();
    	vExplosion = cp.getVectorExplosion();
    	dExplosion = cp.getDimensionExplosion();
    }
	
	public boolean estaExplotada() {
		return exploto;
	}

	public boolean estaSoltada() {
		return soltada;
	}
	
	public Vector2D getVectorExplosion()
	{
		return vExplosion;
	}
	
	public Dimension getDimensionExplosion()
	{
		return dExplosion;
	}
}