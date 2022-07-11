package gui.gameRes;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Imagen 
{
	public static final String URL_PAUSA = "/res/imagenes/estados/pausa.png";
	public static final String URL_GAME_OVER = "/res/imagenes/estados/gameover.png";
	public static final String URL_SUBMARINO_0 = "/res/imagenes/submarino/submarino0.png";
    public static final String URL_SUBMARINO_1 = "/res/imagenes/submarino/submarino1.png";
    public static final String URL_BUQUE_0 = "/res/imagenes/buque/buque0.png";
    public static final String URL_BUQUE_1 = "/res/imagenes/buque/buque1.png";
    public static final String URL_CARGA = "/res/imagenes/carga/carga.png";
    public static final String URL_EXPLOSION = "/res/imagenes/carga/explosion.png";
	
	
    public Image getIconImage(String ubicacion)
    { // Crear icono de ventana.
    	Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource(ubicacion));
    	return icono;
    }
    
    public JLabel dibujarLabel(String ubicacion, int ancho, int alto)
	{ // Crear JLabel de 0 con imagen escalada.
    	JLabel lbl = new JLabel();
    	lbl.setSize(ancho, alto);
    	lbl.setVisible(true);
		ImageIcon imagen = new ImageIcon(getClass().getResource(ubicacion));
		Icon icon = new ImageIcon(imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
		lbl.setIcon(icon);
		return lbl;
	}
    
    public Icon crearImagen(String ubicacion, int ancho, int alto)
    { // Crear imagen escalada.
    	ImageIcon imagen = new ImageIcon(getClass().getResource(ubicacion));
		Icon icon = new ImageIcon(imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
		return icon;
    }
    
}
