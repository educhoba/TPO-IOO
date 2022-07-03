package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.Debugger;

public class Juego {

	private final static float TIEMPO_REFRESCO_MS = 0.024f;
	private final static int BUQUES_POR_NIVEL = 10;
	private final static float PORCENTAJE_INCREMENTO_DIFICULTAD = 10;
	
	private final static int AREA_JUEGO_PROFUNDIDAD = -800;
	private final static int AREA_JUEGO_LARGO = 600;
	
	private final static int SUBMARINO_Y_MIN = -800;
	private final static int SUBMARINO_Y_MAX = -300;
	private final static float SUBMARINO_VELOCIDAD = 10;
	private final static int SUBMARINO_ALTO = 18;
	private final static int SUBMARINO_LARGO = 50;
	
	private final static float BUQUE_VELOCIDAD = 50;
	private final static int BUQUE_ALTO = 1;
	private final static int BUQUE_LARGO = 1;

	private final static int CARGA_Y_MIN = -700;
	private final static int CARGA_Y_MAX = -300;
	private final static float CARGA_VELOCIDAD = 200;
	private final static int CARGA_ALTO = 1;
	private final static int CARGA_LARGO = 1;
	
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
		areaJuego = new AreaJuego(AREA_JUEGO_LARGO, 0, AREA_JUEGO_PROFUNDIDAD, 0);
	}
	
	private void inicializarSubmarino() {
		Coordenada coordSubmarino = new Coordenada(areaJuego.getXMax() / 2, areaJuego.getYMax() / 2, areaJuego,
				areaJuego.getXMin(), SUBMARINO_Y_MAX, areaJuego.getXMax(), SUBMARINO_Y_MIN);
		this.jugador = new Submarino(SUBMARINO_VELOCIDAD, SUBMARINO_ALTO, SUBMARINO_LARGO, coordSubmarino);
	}
	
	public void actualizarJuego() {
		
		if (buque == null || buque.finalizoRecorrido())
			aparecerBuque();
		else
			buque.moverX(TIEMPO_REFRESCO_MS);
			
		for (int i = cargas.size() - 1; i >= 0; i--)
		{
			if (cargas.get(i).estaExplotada())
			{
				eventoExplosion(cargas.get(i));
				cargas.remove(i);
			}
			else
				cargas.get(i).moverY(TIEMPO_REFRESCO_MS);
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

		if (Buque.getCantidadBuques() == BUQUES_POR_NIVEL)
			pasarDeNivel();

		int lado = new Random().nextInt(1 + 1); // nro 0 o 1.
		float velocidad = BUQUE_VELOCIDAD;
		int x;

		if (lado == 0) { // Si da 0, aparece por la izquierda.
			x = areaJuego.getXMin();
		} else { // Si da 1, aparece por la derecha.
			x = areaJuego.getXMax();
			velocidad *= -1f;
		}
		
		CargaProfundidad carga = crearCargaProfundidad(x);
		Coordenada coordBuque = new Coordenada(x, areaJuego.getYMin(), areaJuego);
		buque = new Buque(velocidad, BUQUE_ALTO, BUQUE_LARGO, coordBuque, carga);
		aparecerCarga(carga);
	}
	
	private void pasarDeNivel() {
		jugador.pasarDeNivelEIncrementarDificultad(PORCENTAJE_INCREMENTO_DIFICULTAD);
		Buque.resetCantidadBuques();
	}

	private CargaProfundidad crearCargaProfundidad(int xBuque) {
		Coordenada c = new Coordenada(xBuque, areaJuego.getYMin(), areaJuego);
		return new CargaProfundidad(CARGA_VELOCIDAD, CARGA_ALTO, CARGA_LARGO, c, CARGA_Y_MAX, CARGA_Y_MIN);
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
