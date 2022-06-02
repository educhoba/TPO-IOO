package Controlador;

import java.util.*;

import Model.AreaJuego;
import Model.CargaProfundidad;
import Model.Coordenada;
import Model.Buque;
import Model.Submarino;

public class Controlador {

	List<AreaJuego> areas;
	List<CargaProfundidad> cargas;
	List<Buque> buques;
	Submarino jugador;

	public Controlador() {
		areas = new ArrayList<AreaJuego>();
		cargas = new ArrayList<CargaProfundidad>();
		buques = new ArrayList<Buque>();
		jugador = null;
	}

	// TODO hacer el orquestador (flow del juego)
	// TODO hacer el event listener de las clases

	public int getNivel() {
		return jugador.getNivel();
	}

	public int getVidasJugador() {
		//TODO
		return 0;
	}

	public int getIntegridadCascoJugador() {
		//TODO
		return 0;
	}
	public int getPuntosJugador() {
		//TODO
		return 0;
	}
	private void eventoNivel() {
		//TODO
	}
	private void eventoDaño() {
		//TODO
	}
	private void eventoPuntos() {
		//TODO
	}
	private void eventoDesaparecerBuque() {
		//TODO
	}

	private void nuevoNivel() {
		jugador.pasarDeNivelEIncrementarDificultad(10);
	}

	private void aparecerBuque(CargaProfundidad dc, Coordenada c) {
		//TODO crear la coordenada random
		this.buques.add(new Buque(6, 1, 1, c, dc));
	}

	private CargaProfundidad crearCargaProfundidad(Coordenada c) {
		return new CargaProfundidad(5, 1, 1, c);
	}

	private void aparecerSubmarino(Coordenada c) {
		this.jugador = new Submarino(1, 50, 10, c, 3);
	}

	private void abrirOSalirDelMenuSiElJuegoEstaCorriendo() {
		for (AreaJuego item : areas) {
			if (item.estaMenuAbierto())
				item.iniciarOReanudarJuego();
			else if (item.estaCorriendo())
				item.abrirMenu();
		}
	}

	private void juegoFinalizado() {
		// TODO WIP
		for (AreaJuego item : areas) {
			if (item.estaCorriendo())
				item.irAlInicio();
		}
	}

	private void comenzarJuegoSiEstaEnInicio() {
		// TODO: Hay que crear las areas de juego, generar los objetos, bla bla
		for (AreaJuego item : areas) {
			if (item.estaEnInicio())
				item.iniciarOReanudarJuego();
		}
	}

	private void terminarJuegoSiEstaElMenuAbierto() {
		for (AreaJuego item : areas) {
			if (item.estaMenuAbierto())
				item.irAlInicio();
		}
		return;
	}

	private void pausarOReanudarJuegoSiElJuegoEstaCorriendo() {
		for (AreaJuego item : areas) {
			if (item.estaPausado())
				item.iniciarOReanudarJuego();
			else if (item.estaCorriendo())
				item.pausarJuego();
		}
	}

	private void desaparecerBuque(Buque b) {
		for (Buque item : buques) {
			if (item.equals(b)) {
				buques.remove(item);
				break;
			}
		}
	}

	private void desaparecerCarga(CargaProfundidad c) {
		for (CargaProfundidad item : cargas) {
			if (item.equals(c)) {
				cargas.remove(item);
				break;
			}
		}
	}

	private void desaparecerSubmarino() {
		// creo que el submarino desaparece solamente si termina completamente el juego
		jugador = null;
	}

	private void aparecerCarga(CargaProfundidad c) {
		cargas.add(c);
	}

	private void moverSubmarino(int entrada) {
		if (entrada == 87)// W
			jugador.moverArriba();
		if (entrada == 65)
			jugador.moverAbajo();
		if (entrada == 83)// S
			jugador.moverIzquierda();
		if (entrada == 68)// D
			jugador.moverDerecha();
	}

	public void obtenerEntradaTeclado(int entrada) {
		switch (entrada) {
		case 27: // Escape
			abrirOSalirDelMenuSiElJuegoEstaCorriendo();
			break;
		case 65:// A
			moverSubmarino(entrada);
			break;
		case 68:// D
			moverSubmarino(entrada);
			break;
		case 78:// N
			// lo puse x las dudas
			break;
		case 80:// P
			pausarOReanudarJuegoSiElJuegoEstaCorriendo();
			break;
		case 83:// S
			moverSubmarino(entrada);
			break;
		case 87:// W
			moverSubmarino(entrada);
			break;
		case 89:// Y
			terminarJuegoSiEstaElMenuAbierto();
			comenzarJuegoSiEstaEnInicio();
			break;
		default:
			break;
		}

	}

	public List<Coordenada> getCoordenadasBuques() {
		// TODO
		return null;
	}

	public List<Coordenada> getCoordenadasJugador() {
		// TODO
		return null;
	}

	public List<Coordenada> getCoordenadasCargas() {
		// TODO
		return null;
	}
}