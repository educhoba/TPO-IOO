package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
import java.net.URL;
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
    private int y0 = 110;
    private int x0 = 0;
    private int heightMin = 0;
    private int widthMin = 0;
    private int widthMax = 0;
    private float xEscalar = 1;
    private float yEscalar = 1;
    private int rangoExplosionX = 100;
    private int rangoExplosionY = 100;
    private JLabel lblgameover;
    private ImageIcon imgCarga;
    private int heightBuqueImg = 47;
    private int widthBuqueImg = 125;
    private int ultimaResolucionX = 0;
    private int ultimaResolucionY = 0;
	private int altoBarraVentana = 39;
	private int anchoBordeVentana = 19;
    private boolean recargarResolucion = false;
    // FINAL DE DECLARACION DE ATRIBUTOS
	
    JLabel gameArea;
    boolean modoTest = true;
    
    
    // CONSTRUCTOR
    public VentanaJuego() 
    {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Submarine Attack");
        //setResizable(false);
        getContentPane().setLayout(null);
        
    	inicializarVariables();
    	
    	inicializarComponentesVisuales();

    	heightMin = -c.getYMax() + BarraMenu.getHeight() + altoBarraVentana;
        widthMin = c.getXMax() + anchoBordeVentana;

        
    	setPreferredSize(new Dimension(widthMin, heightMin));
        setMinimumSize(new Dimension(widthMin, heightMin));
        setSize(new Dimension(widthMin, heightMin));
    	
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = (int)screenSize.getWidth();
        int alto = (int)screenSize.getHeight();

        int anchoVentana = getWidth();
        int altoVentana = getHeight();
        //this.setLocation(0,0);
        this.setLocation(ancho/2 - anchoVentana/2, alto/2 - altoVentana/2);
        
    	setIconImage(getIconImage());

    	
    	addKeyListener(new EventoTeclado());
    	
    	timer.start();
    }
    
    
    long inicioExpl = 0;
    private void actualizar()
    {
    	c.actualizarJuego();
    	recargarResolucion = ultimaResolucionX != getWidth() || ultimaResolucionY != getHeight();
    	if(recargarResolucion){
    		cargarResolucionNueva();
    	}
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
    	setBoundsSubmarino(submarinos[0],submarinoView);
    	setBoundsSubmarino(submarinos[1],submarinoView);
		if(modoTest) {
	    	setImageIcon(recargarResolucion, submarinos[0],"/1x1.png");
	    	setImageIcon(recargarResolucion, submarinos[1],"/1x1.png");
		}
		else {
			setImageIcon(recargarResolucion, submarinos[0],"/imagenes/submarino/submarino0.png");
			setImageIcon(recargarResolucion, submarinos[1],"/imagenes/submarino/submarino1.png");
		}
			
    	//DIBUJO VISIBLE EL BUQUE EN SU NUEVA POS. buqueView.getDireccion() = 0 o 1 depende para donde va

		if(buqueView.getDireccion() == 0) {
			setBoundsBuque(buques[0],buqueView);
			setBoundsBuqueInNarnia(buques[1]);
		}
		else
		{
			setBoundsBuque(buques[1],buqueView);
			setBoundsBuqueInNarnia(buques[0]);
		}
		
    	if(modoTest) {
    		setImageIcon(recargarResolucion, buques[0],"/1x1.png");
    		setImageIcon(recargarResolucion, buques[1],"/1x1.png");
    	}
    	else
    	{
    		setImageIcon(recargarResolucion, buques[0],"/imagenes/buque/buque0.png");
    		setImageIcon(recargarResolucion, buques[1],"/imagenes/buque/buque1.png");
    	}
    	
		//if(buqueView.getDireccion() == 0) {
		//	setBoundsBuque(buques[0],buqueView);
    	//	setImageIcon(recargarResolucion, buques[1],"/1x1.png");
		//}
		//else {
		//	setBoundsBuque(buques[1],buqueView);
    	//	setImageIcon(recargarResolucion, buques[0],"/1x1.png");
		//}
		

		
//    	buques[buqueView.getDireccion()].setBounds((int)(buqueView.getX() * xEscalarSubmarino), 150, 125, 47);
    	
    	if (cargasViews.size() != cargas.size()) // SI SE CREARON CARGAS NUEVAS.
    	{
    		int j = cargasViews.size() - cargas.size();
    		for (int i = 0; i < j; i++) // ITERO LA CANTIDAD DE CARGAS NUEVAS CREADAS.
    		{
    			JLabel lbl = new JLabel();

    			
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
    			
    			explosion.setBounds(
    					(int)(x0 + (cargasViews.get(i).getX()*xEscalar) - rangoExplosionX*xEscalar), 
    					(int)(y0 -cargasViews.get(i).getY()* yEscalar - rangoExplosionY* yEscalar),
    					(int)(rangoExplosionX*2*xEscalar),
    					(int)(rangoExplosionY*2* yEscalar));
    			if(modoTest) 
    				setImageIcon(true, explosion,"/200x200circle.png");
    			else
    				setImageIcon(true, explosion,"/imagenes/carga/explosionBurbuja.png");
    			
    			
    			panel.remove(cargas.get(i)); // REMUEVO LA CARGA.
    			cargas.remove(i);
    		}
    		else if (cargasViews.get(i).estaSoltada()) // SI LA CARGA NO ESTA EXPLOTADA, PERO SI SOLTADA.
    		{
    			cargas.get(i).setVisible(true); // DIBUJO VISIBLE LA CARGA EN SU NUEVA POSICION.
    			cargas.get(i).setBounds(
    					(int)(x0 + cargasViews.get(i).getX()*xEscalar), 
    							(int)(y0 - cargasViews.get(i).getY()* yEscalar),
    					1,
    					1);
    			
    			if(modoTest) 
        			setImageIcon(true, cargas.get(i),"/1x1.png"); 
    			else
    			{
    				cargas.get(i).setBounds(cargas.get(i).getX(), cargas.get(i).getY(),
        					32,32);
        			cargas.get(i).setIcon(imgCarga);
    			}
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
    		lblgameover.setBounds(xResolucion/2 -(150*xResolucion)/2, yResolucion/2 -(600*yResolucion)/2, 150*xResolucion, 600*yResolucion);
			setImageIcon(recargarResolucion, lblgameover,"imagenes\\estados\\gameover.png");
    	}
    	
    	panel.add(gameArea);
    }
    
    private void setBoundsBuqueInNarnia(JLabel jLabel) {
    	jLabel.setBounds(-widthBuqueImg,-heightBuqueImg,widthBuqueImg,heightBuqueImg); //125largo, 47ancho
	}

	private void setBoundsBuque(JLabel jLabel, BuqueView buqueView2) {
    	jLabel.setBounds(
    			(int)(x0 + (buqueView.getX())*xEscalar),
    			(int)(y0 + buqueView.getY()* yEscalar), 
    			modoTest?1:widthBuqueImg,
    			modoTest?1:heightBuqueImg); //125largo, 47ancho
	}

	private void setBoundsSubmarino(JLabel jLabel, SubmarinoView submarinoView2) {

    	submarinoView = submarinoView2;
    	int largo = (int)(submarinoView.getLargo()* xEscalar);
    	int alto = (int)(submarinoView.getAlto()* yEscalar);
    	
    	jLabel.setBounds(
    			(int)(x0 + (submarinoView.getX() -submarinoView.getLargo()/2)* xEscalar),
    			(int)(y0 + (-submarinoView.getY() - submarinoView.getAlto()/2 )* yEscalar), 
    			largo <= 0 ? 1: largo,
    			alto <= 0 ? 1: alto);
		
	}

	// INICIO DE DECLARACION DE METODOS
    
    private void crearContenedor()
    {
    	panel = new JPanel(){

			private static final long serialVersionUID = 2013056688719049185L;

			public void paintComponent(Graphics g)
            {
				if(modoTest) {
		               ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondo/fondo2.png"));
		                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
				}
				else {
		               ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondo/fondo.png"));
		                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
				}
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
    
    
    private void cargarResolucionNueva() {
    	configurarVentana();
    	dibujarJLabelsIniciales();
        cargarBGs();
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
    	ultimaResolucionY = yResolucion = getHeight();
    	ultimaResolucionX = xResolucion = getWidth();
        //setPreferredSize(new Dimension(xResolucion, yResolucion));
    	setPreferredSize(new Dimension(xResolucion, yResolucion));

        panel.setPreferredSize(new Dimension(xResolucion, yResolucion));
        panel.setLayout(null);

    	yEscalar =  (float)yResolucion / (float)heightMin;
    	//hay un desfasaje raro en el ancho
    	xEscalar =  (float)xResolucion / ((float)c.getXMax() + anchoBordeVentana/((float)xResolucion / (float)c.getXMax()));
    	
    	//x0 = xResolucion/2 - c.getXMax()*xEscalar/2;
    	
    	//y0 = (yResolucion-BarraMenu.getHeight()-altoBarraVentana)/2 + c.getYMax()*yEscalar/2;
    }
    
    
    private void dibujarJLabelsIniciales()
    {
    	panel.add(lblPausa);
    	int lblPausaWidth = 100;
    	int lblPausaHeight= 100;
    	lblPausa.setBounds(
    			(int)(xResolucion/2 -lblPausaWidth*xEscalar/2),
    			(int)(yResolucion/2-lblPausaHeight*yEscalar/2),
    			(int)(lblPausaWidth*xEscalar),
    			(int)(lblPausaHeight*yEscalar));
    	lblPausa.setVisible(false);
    	
    	panel.add(lblgameover);
    	
    	panel.add(explosion);
    	panel.add(buques[0]);
    	panel.add(buques[1]);
    	
    	panel.add(submarinos[0]);
        panel.add(submarinos[1]);
        submarinos[1].setVisible(false);
    	
        Font fuente = new Font("Press Start 2P", 0, (int)(18+yEscalar+xEscalar));
        
        int cantPaneles = 4;
        int divisionLbls =  xResolucion / cantPaneles;
        int yTexto = 20;
        int yPuntos= 60;
        int alturaTextos = 20;
        int margenTextos = 20;
        // PARA WIDTH Y HEIGHT: 20px ES LO QUE OCUPA UN SOLO CARACTER DE ALTO/ANCHO. (ya que la letra esta en 20)
        nivel.setFont(fuente);
        nivel.setText("NIVEL");
        panel.add(nivel);
        nivel.setBounds(x0 + margenTextos + divisionLbls * 0, yTexto, 100, alturaTextos);
        
        cantNivel.setFont(fuente);
        cantNivel.setHorizontalAlignment(JLabel.CENTER);
        cantNivel.setText(String.valueOf(c.getNivel()));
        panel.add(cantNivel);
        cantNivel.setBounds(x0 + margenTextos  + divisionLbls * 0, yPuntos, 60, alturaTextos);
        
        integridad.setFont(fuente);
        integridad.setText("INTEGRIDAD");
        panel.add(integridad);
        integridad.setBounds(x0 + margenTextos + divisionLbls * 1, yTexto, 200, alturaTextos);
        
        cantIntegridad.setFont(fuente);
        cantIntegridad.setHorizontalAlignment(JLabel.CENTER);
        cantIntegridad.setText(String.valueOf(c.getIntegridadCasco()));
        panel.add(cantIntegridad);
        cantIntegridad.setBounds(x0 + margenTextos + divisionLbls * 1, yPuntos, 60, alturaTextos);
        
        vidas.setFont(fuente);
        vidas.setText("VIDAS");
        panel.add(vidas);
        vidas.setBounds(x0 + margenTextos + divisionLbls * 2, yTexto, 100, alturaTextos);
        
        cantVidas.setFont(fuente);
        cantVidas.setHorizontalAlignment(JLabel.CENTER);
        cantVidas.setText(String.valueOf(c.getVidasJugador()));
        panel.add(cantVidas);
        cantVidas.setBounds(x0 + margenTextos  + divisionLbls * 2, yPuntos, 60, alturaTextos);

        puntos.setFont(fuente);
        puntos.setText("PUNTOS");
        panel.add(puntos);
        puntos.setBounds(x0 + margenTextos + divisionLbls * 3, yTexto, 120, alturaTextos);

        cantPuntos.setFont(fuente);
        cantPuntos.setHorizontalAlignment(JLabel.CENTER);
        cantPuntos.setText(String.valueOf(c.getPuntosJugador()));
        panel.add(cantPuntos);
        cantPuntos.setBounds(x0 + margenTextos + divisionLbls * 3, yPuntos, 140, alturaTextos);

    	if(modoTest) {
    		panel.add(gameArea);
        	gameArea.setBounds(
        			x0, 
        			y0, 
        			(int)(widthMin * xEscalar), 
        			(int)(heightMin * yEscalar));
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
    	
    	inicializarBarraMenu();

    	dibujarBarraMenu();
    	
    	eventos();

        pack();
        
        //setLocationRelativeTo(null);
        
    }                       

    // FINAL DE DECLARACION DE METODOS
    
    
    
    // INICIO DE DECLARACION DE METODO DE EVENTOS
    
    private void cargarBGs() {
    		//setImageIcon(true, gameArea, "1000x1000.png");
    		imgCarga = new ImageIcon(getClass().getResource("/imagenes/carga/carga32.png"));
    		setImageIcon(true, lblPausa, "/imagenes/estados/labelpausa.png");
	}


    private void setImageIcon(boolean recargar, JLabel lbl, String path) {
    	if(recargar) {
    		BufferedImage img = null;
    		try {
    			String newPath = getClass().getResource(path).getFile();
    			img = ImageIO.read(new File(newPath));
    			if(lbl.getWidth() > 0 && lbl.getHeight() > 0) {
    				
    				if(lbl.getWidth() < 1) ;
        			Image dimg = img.getScaledInstance(lbl.getWidth() < 1? 1: lbl.getWidth(), lbl.getHeight() < 1? 1: lbl.getHeight(),
        			        Image.SCALE_SMOOTH);
        			
        			lbl.setIcon(new ImageIcon(dimg));
    			}
    	} catch (IOException e) {
    		e.printStackTrace();
    		}
    	}
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
