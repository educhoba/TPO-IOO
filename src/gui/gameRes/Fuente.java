package gui.gameRes;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class Fuente 
{
	public Font cargarFuente(String ubicacionFuente, int estilo, int size)
    {
    	Font fuente = new Font("Arial", estilo, size);
    	try 
    	{
			fuente = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(ubicacionFuente));
			fuente = fuente.deriveFont(estilo, size);
		} 
    	catch (FontFormatException | IOException e) 
    	{
			System.err.println("No se cargo la fuente");
		}
		return fuente;
    }
}
