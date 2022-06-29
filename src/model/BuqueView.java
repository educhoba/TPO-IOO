package model;

public class BuqueView extends ObjetoJuegoView{

	private boolean finalizoRecorrido;
	
	public BuqueView(Buque b) {
    	super(b);
    	finalizoRecorrido = b.finalizoRecorrido();
    }
	
	public boolean finalizoRecorrido() {
		return finalizoRecorrido;
	}
}