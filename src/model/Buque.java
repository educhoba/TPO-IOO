package model;

import java.util.Random;

public class Buque extends ObjetoJuego {

	private float xSoltar;
	private CargaProfundidad cargaProfunidad;
	private static int cantBuques = 1;

	public Buque(float velocidad, int altura, int largo, Coordenada c, CargaProfundidad cp) {
		super(velocidad, altura, largo, c);
		// Buque
		a�adirCarga(cp);
		setSoltar();
		Buque.cantBuques++;
	}

	// Getters
	private boolean tieneCarga() {
		return this.cargaProfunidad != null;
	}

	// Setters
	private void setSoltar() {

		int xMin = (int) this.coordenada.getxMin(); // esto ta mal
		int xMax = (int) this.coordenada.getxMax(); // esto ta mal
		this.xSoltar = new Random().nextInt(xMax + 1 - xMin) + xMin; // nro entre xMin y xMax.

	}

	public void moverX(float deltaTiempo) {
		float distancia = deltaTiempo * this.velocidad * ObjetoJuego.velocidadMultiplicador;

		this.coordenada.moverX(distancia);
		if (this.tieneCarga()) {
			this.cargaProfunidad.moverConBuque(distancia);
			// chequeo si tiene que soltar la carga
			float xActual = this.coordenada.getX();
			if ((xActual == this.xSoltar) // si justo esta en el punto
					|| (xActual - distancia < this.xSoltar && xActual > this.xSoltar) // si pas� el punto yendo de
																						// izquierda a derecha
																						// (velocidad es + => distancia
																						// es +)
					|| (xActual - distancia > this.xSoltar && xActual < this.xSoltar))// si pas� el punto yendo de
																						// derecha a izquierda
																						// (velocidad es - => distancia
																						// es -)
				soltarCarga();
		}
	}

	private void soltarCarga() {
		this.cargaProfunidad.soltar();
	}

	public void a�adirCarga(CargaProfundidad carga) {
		this.cargaProfunidad = carga;
	}

	// Facu

	public float getxSoltar() {
		return this.xSoltar;
	}

	public boolean isxSoltar() {
		if (velocidad > 0)
			return coordenada.getX() >= xSoltar && tieneCarga();
		else
			return coordenada.getX() <= xSoltar && tieneCarga();
	}

	public static int getCantidadBuques() {
		return Buque.cantBuques;
	}
	public static void resetCantidadBuques()
	{
		Buque.cantBuques = 1;
	}

	public boolean finalizoRecorrido() {
		if (velocidad > 0)
			return coordenada.getX() == coordenada.getxMax();
		else
			return coordenada.getX() == coordenada.getxMin();
	}
}