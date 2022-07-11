package gui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import controlador.Controlador;
import gui.gameLabels.BuqueGUI;
import gui.gameLabels.CargaProfundidadGUI;
import gui.gameLabels.SubmarinoGUI;
import gui.gameRes.Fuente;
import gui.gameRes.Imagen;
import gui.gameRes.Sonido;
import utilidades.Vector2D;


public class VentanaJuego extends JFrame {
	
	
    // INICIO DE DECLARACION DE ATRIBUTOS
	
	private static final long serialVersionUID = 3947423717758576393L;
	
	private JPanel panel;
	private JMenuBar BarraMenu;
	private JMenu pausa, salir, volumen, resolucion;
	private JMenuItem pausar, reanudar, finalizar;
	private JMenuItem volDesactivado, volBajo, volMedio, volAlto, musFondoDes, musFondoAc;
	private JMenuItem resPantCompl, res1920, res1366, res1024, res800;
	
	private JLabel lblPausa, lblgameover;
	private JLabel nivel, integridad, vidas, puntos;
	private JLabel cantNivel, cantIntegridad, cantVidas, cantPuntos;
    private JLabel submarino, buque;
    private List<JLabel> cargas;
    
    private Sonido musicaFondo;
    private int volumenActual;
    private SubmarinoGUI submarinoGUI;
    private BuqueGUI buqueGUI;
    private CargaProfundidadGUI cargasGUI;
    
    private Timer timer;
    private Controlador c;
    private boolean bRedibujar;
    
    private int anchoPantalla;
	private int altoPantalla;
	private int correccion;
	private Vector2D vEscalar;
    
    // FINAL DE DECLARACION DE ATRIBUTOS
	
    
    
