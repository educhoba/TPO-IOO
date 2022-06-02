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

	public Submarino(int velocidad, int altura, int largo, Coordenada c, int vidas, int integridadCasco) {
		// OJ
		this.velocidad = velocidad;
		this.altura = altura;
		this.largo = largo;
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

		float A = xSubmarino + medioLargo; // xVerticeDerecho
		float B = xSubmarino - medioLargo; // xVerticeIzquierdo
		float C = xSubmarino + mediaAltura;// yVerticeArriba
		float D = xSubmarino - mediaAltura; // yVerticeAbajo

		// (A,C)          largo              (B,C)
		// //////////////////////////////////
		// //                              //
		// //                              // alto
		// //                              //
		// //////////////////////////////////
		// (A,D)                             (B,D)

		// TODO: calcular distancia minima
		float explosionDistancia = 0;

		calcularPuntos(explosionDistancia);
		calcularDaño(explosionDistancia);
	}

	private void calcularDaño(float explosionDistancia) {
		//TODO ponerle los valores y las condiciones de la consigna
		boolean condicion1 = true;
		boolean condicion2 = true;
		int valor1 = 0;
		int valor2 = 0;
		int valor3 = 0;
		
		if (condicion1)
			dañar(valor1);
		else if (condicion2)
			dañar(valor2);
		else
			dañar(valor3);
	}

	private void calcularPuntos(float explosionDistancia) {
		//TODO ponerle los valores y las condiciones de la consigna
		boolean condicion1 = true;
		boolean condicion2 = true;
		int valor1 = 0;
		int valor2 = 0;
		int valor3 = 0;
		
		if (condicion1)
			añadirPuntos(valor1);
		else if (condicion2)
			añadirPuntos(valor2);
		else
			añadirPuntos(valor3);

	}

	private void dañar(int valor) {
		this.integridadCasco -= valor;
		// TODO evento de daño
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
	}

	private void restarVida() {
		this.vidas--;
		if (vidas >= 0)
			this.integridadCasco = 100;
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