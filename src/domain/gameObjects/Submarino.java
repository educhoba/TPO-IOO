package domain.gameObjects;

import java.awt.Dimension;

import domain.gameArea.Coordenada;
import test.Debugger;
import utilidades.PuntoObjeto;
import utilidades.Vector2D;

public class Submarino extends ObjetoJuego {

	private int integridadCasco;
	private int vidas;
	private int puntos;
	private int nivel;
	private int direccion;
	private int contVidasGanadas;
	private static final int INTEGRIDAD_CASCO_MAX = 100;

	public Submarino(int velocidad, int altura, int largo, Coordenada c) {
		super(velocidad, altura, largo, c);
		this.integridadCasco = INTEGRIDAD_CASCO_MAX;
		this.puntos = 0;
		this.vidas = 3;
		this.nivel = 1;
		this.direccion = 0;
		this.contVidasGanadas = 1;
		ObjetoJuego.velocidadMultiplicador = 1;
	}
	

	public int getNivel() 
	{
		return this.nivel;
	}

	public int getPuntos() 
	{
		return this.puntos;
	}

	public int getVidas() 
	{
		int v = this.vidas;
		if (v < 0)
			v = 0;
		return v;
	}
	
	public int getIntegridadCasco() 
	{
		int i = this.integridadCasco;
		if (i < 0)
			i = 0;
		return i;
	}
	
	public boolean estaVivo() {
		boolean vivo = false;
		if (vidas > 0)
			vivo = true;
		else if (vidas == 0)
		{
			if (integridadCasco > 0)
				vivo = true;
		}
		return vivo;
	}

	private Vector2D[] buscarVectoresEnCuadrante(Coordenada coordCarga, Dimension dimCarga)
    {
    	PuntoObjeto pCarga = new PuntoObjeto(coordCarga.getVectorCoordenada(), dimCarga);
		PuntoObjeto pSubmarino = new PuntoObjeto(this.coordenada.getVectorCoordenada(), this.dimension);
		
		int xMCarga = pCarga.puntoMedio().getX();
		int yMCarga = pCarga.puntoMedio().getY();
		int xMSubmarino = pSubmarino.puntoMedio().getX();
		int yMSubmarino = pSubmarino.puntoMedio().getY();
		
		Vector2D vIniSub;
		Vector2D vFinSub;
		Vector2D vIniCarga;
		Vector2D vFinCarga;
		
		if (xMCarga > xMSubmarino && yMCarga > yMSubmarino)
		{ // 2do Cuadrante
			vIniSub = pSubmarino.puntoMedioDerecha();
			vFinSub = pSubmarino.puntoAbajoMedio();
			
			vIniCarga = pCarga.puntoMedioIzquierda();
			vFinCarga = pCarga.puntoArribaMedio();
		}
		else if (xMCarga > xMSubmarino && yMCarga < yMSubmarino)
		{ // 1er Cuadrante
			vIniSub = pSubmarino.puntoArribaMedio();
			vFinSub = pSubmarino.puntoMedioDerecha();
			
			vIniCarga = pCarga.puntoAbajoMedio();
			vFinCarga = pCarga.puntoMedioIzquierda();
		}
		else if (xMCarga < xMSubmarino && yMCarga < yMSubmarino)
		{ // 4to Cuadrante
			vIniSub = pSubmarino.puntoMedioIzquierda();
			vFinSub = pSubmarino.puntoArribaMedio();
			
			vIniCarga = pCarga.puntoMedioDerecha();
			vFinCarga = pCarga.puntoAbajoMedio();
		}
		else // else if (xMCarga < xMSubmarino && yMCarga > yMSubmarino)
		{ // 3er Cuadrante o Mismo Punto Medio.
			vIniSub = pSubmarino.puntoAbajoMedio();
			vFinSub = pSubmarino.puntoMedioIzquierda();
			
			vIniCarga = pCarga.puntoArribaMedio();
			vFinCarga = pCarga.puntoMedioDerecha();
		}
		
		Vector2D[] vectores = new Vector2D[4];
		vectores[0] = vIniSub;
		vectores[1] = vFinSub;
		vectores[2] = vIniCarga;
		vectores[3] = vFinCarga;
		
		return vectores;
    }
    
