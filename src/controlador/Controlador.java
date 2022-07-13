package controlador;

import java.awt.Dimension;
import java.util.List;

import domain.Ini;
import domain.Juego;
import domain.gameObjects.Submarino;
import domain.gameObjetcsViews.BuqueView;
import domain.gameObjetcsViews.CargaProfundidadView;
import domain.gameObjetcsViews.SubmarinoView;
import utilidades.Vector2D;

public class Controlador {

	
	Juego juego;
	Submarino s;
	
	
	public Controlador() 
	{
		juego = new Juego();
		s = juego.getSubmarino();
	}

	
	public void actualizarJuego() 
	{
		juego.actualizarJuego();
	}
	
	public int getNivel() 
	{
		return s.getNivel();
	}

	public int getVidasJugador() 
	{
		return s.getVidas();
	}

	public int getIntegridadCasco() 
	{
		return s.getIntegridadCasco();
	}

	public int getPuntosJugador() 
	{
		return s.getPuntos();
	}
	
	public boolean estaVivo()
	{
		return s.estaVivo();
	}

	public void moverSubmarino(int entrada) 
	{
		juego.moverSubmarino(entrada);
	}

	public SubmarinoView getSubmarinoView() 
	{
		return juego.getSubmarinoView();
	}
	
	public BuqueView getBuqueView() 
	{
		return juego.getBuqueView();
	}
	
	public List<CargaProfundidadView> getCargasViews() 
	{
		return juego.getCargasViews();
	}
	
	public Dimension getDimensionCargaProfundidad()
	{
		return new Dimension(Ini.CARGA_LARGO, Ini.CARGA_ALTO);
	}
	
	public Vector2D getAreaJuego()
	{
		return juego.getAreaJuego();
	}
	
	public int getTiempoRefresco()
	{
		return Math.round(Ini.TIEMPO_REFRESCO_MS * 1000);
	}
	
}