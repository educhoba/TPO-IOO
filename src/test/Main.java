package test;

import controlador.Controlador;
import eventos.EventoTeclado;
import model.Coordenada;


public class Main {

	public static void main(String[] args) {

		Controlador controlador = new Controlador();
		new EventoTeclado(controlador);
		
		while(!controlador.estaCerrando()) {
			if (controlador.estaEnInicio()){
				printInicio();
			}
			else if (controlador.estaCorriendo()){
				controlador.actualizarJuego();
				printJuego(controlador);
			}
			else if (controlador.estaMenuAbierto()){
				printMenu();
			}
			else if (controlador.estaPausado()){
				printPausado();
			}
			clearScreen();
		}
	}

	public static void printJuego(Controlador controlador){
		printNivel(controlador.getNivel());
		printInfoJugador(controlador.getVidasJugador(),
				controlador.getIntegridadCascoJugador(),
				controlador.getPuntosJugador()
				);
		var buques = controlador.getCoordenadasBuques();
		for(Coordenada item : buques) {
			printCoordenadasBuque(item);
		}
		var cargas = controlador.getCoordenadasCargas();
		for(Coordenada item : cargas) {
			printCoordenadasCarga(item);
		}
		printCoordenadasSubmarino(controlador.getCoordenadasJugador());
	}
	private static void printNivel(int nivel) {
		System.out.println("Nivel: " +nivel );
		
	}

	public static void printCoordenadasSubmarino(Coordenada coordSubmarino) {
		System.out.printf("Posición Submarino: (%.2f,%.2f)%n",coordSubmarino.getX(),coordSubmarino.getY());
	}
	public static void printCoordenadasBuque(Coordenada coordBuque)
	{
		System.out.printf("Posición Buque: (%.2f,%.2f)%n",coordBuque.getX(),coordBuque.getY());
	}
	public static void printCoordenadasCarga(Coordenada coordCarga)
	{
		System.out.printf("Posición Carga: (%.2f,%.2f)%n",coordCarga.getX(),coordCarga.getY());
	}
	public static void printInfoJugador(int vidas,int integridad, int puntos) {
		System.out.print("Vidas: " +vidas +" - ");
		System.out.print("Integridad: " +integridad +" - ");
		System.out.print("Puntos: " +puntos);
		System.out.println();
	}
	public static void printInicio() {
		System.out.println("=============MENU===============");
		System.out.println("Apretar 'Y' para comenzar el juego");
	}
	public static void printMenu() {
		System.out.println("=============MENU===============");
		System.out.println("Apretar 'Y' para finalizar el juego");
		System.out.println("Apretar 'ESC' para reanudar el juego");
	}
	public static void printPausado() {
		System.out.println("=============PAUSADO===============");
		System.out.println("Apretar 'P' para reanudar el juego");
	}
    public static void clearScreen() {  
    	try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        System.out.print("\r");  
        for(int i=0;i<30;i++) {
            System.out.println();
		}

     } 
}
