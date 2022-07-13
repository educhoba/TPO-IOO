package negocio.gameObjects;

import java.awt.Dimension;
import java.util.Random;

import negocio.Ini;
import negocio.gameArea.Coordenada;
import utilidades.PuntoObjeto;
import utilidades.Vector2D;

public class CargaProfundidad extends ObjetoJuego 
{
	private int yExplosion;
	private boolean soltada;
	private boolean explotada;

	
	public CargaProfundidad(int velocidad, int altura, int largo, Coordenada c
			, int yMaxExplosion, int yMinExplosion) 
	{
		super(velocidad, altura, largo, c);
		setAlturaExplosion(yMaxExplosion, yMinExplosion);
		this.soltada = false;
		this.explotada = false;
	}
	
	
	private void setAlturaExplosion(int yMaxExplosion, int yMinExplosion) 
	{
		this.yExplosion = new Random().nextInt(yMaxExplosion + 1 - yMinExplosion) + yMinExplosion;
	}
	
	public void moverConBuque(int xCoordBuque) 
	{ // Recibe xCoordBuque + largoBuque/2 (puntoMedioBuque) para estar siempre en el punto medio del buque. 
		this.coordenada.setX(xCoordBuque);
	}

	public void moverY(float deltaTime)
	{
		int distancia = Math.round(deltaTime * this.velocidad * ObjetoJuego.velocidadMultiplicador);
		this.coordenada.moverY(distancia);
		
		if (this.coordenada.getVectorCoordenada().getY() >= this.yExplosion) //chequeo si tiene que explotar
			explotar();
	}
	
	public void soltar() 
	{
		soltada = true;
	}
	
	public boolean estaSoltada() 
	{
		return this.soltada;
	}
	
	private void explotar() 
	{
		explotada = true;
	}
	
	public boolean estaExplotada() 
	{
		return this.explotada;
	}
	
	public Vector2D getVectorExplosion()
	{
		Vector2D pMedioCarga = new PuntoObjeto(this.coordenada.getVectorCoordenada(), this.dimension).puntoMedio();

		int x = pMedioCarga.getX() - Ini.EXPLOSION_LARGO / 2;
		int y = pMedioCarga.getY() - Ini.EXPLOSION_ALTO / 2;
		
		return new Vector2D(x, y);
	}
	
	public Dimension getDimensionExplosion()
	{
		return new Dimension(Ini.EXPLOSION_LARGO, Ini.EXPLOSION_ALTO);
	}
	
}