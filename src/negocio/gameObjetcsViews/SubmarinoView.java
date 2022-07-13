package negocio.gameObjetcsViews;

import negocio.gameObjects.Submarino;

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