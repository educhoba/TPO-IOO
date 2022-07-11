package utilidades;

public class Vector2D {
	
	private float x;
	private float y;
	
	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return Math.round(x);
	}

	public void setX(float x) {
		this.x = x;
	}

	public int getY() {
		return Math.round(y);
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getFloatX()
	{
		return this.x;
	}
	
	public float getFloatY()
	{
		return this.y;
	}
}
