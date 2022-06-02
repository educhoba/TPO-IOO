package Model;

public class Submarino extends ObjetosJuego {

	private int integridadCasco;
	private int vidas;
	private int puntos;
	private int nivel;
	private int vidasExtrasContador;
	private final int INTEGRIDAD_CASCO_MAX = 100;

	public Submarino(int velocidad, int altura, int largo, Coordenada c, int vidas) {
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
		this.integridadCasco = INTEGRIDAD_CASCO_MAX;

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
		// TODO ponerle los valores y las condiciones de la consigna
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
		// TODO ponerle los valores y las condiciones de la consigna
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
			this.integridadCasco = INTEGRIDAD_CASCO_MAX;
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