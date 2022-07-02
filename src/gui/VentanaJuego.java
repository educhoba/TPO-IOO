package gui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
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
    private JLabel submarinos[];
    private JLabel buques[];
    private List<JLabel> cargas;
    private JLabel explosion;
    private Timer timer;
    private Timer timerExplosion;
    private Controlador c;
    private SubmarinoView submarinoView;
    private BuqueView buqueView;
    private List<CargaProfundidadView> cargasViews;
    private float yEscalar;
    private float xEscalarSubmarino;
    private float xEscalarBuque;
    private float xEscalarCarga;
    private int direccJugador;
    private ImageIcon imgExplosion;
    private JLabel lblgameover;
    
    // FINAL DE DECLARACION DE ATRIBUTOS
	
    
    
    // CONSTRUCTOR
    public VentanaJuego() 
    {
    	inicializarVariables();
    	
    	inicializarComponentesVisuales();
    	
    	addKeyListener(new EventoTeclado());
    	
    	// INICIO CODIGO DE PRUEBA: QUITAR
    	timer.start();
    	
//    	panel.add(buques[0]);
//    	buques[0].setBounds(0, 150, 125, 47);
//    	panel.add(carga);
//    	carga.setBounds(47, 300, 32, 32);
    	// FIN DE CODIGO DE PRUEBA: QUITAR
    }
    
    private void finalizarJuego()
    {
    }
    
    
    long inicioExpl = 0;
    private void actualizar()
    {
    	
    	c.actualizarJuego();
    	
    	cantNivel.setText(String.valueOf(c.getNivel()));
    	cantIntegridad.setText(String.valueOf(c.getIntegridadCasco()));
    	cantVidas.setText(String.valueOf(c.getVidasJugador()));
    	cantPuntos.setText(String.valueOf(c.getPuntosJugador()));
    	
    	submarinoView = c.getSubmarinoView();
    	buqueView = c.getBuqueView();
    	cargasViews = c.getCargasViews();
    	
    	submarinos[direccJugador].setBounds((int)(submarinoView.getX() * xEscalarSubmarino), 
    			(int)((submarinoView.getY() * -1) * yEscalar + 180), 125, 43);
    	
    	buques[buqueView.getDireccion()].setBounds((int)(buqueView.getX() * xEscalarBuque - 115), 150, 125, 47);
    	
    	if (cargasViews.size() != cargas.size())
    	{
    		int j = cargasViews.size() - cargas.size();
    		for (int i = 0; i < j; i++)
    		{
    			JLabel lbl = new JLabel();
    			lbl.setIcon(new ImageIcon(getClass().getResource("/imagenes/carga/carga32.png")));
    			
    			cargas.add(lbl);
    			
    			int k = cargas.size() - 1;
    			panel.add(cargas.get(k));
    		}
    	}
    	
    	
    	for (int i = cargasViews.size() - 1; i >= 0; i--)
    	{
    		if (cargasViews.get(i).estaExplotada())
    		{
    			cargas.get(i).setVisible(false);
    			inicioExpl = System.currentTimeMillis();
    			explosion.setVisible(true);
    			explosion.setBounds((int)(cargasViews.get(i).getX() * xEscalarSubmarino) - 70, 
    					(int)((cargasViews.get(i).getY() * -1) * yEscalar + 125),
    					168, 100);
    			panel.remove(cargas.get(i));
    			cargas.remove(i);
    		}
    		else if (cargasViews.get(i).estaSoltada())
    		{
    			cargas.get(i).setVisible(true);
    			cargas.get(i).setBounds((int)(cargasViews.get(i).getX() * xEscalarSubmarino), 
    					(int)((cargasViews.get(i).getY() * -1) * yEscalar + 180),
    					32, 32);
    		}
    	}
    	
    	long finExpl = System.currentTimeMillis();
    	if (finExpl - inicioExpl >= 250)
    	{
    		explosion.setVisible(false);
    	}
    	
    	if (c.getVidasJugador() <= 0 && c.getIntegridadCasco() <= 0)
    	{
    		timer.stop();
    		lblgameover.setVisible(true);
    		lblgameover.setBounds(129, 100, 720, 631);
    		
    	}
    		
    		
    	
    	
//    	intPuntos++;
//		cantPuntos.setText(String.valueOf(intPuntos));
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
    	lblgameover.setIcon(new ImageIcon(getClass().getResource("/imagenes/estados/gameover2.png")));
    	
    	explosion.setIcon(new ImageIcon(getClass().getResource("/imagenes/carga/explosion.png")));
    	
    	imgExplosion = new ImageIcon(getClass().getResource("/imagenes/carga/explosion.png"));
    	
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
    	
//    	carga.setIcon(new ImageIcon(getClass().getResource("/imagenes/carga/carga32.png")));
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
        
        panel.setPreferredSize(new Dimension(1000, 931));
        panel.setLayout(null);
    }
    
    
    private void dibujarJLabelsIniciales()
    {
    	panel.add(lblgameover);
    	
    	panel.add(explosion);
    	
    	panel.add(buques[0]);
    	panel.add(buques[1]);
    	
    	System.out.println("width" +panel.getWidth());
    	System.out.println("height" +panel.getHeight());
    	
    	panel.add(lblPausa);
    	lblPausa.setBounds(240, 300, 500, 285);
    	lblPausa.setVisible(false);
    	
        panel.add(submarinos[0]);
        submarinos[0].setBounds(x, y, 125, 43);
//        submarinos[0].setBounds((int)(submarinoView.getX() * xEscalarSubmarino), 
//    			(int)((submarinoView.getY() * -1) * yEscalar + 180), 125, 43);
        panel.add(submarinos[1]);
        submarinos[1].setBounds(x, y, 125, 43);
//        submarinos[1].setBounds((int)(submarinoView.getX() * xEscalarSubmarino), 
//    			(int)((submarinoView.getY() * -1) * yEscalar + 180), 125, 43);
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
        
        // ANIADIR EL CONTENEDOR A LA VENTANA PARA QUE CARGUE CORRECTAMENTE LOS JLABELS.
        getContentPane().add(panel);
        panel.setBounds(0, 0, 1000, 931);
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
    	timer = new Timer(1, new AccionTimer());
    	timerExplosion = new Timer(1000, new AccionTimer());
    	
    	cargas = new ArrayList<JLabel>();
    	
    	yEscalar =  (790f - 150) / 800;
    	xEscalarBuque = ((975f - (-115)) / 150);
    	xEscalarCarga = ((956f - (-4)) / 150);
    	xEscalarSubmarino = (860f / 150);
    	
    	
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
        setLocationRelativeTo(null);
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
	}
    
    // FINAL DE DECLARACION DE METODO DE EVENTOS
    
    
    
    // INICIO DE DECLARACION DE CLASES
    
    class ManejoTimerMenu implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
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
    
    
    // ESTA CLASE NO SE UTILIZA PORQUE NO FUNCIONA
    class ManejoTimerTeclado implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (timer.isRunning())
				timer.stop();
			else
				timer.start();
		}
    }
    
    
    class AccionTimer implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// INICIO CODIGO DE PRUEBA: QUITAR
			actualizar();
