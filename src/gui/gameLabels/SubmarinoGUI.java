package gui.gameLabels;

import javax.swing.JLabel;

import controlador.Controlador;
import gui.gameRes.Imagen;
import negocio.gameObjetcsViews.SubmarinoView;
import utilidades.Vector2D;

public class SubmarinoGUI
{
	private JLabel sGUI0;
	private JLabel sGUI1;
	private int largo;
	private int alto;
	
	private SubmarinoView sv;
	private Controlador c;
	private Vector2D vEscalar;
	private int correccion;
	
	
	public SubmarinoGUI(int correccion, Vector2D vEscalar, Controlador c)
	{
		sGUI0 = new JLabel();
		sGUI1 = new JLabel();
		
		this.c = c;
		
		redimensionarSubmarino(correccion, vEscalar);
	}
	
	
	public JLabel actualizar()
	{	
		actualizarSubmarinoView();
		
		if (sv.getDireccion() == 0) // Va de izquierda a derecha;
		{
			sGUI0.setVisible(true);
			sGUI1.setVisible(false);
			return sGUI0;
		}
		
		else // Va de derecha a izquierda.
		{
			sGUI0.setVisible(false);
			sGUI1.setVisible(true);
			return sGUI1;
		}
	}
	
	public int getX()
	{
		return Math.round(sv.getX() * vEscalar.getFloatX());
	}
	
	public int getY()
	{
		return Math.round(sv.getY() * vEscalar.getFloatY() + correccion);
	}
	
	public int getLargo()
	{
		return this.largo;
	}
	
	public int getAlto()
	{
		return this.alto;
	}
	
	public void redimensionarSubmarino(int correccion, Vector2D vEscalar)
	{
		this.correccion = correccion;
		
		this.vEscalar = vEscalar;
		
		actualizarSubmarinoView();
		
		calcularDimension();
		
		cargarImagenes();
	}
	
	private void actualizarSubmarinoView()
	{
		this.sv = c.getSubmarinoView();
	}
	
	private void calcularDimension()
	{
		largo = Math.round(sv.getLargo() * vEscalar.getFloatX());
		alto = Math.round(sv.getAlto() * vEscalar.getFloatY());
	}
	
	private void cargarImagenes()
	{
		Imagen i = new Imagen();
		sGUI0.setIcon(i.crearImagen(Imagen.URL_SUBMARINO_0, largo, alto));
		sGUI1.setIcon(i.crearImagen(Imagen.URL_SUBMARINO_1, largo, alto));
	}
	
}
