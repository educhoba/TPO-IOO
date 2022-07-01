package model;

public abstract class ObjetoJuego {

    protected float velocidad;
    protected static float velocidadMultiplicador;
    protected int altura;
    protected int largo;
    protected Coordenada coordenada;

	public ObjetoJuego(float velocidad, int altura, int largo, Coordenada coordenada) 
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

}