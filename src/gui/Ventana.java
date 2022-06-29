package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import controlador.Controlador;

public class Ventana extends JFrame{
	

	private static final long serialVersionUID = -5360041225007601188L;
	private Controlador controlador;
	
	private static int WIDTH;
	private static int HEIGHT;
	
	private int puntos;
	
//	private JDesktopPane dp;
	private FondoPantalla panel;
	private JMenu menu, salir;
	private JMenuBar barraMenu;
	private JMenuItem activar, desactivar, finalizar;
	private Timer timer;
	private JLabel lblVidas, lblIntegridad, lblNivel, lblPuntos, lblTiempo, lblRotulo;
	private int segundero;
	
	
	public Ventana(Controlador controlador)
	{
		this.controlador = controlador;
		
		this.configurarMenu();
		this.devolverTamañoPantalla();
		this.iniciarVentana();
		this.eventos();
		
		segundero = 0;
		timer = new Timer(1000, new AccionTimer());
		
		this.addKeyListener(new EventoTeclado());
	}
	
	
	private void configurarMenu() {
		barraMenu = new JMenuBar();
		menu = new JMenu("Menu");
		salir = new JMenu("Salir");
		activar = new JMenuItem("Abrir menu");
		desactivar = new JMenuItem("Cerrar menu");
		finalizar = new JMenuItem("Salir");
		menu.add(activar);
		menu.add(desactivar);
		salir.add(finalizar);
		barraMenu.add(menu);
		barraMenu.add(salir);
		this.setJMenuBar(barraMenu);
	}
	
	
	private void devolverTamañoPantalla() {
	    Toolkit t = Toolkit.getDefaultToolkit();    
        Dimension dimensions = t.getScreenSize();
        WIDTH = dimensions.width;
        HEIGHT = dimensions.height;
	}
	
	
	public void iniciarVentana()
	{
		this.setTitle("Submarine Attack");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
//		this.setSize(WIDTH/2, HEIGHT/2);
		this.setSize(1000, 1000);
		this.setVisible(true);
		this.setLocation((WIDTH - this.getWidth())/2, (HEIGHT - this.getHeight())/2);
		
		this.setResizable(false);
//		this.setContentPane(new FondoPantalla(dp));
//		this.setContentPane(dp);
		
		
		panel = new FondoPantalla();
		
		this.setContentPane(panel);
//		this.setContentPane(new FondoPantalla()); // Para establecer el menu
		
		
		this.cargarLabels();
		panel.add(lblRotulo);
		panel.add(lblTiempo);
		panel.add(lblVidas);
	}
	
	
	public void cargarLabels()
	{
		Font letra = new Font("Press Start 2P", Font.PLAIN, 12);
		lblRotulo = new JLabel("Timer : ");
		lblRotulo.setBounds(50, 10, 100, 30);
//		lblRotulo.setHorizontalAlignment(JLabel.CENTER);
		lblRotulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblRotulo.setFont(letra);
		lblRotulo.setOpaque(false);
		
		lblTiempo = new JLabel("0");
		lblTiempo.setBounds(150, 10, 80, 30);
		lblTiempo.setHorizontalAlignment(JLabel.CENTER);
		lblTiempo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblTiempo.setFont(letra);
		
		lblVidas = new JLabel("Vidas : 3");
		lblVidas.setBounds(150, 50, 80, 30);
//		lblVidas.setHorizontalAlignment(JLabel.CENTER);
		lblVidas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblVidas.setFont(letra);
		
	}
	
	
	public void eventos() 
	{
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		finalizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		activar.addActionListener(new ManejoTimer());
		desactivar.addActionListener(new ManejoTimer());
	}
	
	
	public void puntaje(Graphics g)
	{
		int x = 600;
		int y = 600;
		
		String puntosToString = Integer.toString(puntos);
		
		for (int i = 0; i < puntosToString.length(); i++)
		{
//			g.drawImage(numeros);
		}
		
	}

	
	class EventoTeclado implements KeyListener{
		

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			System.out.println(e.getKeyCode());
			
			switch(e.getKeyCode()) 
			{
			case 80:
				if (timer.isRunning())
				{
					activar.addActionListener(new ManejoTimer());
				}
				else
				{
					desactivar.addActionListener(new ManejoTimer());
				}
				break;
					
			}
			
//			controlador.obtenerEntradaTeclado(e.getKeyCode());
		}
		
	}

	
	class FondoPantalla extends JDesktopPane{

		
		private static final long serialVersionUID = -3555555288909468761L;
		
		
		@Override
		public void paint(Graphics g)
		{
			ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/fondopantallaclaro.jpg"));
			g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
		
	}

	
	class ManejoTimer implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getActionCommand().equalsIgnoreCase("Cerrar menu"))
				timer.start();
			else
				timer.stop();
		}
	}
	
	
	class AccionTimer implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			segundero++;
			lblTiempo.setText(String.valueOf(segundero));
		}
	}
	
}
