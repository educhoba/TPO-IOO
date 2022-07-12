package domain;

public class Ini 
{
	// ESTA CLASE CONTIENE TODAS LAS CONSTANTES DE INICIACION DEL JUEGO.
	public final static float TIEMPO_REFRESCO_MS = 0.024f;
	public final static int BUQUES_POR_NIVEL = 10;
	public final static int PORCENTAJE_INCREMENTO_DIFICULTAD = 10;
	
	public final static int AREA_JUEGO_X_MIN = 0;
	public final static int AREA_JUEGO_X_MAX = 600;
	public final static int AREA_JUEGO_Y_MIN = 0;
	public final static int AREA_JUEGO_Y_MAX = 800;
	
	public final static int SUBMARINO_Y_MIN = 300;
	public final static int SUBMARINO_Y_MAX = 800;
	public final static int SUBMARINO_VELOCIDAD = 10;
	
	public final static int BUQUE_VELOCIDAD = 50;
	
	public final static int CARGA_EXPLOSION_Y_MIN = 300;
	public final static int CARGA_EXPLOSION_Y_MAX = 700;
	public final static int CARGA_VELOCIDAD = 200;
	public final static int CANT_CARGAS_MIN = 10;
	public final static int CANT_CARGAS_MAX = 10;
	
	
	/*
	 * 
	 * 
	 *        A PARTIR DE AQUI PARA ABAJO, NO TOCAR!!!!!
	 * 
	 * 
	 */
	
	
	public final static int SUBMARINO_LARGO = (AREA_JUEGO_X_MAX - AREA_JUEGO_X_MIN) * 80 / 600;
	public final static int SUBMARINO_ALTO = (AREA_JUEGO_Y_MAX - AREA_JUEGO_Y_MIN) * 50 / 800;
	public final static int BUQUE_LARGO = (AREA_JUEGO_X_MAX - AREA_JUEGO_X_MIN) * 80 / 600;
	public final static int BUQUE_ALTO = (AREA_JUEGO_Y_MAX - AREA_JUEGO_Y_MIN) * 90 / 800;
	public final static int CARGA_LARGO = (AREA_JUEGO_X_MAX - AREA_JUEGO_X_MIN) * 10 / 600;
	public final static int CARGA_ALTO = (AREA_JUEGO_Y_MAX - AREA_JUEGO_Y_MIN) * 15 / 800;
	public final static int EXPLOSION_LARGO = (AREA_JUEGO_X_MAX - AREA_JUEGO_X_MIN) * (200 + CARGA_LARGO) / 600;
	public final static int EXPLOSION_ALTO = (AREA_JUEGO_Y_MAX - AREA_JUEGO_Y_MIN) * (200 + CARGA_ALTO) / 800;
	
}
