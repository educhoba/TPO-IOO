package Model;

public class Submarino extends ObjetosJuego {

	/**
	 * Default constructor
	 */
	public Submarino() {
	}

	private int integridadCasco;
	private int vidas;
	private int puntos;
	private int nivel;
	private int vidasExtrasContador;

	public Submarino(int velocidad, int altura, int largo, AreaJuego aj, Coordenada c, int vidas, int integridadCasco) {
		// OJ
		this.velocidad = velocidad;
		this.altura = altura;
		this.largo = largo;
		this.areaJuego = aj;
		this.coordenada = c;
		// Sub
		ObjetosJuego.velocidadMultiplicador = 1;
		this.nivel = 1;
		this.vidasExtrasContador = 0;
		this.puntos = 0;
		this.vidas = vidas;
		this.integridadCasco = integridadCasco;

	}
	// Getters

	public int getNivel() {
		return this.nivel;
	}

	public int getPuntos() {
		return this.puntos;
	}

	public boolean estaVivo() {
		return vidas > 0;
	}

	// Setters

	public void dañar(int valor) {
		this.integridadCasco -= valor;
		// TODO evento de daño
		if (integridadCasco <= 0) {
			restarVida();
		}
	}

	public void añadirPuntos(int valor) {
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

	public void incrementarDificultad(float porcentaje) {
		ObjetosJuego.velocidadMultiplicador = ObjetosJuego.velocidadMultiplicador * (1 + (porcentaje / 100));
	}

	private void restarVida() {
		this.vidas--;
		if (vidas > 0)
			this.integridadCasco = 100;
	}

	public void moverArriba() {
		// TODO implement here
		return;
	}

	public void moverAbajo() {
		// TODO implement here
		return;
	}

	public void moverDerecha() {
		// TODO implement here
		return;
	}

	public void moverIzquierda() {
		// TODO implement here
		return;
	}

}