//			intPuntos++;
//			cantPuntos.setText(String.valueOf(intPuntos));
			// FIN CODIGO DE PRUEBA: QUITAR
		}
	}
    
    
    int x = 0;
    int y = 790;
    class EventoTeclado implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) 
		{

			// INICIO CODIGO DE PRUEBA: QUITAR
			System.out.println(e.getKeyCode());
			int tecla = e.getKeyCode();
			
			if (tecla == 32)
			{
				
				new VentanaJuego().setVisible(true);
				finalizarJuego();
				
			}
			
			else if (tecla == 78)
			{
				lblgameover.setVisible(true);
	    		lblgameover.setBounds(129, 100, 720, 631);
			}
			
			else if (tecla == 80 || tecla == 27) // P o ESC
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
				if (tecla == 87 || tecla == 38) // W o FlechaArriba
				{
					c.recibirEntradaTeclado(tecla);
//					y -= 5;
				}
				else if (tecla == 83 || tecla == 40) // S o FlechaAbajo
				{
					c.recibirEntradaTeclado(tecla);
//					y += 5;
				}
				else if (tecla == 68 || tecla == 39) // D o FlechaDerecha
				{
					c.recibirEntradaTeclado(tecla);
					submarinos[0].setVisible(true);
					submarinos[1].setVisible(false);
					direccJugador = 0;
//					x += 5;
				}
				else if (tecla == 65 || tecla == 37) // A o FlechaIzquierda
				{
					c.recibirEntradaTeclado(tecla);
					direccJugador = 1;
					submarinos[0].setVisible(false);
					submarinos[1].setVisible(true);
//					x -= 5;
				}
				
//				submarinos[0].setBounds(x, y, 125, 43);
//				submarinos[1].setBounds(x, y, 125, 43);
				
				submarinos[direccJugador].setBounds((int)(submarinoView.getX() * xEscalarSubmarino), 
		    			(int)((submarinoView.getY() * -1) * yEscalar + 180), 125, 43);
			}
			// FIN CODIGO DE PRUEBA: QUITAR
		}

		@Override
		public void keyReleased(KeyEvent e){}
    	
    }
    
    // FINAL DE DECLARACION DE CLASES
    
    
    
    // METODO MAIN
    public static void main(String args[]) 
    {
    	new VentanaJuego().setVisible(true);
    }

}
