package gui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;


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
    private JLabel carga;
    private Timer timer;
    private int intNivel;
    private int intIntegridad;
    private int intVidas;
    private int intPuntos;
    
    // FINAL DE DECLARACION DE ATRIBUTOS
	
    
    
    // CONSTRUCTOR
    public VentanaJuego() 
    {
    	inicializarComponentesVisuales();
    	
    	addKeyListener(new EventoTeclado());
    	
    	// INICIO CODIGO DE PRUEBA: QUITAR
    	timer.start();
    	
    	panel.add(buques[0]);
    	buques[0].setBounds(10, 150, 125, 47);
    	panel.add(carga);
    	carga.setBounds(46, 300, 32, 32);
    	// FIN DE CODIGO DE PRUEBA: QUITAR
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
        carga = new JLabel();
    }
    
    
    private void cargarImagenes()
    {
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
    	
    	carga.setIcon(new ImageIcon(getClass().getResource("/imagenes/carga/carga32.png")));
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
    	panel.add(lblPausa);
    	lblPausa.setBounds(250, 300, 500, 285);
    	lblPausa.setVisible(false);
    	
        panel.add(submarinos[0]);
        submarinos[0].setBounds(x, y, 125, 43);
        panel.add(submarinos[1]);
        submarinos[1].setBounds(x, y, 125, 43);
        submarinos[1].setVisible(false);
        
        Font fuente = new Font("Press Start 2P", 0, 20);
        
        // PARA WIDTH Y HEIGHT: 20px ES LO QUE OCUPA UN SOLO CARACTER DE ALTO/ANCHO. (ya que la letra esta en 20)
        nivel.setFont(fuente);
        nivel.setText("NIVEL");
        panel.add(nivel);
        nivel.setBounds(50, 20, 100, 20);
        
        cantNivel.setFont(fuente);
        cantNivel.setHorizontalAlignment(JLabel.CENTER);
        cantNivel.setText(String.valueOf(intNivel));
        panel.add(cantNivel);
        cantNivel.setBounds(65, 50, 60, 20);
        
        integridad.setFont(fuente);
        integridad.setText("INTEGRIDAD");
        panel.add(integridad);
        integridad.setBounds(260, 20, 200, 20);
        
        cantIntegridad.setFont(fuente);
        cantIntegridad.setHorizontalAlignment(JLabel.CENTER);
        cantIntegridad.setText(String.valueOf(intIntegridad));
        panel.add(cantIntegridad);
        cantIntegridad.setBounds(330, 50, 60, 20);
        
        vidas.setFont(fuente);
        vidas.setText("VIDAS");
        panel.add(vidas);
        vidas.setBounds(590, 20, 100, 20);
        
        cantVidas.setFont(fuente);
        cantVidas.setHorizontalAlignment(JLabel.CENTER);
        cantVidas.setText(String.valueOf(intVidas));
        panel.add(cantVidas);
        cantVidas.setBounds(610, 50, 60, 20);

        puntos.setFont(fuente);
        puntos.setText("PUNTOS");
        panel.add(puntos);
        puntos.setBounds(830, 20, 120, 20);

        cantPuntos.setFont(fuente);
        cantPuntos.setHorizontalAlignment(JLabel.CENTER);
        cantPuntos.setText(String.valueOf(intPuntos));
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
    
    
    private void inicializarComponentesVisuales() 
    {
    	// DESPUES LA INICIALIZACION DE VARIABLES SE VA A PASAR A UN METODO
    	timer = new Timer(1000, new AccionTimer());
    	intNivel = 1;
    	intIntegridad = 100;
    	intVidas = 3;
    	intPuntos = 0;
    	// FIN DE INICIALIZACION DE VARIABLES

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
			intPuntos++;
			cantPuntos.setText(String.valueOf(intPuntos));
			// FIN CODIGO DE PRUEBA: QUITAR
		}
	}
    
    
    int x = 430;
    int y = 530;
    class EventoTeclado implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			
			// INICIO CODIGO DE PRUEBA: QUITAR
//			System.out.println(e.getKeyCode());
			int tecla = e.getKeyCode();
			
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
				if (tecla == 87 || tecla == 38) // W o FlechaArriba
				{
					y -= 10;
				}
				else if (tecla == 83 || tecla == 40) // S o FlechaAbajo
				{
					y += 10;
				}
				else if (tecla == 68 || tecla == 39) // D o FlechaDerecha
				{
					submarinos[0].setVisible(true);
					submarinos[1].setVisible(false);
					x += 10;
				}
				else if (tecla == 65 || tecla == 37) // A o FlechaIzquierda
				{
					submarinos[0].setVisible(false);
					submarinos[1].setVisible(true);
					x -= 10;
				}
				
				submarinos[0].setBounds(x, y, 125, 43);
				submarinos[1].setBounds(x, y, 125, 43);
			}
			// FIN CODIGO DE PRUEBA: QUITAR
		}
    	
    }
    
    // FINAL DE DECLARACION DE CLASES
    
    
    
    // METODO MAIN
    public static void main(String args[]) 
    {
    	new VentanaJuego().setVisible(true);
    }

}
