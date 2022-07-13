package gui.gameLabels;

import java.awt.Dimension;

import javax.swing.JLabel;

import gui.gameRes.Imagen;
import gui.gameRes.Sonido;
import negocio.gameObjetcsViews.CargaProfundidadView;
import utilidades.Vector2D;

public class ExplosionGUI 
{
	private Vector2D vCoord;
	private Dimension dim;
	private double tiempoInicio;
	private int correccion;
	private Vector2D vEscalar;
	private int largo;
	private int alto;
	private JLabel lblExplosion;
	
	
	public ExplosionGUI(int correccion, Vector2D vEscalar, CargaProfundidadView cv)
	{
		this.dim = cv.getDimensionExplosion();
		this.vCoord = cv.getVectorExplosion();
		tiempoInicio = System.currentTimeMillis();
		lblExplosion = new JLabel();
		redimensionarExplosion(correccion, vEscalar);
		
		Sonido s = new Sonido(Sonido.URL_SONIDO_EXPLOSION);
		s.cambiarVolumen();
		s.reproducir();
	}
	
	
	public JLabel actualizar()
	{
		return lblExplosion;
	}

	public double getTiempoInicio() 
	{
		return this.tiempoInicio;
	}
	
	public int getX()
	{
		return Math.round(vCoord.getX() * vEscalar.getFloatX());
	}
	
	public int getY()
	{
		return Math.round(vCoord.getY() * vEscalar.getFloatY() + correccion);
	}
	
	public int getLargo()
	{
		return this.largo;
	}
	
	public int getAlto()
	{
		return this.alto;
	}
	
	public void redimensionarExplosion(int correccion, Vector2D vEscalar)
	{
		this.correccion = correccion;
		
		this.vEscalar = vEscalar;
		
		calcularDimension();
		
		recargarImagen();
	}
	
	private void calcularDimension()
	{
		largo = Math.round(dim.width * vEscalar.getFloatX());
		alto = Math.round(dim.height * vEscalar.getFloatY());
	}
	
	private void recargarImagen()
	{
		lblExplosion.setIcon(new Imagen().crearImagen(Imagen.URL_EXPLOSION, largo, alto));
		lblExplosion.setVisible(true);
	}
	
}
