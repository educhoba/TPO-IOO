package domain.gameObjetcsViews;

import domain.gameObjects.Submarino;

public class SubmarinoView extends ObjetoJuegoView{
	
	private int direccion;

	public SubmarinoView(Submarino s) {
		super(s);
		this.direccion = s.getDireccion();
	}
	
	public int getDireccion()
	{
		return this.direccion;
	}

}