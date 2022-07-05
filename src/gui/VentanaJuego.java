package gui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
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
import model.BuqueView;
import model.CargaProfundidadView;
import model.SubmarinoView;


public class VentanaJuego extends JFrame {
	
	
    // INICIO DE DECLARACION DE ATRIBUTOS
	
	private static final long serialVersionUID = 3947423717758576393L;
	
	private JPanel panel;
	private JMenuBar BarraMenu;
	private JMenu pausa;
	private JMenu salir;
	private JMenuItem pausar;
	private JMenuItem reanudar;
	private JMenuItem finalizar;
	private JLabel lblPausa;
	private JLabel nivel;
	private JLabel cantNivel;
	private JLabel integridad;
    private JLabel cantIntegridad;
    private JLabel vidas;
    private JLabel cantVidas;
    private JLabel puntos;
    private JLabel cantPuntos;
    private JLabel submarinos[]; //es un array xq hay 2 imagenes ( <- y -> )
    private JLabel buques[];//idem submarino
    private List<JLabel> cargas;
    private JLabel explosion;
    private Timer timer;
    private Controlador c;
    private SubmarinoView submarinoView;
    private BuqueView buqueView;
    private List<CargaProfundidadView> cargasViews;
    private float yEscalar;
    private float xEscalarSubmarino;
    private float xEscalarBuque;
    private JLabel lblgameover;
    private ImageIcon imgCarga;
//    private Sonido sonidoExplosion;
    private Sonido musicaFondo;
    private JMenu volumen;
    private JMenuItem volDesactivado, volBajo, volMedio, volAlto;
    private List<Sonido> sonidosExplosiones;
    private int volumenActual;
    
    // FINAL DE DECLARACION DE ATRIBUTOS
	
    
    
