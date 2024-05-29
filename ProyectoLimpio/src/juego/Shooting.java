package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Shooting {
	double x;
	double y;
	double velocidad;
	double escala;
	Image tiroDer;
	Image tiroIzq;
	boolean direccion; 

	Shooting (double x, double y, double velocidad, boolean direccion) {
		this.x = x;				
		this.y = y;			     
		this.velocidad = velocidad;
		this.direccion = direccion;
		this.escala = 0.1;
		tiroDer = Herramientas.cargarImagen("balaDer.gif");
		tiroIzq = Herramientas.cargarImagen("balaIzq.gif");
	}
	
	public void dibujarShooting (Entorno e) {
		if (direccion) {
			e.dibujarImagen(this.tiroIzq, x, y, this.escala);
		}
		else {
			e.dibujarImagen(this.tiroDer, x, y, this.escala);
		}
	}																	 
	
	public void mover() {
		if (direccion) {
			this.x -= velocidad; 
		}						 
		else {
			this.x += velocidad; 
		}
	}
	
	public boolean seFue (Entorno entorno) {		
		return this.x < 0 || this.x > entorno.ancho();
	}
	
	public double getAncho() {
		return tiroIzq.getWidth(null)*this.escala;
	}
	public double getAlto() {
		return tiroIzq.getHeight(null)*this.escala;
	}
	public double getTecho() {
		return this.y-this.getAlto()/2;

	}
	public double getPiso() {
		return this.y+this.getAlto()/2;
	}

	public double getIzquierda() {
		return this.x-this.getAncho()/2;

	}
	public double getDerecha() {
		return this.x+this.getAncho()/2;
	}
}
