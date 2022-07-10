package test;

import model.Submarino;

public class Debugger {

	boolean debuggerON = true;
	public static void printIntegridadSubmarino(int daño, int integridadCasco) {
		//System.out.println("\n-" +daño +"INTEGRIDAD.\nIntegridad Submarino: " +integridadCasco +"\n");
	}
	public static void printNuevosPuntos(int valor, int puntos){
		//System.out.println("\n+" +valor +"PUNTOS\nPuntos Actuales: " + puntos +"\n");
	}
	public static void printVidaExtra(){
		//System.out.println("\nSUMASTE UNA VIDA EXTRA!\n");
		}
	public static void printPasarNivel(int nivel) {
		//System.out.println("\nPASASTE DE NIVEL!! \nNivel: " + nivel +"\n");
	}
	
	public static void printPerderVida(int vidas) {
		//System.out.println("\nHas perdido una vida!\nVidas Actuales: " + vidas);
	}
	public static void printRecargarIntegridad() {
		//System.out.println("Integridad del casco recargada al 100.\n");
	}
	public static void printGameOver() {
		//System.out.println("\n\n\n\nGAME OVER!!");
		
	}
	public static void PrintXSoltar(float xSoltar) {
		System.out.println("\nX SOLTAR:: " + xSoltar +"\n");
		
	}
	public static void PrintCoordenadasSubmarino(Submarino jugador) {
		System.out.println("\nX :: " + jugador.getCoordenada().getX() +"\n");
		System.out.println("\nY :: " + jugador.getCoordenada().getY() +"\n");
		
	}
	
	
	
	}