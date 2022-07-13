package negocio.gameObjects;

import java.util.List;
import java.util.Random;

import negocio.gameArea.Coordenada;

public class Buque extends ObjetoJuego {

	
	private List<CargaProfundidad> cargas;
	private int[] lstSoltar;
	private static int cantBuques;

	
	public Buque(int velocidad, int altura, int largo, Coordenada c, List<CargaProfundidad> cargas) {
		super(velocidad, altura, largo, c);
		this.cargas = cargas;
		setearLado();
		setearXSoltar();
		Buque.cantBuques++;
	}
	
	
	private void setearLado()
	{
		int lado = new Random().nextInt(1 + 1); // 0 en caso de izq a der. 1 en caso de der a izq
		
		if (lado == 1) // Si el random es 1, se mueve de derecha a izquierda.
		{
			this.coordenada.setX(this.coordenada.getxMax());
			this.velocidad *= -1;
		}
	}
	
	private void setearXSoltar()
	{
		lstSoltar = new int[cargas.size()];
		
		for (int j = cargas.size() - 1; j >= 0; j--)
		{
			lstSoltar[j] = setSoltar(cargas.get(j).dimension.width);
		}
	}

	private int setSoltar(int largoCarga) 
	{
		int xMin = this.coordenada.getxMin() + dimension.width;
		int xMax = this.coordenada.getxMax() - largoCarga;
		int xSoltar = new Random().nextInt(xMax + 1 - xMin) + xMin;
		return xSoltar;
	}

	public void moverX(float deltaTiempo) 
	{
		int distancia = Math.round(deltaTiempo * this.velocidad * ObjetoJuego.velocidadMultiplicador);

		this.coordenada.moverX(distancia);
		
		for (int i = cargas.size() - 1; i >= 0; i--)
		{
			var c = cargas.get(i);
			
			if (!c.estaSoltada())
			{
				int puntoMedioBuque = this.coordenada.getVectorCoordenada().getX() + (this.dimension.width / 2);
				c.moverConBuque(puntoMedioBuque);
			
				if (isxSoltar(lstSoltar[i], puntoMedioBuque))
				{
					if (c.getCoordenada().getVectorCoordenada().getX() > c.getCoordenada().getxMax())
					{
						c.getCoordenada().setX(c.getCoordenada().getxMax());
					}
					
					else if (c.getCoordenada().getVectorCoordenada().getX() < c.getCoordenada().getxMin())
					{
						c.getCoordenada().setX(c.getCoordenada().getxMin());
					}
					
					soltarCarga(i);
				}
			}
		}
	}

	private void soltarCarga(int pos) 
	{		
		cargas.get(pos).soltar();
	}

	private boolean isxSoltar(int xSoltar, int xPMBuque) 
	{
		boolean boolSoltar = false;
		
		if (velocidad > 0) // Si se mueve de izq a der
			boolSoltar = xPMBuque >= xSoltar;
		else
			boolSoltar = xPMBuque <= xSoltar;
		
		return boolSoltar;
	}
	
	public static int getCantidadBuques() 
	{
		return Buque.cantBuques;
	}
	
	public static void resetCantidadBuques()
	{
		Buque.cantBuques = 0;
	}

	public boolean finalizoRecorrido() 
	{
		if (velocidad > 0) // Si se mueve de izq a der
			return coordenada.getVectorCoordenada().getX() == coordenada.getxMax();
		else
			return coordenada.getVectorCoordenada().getX() == coordenada.getxMin();
	}
	
	public float getVelocidad()
	{
		return this.velocidad;
	}
	
}