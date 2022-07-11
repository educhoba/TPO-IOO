package domain;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.gameArea.AreaMovimiento;
import domain.gameArea.Coordenada;
import domain.gameObjects.Buque;
import domain.gameObjects.CargaProfundidad;
import domain.gameObjects.Submarino;
import domain.gameObjetcsViews.BuqueView;
import domain.gameObjetcsViews.CargaProfundidadView;
import domain.gameObjetcsViews.SubmarinoView;
import utilidades.Vector2D;

public class Juego {
	
	Submarino submarino;
	Buque buque;
	List<CargaProfundidad> cargas;
	AreaMovimiento[] areas;
	
	
	public Juego() 
	{
		inicializarAreasMovimiento();
		crearSubmarino();
		cargas = new ArrayList<CargaProfundidad>();
	}
	
	
	private void inicializarAreasMovimiento() 
	{
		areas = new AreaMovimiento[3];
		
		areas[0] = new AreaMovimiento(Ini.AREA_JUEGO_X_MAX - Ini.SUBMARINO_LARGO, Ini.AREA_JUEGO_X_MIN
				, Ini.SUBMARINO_Y_MAX - Ini.SUBMARINO_ALTO, Ini.SUBMARINO_Y_MIN); // AreaMovimiento del Submarino;
		
		areas[1] = new AreaMovimiento(Ini.AREA_JUEGO_X_MAX, Ini.AREA_JUEGO_X_MIN - Ini.BUQUE_LARGO
				, Ini.AREA_JUEGO_Y_MIN, Ini.AREA_JUEGO_Y_MIN - Ini.BUQUE_ALTO); // AreaMovimiento del Buque;
		
		areas[2] = new AreaMovimiento(Ini.AREA_JUEGO_X_MAX - Ini.CARGA_LARGO, Ini.AREA_JUEGO_X_MIN
				, Ini.CARGA_EXPLOSION_Y_MAX - Ini.CARGA_ALTO, Ini.AREA_JUEGO_Y_MIN - Ini.CARGA_ALTO); // AreaMovimiento de las Cargas;
	}
	
	private void crearSubmarino() 
	{
		Coordenada coord = new Coordenada(areas[0].getMax().getX() / 2
				, areas[0].getMax().getY() / 2, areas[0]);
		
		this.submarino = new Submarino(Ini.SUBMARINO_VELOCIDAD, Ini.SUBMARINO_ALTO
				, Ini.SUBMARINO_LARGO, coord);
	}
	
	private void crearBuque() 
	{
		if (Buque.getCantidadBuques() == Ini.BUQUES_POR_NIVEL)
			pasarDeNivel();
		
		Coordenada coord = new Coordenada(areas[1].getMin().getX()
				, areas[1].getMin().getY(), areas[1]);

		buque = new Buque(Ini.BUQUE_VELOCIDAD, Ini.BUQUE_ALTO, Ini.BUQUE_LARGO
				, coord, crearCargasProfundidad());
	}
	
	private List<CargaProfundidad> crearCargasProfundidad()
	{
		List<CargaProfundidad> lstCargas = new ArrayList<CargaProfundidad>();
		
		int r = new Random().nextInt(Ini.CANT_CARGAS_MAX + 1 - Ini.CANT_CARGAS_MIN) + Ini.CANT_CARGAS_MIN;
		
		for (int i = 0; i < r; i++)
		{
			Coordenada coord = new Coordenada(areas[2].getMin().getX()
					, areas[2].getMin().getY(), areas[2]);
			
			var carga = new CargaProfundidad(Ini.CARGA_VELOCIDAD, Ini.CARGA_ALTO, Ini.CARGA_LARGO
					, coord, Ini.CARGA_EXPLOSION_Y_MAX - Ini.CARGA_ALTO, Ini.CARGA_EXPLOSION_Y_MIN);
			
			lstCargas.add(carga);
			cargas.add(carga);
		}
		
		return lstCargas;
	}
	
	private void moverYEliminarCargas()
	{
		for (int i = cargas.size() - 1; i >= 0; i--)
		{
			var carga = cargas.get(i);
			
			if (carga.estaExplotada())
			{
				eventoExplosion(carga.getCoordenada(), carga.getDimension());
				cargas.remove(i);
			}
			else if (carga.estaSoltada())
			{
				carga.moverY(Ini.TIEMPO_REFRESCO_MS);
			}
		}
	}
	
	private void eventoExplosion(Coordenada coordCarga, Dimension dimCarga) 
	{
		submarino.calcularExplosion(coordCarga, dimCarga);
	}
	
	private void pasarDeNivel() 
	{
		submarino.pasarDeNivelEIncrementarDificultad(Ini.PORCENTAJE_INCREMENTO_DIFICULTAD);
		Buque.resetCantidadBuques();
	}
	
	public void actualizarJuego() 
	{
		if (buque == null || buque.finalizoRecorrido())
			crearBuque();
		else
			buque.moverX(Ini.TIEMPO_REFRESCO_MS);
		
		moverYEliminarCargas();
	}

	public void moverSubmarino(int entrada) 
	{
		switch(entrada)
		{
		case 87, 38:
		{
			submarino.moverArriba();
			break;
		}
		case 68, 39:
		{
			submarino.moverDerecha();
			break;
		}
		case 83, 40:
		{
			submarino.moverAbajo();
			break;
		}
		case 65, 37:
		{
			submarino.moverIzquierda();
			break;
		}
		}
	}
	
	public Submarino getSubmarino()
	{
		return this.submarino;
	}
	
	public Vector2D getAreaJuego()
	{
		return new Vector2D(Ini.AREA_JUEGO_X_MAX - Ini.AREA_JUEGO_X_MIN
				, Ini.AREA_JUEGO_Y_MAX - Ini.AREA_JUEGO_Y_MIN);
	}
	
	public SubmarinoView getSubmarinoView() 
	{
		return new SubmarinoView(submarino);
	}

	public BuqueView getBuqueView() 
	{
		return new BuqueView(buque);
	}

	public List<CargaProfundidadView> getCargasViews() 
	{
		List<CargaProfundidadView> lstCargasView = new ArrayList<CargaProfundidadView>();
		
		for (CargaProfundidad carga : cargas) 
		{
			lstCargasView.add(new CargaProfundidadView(carga));
		}
		return lstCargasView;
	}

}
