package Model;

public class Buque extends ObjetosJuego {

	public Buque() {
	}

	private float xSoltar;
	private float xDesaparecer;
	private CargaProfundidad cargaProfunidad;

	public Buque(int velocidad, int altura, int largo, Coordenada c, CargaProfundidad cp) {
		// OJ
		this.velocidad = velocidad; // TODO multiplicar x -1 depende donde aparece?
		this.altura = altura;
		this.largo = largo;
		this.coordenada = c;
		// Buque
		añadirCarga(cp);
		setSoltar();
		setDesaparecer();
	}

	// Getters
	private boolean tieneCarga() {
		return this.cargaProfunidad != null;
	}

	// Setters
	private void setSoltar() {
		// TODO numero random dentro del gamearea dependiendo de donde aparece
		this.xSoltar = 0.0f;
	}

	private void setDesaparecer() {
		// TODO xMax o xMin depende donde aparece
		this.xDesaparecer = 0.0f;
	}

	public void moverX(float deltaTiempo) {
		float distancia = deltaTiempo * this.velocidad * ObjetosJuego.velocidadMultiplicador;

		this.coordenada.moverX(distancia);
		if (this.tieneCarga()) {
			this.cargaProfunidad.moverConBuque(distancia);
			//chequeo si tiene que soltar la carga
			float xActual = this.coordenada.getX();
			if ((xActual == this.xSoltar) // si justo esta en el punto
					|| (xActual - distancia < this.xSoltar && xActual > this.xSoltar) // si pasó el punto yendo de izquierda a derecha (velocidad es + => distancia es +)
					|| (xActual - distancia > this.xSoltar && xActual < this.xSoltar))// si pasó el punto yendo de derecha a izquierda (velocidad es - => distancia es -)
				soltarCarga();
		}
		if (this.coordenada.getX() >= this.xDesaparecer) {
			//TODO evento desaparecer = desaparecer(); lo hace el controlador
		}
	}

	private void soltarCarga() {
		this.cargaProfunidad.soltar();
		this.cargaProfunidad = null;
	}

	public void añadirCarga(CargaProfundidad carga) {
		this.cargaProfunidad = carga;
		this.cargaProfunidad.añadir(this);
		return;
	}

}