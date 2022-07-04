package gui;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sonido {
	
	public static Clip musicaFondo = CargarSonido.cargarSonido("/sonidos/fondo.wav");
	public static Clip explosion = CargarSonido.cargarSonido("/sonidos/explosion.wav");
	
	private Clip sonido; 
	private FloatControl volumen;
	
	
	public Sonido(Clip sonido)
	{
		this.sonido = sonido;
		this.volumen = (FloatControl)sonido.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	
	public Sonido(String ubicacionSonido)
	{
		this.sonido = CargarSonido.cargarSonido(ubicacionSonido);
		this.volumen = (FloatControl)sonido.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	
	public void reproducir()
	{
		sonido.setFramePosition(0);
		sonido.start();
	}
	
	public void detener()
	{
		sonido.stop();
	}
	
	public void iniciarLoopeo()
	{
		sonido.setFramePosition(0);
		sonido.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void continuarLoopeo()
	{
		sonido.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public boolean isFinalizado()
	{
		return sonido.isRunning();
	}
	
	public void cambiarVolumen(float porcentajeVolumen)
	{
		float deltaPorcentaje = (volumen.getMaximum() - volumen.getMinimum()) / 100;
		float cantVolumen = deltaPorcentaje * porcentajeVolumen;
		volumen.setValue(volumen.getMinimum() + cantVolumen);
	}
	
	
	static class CargarSonido{
		
		public static Clip cargarSonido(String ubicacionSonido)
		{
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(Sonido.class.getResource(ubicacionSonido)));
				return clip;
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				System.err.println("No se cargo el sonido en:" +ubicacionSonido);
			}
			return null;
		}
	}
	
}
