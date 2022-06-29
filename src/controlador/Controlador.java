package controlador;

import java.util.List;

import model.SubmarinoView;
import model.BuqueView;
import model.CargaProfundidadView;
import model.Juego;

public class Controlador {

	Juego juego;
	
	public Controlador() {
		juego = new Juego();
	}

	public void actualizarJuego() {
		juego.actualizarJuego();
	}
	
	public int getNivel() {
		return juego.getNivel();
	}

	public int getVidasJugador() {
		return juego.getVidasJugador();
	}

	public int getIntegridadCasco() {
		return juego.getIntegridadCasco();
	}

	public int getPuntosJugador() {
		return juego.getPuntosJugador();
	}

	public void obtenerEntradaTeclado(int entrada) {
		switch (entrada) {
		case 27: // Escape
			juego.abrirOSalirDelMenuSiElJuegoEstaCorriendo();
			break;
		case 40: // Flecha abajo.
		case 83:// S
		case 37: // Flecha izquierda.
		case 65:// A
		case 39: // Flecha derecha.
		case 68:// D
		case 38: // Flecha arriba.
		case 87:// W
			juego.moverSubmarino(entrada);
			break;
		case 78:// N
			// lo puse x las dudas
			break;
		case 80:// P
			juego.pausarOReanudarJuegoSiElJuegoEstaCorriendo();
			break;
		case 89:// Y
			juego.terminarJuegoSiEstaElMenuAbierto();
			juego.comenzarJuegoSiEstaEnInicio();
			break;
		default:
			break;
		}

	}

	public List<BuqueView> getBuquesViews() {
		return juego.getBuquesViews();
	}

	public SubmarinoView getSubmarino() {
		return juego.getSubmarinoView();
	}
	
	public List<CargaProfundidadView> getCargasViews() {
		return juego.getCargasViews();
	}

	public boolean estaEnInicio() {
		return juego.estaEnInicio();
	}

	public boolean estaCorriendo() {
		return juego.estaCorriendo();
	}

	public boolean estaPausado() {
		return juego.estaPausado();
	}

	public boolean estaMenuAbierto() {
		return juego.estaMenuAbierto();
	}

	public boolean estaCerrando() {
		return juego.estaCerrando();
	}
	
}