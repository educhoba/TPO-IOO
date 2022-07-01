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
		areaJuego = null; // EL AREA DE JUEGO YA ES NULL, NO ES NECESARIA ESTA LINEA.
		cargas = new ArrayList<CargaProfundidad>();
		buques = new ArrayList<Buque>();
		jugador = null; // EL SUBMARINO YA ES NULL, NO ES NECESARIA ESTA LINEA.
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
			/* ES PREFERIBLE INICIALIZAR EL SUBMARINO EN EL CONSTRUCTOR, ESTO SE VA A EJECUTAR UNA SOLA VEZ
			 * Y SE VA A RECORRER ESE IF EN CADA ITERACION SIN EJECUTARSE (SALVO LA PRIMERA VEZ). */
			inicializarSubmarino();
			aparecerBuque(); // APARECER BUQUE PUEDE QUEDAR CON UN IF APARTE, EN CASO DE SACAR ESTE IF.
		}

		List<Buque> dumpBuque = new ArrayList<Buque>();
		List<CargaProfundidad> dumpCarga = new ArrayList<CargaProfundidad>();
		
		for (Buque obj : buques) {
			if (obj.finalizoRecorrido())
				dumpBuque.add(obj);
			else
				obj.moverX(1);
		}
		
//		for (int i = buques.size() - 1; i >= 0; i--)
//		{
//			if (buques.get(i).finalizoRecorrido())
//				buques.remove(i);
//			else
//				buques.get(i).moverX(1);
//		}

		for (CargaProfundidad obj : cargas) {
			if (obj.exploto()) {
				eventoExplosion(obj);
				dumpCarga.add(obj);
			}
			else
				obj.moverY(1);
		}
		
//		for (int i = cargas.size() - 1; i >= 0; i--)
//		{
//			if (cargas.get(i).exploto())
//			{
//				eventoExplosion(cargas.get(i));
//				cargas.remove(i);
//			}
//			else
//				cargas.get(i).moverY(1);
//		}
		
		
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
		else if (entrada == 65 || entrada == 37)
			jugador.moverIzquierda();
		else if (entrada == 83 || entrada == 40)
			jugador.moverAbajo();
		else if (entrada == 68 || entrada == 39)
			jugador.moverDerecha();
	}

	public List<BuqueView> getBuquesViews() {
		List<BuqueView> coords = new ArrayList<BuqueView>();
		for (Buque item : buques) {
			coords.add(new BuqueView(item));
		}
		return coords;
	}

	public List<CargaProfundidadView> getCargasViews() {
		List<CargaProfundidadView> coords = new ArrayList<CargaProfundidadView>();
		for (CargaProfundidad item : cargas) {
			coords.add(new CargaProfundidadView(item));
		}
		return coords;
	}

	public SubmarinoView getSubmarinoView() {
		return new SubmarinoView(jugador);
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