    private Vector2D[] ordenarVectores(Vector2D[] vectores)
    {
    	 Vector2D vIniSub = vectores[0];
    	 Vector2D vFinSub = vectores[1];
    	 Vector2D vIniCarga = vectores[2];
    	 Vector2D vFinCarga = vectores[3];
    	
    	int xIniSub, xFinSub, yIniSub, yFinSub;
		int xIniCarga, xFinCarga, yIniCarga, yFinCarga;
		
		if (vIniSub.getX() < vFinSub.getX())
		{
			xIniSub = vIniSub.getX();
			xFinSub = vFinSub.getX();
		}
		else
		{
			xIniSub = vFinSub.getX();
			xFinSub = vIniSub.getX();
		}
			
		if (vIniSub.getY() < vFinSub.getY())
		{
			yIniSub = vIniSub.getY();
			yFinSub = vFinSub.getY();
		}
		else
		{
			yIniSub = vFinSub.getY();
			yFinSub = vIniSub.getY();
		}
		
		if (vIniCarga.getX() < vFinCarga.getX())
		{
			xIniCarga = vIniCarga.getX();
			xFinCarga = vFinCarga.getX();
		}
		else
		{
			xIniCarga = vFinCarga.getX();
			xFinCarga = vIniCarga.getX();
		}
		
		if (vIniCarga.getY() < vFinCarga.getY())
		{
			yIniCarga = vIniCarga.getY();
			yFinCarga = vFinCarga.getY();
		}
		else
		{
			yFinCarga = vIniCarga.getY();
			yIniCarga = vFinCarga.getY();
		}
		
		vectores[0] = new Vector2D(xIniSub, yIniSub);
		vectores[1] = new Vector2D(xFinSub, yFinSub);
		vectores[2] = new Vector2D(xIniCarga, yIniCarga);
		vectores[3] = new Vector2D(xFinCarga, yFinCarga);
		
		return vectores;
    }
    
    private float calcularDistancia(Vector2D[] vectores)
    {
    	int xIniSub = vectores[0].getX();
    	int yIniSub = vectores[0].getY();
    	int xFinSub = vectores[1].getX();
    	int yFinSub = vectores[1].getY();
    	int xIniCarga = vectores[2].getX();
    	int yIniCarga = vectores[2].getY();
    	int xFinCarga = vectores[3].getX();
    	int yFinCarga = vectores[3].getY();
    	
    	
    	float distanciaMinima = calcularDistanciaPorPitagoras(xIniSub, yIniSub, xIniCarga, yIniCarga);
		float distancia;
		
		for (int x1 = xIniSub; x1 <= xFinSub; x1++)
		{
			for (int y1 = yIniSub; y1 <= yFinSub; y1++)
			{
				for (int x2 = xIniCarga; x2 <= xFinCarga; x2++)
				{
					for (int y2 = yIniCarga; y2 <= yFinCarga; y2++)
					{
						distancia = calcularDistanciaPorPitagoras(x1, y1, x2, y2);
						
						if (distancia < distanciaMinima)
							distanciaMinima = distancia;
					}
				}
			}
		}
		
		return distanciaMinima;
    }
    
    private float calcularDistanciaPorPitagoras(float x1, float y1, float x2, float y2) {
		return (float)(Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1, 2)));
	}
	
	public void calcularExplosion(Coordenada coordCarga, Dimension dimCarga)
	{
		Vector2D[] vectores = buscarVectoresEnCuadrante(coordCarga, dimCarga);
		
		vectores = ordenarVectores(vectores);
		
		float distanciaMinima = calcularDistancia(vectores);
		
		calcularDaño(distanciaMinima);
		calcularPuntos(distanciaMinima);
	}

	private void calcularDaño(float explosionDistancia) {
		if (explosionDistancia < 10)
			dañar(100);
		else if (explosionDistancia < 50)
			dañar(50);
		else if (explosionDistancia < 100)
			dañar(30);
	}

	private void calcularPuntos(float explosionDistancia) {
		if (explosionDistancia > 100)
			añadirPuntos(30);
		else if (explosionDistancia > 50)
			añadirPuntos(10);
	}

	private void dañar(int valor) {
		this.integridadCasco -= valor;
		Debugger.printIntegridadSubmarino(valor, this.integridadCasco);
		if (integridadCasco <= 0)
		{
			int x = -integridadCasco;
			restarVida();
			if (this.vidas > 0)
				dañar(x);
		}
	}

	private void añadirPuntos(int valor) {
		this.puntos += valor;
		if (this.puntos / (500 * this.contVidasGanadas) >= 1) 
		{
			añadirVidas();
		}
	}

	private void añadirVidas() 
	{
		this.vidas++;
		this.contVidasGanadas++;
	}

	private void restarVida() {
		this.vidas--;
		Debugger.printPerderVida(this.vidas);
		if (vidas > 0)
		{
			Debugger.printRecargarIntegridad();
			this.integridadCasco = INTEGRIDAD_CASCO_MAX;
		}
	}
	
	public void pasarDeNivelEIncrementarDificultad(float porcentaje) {
		ObjetoJuego.velocidadMultiplicador = ObjetoJuego.velocidadMultiplicador * (1 + (porcentaje / 100));
		this.nivel++;
		Debugger.printPasarNivel(this.nivel);
		añadirPuntos(200);
	}

	public void moverArriba() {
		this.coordenada.moverY(-this.velocidad);
	}

	public void moverAbajo() {
		this.coordenada.moverY(this.velocidad);
	}

	public void moverDerecha() 
	{
		if (this.direccion == 0)
		{
			this.coordenada.moverX(this.velocidad);
		}
		this.direccion = 0;
	}

	public void moverIzquierda() 
	{
		if (this.direccion == 1)
		{
			this.coordenada.moverX(-this.velocidad);
		}
		this.direccion = 1;
	}
	
	public int getDireccion()
	{
		return this.direccion;
	}

}