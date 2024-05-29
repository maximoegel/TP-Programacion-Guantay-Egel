package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	double x;
	double y;
	double escala;
	boolean rompible;
	Image barra;
	Image barra2;

	public Bloque(double x, double y) {
		this.x=x;
		this.y=y;
		this.escala=0.222;
		this.rompible=true;
		if(Math.random()> 0.5) {
			this.rompible=false;
		}
		barra=Herramientas.cargarImagen("bloquemario2.jpg");
		barra2=Herramientas.cargarImagen("bloqueminecraft.jpg");
	}

	public void dibujar(Entorno e) {
		if(rompible) {
			e.dibujarImagen(this.barra, this.x, this.y, 0, this.escala);
		}
		else {
			e.dibujarImagen(this.barra2, this.x, this.y, 0, this.escala);
		}

	}

	public double getAlto() {
		return this.barra.getHeight(null)*this.escala;
	}
	public double getAncho() {
		return this.barra.getWidth(null)*this.escala;
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
