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
		aj = juego.getAreaJuego();
		s = juego.getSubmarino();
	}

	public void actualizarJuego() {
		juego.actualizarJuego();
	}
	
	public int getNivel() {
		return s.getNivel();
	}

	public int getVidasJugador() {
		int vidas = s.getVidas();
		if (vidas < 0)
			vidas = 0;
		return vidas;
	}

	public int getIntegridadCasco() {
		int integridad = s.getIntegridadCasco();
		if (integridad < 0)
			integridad = 0;
		return integridad;
	}

	public int getPuntosJugador() {
		return s.getPuntos();
	}
	
	public boolean estaVivo()
	{
		return s.estaVivo();
	}

	public void recibirEntradaTeclado(int entrada) {
		switch (entrada) {
//		case 27: // Escape
//			juego.abrirOSalirDelMenuSiElJuegoEstaCorriendo(); // TODA LA PARTE DE PAUSAS LA TIENE LA INTERFAZ PORQUE TIENE EL TIMER
//			break;
		case 40, 83, 37, 65, 39, 68, 38, 87: // WASD y Flechas
			juego.moverSubmarino(entrada);
			break;
//		case 80:// P
//			juego.pausarOReanudarJuegoSiElJuegoEstaCorriendo();
//			break;
//		case 89:// Y
//			juego.terminarJuegoSiEstaElMenuAbierto();
//			juego.comenzarJuegoSiEstaEnInicio();
//			break;
		}
	}

	public SubmarinoView getSubmarinoView() {
		return juego.getSubmarinoView();
	}
	
	public BuqueView getBuqueView() {
		return juego.getBuqueView();
	}
	
	public List<CargaProfundidadView> getCargasViews() {
		return juego.getCargasViews();
	}
	
	public int getXMax()
	{
		return aj.getXMax();
	}
	
	public int getXMin()
	{
		return aj.getXMin();
	}
	
	public int getYMax()
	{
		return aj.getYMax();
	}
	
	public int getYMin()
	{
		return aj.getYMin();
	}

//	public boolean estaEnInicio() {
//		return aj.estaEnInicio();
//	}
//
//	public boolean estaCorriendo() {
//		return aj.estaCorriendo();
//	}
//
//	public boolean estaPausado() {
//		return aj.estaPausado();
//	}
//
//	public boolean estaMenuAbierto() {
//		return aj.estaMenuAbierto();
//	}
//
//	public boolean estaCerrando() {
//		return aj.estaSaliendo();
//	}
	
}