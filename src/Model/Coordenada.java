package model;

public class Coordenada {

    /**
     * Default constructor
     */
    public Coordenada() {
    }

    private float xCoordenada;
    private float yCoordenada;
    private AreaJuego areaJuego;

	private float xMin;
	private float yMin;
	private float xMax;
	private float yMax;
	
	public Coordenada(float xCoordenada, float yCoordenada, AreaJuego areaJuego) 
	{
		this.xCoordenada = xCoordenada;
		this.yCoordenada = yCoordenada;
		this.areaJuego = areaJuego;
		this.xMin = areaJuego.getXMin();
		this.yMin = areaJuego.getYMin();
		this.xMax = areaJuego.getXMax();
		this.yMax = areaJuego.getYMax();
	}
	public Coordenada(float xCoordenada, float yCoordenada, AreaJuego areaJuego, float xMin, float yMin, float xMax, float yMax) 
	{
		this.xCoordenada = xCoordenada;
		this.yCoordenada = yCoordenada;
		this.areaJuego = areaJuego;
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	

    public float getX() {
        return this.xCoordenada;
    }

    public float getY() {
        return this.yCoordenada;
    }

    public void moverX(float distance) {
    	if(areaJuego.estaCorriendo()) {
        	float xFinal = this.xCoordenada+distance;
        	
        	if(xFinal > this.xMax)
        		this.xCoordenada = this.xMax;
        	else if(xFinal < this.xMin)
        		this.xCoordenada = this.xMin;
        	else
        		this.xCoordenada = xFinal;
    	}
    }
    
    public void moverY(float distance) {
    		if(areaJuego.estaCorriendo()) {
            	float yFinal = this.yCoordenada+distance;
            	
            	if(yFinal > this.yMax)
            		this.yCoordenada = this.yMax;
            	else if(yFinal < this.yMin)
            		this.yCoordenada = this.yMin;
            	else
            		this.yCoordenada = yFinal;
    		}
    }

    
	public float getxMin() {
		return xMin;
	}
	public float getyMin() {
		return yMin;
	}
	public float getxMax() {
		return xMax;
	}
	public float getyMax() {
		return yMax;
	}

}