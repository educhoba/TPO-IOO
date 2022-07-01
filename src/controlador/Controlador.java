package controlador;

import java.util.List;

import model.AreaJuego;
import model.BuqueView;
import model.CargaProfundidadView;
import model.Juego;
import model.Submarino;
import model.SubmarinoView;

public class Controlador {

	Juego juego;
	Submarino s;
	AreaJuego aj;
	
	public Controlador() {
		juego = new Juego();
	}

	public void actualizarJuego() {
		juego.actualizarJuego();
	}
	
	public int getNivel() {
		return s.getNivel();
	}

	public int getVidasJugador() {
		return s.getVidas();
	}

	public int getIntegridadCasco() {
		return s.getIntegridadCasco();
	}

	public int getPuntosJugador() {
		return s.getPuntos();
	}

	public void recibirEntradaTeclado(int entrada) {
		switch (entrada) {
		case 27: // Escape
			juego.abrirOSalirDelMenuSiElJuegoEstaCorriendo(); // TODA LA PARTE DE PAUSAS LA TIENE LA INTERFAZ PORQUE TIENE EL TIMER
			break;
		case 40, 83, 37, 65, 39, 68, 38, 87: // WASD y Flechas
			juego.moverSubmarino(entrada);
			break;
		case 80:// P
			juego.pausarOReanudarJuegoSiElJuegoEstaCorriendo();
			break;
		case 89:// Y
			juego.terminarJuegoSiEstaElMenuAbierto();
			juego.comenzarJuegoSiEstaEnInicio();
			break;
		}
	}

	public SubmarinoView getSubmarino() {
		return juego.getSubmarinoView();
	}
	
	public BuqueView getBuqueView() {
		return juego.getBuqueView();
	}
	
	public List<CargaProfundidadView> getCargasViews() {
		return juego.getCargasViews();
	}

	public boolean estaEnInicio() {
		return aj.estaEnInicio();
	}

	public boolean estaCorriendo() {
		return aj.estaCorriendo();
	}

	public boolean estaPausado() {
		return aj.estaPausado();
	}

	public boolean estaMenuAbierto() {
		return aj.estaMenuAbierto();
	}

	public boolean estaCerrando() {
		return aj.estaSaliendo();
	}
	
}