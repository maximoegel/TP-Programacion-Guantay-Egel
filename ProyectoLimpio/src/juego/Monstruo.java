package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

	public class Monstruo {
		double x;
		double y;
		double velocidad;
		double escala;
		Image izq;
		Image der;
		Image cae;
		boolean direccion; 
		boolean estaApoyado;
		boolean seMueve;
		boolean cayendo;
		boolean estaBomba; 
		boolean seQuema;
		int contadorBomba;
		Bomba bomba;
	
		Monstruo(double x, double y){
			this.x=x;
			this.y=y;
			this.velocidad=2.0;
			this.escala=0.1;
			izq=Herramientas.cargarImagen("villano2.gif");
			der=Herramientas.cargarImagen("villano.gif");
			cae=Herramientas.cargarImagen("caidaVillano.gif");
			this.direccion=false;
			this.estaApoyado=false;
			this.seMueve=false;
			this.cayendo=false;
			this.estaBomba=false;
			this.seQuema=false;
			this.contadorBomba = 0;
		}
	
		public void mover(Entorno e) {
			seMueve = true;
			if(estaApoyado) {
				this.x +=this.direccion?-this.velocidad:this.velocidad;
				if(this.x > e.ancho()-this.getAncho()/2) {
					this.x=e.ancho()-this.getAncho()/2;
					this.direccion = !this.direccion;
				}
				if(this.x < this.getAncho()/2) {
					this.x=this.getAncho()/2;
					this.direccion = !this.direccion; 
				}
			}
			this.contadorBomba++;
			
			if(this.contadorBomba >= 200) {
				disparar();
				this.contadorBomba = 0; 
			}
			if(bomba != null) {
				bomba.mover();
				bomba.dibujarBomba(e);
				if(bomba.seFue(e)) {
					bomba = null;
				}
			}
		}
		
		public void disparar() {
			bomba = new Bomba(this.x, this.y, 5, this.direccion);
		}
		
		public void detener() {
			seMueve = false;
		}
		
		public void caerSubir(Entorno e) {
			if(!this.estaApoyado) {
				this.y += 3.5;
				cayendo = true;
			}
			else {
				cayendo = false;
			}
		}
	
		public void dibujar(Entorno e) {
			if (cayendo) {
				e.dibujarImagen(this.cae, this.x, this.y, 0, this.escala);
			}
			else if (seMueve) {
				if(direccion) {
					e.dibujarImagen(this.izq, this.x, this.y, 0, this.escala);
				}
				else {
					e.dibujarImagen(this.der, this.x, this.y, 0, this.escala);
				}
			}
		}
		public double getAncho() {
			return izq.getWidth(null)*this.escala;
		}
		public double getAlto() {
			return izq.getHeight(null)*this.escala;
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
		public double getX() {
			return this.x;
		}
		public double getY() {
			return this.y;
		}
}
