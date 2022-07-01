package eventos;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import controlador.Controlador;

public class EventoTeclado extends JFrame implements KeyListener {
	
	/*
	 * ESTA CLASE LA TIENE VENTANA JUEGO. POR LO TANTO HAY QUE ELIMINARLA. 
	 */

	private static final long serialVersionUID = 1572466681311892571L;
	Controlador controlador;
	
	public EventoTeclado(Controlador c) 
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(0, 0);
		this.setLayout(null);
		this.setVisible(true);
		this.addKeyListener(this);
		this.controlador = c;
	}

	
	@Override
	public void keyTyped(KeyEvent e) {}

	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		
	}

	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		controlador.obtenerEntradaTeclado(e.getKeyCode());
	}

}
