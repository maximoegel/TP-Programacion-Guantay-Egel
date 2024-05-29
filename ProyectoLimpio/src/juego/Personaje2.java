package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje2 {
	double x;
	double y;
	double velocidad;
	double escala;
	Image izq;
	Image der;
	Image up;
	Image quietoDer;
	Image quietoIzq;
	Image caidaDer;
	Image caidaIzq;
	Image downDer;
	Image downIzq;
	boolean direccion; 
	boolean estaApoyado;
	boolean saltando;
	boolean enMovimiento;
	boolean enCaida;
	boolean estaDisparando;
	boolean agachado; 
	int contadorDisparo; 
	int contadorSalto;

	Personaje2(double x, double y){
		this.x=x;
		this.y=y;
		this.velocidad=2.0;
		this.escala=0.32;
		izq = Herramientas.cargarImagen("sonicIzq.gif");
		der = Herramientas.cargarImagen("sonicDer.gif");
		up = Herramientas.cargarImagen("jump.gif");
		downDer = Herramientas.cargarImagen("abajoDer.gif");
		downIzq = Herramientas.cargarImagen("abajoIzq.gif");
		quietoDer = Herramientas.cargarImagen("quietDer.gif");
		quietoIzq = Herramientas.cargarImagen("quietIzq.gif");
		caidaDer = Herramientas.cargarImagen("fallDer.gif");
		caidaIzq = Herramientas.cargarImagen("fallIzq.gif");
		this.direccion=false;
		this.estaApoyado=false;
		this.saltando=false;
		this.enMovimiento=false;
		this.enCaida=false;
		this.estaDisparando=false;
		this.agachado = false;
		this.contadorSalto=0;
		this.contadorDisparo=0;
	}

	public void mover(Entorno e) {
		enMovimiento = true;
		if(estaApoyado || saltando || enCaida) {
			this.x +=this.direccion?-this.velocidad:this.velocidad;
			if(this.x > e.ancho()-this.getAncho()/2) {
				this.x=e.ancho()-this.getAncho()/2;
			}
			if(this.x < this.getAncho()/2) {
				this.x=this.getAncho()/2;
			}
		}
	}
	
	public void detener() {
		enMovimiento = false;
	}
	
	public void saltar() {
		if(estaApoyado) {
			saltando = true;
			estaApoyado = false;
			contadorSalto = 0;
		}
	}
	
	public void caerSubir(Entorno e) {
		if(!this.estaApoyado && !saltando) {
			this.y += 3.5;				  
			enCaida = true;
		}
		else {
			enCaida = false;		
		}
		if(saltando) {
			this.y -=7;
			this.contadorSalto++;
		}
		if(this.contadorSalto > 18){
			saltando=false;
			this.contadorSalto=0;
		}
	}
	
	public void disparar () {	
		estaDisparando = true;
		contadorDisparo = 15;
	}
	
	public void actualizarDisparo () {	
		if (estaDisparando) {			
			contadorDisparo--;			
			if (contadorDisparo <= 0) { 
				estaDisparando = false;
			}
		}
	}
	
	public void agachado() {
		if (!agachado) {
			double alturaVieja = this.getAlto();
	        
	        Image agachar = direccion ? this.downIzq : this.downDer;
	        double nuevaAltura = agachar.getHeight(null) * this.escala; 
	        this.y += (alturaVieja - nuevaAltura) / 2;
			agachado = true;
		}
	}
	
	public void parado() {
		if (agachado) {
	        double alturaVieja = this.getAlto(); 
	        
	        double nuevaAltura = izq.getHeight(null) * this.escala; 
	        this.y -= (nuevaAltura - alturaVieja) / 2;
			agachado = false;
		}
	}
	public void dibujar(Entorno e) {
		if (saltando) {
			e.dibujarImagen(this.up, this.x, this.y, 0, this.escala);
		}
		else if (enCaida) {
			if (direccion) {
				e.dibujarImagen(this.caidaIzq, this.x, this.y, 0, this.escala);
			}
			else {
				e.dibujarImagen(this.caidaDer, this.x, this.y, 0, this.escala);
			}
		}
		else if (agachado) {
			if (direccion) {
				e.dibujarImagen(this.downIzq, this.x, this.y, 0, this.escala);
			}
			else {
				e.dibujarImagen(this.downDer, this.x, this.y, 0, this.escala);
			}
		}
		else if (enMovimiento) {
			if (direccion) {
				e.dibujarImagen(this.izq, this.x, this.y, 0, this.escala);  
			}																
			else {															
				e.dibujarImagen(this.der, this.x, this.y, 0, this.escala);
			}
		}
		else {
			if (direccion) {
				e.dibujarImagen(this.quietoIzq, this.x, this.y, 0, this.escala);
			}
			else {
				e.dibujarImagen(this.quietoDer, this.x, this.y, 0, this.escala);
			}
		}
	}
	
	public double getAncho() {
		Image foto = agachado ? (direccion ? downIzq : downDer) : izq;
		return izq.getWidth(null)*this.escala;
	}
	
	public double getAlto() {
		if (agachado) {
			return (direccion ? downIzq : downDer).getHeight(null)*this.escala;
		}
		else {
			return izq.getHeight(null)*this.escala;
		}
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
