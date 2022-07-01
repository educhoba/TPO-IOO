package model;

import java.util.Random;

public class Buque extends ObjetoJuego {

	private float xSoltar;
	private CargaProfundidad cargaProfunidad;
	private static int cantBuques = 1;

	public Buque(float velocidad, int altura, int largo, Coordenada c, CargaProfundidad cp) {
		super(velocidad, altura, largo, c);
		// Buque
		añadirCarga(cp);
		setSoltar();
		Buque.cantBuques++;
	}

	// Getters
	private boolean tieneCarga() { // NO ES NECESARIO YA QUE PODEMOS ACCEDER AL ATRIBUTO PORQUE EL METODO ES PRIVADO.
		return this.cargaProfunidad != null;
	}

	// Setters
	private void setSoltar() {

		int xMin = (int) this.coordenada.getxMin(); // esto ta mal
		int xMax = (int) this.coordenada.getxMax(); // esto ta mal
		this.xSoltar = new Random().nextInt(xMax + 1 - xMin) + xMin; // nro entre xMin y xMax.

	}

	public void moverX(float deltaTiempo) {
		float distancia = deltaTiempo * this.velocidad * ObjetoJuego.velocidadMultiplicador; // EL TIEMPO LO MANEJA LA INTERFAZ GRAFICA

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

	private void soltarCarga() { // TIENE QUE SETEAR cargaProfundidad A NULL
		this.cargaProfunidad.soltar();
	}

	public void añadirCarga(CargaProfundidad carga) { // TIENE QUE SER PRIVADO, NO SE ACCEDE DESDE AFUERA.
		this.cargaProfunidad = carga;
	}

	// Facu

	public float getxSoltar() { // NO LO ESTAMOS UTILIZANDO
		return this.xSoltar;
	}

	public boolean isxSoltar() { // TIENE QUE SER PRIVADO PORQUE LO ACCEDEMOS SOLO DESDE MOVER
		boolean soltar = false;
		if (tieneCarga())
		{
			if (velocidad > 0) // Si se mueve de izq a der
				soltar = coordenada.getX() >= xSoltar;
			else
				soltar = coordenada.getX() <= xSoltar;
		}
		return soltar;
	}

	public static int getCantidadBuques() {
		return Buque.cantBuques;
	}
	
	public static void resetCantidadBuques()
	{
		Buque.cantBuques = 1;
	}

	public boolean finalizoRecorrido() {
		if (velocidad > 0) // Si se mueve de izq a der
			return coordenada.getX() == coordenada.getxMax();
		else
			return coordenada.getX() == coordenada.getxMin();
	}
}