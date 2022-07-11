package domain.gameArea;

import utilidades.Vector2D;

public class Coordenada {

    private AreaMovimiento areaMovimiento;
    private Vector2D coordenada;

	
	public Coordenada(int xCoordenada, int yCoordenada, AreaMovimiento areaMovimiento) 
	{
		coordenada = new Vector2D(xCoordenada, yCoordenada);
		this.areaMovimiento = areaMovimiento;
	}
	
  
    public void moverX(int distancia) 
    {
    	Vector2D min = this.areaMovimiento.getMin();
    	Vector2D max = this.areaMovimiento.getMax();
    	
    	int xFinal = this.coordenada.getX() + distancia;
    	
    	if(xFinal > max.getX())
    		this.coordenada.setX(max.getX());
    	else if(xFinal < min.getX())
    		this.coordenada.setX(min.getX());
    	else
    		this.coordenada.setX(xFinal);
    }
    
    public void moverY(int distancia) 
    {
    	Vector2D min = this.areaMovimiento.getMin();
    	Vector2D max = this.areaMovimiento.getMax();
    	
    	int yFinal = this.coordenada.getY() + distancia;
    	
    	if(yFinal > max.getY())
    		this.coordenada.setY(max.getY());
    	else if(yFinal < min.getY())
    		this.coordenada.setY(min.getY());
    	else
    		this.coordenada.setY(yFinal);
    }
    
    public Vector2D getVectorCoordenada()
    {
    	return this.coordenada;
    }
    
    public void setX(int x)
    {
    	this.coordenada.setX(x);
    }
    
    public int getxMin()
    {
    	return areaMovimiento.getMin().getX();
    }
    
    public int getxMax()
    {
    	return areaMovimiento.getMax().getX();
    }

}