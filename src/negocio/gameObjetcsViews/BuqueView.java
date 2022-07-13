package negocio.gameObjetcsViews;

import negocio.gameObjects.Buque;

public class BuqueView extends ObjetoJuegoView{

	private boolean finalizoRecorrido;
	private int direccion;
	
	public BuqueView(Buque b) {
    	super(b);
    	finalizoRecorrido = b.finalizoRecorrido();
    	verificarDireccion(b);
    }
	
	private void verificarDireccion(Buque b)
	{
		if (b.getVelocidad() > 0) // si la velocidad es positiva -si va de izq a der-
			direccion = 0;
		else // si la velocidad es negativa -si va de der a izq-
			direccion = 1;
	}
	
	public boolean finalizoRecorrido() 
	{
		return this.finalizoRecorrido;
	}
	
	public int getDireccion()
	{
		return this.direccion;
	}
	
}
