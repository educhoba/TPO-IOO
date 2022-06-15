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

	static void printJuego(Controlador controlador){
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
	static void printNivel(int nivel) {
		System.out.println("Nivel: " +nivel );
		
	}
	static void printCoordenadasSubmarino(Coordenada coordSubmarino) {
		System.out.printf("Posición Submarino: (%.2f,%.2f)%n",coordSubmarino.getX(),coordSubmarino.getY());
	}
	static void printCoordenadasBuque(Coordenada coordBuque)
	{
		System.out.printf("Posición Buque: (%.2f,%.2f)%n",coordBuque.getX(),coordBuque.getY());
	}
	static void printCoordenadasCarga(Coordenada coordCarga)
	{
		System.out.printf("Posición Carga: (%.2f,%.2f)%n",coordCarga.getX(),coordCarga.getY());
	}
	static void printInfoJugador(int vidas,int integridad, int puntos) {
		System.out.print("Vidas: " +vidas +" - ");
		System.out.print("Integridad: " +integridad +" - ");
		System.out.print("Puntos: " +puntos);
		System.out.println();
	}
	static void printInicio() {
		System.out.println("=============MENU===============");
		System.out.println("Apretar 'Y' para comenzar el juego");
	}
	static void printMenu() {
		System.out.println("=============MENU===============");
		System.out.println("Apretar 'Y' para finalizar el juego");
		System.out.println("Apretar 'ESC' para reanudar el juego");
	}
	static void printPausado() {
		System.out.println("=============PAUSADO===============");
		System.out.println("Apretar 'P' para reanudar el juego");
	}
    static void clearScreen() {  
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
