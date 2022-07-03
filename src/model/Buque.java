package model;

import java.util.Random;

import test.Debugger;

public class Buque extends ObjetoJuego {

	private float xSoltar;
	private CargaProfundidad cargaProfunidad;
	private static int cantBuques;

	public Buque(float velocidad, int altura, int largo, Coordenada c, CargaProfundidad cp) {
		super(velocidad, altura, largo, c);
		añadirCarga(cp);
		setSoltar();
		Buque.cantBuques++;
	}

	private void setSoltar() {
		int xMin = (int) this.coordenada.getxMin(); // esto ta mal
		int xMax = (int) this.coordenada.getxMax(); // esto ta mal
		this.xSoltar = new Random().nextInt(xMax + 1 - xMin) + xMin; // nro entre xMin y xMax.
		Debugger.PrintXSoltar(this.xSoltar);
	}

	public void moverX(float deltaTiempo) {
		float distancia = deltaTiempo * this.velocidad * ObjetoJuego.velocidadMultiplicador; // EL TIEMPO LO MANEJA LA INTERFAZ GRAFICA

		this.coordenada.moverX(distancia);
		
		if (cargaProfunidad != null) {
			
			cargaProfunidad.moverConBuque(distancia);
			
			if (isxSoltar())
				soltarCarga();
		}
	}

	private void soltarCarga() {
		this.cargaProfunidad.soltar(xSoltar);
		this.cargaProfunidad = null;
	}

	private void añadirCarga(CargaProfundidad carga) {
		this.cargaProfunidad = carga;
	}

	private boolean isxSoltar() {
		boolean soltar = false;
		if (this.cargaProfunidad != null)
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
		Buque.cantBuques = 0;
	}

	public boolean finalizoRecorrido() {
		if (velocidad > 0) // Si se mueve de izq a der
			return coordenada.getX() == coordenada.getxMax();
		else
			return coordenada.getX() == coordenada.getxMin();
	}
	
	public float getVelocidad()
	{
		return this.velocidad;
	}
}