    // CONSTRUCTOR
    public VentanaJuego() 
    {
    	inicializarVariables();
    	
    	inicializarComponentesVisuales();
    	
    	setIconImage(getIconImage());
    	
    	addKeyListener(new EventoTeclado());
    	
    	musicaFondo.iniciarLoopeo();
    	timer.start();
    }
    
    
    long inicioExpl = 0;
    private void actualizar()
    {
    	c.actualizarJuego();
    	
    	// DIBUJO VISIBLE TODAS LAS CANTIDADES.
    	cantNivel.setText(String.valueOf(c.getNivel()));
    	cantIntegridad.setText(String.valueOf(c.getIntegridadCasco()));
    	cantVidas.setText(String.valueOf(c.getVidasJugador()));
    	cantPuntos.setText(String.valueOf(c.getPuntosJugador()));
    	
    	// RECIBO LAS VIEWS PARA PODER DIBUJAR.
    	submarinoView = c.getSubmarinoView();
    	buqueView = c.getBuqueView();
    	cargasViews = c.getCargasViews();
    	
    	//DIBUJO VISIBLE EL SUBMARINO EN SU NUEVA POS.
    	submarinos[0].setBounds((int)(submarinoView.getX() * xEscalarSubmarino), 
    			(int)((submarinoView.getY() * -1) * yEscalar + 180), 125, 43);
    	submarinos[1].setBounds((int)(submarinoView.getX() * xEscalarSubmarino), 
    			(int)((submarinoView.getY() * -1) * yEscalar + 180), 125, 43);
    	
    	//DIBUJO VISIBLE EL BUQUE EN SU NUEVA POS. buqueView.getDireccion() = 0 o 1 depende para donde va
    	buques[buqueView.getDireccion()].setBounds((int)(buqueView.getX() * xEscalarBuque - 115), 150, 125, 47);
//    	buques[buqueView.getDireccion()].setBounds((int)(buqueView.getX() * xEscalarSubmarino), 150, 125, 47);
    	
    	if (cargasViews.size() != cargas.size()) // SI SE CREARON CARGAS NUEVAS.
    	{
    		int j = cargasViews.size() - cargas.size();
    		for (int i = 0; i < j; i++) // ITERO LA CANTIDAD DE CARGAS NUEVAS CREADAS.
    		{
    			JLabel lbl = new JLabel();
    			lbl.setIcon(imgCarga);
    			
    			cargas.add(lbl); // CREO LA NUEVA CARGA Y LA DIBUJO INVISIBLE.
    			
    			int k = cargas.size() - 1; // ENCUENTRO EL INDICE EN Cargas DE LA NUEVA CARGA CREADA Y LO ANIADO AL CONTENEDOR.
    			panel.add(cargas.get(k));
    		}
    	}
    	
    	for (int i = cargasViews.size() - 1; i >= 0; i--) // RECORRO TODAS LAS CARGAS EXISTENTES.
    	{
    		if (cargasViews.get(i).estaExplotada()) // SI LA CARGA ESTA EXPLOTADA.
    		{
    			cargas.get(i).setVisible(false); // DESAPAREZCO LA CARGA.
    			
    			// DIBUJO LA EXPLOSION.
    			inicioExpl = System.currentTimeMillis();
    			explosion.setVisible(true);
//    			explosion.setBounds((int)(cargasViews.get(i).getX() * xEscalarSubmarino) - 70, 
//    					(int)((cargasViews.get(i).getY() * -1) * yEscalar + 125),
//    					168, 100);
    			explosion.setBounds((int)(cargasViews.get(i).getX() * xEscalarSubmarino) - 70, 
    					(int)((cargasViews.get(i).getY() * -1) * yEscalar + 125),
    					200, 200);
//    			if (!sonidoExplosion.termino())
//				sonidoExplosion.detener();
//    			sonidoExplosion.reproducir();
    			
//    			Sonido expl = new Sonido(Sonido.explosion);
//    	    	expl.cambiarVolumen(volumenActual);
//    	    	expl.reproducir();
//    	    	sonidosExplosiones.add(expl);
    			sonidosExplosiones.add(reproducirSonidoExplosion());
    	    	System.out.println("Size lista explosiones: " +sonidosExplosiones.size());
    			
    			panel.remove(cargas.get(i)); // REMUEVO LA CARGA.
    			cargas.remove(i);
    		}
    		
    		else if (cargasViews.get(i).estaSoltada()) // SI LA CARGA NO ESTA EXPLOTADA, PERO SI SOLTADA.
    		{
    			cargas.get(i).setVisible(true); // DIBUJO VISIBLE LA CARGA EN SU NUEVA POSICION.
    			cargas.get(i).setBounds((int)(cargasViews.get(i).getX() * xEscalarSubmarino), 
    					(int)((cargasViews.get(i).getY() * -1) * yEscalar + 180),
    					32, 32);
    		}
    	}
    	
    	// CUMPLIDO EL TIEMPO, DESAPAREZCO EL DIBUJO DE EXPLOSION.
    	long finExpl = System.currentTimeMillis();
    	if (finExpl - inicioExpl >= 250)
    	{
    		explosion.setVisible(false);
    	}
    	
    	// SI FINALIZO EL SONIDO DE EXPLOSION, LO REMUEVO DE LA LISTA.
    	for (int i = sonidosExplosiones.size() - 1; i >= 0; i--)
    	{
    		if (sonidosExplosiones.get(i).isFinalizado())
    			sonidosExplosiones.remove(i);
    	}
    	
    	
    	
    	// SI EL JUGADOR NO TIENE VIDAS NI INTEGRIDAD.
    	if (!c.estaVivo())
    	{
    		timer.stop();
    		lblgameover.setVisible(true); // DETENGO EL TIEMPO Y DIBUJO GAME OVER.
    		lblgameover.setBounds(129, 100, 720, 631);
    	}
    }
    
    
    private Sonido reproducirSonidoExplosion()
    {
    	Sonido expl = new Sonido("/sonidos/explosion.wav");
    	expl.cambiarVolumen(volumenActual);
    	expl.reproducir();
    	return expl;
    }
    
    // INICIO DE DECLARACION DE METODOS
    
