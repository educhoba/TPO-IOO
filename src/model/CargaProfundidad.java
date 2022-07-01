package model;

import java.util.Random;

public class CargaProfundidad extends ObjetoJuego {

	private float yExplosion;
	private boolean soltada;
	private boolean explotada;

	public CargaProfundidad(float velocidad, int altura, int largo, Coordenada c, int yMaxExplosion, int yMinExplosion) {
		super(-velocidad, altura, largo, c);
		setAlturaExplosion(yMaxExplosion, yMinExplosion);
		this.soltada = false;
		this.explotada = false;
	}

	public void moverY(float deltaTime) {
		if (estaSoltada()) {
			float distancia = deltaTime * this.velocidad * ObjetoJuego.velocidadMultiplicador;
			this.coordenada.moverY(distancia);
			
			if (this.coordenada.getY() <= this.yExplosion) //chequeo si tiene que explotar
				explotar();
		}
	}
	
	private void setAlturaExplosion(float yMaxExplosion, float yMinExplosion) {
		int yMax =  -(int)yMinExplosion;
		int yMin =  -(int)yMaxExplosion;
		
		int yExpl = new Random().nextInt(yMax + 1 - yMin) + yMin; // nro entre yMin e yMax.
		this.yExplosion = -yExpl;
	}
	
	private void explotar() {
		explotada = true;
	}

	public void moverConBuque(float deltaX) {
		if (!estaSoltada())
			this.coordenada.moverX(deltaX);
	}

	public boolean estaSoltada() {
		return soltada;
	}
	
	public void soltar() {
		soltada = true;
	}
	
	public boolean estaExplotada() {
		return explotada;
	}
	
}