package gui.gameLabels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;

import controlador.Controlador;
import gui.gameRes.Imagen;
import negocio.gameObjetcsViews.CargaProfundidadView;
import utilidades.Vector2D;

public class CargaProfundidadGUI 
{
	private List<CargaProfundidadView> cargasViews;
	private List<JLabel> cargas;
	private List<ExplosionGUI> cargasExplotadas;
	private int largo;
	private int alto;
	
	private Controlador c;
	private Vector2D vEscalar;
	private int correccion;
	
	
	public CargaProfundidadGUI(int correccion, Vector2D vEscalar, Controlador c)
	{
		this.cargas = new ArrayList<JLabel>();
		this.cargasExplotadas = new ArrayList<ExplosionGUI>();
		
		this.c = c;
		
		redimensionarCargaProfundidad(correccion, vEscalar);
	}
	
	
	public List<JLabel> actualizar()
	{	
		actualizarCargaProfundidadView();
		List<JLabel> cgs = new ArrayList<JLabel>();
		
		if (cargasViews.size() != cargas.size()) // SI SE CREARON CARGAS NUEVAS.
    	{
    		int j = cargasViews.size() - cargas.size();
    		for (int i = 0; i < j; i++) // ITERO LA CANTIDAD DE CARGAS NUEVAS CREADAS.
    		{
    			cargas.add(cargarImagen()); // CREO LA NUEVA CARGA Y LA DIBUJO INVISIBLE.
    			cargas.get(i).setVisible(false);
    		}
    	}
    	
		for (int i = cargasViews.size() - 1; i >= 0; i--) // RECORRO TODAS LAS CARGAS EXISTENTES.
    	{
    		if (cargasViews.get(i).estaExplotada()) // SI LA CARGA ESTA EXPLOTADA.
    		{
    			cargas.get(i).setVisible(false); // DESAPAREZCO LA CARGA.
    			
    			ExplosionGUI e = new ExplosionGUI(correccion, vEscalar, cargasViews.get(i));
    			e.actualizar().setVisible(true);
    			
    			
    			cargasExplotadas.add(e);
    			cargas.remove(i);
    		}
    		
    		else if (cargasViews.get(i).estaSoltada()) // SI LA CARGA NO ESTA EXPLOTADA, PERO SI SOLTADA.
    		{
    			cargas.get(i).setVisible(true); // DIBUJO VISIBLE LA CARGA EN SU NUEVA POSICION.
    		}
    	}
		
		double tiempoFin = System.currentTimeMillis();
		for (int i = cargasExplotadas.size() - 1; i >= 0; i--)
		{
			if (tiempoFin - cargasExplotadas.get(i).getTiempoInicio() >= 2000)
			{
				cargasExplotadas.get(i).actualizar().setVisible(false);
				cargasExplotadas.remove(i);
			}
		}
    	
    	
    	int j = cargas.size() - 1;
    	for (int i = 0; i <= j; i++)
    	{
    		cgs.add(cargas.get(i));
    	}
    	
    	j = cargasExplotadas.size() - 1;
    	for (int i = 0; i <= j; i++)
    	{
    		cgs.add(cargasExplotadas.get(i).actualizar());
    	}

		
    		
    	return cgs;
	}

	
	public int getX(int i)
	{
		int x;
		
		if (i > cargasViews.size() - 1)
		{
			int j = i - cargasViews.size();
			x = Math.round(cargasExplotadas.get(j).getX());
		}
		else
		{
			x = Math.round(cargasViews.get(i).getX() * vEscalar.getFloatX());
		}
			
		return x;
	}
	
	public int getY(int i)
	{
		int y;
		
		if (i > cargasViews.size() - 1)
		{
			int j = i - cargasViews.size();
			y = Math.round(cargasExplotadas.get(j).getY());
		}
		else
		{
			y = Math.round(cargasViews.get(i).getY() * vEscalar.getFloatY() + correccion);
		}
			
		return y;
	}
	
	public int getLargo(int i)
	{
		int l;
		
		if (i > cargasViews.size() - 1)
		{
			int j = i - cargasViews.size();
			l = cargasExplotadas.get(j).getLargo();
		}
		else
		{
			l = this.largo;
		}
		
		return l;
	}
	
	public int getAlto(int i)
	{
		int a;
		
		if (i > cargasViews.size() - 1)
		{
			int j = i - cargasViews.size();
			a = cargasExplotadas.get(j).getAlto();
		}
		else
		{
			a = this.alto;
		}
		
		return a;
	}
	
	private void actualizarRedimensionCargasyExplosiones()
	{
		for (JLabel carga: cargas)
		{
			boolean cargaVis = carga.isVisible();
			
			carga.setIcon(redibujarImagen());
			
			carga.setVisible(cargaVis);
		}
		
		int j = cargasExplotadas.size() - 1;
		for (int i = 0; i <= j; i++)
		{
			cargasExplotadas.get(i).redimensionarExplosion(correccion, vEscalar);
			cargasExplotadas.get(i).actualizar().setVisible(true);
		}
	}
	
	public void redimensionarCargaProfundidad(int correccion, Vector2D vEscalar)
	{
		this.correccion = correccion;
		
		this.vEscalar = vEscalar;
		
		actualizarCargaProfundidadView();
		
		calcularDimension();
		
		actualizarRedimensionCargasyExplosiones();
	}
	
	private void actualizarCargaProfundidadView()
	{
		cargasViews = c.getCargasViews();
	}
	
	private void calcularDimension()
	{
		largo = Math.round(c.getDimensionCargaProfundidad().width * vEscalar.getFloatX());
		alto = Math.round(c.getDimensionCargaProfundidad().height * vEscalar.getFloatY());
	}
	
	private JLabel cargarImagen()
	{
		Imagen i = new Imagen();
		JLabel lbl = i.dibujarLabel(Imagen.URL_CARGA, largo, alto);
		return lbl;
	}
	
	private Icon redibujarImagen()
	{
		return new Imagen().crearImagen(Imagen.URL_CARGA, largo, alto);
	}
	
}
