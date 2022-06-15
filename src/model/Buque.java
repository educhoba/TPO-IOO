package model;

import java.util.Random;

import controlador.Controlador;

public class Buque extends ObjetosJuego {

	private float xSoltar;
	private float xDesaparecer;
	private CargaProfundidad cargaProfunidad;
	private static int cantBuques = 1;

	public Buque(float velocidad, int altura, int largo, Coordenada c, CargaProfundidad cp) {
		super(velocidad, altura, largo, c);
		// Buque
		añadirCarga(cp);
		setSoltar();
		setDesaparecer();
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

	private void setDesaparecer() {
		if (velocidad > 0)
			this.xDesaparecer = coordenada.getxMax();
		else
			this.xDesaparecer = coordenada.getxMin();
	}

	public void moverX(float deltaTiempo) {
		float distancia = deltaTiempo * this.velocidad * ObjetosJuego.velocidadMultiplicador;

		this.coordenada.moverX(distancia);
		if (this.tieneCarga()) {
			this.cargaProfunidad.moverConBuque(distancia);
			// chequeo si tiene que soltar la carga
			float xActual = this.coordenada.getX();
			if ((xActual == this.xSoltar) // si justo esta en el punto
					|| (xActual - distancia < this.xSoltar && xActual > this.xSoltar) // si pasó el punto yendo de
																						// izquierda a derecha
																						// (velocidad es + => distancia
																						// es +)
					|| (xActual - distancia > this.xSoltar && xActual < this.xSoltar))// si pasó el punto yendo de
																						// derecha a izquierda
																						// (velocidad es - => distancia
																						// es -)
				soltarCarga();
		}
	}

	private void soltarCarga() {
		this.cargaProfunidad.soltar();
	}

	public void añadirCarga(CargaProfundidad carga) {
		this.cargaProfunidad = carga;
		return;
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