    private void crearContenedor()
    {
    	panel = new JPanel(){

			private static final long serialVersionUID = 2013056688719049185L;

			public void paintComponent(Graphics g)
            {
                ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondo/fondo.png"));
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
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
        
        submarinos = new JLabel[2];
        buques = new JLabel[2];
        explosion = new JLabel();
    }
    
    
    private void cargarImagenes()
    {
    	lblgameover.setIcon(new ImageIcon(getClass().getResource("/imagenes/estados/gameover.png")));
    	
    	explosion.setIcon(new ImageIcon(getClass().getResource("/imagenes/carga/explosion_200.png")));
    	
    	lblPausa.setIcon(new ImageIcon(getClass().getResource("/imagenes/estados/labelpausa.png")));
    	
    	for (int i = 0; i < submarinos.length; i++)
    	{
    		submarinos[i] = new JLabel();
    		submarinos[i].setIcon(new ImageIcon(getClass().getResource("/imagenes/submarino/submarino"+i+".png")));
    	}
    	
    	for (int i = 0; i < buques.length; i++)
    	{
    		buques[i] = new JLabel();
    		buques[i].setIcon(new ImageIcon(getClass().getResource("/imagenes/buque/buque"+i+".png")));
    	}
    	
    	imgCarga = new ImageIcon(getClass().getResource("/imagenes/carga/carga32.png"));
    }
    
    
    @Override
    public Image getIconImage()
    {
    	Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/icono/icono.png"));
    	return icono;
    }
    
    
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
    }
    
    
    private void configurarVentana()
    {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Submarine Attack");
        setPreferredSize(new Dimension(1000, 1000));
        setSize(new Dimension(1000, 1000));
        setResizable(false);
        getContentPane().setLayout(null);
        
        panel.setPreferredSize(new Dimension(1000, 931));
        panel.setLayout(null);
    }
    
    
    private void dibujarJLabelsIniciales()
    {
    	panel.add(lblPausa);
    	lblPausa.setBounds(240, 300, 500, 285);
    	lblPausa.setVisible(false);
    	
    	panel.add(lblgameover);
    	
    	panel.add(explosion);
    	
    	panel.add(buques[0]);
    	panel.add(buques[1]);
    	
    	panel.add(submarinos[0]);
        panel.add(submarinos[1]);
        submarinos[1].setVisible(false);
    	
    	
        Font fuente = cargarFuente("/fuentes/PressStart2P-Regular.ttf", 0, 20);
        
        // PARA WIDTH Y HEIGHT: 20px ES LO QUE OCUPA UN SOLO CARACTER DE ALTO/ANCHO. (ya que la letra esta en 20)
        nivel.setFont(fuente);
        nivel.setText("NIVEL");
        panel.add(nivel);
        nivel.setBounds(50, 20, 100, 20);
        
        cantNivel.setFont(fuente);
        cantNivel.setHorizontalAlignment(JLabel.CENTER);
        cantNivel.setText(String.valueOf(c.getNivel()));
        panel.add(cantNivel);
        cantNivel.setBounds(65, 50, 60, 20);
        
        integridad.setFont(fuente);
        integridad.setText("INTEGRIDAD");
        panel.add(integridad);
        integridad.setBounds(260, 20, 200, 20);
        
        cantIntegridad.setFont(fuente);
        cantIntegridad.setHorizontalAlignment(JLabel.CENTER);
        cantIntegridad.setText(String.valueOf(c.getIntegridadCasco()));
        panel.add(cantIntegridad);
        cantIntegridad.setBounds(330, 50, 60, 20);
        
        vidas.setFont(fuente);
        vidas.setText("VIDAS");
        panel.add(vidas);
        vidas.setBounds(590, 20, 100, 20);
        
        cantVidas.setFont(fuente);
        cantVidas.setHorizontalAlignment(JLabel.CENTER);
        cantVidas.setText(String.valueOf(c.getVidasJugador()));
        panel.add(cantVidas);
        cantVidas.setBounds(610, 50, 60, 20);

        puntos.setFont(fuente);
        puntos.setText("PUNTOS");
        panel.add(puntos);
        puntos.setBounds(830, 20, 120, 20);

        cantPuntos.setFont(fuente);
        cantPuntos.setHorizontalAlignment(JLabel.CENTER);
        cantPuntos.setText(String.valueOf(c.getPuntosJugador()));
        panel.add(cantPuntos);
        cantPuntos.setBounds(821, 50, 140, 20);
        
        // ANIADIR EL CONTENEDOR A LA VENTANA PARA QUE CARGUE CORRECTAMENTE LOS JLABELS.
        getContentPane().add(panel);
        panel.setBounds(0, 0, 1000, 931);
    }
    
    
    private void dibujarBarraMenu()
    {
    	BarraMenu.setPreferredSize(new Dimension(152, 30));
    	
    	Font fuenteMenu = new Font("Press Start 2P", 0, 12);
    	Font fuenteItem = new Font("Press Start 2P", 0, 10);
    	
    	volumen.setText("Volumen");
    	volumen.setFont(fuenteMenu);
    	volumen.setMargin(new Insets(6, 6, 6, 10)); //MARGENES
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
    
    
    private void inicializarVariables()
    {
    	c = new Controlador();
    	timer = new Timer(24, new AccionTimer());
    	
    	cargas = new ArrayList<JLabel>();
    	
    	yEscalar =  (790f - 150) / 750;
    	xEscalarBuque = ((975f - (-115)) / 150);
    	xEscalarSubmarino = (860f / 150);
    	
    	volumenActual = 75;
    	
    	musicaFondo = new Sonido(Sonido.musicaFondo);
    	musicaFondo.cambiarVolumen(volumenActual);
//    	sonidoExplosion = new Sonido(Sonido.explosion);
//    	sonidoExplosion.cambiarVolumen(volumenActual);
    	
    	sonidosExplosiones = new ArrayList<Sonido>();
    	
    	// VOLUMEN MINIMO TENDRIA QUE SER 50, MAS BAJO NO SE LLEGA A ESCUCHAR
    	// VOLUMEN MEDIO 75
    	// VOLUMEN MAXIMO 100
    }
    
    
    private void inicializarComponentesVisuales() 
    {
    	crearContenedor();
        
    	inicializarJLabels();
    	
    	cargarImagenes();
        
    	inicializarBarraMenu();

    	configurarVentana();

    	dibujarJLabelsIniciales();

    	dibujarBarraMenu();
    	
    	eventos();

        pack();
//        setLocationRelativeTo(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setLocation(((int)width - this.getWidth())/2, ((int)height - this.getHeight())/2);
    }                       

    // FINAL DE DECLARACION DE METODOS
    
    
    
    // INICIO DE DECLARACION DE METODO DE EVENTOS
    
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
	}
    
    // FINAL DE DECLARACION DE METODO DE EVENTOS
    
    
    
    /*
     * 
     * 
     * 
     * INICIO DE DECLARACION DE CLASES
     * 
     * 
     * 
    */ 
    
    class ManejoVolumen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equalsIgnoreCase("Desactivar"))
			{
				musicaFondo.cambiarVolumen(0);
//				sonidoExplosion.cambiarVolumen(0);
				volumenActual = 0;
			}
			else if(e.getActionCommand().equalsIgnoreCase("Volumen bajo"))
			{
				musicaFondo.cambiarVolumen(55);
//				sonidoExplosion.cambiarVolumen(50);
				volumenActual = 50;
			}
			else if(e.getActionCommand().equalsIgnoreCase("Volumen medio"))
			{
				musicaFondo.cambiarVolumen(0);
//				sonidoExplosion.cambiarVolumen(75);
				volumenActual = 75;
			}
			else if(e.getActionCommand().equalsIgnoreCase("Volumen alto"))
			{
				musicaFondo.cambiarVolumen(0);
//				sonidoExplosion.cambiarVolumen(100);
				volumenActual = 100;
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
			actualizar();
		}
	}
    
    
    class EventoTeclado implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) 
		{

			// INICIO CODIGO DE PRUEBA: QUITAR
//			System.out.println(e.getKeyCode());
			int tecla = e.getKeyCode();
			
			if (c.estaVivo())
			{
//				if (tecla == 32) // ESPACIO
//				{
////					new VentanaJuego().setVisible(true); // REINICIAR JUEGO
//				}
//				
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
					if (tecla == 68 || tecla == 39) // D o FlechaDerecha
					{
						submarinos[0].setVisible(true);
						submarinos[1].setVisible(false);
					}
					else if (tecla == 65 || tecla == 37) // A o FlechaIzquierda
					{
						submarinos[0].setVisible(false);
						submarinos[1].setVisible(true);
					}
					c.recibirEntradaTeclado(tecla);
				}
				
			}
			// FIN CODIGO DE PRUEBA: QUITAR
		}

		@Override
		public void keyReleased(KeyEvent e){}
    	
    }
    
    /* 
     * 
     * 
     * 
     * FINAL DE DECLARACION DE CLASES
     * 
     * 
     * 
     * 
    */
    
    
    // METODO MAIN
    public static void main(String args[]) 
    {
		new VentanaJuego().setVisible(true);
    }

}
