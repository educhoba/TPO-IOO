package gui.gameLabels;

import javax.swing.JLabel;

import controlador.Controlador;
import gui.gameRes.Imagen;
import negocio.gameObjetcsViews.BuqueView;
import utilidades.Vector2D;

public class BuqueGUI 
{
	
	private JLabel bGUI0;
	private JLabel bGUI1;
	private int largo;
	private int alto;
	
	private BuqueView bv;
	private Controlador c;
	private Vector2D vEscalar;
	private int correccion;
	
	public BuqueGUI(int correccion, Vector2D vEscalar, Controlador c)
	{
		bGUI0 = new JLabel();
		bGUI1 = new JLabel();
		
		this.c = c;
		
		redimensionarBuque(correccion, vEscalar);
	}
	
	public JLabel actualizar()
	{	
		actualizarBuqueView();
		
		if (bv.getDireccion() == 0) // Va de izquierda a derecha;
		{
			bGUI0.setVisible(true);
			bGUI1.setVisible(false);
			return bGUI0;
		}
		
		else // Va de derecha a izquierda.
		{
			bGUI0.setVisible(false);
			bGUI1.setVisible(true);
			return bGUI1;
		}
	}
	
	public int getX()
	{
		return Math.round(bv.getX() * vEscalar.getFloatX());
	}
	
	public int getY()
	{
		return Math.round(bv.getY() * vEscalar.getFloatY() + correccion);
	}
	
	public int getLargo()
	{
		return this.largo;
	}
	
	public int getAlto()
	{
		return this.alto;
	}
	
	public void redimensionarBuque(int correccion, Vector2D vEscalar)
	{
		this.correccion = correccion;
		
		this.vEscalar = vEscalar;
		
		actualizarBuqueView();
		
		calcularDimension();
		
		cargarImagenes();
	}
	
	private void actualizarBuqueView()
	{
		this.bv = c.getBuqueView();
	}
	
	private void calcularDimension()
	{
		largo = Math.round(bv.getLargo() * vEscalar.getFloatX());
		alto = Math.round(bv.getAlto() * vEscalar.getFloatY());
	}
	
	private void cargarImagenes()
	{
		Imagen i = new Imagen();
		bGUI0.setIcon(i.crearImagen(Imagen.URL_BUQUE_0, largo, alto));
		bGUI1.setIcon(i.crearImagen(Imagen.URL_BUQUE_1, largo, alto));
	}
	
}
