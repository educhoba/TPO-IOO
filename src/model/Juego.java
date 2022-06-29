package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.Debugger;

public class Juego {

	AreaJuego areaJuego;
	List<CargaProfundidad> cargas;
	List<Buque> buques;
	Submarino jugador;

	public Juego() {
		areaJuego = null;
		cargas = new ArrayList<CargaProfundidad>();
		buques = new ArrayList<Buque>();
		jugador = null;
		crearAreaJuego();
	}

	public int getNivel() {
		return jugador.getNivel();
	}

	public int getVidasJugador() {
		return jugador.getVidas();
	}

	public int getIntegridadCasco() {
		return jugador.getIntegridadCasco();
	}

	public int getPuntosJugador() {
		return jugador.getPuntos();
	}
	
	public void actualizarJuego() {
		// cuando sea grafico hay que poner el delta tiempo
		if (jugador == null) {
			inicializarSubmarino();
			aparecerBuque();
		}

		List<Buque> dumpBuque = new ArrayList<Buque>();
		List<CargaProfundidad> dumpCarga = new ArrayList<CargaProfundidad>();
		for (Buque obj : buques) {
			obj.moverX(1);
			if (obj.finalizoRecorrido())
				dumpBuque.add(obj);
		}

		for (CargaProfundidad obj : cargas) {
			obj.moverY(1);
			if (obj.exploto()) {
				eventoExplosion(obj);
				dumpCarga.add(obj);
			}
		}
		for (Buque obj : dumpBuque) {
			desaparecerBuque(obj);
		}

		for (CargaProfundidad obj : dumpCarga) {
			desaparecerCarga(obj);
		}

		if (!jugador.estaVivo()) {
			finalizarJuego();
		}

	}

	private void finalizarJuego() {
		Debugger.printGameOver();
		areaJuego.finalizarEjecucion();
	}

	private void crearAreaJuego() {
		areaJuego = new AreaJuego(150, 0, -800, 0);
		// int xMax, int xMin, int yMax, int yMin
	}

	private void eventoExplosion(CargaProfundidad carga) {
		jugador.calcularExplosion(carga.getCoordenada());

	}

	private void pasarDeNivel() {
		jugador.pasarDeNivelEIncrementarDificultad(10);
		Buque.resetCantidadBuques();
	}

	private void aparecerBuque() {

		if (Buque.getCantidadBuques() == 10)
			pasarDeNivel();

		int lado = new Random().nextInt(1 + 1); // nro 0 o 1.
		float velocidad;
		int x;

		if (lado == 1) {
			x = areaJuego.getXMin();
			velocidad = 8f;
		} else {
			x = areaJuego.getXMax();
			velocidad = -8f;
		}
		CargaProfundidad carga = crearCargaProfundidad(x);
		Coordenada coordBuque = new Coordenada(x, areaJuego.getYMin(), areaJuego);
		Buque buque = new Buque(velocidad, 3, 5, coordBuque, carga);
		buques.add(buque);
		aparecerCarga(carga);
	}

	private CargaProfundidad crearCargaProfundidad(int xBuque) {

		Coordenada c = new Coordenada(xBuque, areaJuego.getYMin(), areaJuego);
		return new CargaProfundidad(6f, 1, 1, c, -300, -700);
	}

	private void aparecerSubmarino(Coordenada c) {
		this.jugador = new Submarino(10, 3, 5, c);
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

	private void desaparecerBuque(Buque b) {
		for (Buque item : buques) {
			if (item.equals(b)) {
				buques.remove(item);
				break;
			}
		}
		aparecerBuque();
	}

	private void desaparecerCarga(CargaProfundidad c) {
		for (CargaProfundidad item : cargas) {
			if (item.equals(c)) {
				cargas.remove(item);
				break;
			}
		}
	}

	private void aparecerCarga(CargaProfundidad c) {
		cargas.add(c);
	}

	public void moverSubmarino(int entrada) {
		if (entrada == 87 || entrada == 38)
			jugador.moverArriba();
		if (entrada == 65 || entrada == 37)
			jugador.moverIzquierda();
		if (entrada == 83 || entrada == 40)
			jugador.moverAbajo();
		if (entrada == 68 || entrada == 39)
			jugador.moverDerecha();
	}

	public List<CoordenadaView> getCoordenadasBuques() {
		List<CoordenadaView> coords = new ArrayList<CoordenadaView>();
		for (Buque item : buques) {
			coords.add(new CoordenadaView(item.getCoordenada()));
		}
		return coords;
	}

	public List<CoordenadaView> getCoordenadasCargas() {
		List<CoordenadaView> coords = new ArrayList<CoordenadaView>();
		for (CargaProfundidad item : cargas) {
			coords.add(new CoordenadaView(item.getCoordenada()));
		}
		return coords;
	}

	public CoordenadaView getCoordenadasJugador() {
		return new CoordenadaView(jugador.getCoordenada());
	}
	
	public boolean estaEnInicio() {
		return areaJuego.estaEnInicio();
	}

	public boolean estaCorriendo() {
		return areaJuego.estaCorriendo();
	}

	public boolean estaPausado() {
		return areaJuego.estaPausado();
	}

	public boolean estaMenuAbierto() {
		return areaJuego.estaMenuAbierto();
	}

	public boolean estaCerrando() {
		return areaJuego.estaSaliendo();
	}

	private void inicializarSubmarino() {
		Coordenada coordSubmarino = new Coordenada(areaJuego.getXMax() / 2, areaJuego.getYMax() / 2, areaJuego,
				areaJuego.getXMin(), -300, areaJuego.getXMax(), -800);
		aparecerSubmarino(coordSubmarino);
	}

}
