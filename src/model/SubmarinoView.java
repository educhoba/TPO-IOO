package model;

public class SubmarinoView extends ObjetoJuegoView{

	private int largo;
	private int alto;
	
	public SubmarinoView(Submarino s) {
		super(s);
		largo = s.altura;
		alto = s.altura;
	}

	public int getLargo() {
		return largo;
	}


	public int getAlto() {
		return alto;
	}


}