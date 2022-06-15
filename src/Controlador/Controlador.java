package controlador;

import java.util.*;

import eventos.EventoTeclado;
import model.AreaJuego;
import model.Buque;
import model.CargaProfundidad;
import model.Coordenada;
import model.Submarino;

public class Controlador {

	private static final int FPS = 24;
	private static final double TIME = 1000000000 / FPS; // 1 segundo expresado en nanosegundos sobre la cantidad de FPS
															// que se desean.
	//private long ultimaActualizacion = System.nanoTime();
	
	AreaJuego areaJuego;
	List<CargaProfundidad> cargas;
	List<Buque> buques;
	Submarino jugador;

	public Controlador() {
		areaJuego = null;
		cargas = new ArrayList<CargaProfundidad>();
		buques = new ArrayList<Buque>();
		jugador = null;
		crearAreaJuego();
	}

	// TODO hacer el orquestador (flow del juego)
	// TODO hacer el event listener de las clases

	
	public void actualizarJuego() {
		if(jugador == null) {
			inicializarSubmarino();
			aparecerBuque();
		}
		//long now = 0;
		//long lastTime = System.nanoTime();
		//long ahora = System.nanoTime();
		//lastTime = now;
		
		//double deltaTiempo = (ahora - lastTime) / TIME;
		//double deltaTiempo = TIME;

		for (Buque obj : buques) {
			obj.moverX(1);
		}

		for (CargaProfundidad obj : cargas) {
			obj.moverY(1);
		}
		//ultimaActualizacion = ahora;
	}

	private void crearAreaJuego() {
		areaJuego = new AreaJuego(400, 0, 800, 0);
		// int xMax, int xMin, int yMax, int yMin
	}

	private void inicializarSubmarino() {
		Coordenada coordSubmarino = new Coordenada(areaJuego.getXMax() / 2, areaJuego.getYMax() / 2, areaJuego,
				areaJuego.getXMin(), 300, areaJuego.getXMax(), 800);
		jugador = new Submarino(10, 3, 5, coordSubmarino);
	}

	public int getNivel() {
		return jugador.getNivel();
	}

	public int getVidasJugador() {
		return jugador.getVidas();
	}

	public int getIntegridadCascoJugador() {
		return jugador.getIntegridadCascoJugador();
	}

	public int getPuntosJugador() {
		// TODO
		return 0;
	}

	private void eventoNivel() {
		// TODO
	}

	private void eventoDaño() {
		// TODO
	}

	private void eventoPuntos() {
		// TODO
	}

	private void eventoDesaparecerBuque() {
		// TODO
	}

	private void eventoExplosionBomba() {
		// TODO
	}

	private void nuevoNivel() {
		jugador.pasarDeNivelEIncrementarDificultad(10);
	}

	private void aparecerBuque() {

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
		return new CargaProfundidad(6f, 1, 1, c, 300, 700);
	}

	private void aparecerSubmarino(Coordenada c) {
		this.jugador = new Submarino(1, 3, 5, c);
	}

	private void abrirOSalirDelMenuSiElJuegoEstaCorriendo() {

		if (areaJuego.estaMenuAbierto())
			areaJuego.iniciarOReanudarJuego();
		else if (areaJuego.estaCorriendo())
			areaJuego.abrirMenu();

	}

	private void juegoFinalizado() {
		// TODO WIP
		if (areaJuego.estaCorriendo())
			areaJuego.irAlInicio();
	}

	private void comenzarJuegoSiEstaEnInicio() {

		if (areaJuego.estaEnInicio()) {
			areaJuego.iniciarOReanudarJuego();
		}

	}

	private void terminarJuegoSiEstaElMenuAbierto() {
		if (areaJuego.estaMenuAbierto())
			areaJuego.finalizarEjecucion();
	}

	private void pausarOReanudarJuegoSiElJuegoEstaCorriendo() {

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
		if (entrada == 87 || entrada == 38)
			jugador.moverArriba();
		if (entrada == 65 || entrada == 37)
			jugador.moverIzquierda();
		if (entrada == 83 || entrada == 40)
			jugador.moverAbajo();
		if (entrada == 68 || entrada == 39)
			jugador.moverDerecha();
	}

	public void obtenerEntradaTeclado(int entrada) {
		switch (entrada) {
		case 27: // Escape
			abrirOSalirDelMenuSiElJuegoEstaCorriendo();
			break;
		case 40: // Flecha abajo.
		case 83:// S
		case 37: // Flecha izquierda.
		case 65:// A
		case 39: // Flecha derecha.
		case 68:// D
		case 38: // Flecha arriba.
		case 87:// W
			moverSubmarino(entrada);
			break;
		case 78:// N
			// lo puse x las dudas
			break;
		case 80:// P
			pausarOReanudarJuegoSiElJuegoEstaCorriendo();
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
		List<Coordenada> coords = new ArrayList<Coordenada>();
		for (Buque item : buques) {
			coords.add(item.getCoordenada());
		}
		return coords;
	}

	public Coordenada getCoordenadasJugador() {
		return jugador.getCoordenada();
	}

	public List<Coordenada> getCoordenadasCargas() {
		List<Coordenada> coords = new ArrayList<Coordenada>();
		for (CargaProfundidad item : cargas) {
			coords.add(item.getCoordenada());
		}
		return coords;
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
}