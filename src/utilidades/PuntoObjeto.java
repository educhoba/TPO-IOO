package utilidades;

import java.awt.Dimension;

public class PuntoObjeto {
	
	private Vector2D coord;
	private Dimension dim;
	
	
	public PuntoObjeto(Vector2D coordenadaObjeto, Dimension dimensionObjeto)
	{
		this.coord = coordenadaObjeto;
		this.dim = dimensionObjeto;
	}
	
	
	public Vector2D puntoArribaIzquierda()
	{
		return new Vector2D(coord.getX(), coord.getY());
	}
	
	public Vector2D puntoArribaMedio()
	{
		int xMedio = coord.getX() + (dim.width / 2);
		int yInicial = coord.getY();
		return new Vector2D(xMedio, yInicial);
	}
	
	public Vector2D puntoArribaDerecha()
	{
		int xFinal = coord.getX() + dim.width;
		int yInicial = coord.getY();
		return new Vector2D(xFinal, yInicial);
	}
	
	public Vector2D puntoMedioIzquierda()
	{
		int xInicial = coord.getX();
		int yMedio = coord.getY() + (dim.height / 2);
		return new Vector2D(xInicial, yMedio);
	}
	
	public Vector2D puntoMedio()
	{
		int xMedio = coord.getX() + (dim.width / 2);
		int yMedio = coord.getY() + (dim.height / 2);
		return new Vector2D(xMedio, yMedio);
	}
	
	public Vector2D puntoMedioDerecha()
	{
		int xFinal = coord.getX() + dim.width;
		int yMedio = coord.getY() + (dim.height / 2);
		return new Vector2D(xFinal, yMedio);
	}
	
	public Vector2D puntoAbajoIzquierda()
	{
		int xInicial = coord.getX();
		int yFinal = coord.getY() + dim.height;
		return new Vector2D(xInicial, yFinal);
	}
	
	public Vector2D puntoAbajoMedio()
	{
		int xMedio = coord.getX() + (dim.width / 2);
		int yFinal = coord.getY() + dim.height;
		return new Vector2D(xMedio, yFinal);
	}
	
	public Vector2D puntoAbajoDerecha()
	{
		int xFinal = coord.getX() + dim.width;
		int yFinal = coord.getY() + dim.height;
		return new Vector2D(xFinal, yFinal);
	}

}
