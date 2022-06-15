package model;

import controlador.Controlador;

public abstract class ObjetosJuego {
    
    protected float velocidad;
    protected static float velocidadMultiplicador;
    protected int altura;
    protected int largo;
    protected Coordenada coordenada;
    protected Controlador controlador;

	public ObjetosJuego(float velocidad, int altura, int largo, Coordenada coordenada) 
	{
		this.velocidad = velocidad;
		this.altura = altura;
		this.largo = largo;
		this.coordenada = coordenada;
	}

	public Coordenada getCoordenada() 
	{
		return this.coordenada;
	}
	
	public float getVelocidad()
	{
		return this.velocidad;
	}

}