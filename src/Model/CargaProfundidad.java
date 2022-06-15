package model;

import java.util.Random;

public class CargaProfundidad extends ObjetosJuego {

	private float yExplosion;
	private boolean soltada;

	public CargaProfundidad(float velocidad, int altura, int largo, Coordenada c, int yMinExplosion, int yMaxExplosion) {
		super(velocidad, altura, largo, c);
		// Carga
		setAlturaExplosion(yMinExplosion,yMaxExplosion);
		this.soltada = false;
	}

	public void moverY(float deltaTime) {
		if (estaSoltada()) {
			float distancia = deltaTime * this.velocidad * ObjetosJuego.velocidadMultiplicador;
			this.coordenada.moverY(distancia);
			//chequeo si tiene que explotar
			float yActual = this.coordenada.getX();
			if (yActual <= this.yExplosion)
				explotar();
		}
	}

	private void explotar() {
		// TODO implement here evento de explosion
	}
	
	private void setAlturaExplosion( float yMinExplosion, float yMaxExplosion) {
		int yMin =  (int)yMinExplosion;
		int yMax =  (int)yMaxExplosion;
			
		this.yExplosion =  new Random().nextInt(yMax + 1 - yMin) + yMin; // nro entre yMin e yMax.
	}

	public void moverConBuque(float deltaX) {
		if (!estaSoltada())
			this.coordenada.moverX(deltaX);
	}

	private boolean estaSoltada() {
		return soltada;
	}
	public void soltar() {
		soltada =true;
	}
	
}