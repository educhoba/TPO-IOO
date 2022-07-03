package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

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
    private int xResolucion = 1;
    private int yResolucion = 1;
    private int y0 = 0;
    private int x0 = 0;
    private int xEscalar = 1;
    private int yEscalar = 1;
    private int rangoExplosionX = 100;
    private int rangoExplosionY = 100;
    private JLabel lblgameover;
    private ImageIcon imgCarga;
    
    // FINAL DE DECLARACION DE ATRIBUTOS
	
    JLabel gameArea;
    boolean testArea = true;
    
    
    // CONSTRUCTOR
    public VentanaJuego() 
    {
    	inicializarVariables();
    	
    	inicializarComponentesVisuales();
    	
    	setIconImage(getIconImage());
    	
    	addKeyListener(new EventoTeclado());
    	
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
    	submarinos[0].setBounds(x0 + (submarinoView.getX() -submarinoView.getLargo()/2)*xEscalar, y0 + (-submarinoView.getY() - submarinoView.getAlto()/2 ), 
    							submarinoView.getLargo()*xEscalar, submarinoView.getAlto());
    	submarinos[1].setBounds(x0 + (submarinoView.getX() -submarinoView.getLargo()/2)*xEscalar, y0 + (-submarinoView.getY() - submarinoView.getAlto()/2), 
    							submarinoView.getLargo()*xEscalar, submarinoView.getAlto());
    	
    	
    	//DIBUJO VISIBLE EL BUQUE EN SU NUEVA POS. buqueView.getDireccion() = 0 o 1 depende para donde va
    	buques[buqueView.getDireccion()].setBounds(x0 + (buqueView.getX())*xEscalar, y0 + buqueView.getY(), 1*xEscalar, 1); //125, 47
    	
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
    			
    			explosion.setBounds(x0 + (cargasViews.get(i).getX()*xEscalar) - (rangoExplosionX*xEscalar) , 
    					y0 -cargasViews.get(i).getY() - rangoExplosionY,
    					rangoExplosionX*2*xEscalar, rangoExplosionY*2);
    			
    			panel.remove(cargas.get(i)); // REMUEVO LA CARGA.
    			cargas.remove(i);
    		}
    		else if (cargasViews.get(i).estaSoltada()) // SI LA CARGA NO ESTA EXPLOTADA, PERO SI SOLTADA.
    		{
    			cargas.get(i).setVisible(true); // DIBUJO VISIBLE LA CARGA EN SU NUEVA POSICION.
    			cargas.get(i).setBounds(x0 + cargasViews.get(i).getX()*xEscalar, 
    					y0 - cargasViews.get(i).getY(),
    					1, 1);
    		}
    	}
    	
    	// CUMPLIDO EL TIEMPO, DESAPAREZCO EL DIBUJO DE EXPLOSION.
    	long finExpl = System.currentTimeMillis();
    	if (finExpl - inicioExpl >= 250)
    	{
    		explosion.setVisible(false);
    	}
    	
    	// SI EL JUGADOR NO TIENE VIDAS NI INTEGRIDAD.
    	if (!c.estaVivo())
    	{
    		timer.stop();
    		lblgameover.setVisible(true); // DETENGO EL TIEMPO Y DIBUJO GAME OVER.
    		lblgameover.setBounds(129, 100, 720, 631);
    	}
    	
    	panel.add(gameArea);
    }
    
    
    
    // INICIO DE DECLARACION DE METODOS
    
    private void crearContenedor()
    {
    	panel = new JPanel(){

			private static final long serialVersionUID = 2013056688719049185L;

//			public void paintComponent(Graphics g)
//            {
//                ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondo/fondo.png"));
//                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
//            }
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
        
        explosion = new JLabel();
        //Un JLabel para cada dirección
        
        submarinos = new JLabel[2];
    	for (int i = 0; i < submarinos.length; i++)
    	{
    		submarinos[i] = new JLabel();
    	}
    	
        buques = new JLabel[2];
    	for (int i = 0; i < buques.length; i++)
    	{
    		buques[i] = new JLabel();
    	}
    	
    	gameArea = new JLabel();
    }
    
    
    private void cargarImagenes()
    {
    	lblgameover.setIcon(new ImageIcon(getClass().getResource("/imagenes/estados/gameover2.png")));
    	
    	explosion.setIcon(new ImageIcon(getClass().getResource("/imagenes/carga/explosionBurbuja.png")));
    	
    	lblPausa.setIcon(new ImageIcon(getClass().getResource("/imagenes/estados/labelpausa.png")));
    	
    	for (int i = 0; i < submarinos.length; i++)
    	{
    		submarinos[i].setIcon(new ImageIcon(getClass().getResource("/imagenes/submarino/submarino"+i+".png")));
    	}
    	
    	for (int i = 0; i < buques.length; i++)
    	{
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
    
    
    private void inicializarBarraMenu()
    {
    	BarraMenu = new JMenuBar();
        pausa = new JMenu();
        pausar = new JMenuItem();
        reanudar = new JMenuItem();
        salir = new JMenu();
        finalizar = new JMenuItem();
    }
    
    
    private void configurarVentana()
    {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Submarine Attack");
        setPreferredSize(new Dimension(1000, 1000));
        setSize(new Dimension(1000, 1000));
        setResizable(false);
        getContentPane().setLayout(null);
        
        panel.setPreferredSize(new Dimension(1000, 1000));
        panel.setLayout(null);

//	    xResolucion = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//	    yResolucion = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    	
    	yResolucion = getHeight();
    	xResolucion = getWidth();
    	
    	yEscalar =  yResolucion / -c.getYMax();
    	//xEscalar =  xResolucion / c.getXMax();
    	xEscalar =  1;
    	
    	x0 = (int) (xResolucion/2 - c.getXMax()*xEscalar/2);
    	
    	y0 = yResolucion/2 + c.getYMax()/2;
//    	xEscalarBuque = ((975f - (-115)) / 150);
//    	xEscalarSubmarino = (860f / 150);
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
    	
    	
        Font fuente = new Font("Press Start 2P", 0, 20);
        
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

    	if(testArea) {
    		panel.add(gameArea);
        	gameArea.setBounds(x0, y0, (int) (c.getXMax()*xEscalar), -c.getYMax());
    	}
    	
        // ANIADIR EL CONTENEDOR A LA VENTANA PARA QUE CARGUE CORRECTAMENTE LOS JLABELS.
        getContentPane().add(panel);
        panel.setBounds(0, 0, xResolucion, yResolucion);
    }
    
    
    private void dibujarBarraMenu()
    {
    	BarraMenu.setPreferredSize(new Dimension(152, 30));
    	
    	Font fuenteMenu = new Font("Press Start 2P", 0, 12);
    	Font fuenteItem = new Font("Press Start 2P", 0, 10);

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


    }
    
    
    private void inicializarComponentesVisuales() 
    {
    	crearContenedor();
        
    	inicializarJLabels();
    	
    	if(!testArea)
    		cargarImagenes();
    	else
    		cargarBGs();
    	
    	inicializarBarraMenu();

    	configurarVentana();

    	dibujarJLabelsIniciales();

    	dibujarBarraMenu();
    	
    	eventos();

        pack();
        setLocationRelativeTo(null);
    }                       

    // FINAL DE DECLARACION DE METODOS
    
    
    
    // INICIO DE DECLARACION DE METODO DE EVENTOS
    
    private void cargarBGs() {
    		String imgPath = "/100x100.png";

        	gameArea.setIcon(new ImageIcon(getClass().getResource("/1000x1000.png")));
        	
    		lblgameover.setIcon(new ImageIcon(getClass().getResource(imgPath)));
        	
        	explosion.setIcon(new ImageIcon(getClass().getResource("/200x200circle.png")));
        	
        	lblPausa.setIcon(new ImageIcon(getClass().getResource(imgPath)));
        	
        	for (int i = 0; i < submarinos.length; i++)
        	{
        		submarinos[i].setIcon(new ImageIcon(getClass().getResource(imgPath)));
        	}
        	
        	for (int i = 0; i < buques.length; i++)
        	{
        		buques[i].setIcon(new ImageIcon(getClass().getResource(imgPath)));
        	}
        	
        	imgCarga = new ImageIcon(getClass().getResource(imgPath));
	}


    private void setImageIcon(JLabel lbl, String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
	} catch (IOException e) {
		e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(1, 1,
		        Image.SCALE_SMOOTH);
		
		lbl.setIcon(new ImageIcon(dimg));
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
    
    class ManejoTimerMenu implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (c.estaVivo())
			{
				if(e.getActionCommand().equalsIgnoreCase("Reanudar"))
				{
					lblPausa.setVisible(false);
					timer.start();
				}
				else
				{
					lblPausa.setVisible(true);
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
						timer.stop();
					}
					else
					{
						lblPausa.setVisible(false);
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
