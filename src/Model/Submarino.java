package model;

import test.Debugger;

public class Submarino extends ObjetosJuego {

	private int integridadCasco;
	private int vidas;
	private int puntos;
	private int nivel;
	private int vidasExtrasContador;
	private static final int INTEGRIDAD_CASCO_MAX = 100;

	public Submarino(float velocidad, int altura, int largo, Coordenada c) {
		super(velocidad, altura, largo, c);
		// Sub
		this.integridadCasco = INTEGRIDAD_CASCO_MAX;
		this.puntos = 0;
		this.vidas = 3;
		this.nivel = 1;
		this.vidasExtrasContador = 0;
		
		ObjetosJuego.velocidadMultiplicador = 1;
	}
	// Getters

	public int getNivel() {
		return this.nivel;
	}

	public int getPuntos() {
		return this.puntos;
	}

	public int getVidas() {
		return this.vidas;
	}
	public int getIntegridadCascoJugador() {
		return this.integridadCasco;
	}
	
	public boolean estaVivo() {
		return vidas >= 0;
	}

	// Setters

	public void calcularExplosion(Coordenada coordenadaCarga) {

		float xExplosion = coordenadaCarga.getX();
		float yExplosion = coordenadaCarga.getY();
		float xSubmarino = this.coordenada.getX();
		float ySubmarino = this.coordenada.getY();

		float mediaAltura = this.altura / 2.0f;
		float medioLargo = this.altura / 2.0f;

		float A = xSubmarino - medioLargo; // xVerticeIzquierdo
		float B = xSubmarino + medioLargo; // xVerticeDerecho
		float C = ySubmarino + mediaAltura;// yVerticeArriba
		float D = ySubmarino - mediaAltura; // yVerticeAbajo

		// (A,C) (B,C)
		// //////
		// // // alto
		// //////
		// (A,D) (B,D)

		float explosionDistancia = calcularDistanciaMinima(A,B,C,D,xExplosion,yExplosion);

		calcularPuntos(explosionDistancia);
		calcularDaño(explosionDistancia);
	}

	private float calcularDistanciaMinima(float A, float B, float C, float D, float xExplosion, float yExplosion) {

		float distanciaMinima = Float.MAX_VALUE;
		float dAux = 0;
		//Recorro x
		for (float x = A; x <= B; x++) {
			// DE (A,C) a (B,C)
			dAux = calcularDistanciaPorPitagoras(x, C, xExplosion, yExplosion);
			if (dAux < distanciaMinima)
				distanciaMinima = dAux;
			// DE (A,D) a (B,D)
			dAux = calcularDistanciaPorPitagoras(x, D, xExplosion, yExplosion);
			if (dAux < distanciaMinima)
				distanciaMinima = dAux;
		}
		//Recorro y
		for (float y = C; y <= D; y++) {
			// DE (A,D) a (A,C)
			dAux = calcularDistanciaPorPitagoras(A, y, xExplosion, yExplosion);
			if (dAux < distanciaMinima)
				distanciaMinima = dAux;
			// DE (B,D) a (B,C)
			dAux = calcularDistanciaPorPitagoras(B, y, xExplosion, yExplosion);
			if (dAux < distanciaMinima)
				distanciaMinima = dAux;
		}

		return distanciaMinima;
	}

	private float calcularDistanciaPorPitagoras(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1, 2));
	}

	private void calcularDaño(float explosionDistancia) {
		if (explosionDistancia < 100)
			dañar(100);
		else if (explosionDistancia < 500)
			dañar(50);
		else if (explosionDistancia < 1000)
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
		// TODO evento de daño
		Debugger.printIntegridadSubmarino(valor, this.integridadCasco);
		if (integridadCasco <= 0) {
			restarVida();
		}
	}

	private void añadirPuntos(int valor) {
		this.puntos += valor;
		// TODO evento de puntos
		if (this.puntos / 500 > vidasExtrasContador) {
			añadirVidas();
		}
	}

	private void añadirVidas() {
		this.vidas++;
		this.vidasExtrasContador++;
	}

	public void pasarDeNivelEIncrementarDificultad(float porcentaje) {
		ObjetosJuego.velocidadMultiplicador = ObjetosJuego.velocidadMultiplicador * (1 + (porcentaje / 100));
		// TODO evento de nivel
		this.nivel++;
		Debugger.printPasarNivel(this.nivel);
		añadirPuntos(200);
	}

	private void restarVida() {
		this.vidas--;
		Debugger.printPerderVida(this.vidas);
		if (vidas >= 0)
		{
			Debugger.printRecargarIntegridad();
			this.integridadCasco = INTEGRIDAD_CASCO_MAX;
		}
		
	}

	public void moverArriba() {
		var distancia = this.velocidad;
		this.coordenada.moverY(distancia);
	}

	public void moverAbajo() {
		var distancia = -this.velocidad;
		this.coordenada.moverY(distancia);
	}

	public void moverDerecha() {
		var distancia = this.velocidad;
		this.coordenada.moverX(distancia);
	}

	public void moverIzquierda() {
		var distancia = -this.velocidad;
		this.coordenada.moverX(distancia);
	}

}