package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.Debugger;

public class Juego {

	AreaJuego areaJuego;
	Submarino jugador;
	Buque buque;
	List<CargaProfundidad> cargas;

	public Juego() {
		inicializarAreaJuego();
		cargas = new ArrayList<CargaProfundidad>();
		inicializarSubmarino();
	}
	
	public Submarino getSubmarino()
	{
		return jugador;
	}
	
	public AreaJuego getAreaJuego()
	{
		return areaJuego;
	}
	
	private void inicializarAreaJuego() {
		areaJuego = new AreaJuego(150, 0, -800, 0);
	}
	
	private void inicializarSubmarino() {
		Coordenada coordSubmarino = new Coordenada(areaJuego.getXMax() / 2, areaJuego.getYMax() / 2, areaJuego,
				areaJuego.getXMin(), -300, areaJuego.getXMax(), -750);
		this.jugador = new Submarino(5, 3, 5, coordSubmarino);
	}
	
	public void actualizarJuego() {
		
		float milisegundos = 0.024f;
		
		if (buque == null || buque.finalizoRecorrido())
			aparecerBuque();
		else
			buque.moverX(milisegundos);
			
		
		for (int i = cargas.size() - 1; i >= 0; i--)
		{
			if (cargas.get(i).estaExplotada())
			{
				eventoExplosion(cargas.get(i));
				cargas.remove(i);
			}
			else
				cargas.get(i).moverY(milisegundos);
		}

		if (!jugador.estaVivo()) {
			finalizarJuego();
		}

	}
	
	private void eventoExplosion(CargaProfundidad carga) {
		jugador.calcularExplosion(carga.getCoordenada());
	}

	private void finalizarJuego() { // FALTA HACER FUNCIONAR EL ESTADO
		Debugger.printGameOver();
		areaJuego.finalizarEjecucion();
	}

	private void aparecerBuque() {

		if (Buque.getCantidadBuques() == 10)
			pasarDeNivel();

		int lado = new Random().nextInt(1 + 1); // nro 0 o 1.
		float velocidad = 50f;
		int x;

		if (lado == 0) { // Si da 0, aparece por la izquierda.
			x = areaJuego.getXMin();
		} else { // Si da 1, aparece por la derecha.
			x = areaJuego.getXMax();
			velocidad *= -1f;
		}
		
		CargaProfundidad carga = crearCargaProfundidad(x);
		Coordenada coordBuque = new Coordenada(x, areaJuego.getYMin(), areaJuego);
		buque = new Buque(velocidad, 3, 5, coordBuque, carga);
		aparecerCarga(carga);
	}
	
	private void pasarDeNivel() {
		jugador.pasarDeNivelEIncrementarDificultad(10);
		Buque.resetCantidadBuques();
	}

	private CargaProfundidad crearCargaProfundidad(int xBuque) {
		Coordenada c = new Coordenada(xBuque, areaJuego.getYMin(), areaJuego);
		return new CargaProfundidad(200f, 1, 1, c, -300, -700);
	}
	
	private void aparecerCarga(CargaProfundidad c) {
		cargas.add(c);
	}

	public void moverSubmarino(int entrada) {
		switch(entrada)
		{
		case 87, 38:
		{
			jugador.moverArriba();
			break;
		}
		case 68, 39:
		{
			jugador.moverDerecha();
			break;
		}
		case 83, 40:
		{
			jugador.moverAbajo();
			break;
		}
		case 65, 37:
		{
			jugador.moverIzquierda();
			break;
		}
		}
	}
	
	public SubmarinoView getSubmarinoView() {
		return new SubmarinoView(jugador);
	}

	public BuqueView getBuqueView() {
//		if (buque != null)
			return new BuqueView(buque);
//		else
//			return null;
	}

	public List<CargaProfundidadView> getCargasViews() {
		List<CargaProfundidadView> lstCargas = new ArrayList<CargaProfundidadView>();
		for (CargaProfundidad item : cargas) {
			lstCargas.add(new CargaProfundidadView(item));
		}
		return lstCargas;
	}
	
	public void abrirOSalirDelMenuSiElJuegoEstaCorriendo() {

		if (areaJuego.estaMenuAbierto())
			areaJuego.iniciarOReanudarJuego();
		else if (areaJuego.estaCorriendo())
			areaJuego.abrirMenu();
	}

	public void comenzarJuegoSiEstaEnInicio() {

		if (areaJuego.estaEnInicio()) {
			areaJuego.iniciarOReanudarJuego();
		}

	}

	public void terminarJuegoSiEstaElMenuAbierto() {
		if (areaJuego.estaMenuAbierto())
			areaJuego.finalizarEjecucion();
	}

	public void pausarOReanudarJuegoSiElJuegoEstaCorriendo() {

		if (areaJuego.estaPausado())
			areaJuego.iniciarOReanudarJuego();
		else if (areaJuego.estaCorriendo())
			areaJuego.pausarJuego();
	}

}
