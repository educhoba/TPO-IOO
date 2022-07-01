package model;

public class CargaProfundidadView extends ObjetoJuegoView{

	private boolean exploto;
	private boolean soltada;
	
	public CargaProfundidadView(CargaProfundidad cp) {
    	super(cp);
    	exploto = cp.estaExplotada();
    	soltada = cp.estaSoltada();
    }
	
	public boolean estaExplotada() {
		return exploto;
	}

	public boolean estaSoltada() {
		return soltada;
	}
}