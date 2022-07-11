package domain.gameObjects;

import java.awt.Dimension;

import domain.gameArea.Coordenada;

public abstract class ObjetoJuego {

    protected int velocidad;
    protected Dimension dimension;
    protected Coordenada coordenada;
    protected static float velocidadMultiplicador;

	public ObjetoJuego(int velocidad, int altura, int largo, Coordenada coordenada) 
	{
		this.velocidad = velocidad;
		this.dimension = new Dimension(largo, altura);
		this.coordenada = coordenada;
	}

	public Coordenada getCoordenada() 
	{
		return this.coordenada;
	}
	
	public Dimension getDimension()
	{
		return this.dimension;
	}

}