    // CONSTRUCTOR
    public VentanaJuego() 
    {
    	inicializarVariables();
    	
    	inicializarComponentesVisuales();
    	
    	addKeyListener(new EventoTeclado());
    	
    	musicaFondo.iniciarLoopeo();
    	timer.start();
    }
    
    
    private void actualizar()
    {
    	if (!bRedibujar)
    		c.actualizarJuego();
    	
    	actualizarCantidades();
    	
    	submarino = submarinoGUI.actualizar();
    	panel.add(submarino);
    	submarino.setBounds(submarinoGUI.getX(), submarinoGUI.getY(), submarinoGUI.getLargo(), submarinoGUI.getAlto());
    	
    	buque = buqueGUI.actualizar();
    	panel.add(buque);
    	buque.setBounds(buqueGUI.getX(), buqueGUI.getY(), buqueGUI.getLargo(), buqueGUI.getAlto());
    	
    	cargas = cargasGUI.actualizar();
    	for (int i = cargas.size() - 1; i >= 0; i--)
    	{
    		panel.add(cargas.get(i));
    		cargas.get(i).setBounds(cargasGUI.getX(i), cargasGUI.getY(i), cargasGUI.getLargo(i), cargasGUI.getAlto(i));
    	}
    	
    	if (!c.estaVivo())
    	{
    		timer.stop();
    		lblgameover.setVisible(true);
    	}
    }
    
    
    private void actualizarCantidades()
    {
    	// DIBUJO VISIBLE TODAS LAS CANTIDADES.
    	cantNivel.setText(String.valueOf(c.getNivel()));
    	cantIntegridad.setText(String.valueOf(c.getIntegridadCasco()));
    	cantVidas.setText(String.valueOf(c.getVidasJugador()));
    	cantPuntos.setText(String.valueOf(c.getPuntosJugador()));
    }
    
    
    private void inicializarVariables()
    {
    	c = new Controlador();
    	c.actualizarJuego();
    	timer = new Timer(42, new AccionTimer());
    	bRedibujar = false;
    	
    	volumenActual = 75; // VOLUMEN MINIMO TENDRIA QUE SER 50, MAS BAJO NO SE LLEGA A ESCUCHAR
    	
    	musicaFondo = new Sonido(Sonido.musicaFondo);
    	musicaFondo.cambiarVolumen(volumenActual);
    	
    	cargas = new ArrayList<JLabel>();
    }
    
    
    private void crearContenedor()
    {
    	panel = new JPanel(){

			private static final long serialVersionUID = 2013056688719049185L;

			public void paintComponent(Graphics g)
            {
                ImageIcon icon = new ImageIcon(getClass().getResource("/res/imagenes/fondo/fondo.png"));
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
    
    
    private void tamanioPantalla()
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		anchoPantalla = d.width;
		altoPantalla = d.height;
		
		calcularAreaJuegoGUI();
	}
    
    
    private void establecerNuevaResolucion()
    {
    	calcularAreaJuegoGUI();
    	setSize(new Dimension(anchoPantalla, altoPantalla));
    	setLocationRelativeTo(null);
    	configurarContenedor();
    	submarinoGUI.redimensionarSubmarino(correccion, vEscalar);
    	buqueGUI.redimensionarBuque(correccion, vEscalar);
    	cargasGUI.redimensionarCargaProfundidad(correccion, vEscalar);
    	bRedibujar = true;
    	dibujarJLabelsIniciales();
    	actualizar();
    }
    
    
    private void calcularAreaJuegoGUI()
    {
    	int pIX = -2;
		int pFX = anchoPantalla - 2;
		int pIY = Math.round((altoPantalla - IniGUI.BARRA_MENU_ALTO) * IniGUI.P_INI_MAR / (IniGUI.RES_Y_INI - IniGUI.BARRA_MENU_ALTO));
		int pFY = Math.round((altoPantalla - IniGUI.BARRA_MENU_ALTO) * IniGUI.P_INI_ARENA / (IniGUI.RES_Y_INI - IniGUI.BARRA_MENU_ALTO));
		Vector2D vAreaJuegoGUI = new Vector2D(pFX - pIX, pFY - pIY);
		Vector2D vAreaJuegoDom = c.getAreaJuego();
		float xEscalar = vAreaJuegoGUI.getFloatX()	/ vAreaJuegoDom.getX();
		float yEscalar = vAreaJuegoGUI.getFloatY() / vAreaJuegoDom.getY();
		
		correccion = pIY;
		vEscalar = new Vector2D(xEscalar, yEscalar);
    }
    
    
    private void configurarVentana()
    {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Submarine Attack");
        setPreferredSize(new Dimension(anchoPantalla, altoPantalla));
        setResizable(false);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setIconImage(new Imagen().getIconImage("/res/imagenes/icono/icono.png"));
    }
    
    
    private void configurarContenedor()
    {
    	int x = anchoPantalla;
        int y = altoPantalla - IniGUI.BARRA_MENU_ALTO;
        panel.setPreferredSize(new Dimension(x, y));
        panel.setLayout(null);
        getContentPane().add(panel);
        panel.setBounds(0, 0, x, y);
    }
    
    
    private void inicializarBarraMenu()
    {
    	BarraMenu = new JMenuBar();
        pausa = new JMenu();
        pausar = new JMenuItem();
        reanudar = new JMenuItem();
        salir = new JMenu();
        finalizar = new JMenuItem();
        volumen = new JMenu();
        volDesactivado = new JMenuItem();
        volBajo = new JMenuItem();
        volMedio = new JMenuItem();
        volAlto = new JMenuItem();
        musFondoAc = new JMenuItem();
        musFondoDes = new JMenuItem();
        resolucion = new JMenu();
        resPantCompl = new JMenuItem();
        res1920 = new JMenuItem();
        res1366 = new JMenuItem();
        res1024 = new JMenuItem();
        res800 = new JMenuItem();
    }
    
    
    private void dibujarBarraMenu()
    {
    	BarraMenu.setPreferredSize(new Dimension(IniGUI.BARRA_MENU_ANCHO, IniGUI.BARRA_MENU_ALTO));
    	
    	Font fuenteMenu = new Fuente().cargarFuente("/res/fuentes/PressStart2P-Regular.ttf", 0, IniGUI.TAM_LETRA_MENU);
    	Font fuenteItem = new Fuente().cargarFuente("/res/fuentes/PressStart2P-Regular.ttf", 0, IniGUI.TAM_LETRA_MENU_ITEM);
    	
    	volumen.setText("Volumen");
    	volumen.setFont(fuenteMenu);
    	volumen.setMargin(new Insets(6, 6, 6, 10));
    	volumen.setPreferredSize(new Dimension(105, 18));

        volDesactivado.setFont(fuenteItem);
        volDesactivado.setText("Desactivar");
        volDesactivado.setMargin(new Insets(6, 6, 6, 6));
        volDesactivado.setPreferredSize(new Dimension(140, 35));
        volumen.add(volDesactivado);
        
        volBajo.setFont(fuenteItem);
        volBajo.setText("Volumen bajo");
        volBajo.setMargin(new Insets(6, 6, 6, 6));
        volBajo.setPreferredSize(new Dimension(140, 35));
        volumen.add(volBajo);
        
        volMedio.setFont(fuenteItem);
        volMedio.setText("Volumen medio");
        volMedio.setMargin(new Insets(6, 6, 6, 6));
        volMedio.setPreferredSize(new Dimension(140, 35));
        volumen.add(volMedio);
        
        volAlto.setFont(fuenteItem);
        volAlto.setText("Volumen alto");
        volAlto.setMargin(new Insets(6, 6, 6, 6));
        volAlto.setPreferredSize(new Dimension(140, 35));
        volumen.add(volAlto);
        
        musFondoAc.setFont(fuenteItem);
        musFondoAc.setText("Activar musica");
        musFondoAc.setMargin(new Insets(6, 6, 6, 6));
        musFondoAc.setPreferredSize(new Dimension(140, 35));
        volumen.add(musFondoAc);
        
        musFondoDes.setFont(fuenteItem);
        musFondoDes.setText("Desactivar musica");
        musFondoDes.setMargin(new Insets(6, 6, 6, 6));
        musFondoDes.setPreferredSize(new Dimension(185, 35));
        volumen.add(musFondoDes);
        
        BarraMenu.add(volumen);
    	
        pausa.setText("Pausa");
        pausa.setFont(fuenteMenu);
        pausa.setMargin(new Insets(6, 6, 6, 10)); //MARGENES
        pausa.setPreferredSize(new Dimension(82, 18));

        pausar.setFont(fuenteItem);
        pausar.setText("Pausar");
        pausar.setMargin(new Insets(6, 6, 6, 6));
        pausar.setPreferredSize(new Dimension(90, 35));
        pausa.add(pausar);
        
        reanudar.setFont(fuenteItem);
        reanudar.setText("Reanudar");
        reanudar.setMargin(new Insets(6, 6, 6, 6));
        reanudar.setPreferredSize(new Dimension(90, 35));
        pausa.add(reanudar);

        BarraMenu.add(pausa);
        
        resolucion.setText("Resolucion");
        resolucion.setFont(fuenteMenu);
        resolucion.setMargin(new Insets(6, 10, 6, 6));
        resolucion.setPreferredSize(new Dimension(139, 18));

        res1920.setFont(fuenteItem);
        res1920.setText("1920x1080");
        res1920.setMargin(new Insets(6, 6, 6, 6));
        res1920.setPreferredSize(new Dimension(170, 35));
        resolucion.add(res1920);
        
        res1366.setFont(fuenteItem);
        res1366.setText("1366x768");
        res1366.setMargin(new Insets(6, 6, 6, 6));
        res1366.setPreferredSize(new Dimension(170, 35));
        resolucion.add(res1366);
        
        res1024.setFont(fuenteItem);
        res1024.setText("1024x720");
        res1024.setMargin(new Insets(6, 6, 6, 6));
        res1024.setPreferredSize(new Dimension(170, 35));
        resolucion.add(res1024);
        
        res800.setFont(fuenteItem);
        res800.setText("800x600");
        res800.setMargin(new Insets(6, 6, 6, 6));
        res800.setPreferredSize(new Dimension(170, 35));
        resolucion.add(res800);
        
        resPantCompl.setFont(fuenteItem);
        resPantCompl.setText("Pantalla completa");
        resPantCompl.setMargin(new Insets(6, 6, 6, 6));
        resPantCompl.setPreferredSize(new Dimension(190, 35));
        resolucion.add(resPantCompl);

        BarraMenu.add(resolucion);
        
        salir.setText("Salir");
        salir.setFont(fuenteMenu);
        salir.setMargin(new Insets(6, 10, 6, 6));
        salir.setPreferredSize(new Dimension(82, 18));

        finalizar.setFont(fuenteItem);
        finalizar.setText("Salir a Windows");
        finalizar.setMargin(new Insets(6, 6, 6, 6));
        finalizar.setPreferredSize(new Dimension(170, 35));
        salir.add(finalizar);

        BarraMenu.add(salir);

        setJMenuBar(BarraMenu);
    }
    
    
    public void eventos() 
	{
		finalizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pausar.addActionListener(new ManejoTimerMenu());
		reanudar.addActionListener(new ManejoTimerMenu());
		volDesactivado.addActionListener(new ManejoVolumen());
		volBajo.addActionListener(new ManejoVolumen());
		volMedio.addActionListener(new ManejoVolumen());
		volAlto.addActionListener(new ManejoVolumen());
		musFondoAc.addActionListener(new ManejoVolumen());
		musFondoDes.addActionListener(new ManejoVolumen());
		resPantCompl.addActionListener(new ManejoResolucion());
		res1920.addActionListener(new ManejoResolucion());
		res1366.addActionListener(new ManejoResolucion());
		res1024.addActionListener(new ManejoResolucion());
		res800.addActionListener(new ManejoResolucion());
	}
    
    
    private void inicializarJLabels()
    {
    	lblgameover = new JLabel();
    	lblPausa = new JLabel();
    	integridad = new JLabel();
        puntos = new JLabel();
        vidas = new JLabel();
        nivel = new JLabel();
        cantVidas = new JLabel();
        cantIntegridad = new JLabel();
        cantPuntos = new JLabel();
        cantNivel = new JLabel();
    }
    
    
    private void dibujarJLabelsIniciales()
    {
    	lblPausa.setIcon(new Imagen().crearImagen(Imagen.URL_PAUSA, 500 * anchoPantalla / 1000, 285 * altoPantalla / 1000));
    	panel.add(lblPausa);
    	lblPausa.setBounds(240 * anchoPantalla / 1000, 300 * altoPantalla / 1000
    			, 500 * anchoPantalla / 1000, 285 * altoPantalla / 1000);
    	
    	lblgameover.setIcon(new Imagen().crearImagen(Imagen.URL_GAME_OVER, 720 * anchoPantalla / 1000, 631 * altoPantalla / 1000));
    	panel.add(lblgameover);
		lblgameover.setBounds(129 * anchoPantalla / 1000, 100 * altoPantalla / 1000, 720 * anchoPantalla / 1000
				, 631 * altoPantalla / 1000);
		
		if (!bRedibujar)
		{
			lblPausa.setVisible(false);
			lblgameover.setVisible(false);
		}
    	
		int tamFuente = Math.round(IniGUI.TAM_LETRA_LABELS * anchoPantalla / 1000);
        Font fuente = new Fuente().cargarFuente("/res/fuentes/PressStart2P-Regular.ttf", 0, tamFuente);
        
        // PARA WIDTH Y HEIGHT: 20px ES LO QUE OCUPA UN SOLO CARACTER DE ALTO/ANCHO. (ya que la letra esta en 20)
        nivel.setFont(fuente);
        nivel.setText(IniGUI.NIVEL);
        panel.add(nivel);
        nivel.setBounds(50 * anchoPantalla / 1024, 20 * altoPantalla / 720
        		, tamFuente * IniGUI.NIVEL.length(), tamFuente);
        
        cantNivel.setFont(fuente);
        cantNivel.setHorizontalAlignment(JLabel.CENTER);
        cantNivel.setText(String.valueOf(c.getNivel()));
        panel.add(cantNivel);
        cantNivel.setBounds(39 * anchoPantalla / 1024, 50 * altoPantalla / 720, tamFuente * 6, tamFuente);
        
        integridad.setFont(fuente);
        integridad.setText(IniGUI.INTEGRIDAD);
        panel.add(integridad);
        integridad.setBounds(260 * anchoPantalla / 1024, 20 * altoPantalla / 720
        		, tamFuente * IniGUI.INTEGRIDAD.length(), tamFuente);
        
        cantIntegridad.setFont(fuente);
        cantIntegridad.setHorizontalAlignment(JLabel.CENTER);
        cantIntegridad.setText(String.valueOf(c.getIntegridadCasco()));
        panel.add(cantIntegridad);
        cantIntegridad.setBounds(300 * anchoPantalla / 1024, 50 * altoPantalla / 720, tamFuente * 6, tamFuente);
        
        vidas.setFont(fuente);
        vidas.setText(IniGUI.VIDAS);
        panel.add(vidas);
        vidas.setBounds(590 * anchoPantalla / 1024, 20 * altoPantalla / 720
        		, tamFuente * IniGUI.VIDAS.length(), tamFuente);
        
        cantVidas.setFont(fuente);
        cantVidas.setHorizontalAlignment(JLabel.CENTER);
        cantVidas.setText(String.valueOf(c.getVidasJugador()));
        panel.add(cantVidas);
        cantVidas.setBounds(578 * anchoPantalla / 1024, 50 * altoPantalla / 720, tamFuente * 6, tamFuente);

        puntos.setFont(fuente);
        puntos.setText(IniGUI.PUNTOS);
        panel.add(puntos);
        puntos.setBounds(830 * anchoPantalla / 1024, 20 * altoPantalla / 720
        		, tamFuente * IniGUI.PUNTOS.length(), tamFuente);

        cantPuntos.setFont(fuente);
        cantPuntos.setHorizontalAlignment(JLabel.CENTER);
        cantPuntos.setText(String.valueOf(c.getPuntosJugador()));
        panel.add(cantPuntos);
        cantPuntos.setBounds(832 * anchoPantalla / 1024, 50 * altoPantalla / 720, tamFuente * 6, tamFuente);
    }

    
    private void inicializarComponentesVisuales() 
    {
    	crearContenedor();
    	
    	tamanioPantalla();
    	
    	configurarVentana();
    	
    	configurarContenedor();
    	
    	inicializarBarraMenu();

    	dibujarBarraMenu();
    	
    	eventos();
    	
    	submarinoGUI = new SubmarinoGUI(this.correccion, this.vEscalar, c);
    	
    	buqueGUI = new BuqueGUI(this.correccion, this.vEscalar, c);
    	
    	cargasGUI = new CargaProfundidadGUI(this.correccion, this.vEscalar, c);
        
    	inicializarJLabels();
    	
    	dibujarJLabelsIniciales();

        pack();
        setLocationRelativeTo(null);
    }                       

    
    // FINAL DE DECLARACION DE METODOS
    
    
    
    // INICIO DE DECLARACION DE METODO DE EVENTOS
    
    
    
    // FINAL DE DECLARACION DE METODO DE EVENTOS
    
    
    
    
    
    class ManejoResolucion implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getActionCommand().equalsIgnoreCase("Pantalla completa"))
			{
				tamanioPantalla();
				establecerNuevaResolucion();
			}
			else if(e.getActionCommand().equalsIgnoreCase("1920x1080"))
			{
				anchoPantalla = 1920;
				altoPantalla = 1080;
				establecerNuevaResolucion();
			}
			else if(e.getActionCommand().equalsIgnoreCase("1366x768"))
			{
				anchoPantalla = 1366;
				altoPantalla = 768;
				establecerNuevaResolucion();
			}
			else if(e.getActionCommand().equalsIgnoreCase("1024x720"))
			{
				anchoPantalla = 1024;
				altoPantalla = 720;
				establecerNuevaResolucion();
			}
			else if(e.getActionCommand().equalsIgnoreCase("800x600"))
			{
				anchoPantalla = 800;
				altoPantalla = 600;
				establecerNuevaResolucion();
			}
		}
    	
    }
    
    
    
    class ManejoVolumen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equalsIgnoreCase("Desactivar"))
			{
				musicaFondo.cambiarVolumen(0);
			}
			else if(e.getActionCommand().equalsIgnoreCase("Volumen bajo"))
			{
				musicaFondo.cambiarVolumen(55);
			}
			else if(e.getActionCommand().equalsIgnoreCase("Volumen medio"))
			{
				musicaFondo.cambiarVolumen(75);
			}
			else if(e.getActionCommand().equalsIgnoreCase("Volumen alto"))
			{
				musicaFondo.cambiarVolumen(100);
			}
			else if(e.getActionCommand().equalsIgnoreCase("Activar musica"))
			{
				musicaFondo.continuarLoopeo();
			}
			else if(e.getActionCommand().equalsIgnoreCase("Desactivar musica"))
			{
				musicaFondo.detener();
			}
		}
    	
    }
    
    
    class ManejoTimerMenu implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (c.estaVivo())
			{
				if(e.getActionCommand().equalsIgnoreCase("Reanudar"))
				{
					lblPausa.setVisible(false);
					musicaFondo.continuarLoopeo();
					timer.start();
				}
				else
				{
					lblPausa.setVisible(true);
					musicaFondo.detener();
					timer.stop();
				}
			}
		}
	}
    
    
    class AccionTimer implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			bRedibujar = false;
			actualizar();
		}
	}
    
    
    
    class EventoTeclado implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) 
		{
			int tecla = e.getKeyCode();
			
			if (c.estaVivo())
			{
				if (tecla == 80 || tecla == 27) // P o ESC
				{
					if (timer.isRunning())
					{
						lblPausa.setVisible(true);
						musicaFondo.detener();
						timer.stop();
					}
					else
					{
						lblPausa.setVisible(false);
						musicaFondo.continuarLoopeo();
						timer.start();
					}
				}
				
				else if (timer.isRunning())
				{
					c.moverSubmarino(tecla);
				}
				
			}
		}

		@Override
		public void keyReleased(KeyEvent e){}
    	
    }
    
    
    
    // METODO MAIN


    public static void main(String args[]) 
    {
		new VentanaJuego().setVisible(true);
    }

}
