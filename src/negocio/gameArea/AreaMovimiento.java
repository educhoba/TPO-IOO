package negocio.gameArea;

import utilidades.Vector2D;

public class AreaMovimiento 
{
	private Vector2D min;
	private Vector2D max;
    
    public AreaMovimiento(int xMax , int xMin, int yMax, int yMin)
    {
    	min = new Vector2D(xMin, yMin);
    	max = new Vector2D(xMax, yMax);
    }

	public Vector2D getMin() 
	{
		return this.min;
	}

	public Vector2D getMax() 
	{
		return this.max;
	}

}