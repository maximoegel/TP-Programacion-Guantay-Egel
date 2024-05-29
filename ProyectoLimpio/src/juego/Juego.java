package juego;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	Personaje claudio;
	Personaje2 claudio2;
	Piso[] piso;
	ArrayList<Monstruo> enemigos;;
	Shooting disparo;
	FondoDelJuego fondo;
	Perdiste perdio;
	Victoria ganaste;
	int enemigosEliminados;
	int puntaje;
	int vidasClaudio;
	int vidasClaudio2; 
	Juego() {
		this.entorno = new Entorno(this, "Demo en clase", 800, 600);
		this.perdio = new Perdiste();
		this.fondo = new FondoDelJuego();
		this.ganaste = new Victoria();
		claudio=new Personaje(400.0, 520.0);
		claudio2=new Personaje2(400.0, 520); 
		vidasClaudio = 3;
		vidasClaudio2 = 3;
		
		piso=new Piso[5];
		for(int i=0;i < piso.length;i++) {
			piso[i]=new Piso(120*(i+0.5)+50);
		}	
		enemigos = new ArrayList<>();	
		generarEnemigos(8);

		this.entorno.iniciar();
		}
	
	public void generarEnemigos(int cantidad) { 
		for (int i = 0; i < 8; i++) {
			double x = Math.random() * entorno.ancho();
			double y = Math.random() * entorno.alto() * 0.5;
			enemigos.add(new Monstruo(x, y)); 
		}
	}
	public void tick() {
		fondo.dibujarFondo(entorno);
		if (claudio == null || claudio2 == null) {
			perdio.GameOver(entorno);
			return;
		}
		if (claudio.getY() < 0 && claudio2.getY() < 0) {
			ganaste.ganaste(entorno);
			return;
		}		
		boolean seMovio = false;
		boolean seMovio2 = false;
		
		
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			claudio.direccion=false;
			claudio.mover(entorno);
			seMovio = true;
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			claudio.direccion=true;
			claudio.mover(entorno);
			seMovio = true;
		}
		if(entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			claudio.agachado();
		}
		else {
			claudio.parado();
		}
		if(entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			claudio.saltar();
			seMovio = true;
		}
		if(entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			if (disparo == null && !claudio.enCaida && !claudio.agachado) {												   
				disparo = new Shooting(claudio.getX(), claudio.getY(), 5, claudio.direccion);
				claudio.disparar();
			}
		}
		if (disparo != null) {
			disparo.dibujarShooting(entorno);	
			disparo.mover();					
			if (disparo.seFue(entorno)) {		
				disparo = null;					
				claudio.estaDisparando = false;
			}
			claudio.actualizarDisparo();
		}
		if(!seMovio) {
			claudio.detener();
		}
		claudio.caerSubir(entorno);

		
		claudio.dibujar(entorno);
		for(Piso p : piso) {
			p.dibujar(entorno);
		}
		if(detectarApoyo(claudio,piso)) {
			claudio.estaApoyado=true;
		}
		else {
			claudio.estaApoyado=false;
			seMovio = true;
		}
		if(detectarColision(claudio,piso)) {
			claudio.saltando=false;
			claudio.contadorSalto=0;
		}
		
		
		if (entorno.estaPresionada('D')) {
			claudio2.direccion = false;
			claudio2.mover(entorno);
			seMovio2 = true;
		}
		if (entorno.estaPresionada('A')) {
			claudio2.direccion = true;
			claudio2.mover(entorno);
			seMovio2 = true;
		}
		if (entorno.estaPresionada('S')) {
			claudio2.agachado();
		}
		else {
			claudio2.parado();
		}
		if (entorno.sePresiono('W')) {
			claudio2.saltar();
			seMovio2 = true;
		}
		if (entorno.sePresiono('Q')) {
			if (disparo == null && !claudio2.enCaida && !claudio2.agachado) {
				disparo = new Shooting(claudio2.getX(), claudio2.getY(), 5, claudio2.direccion);
				claudio2.disparar();
			}
		}
		if (!seMovio2) {
			claudio2.detener();
		}
		claudio2.caerSubir(entorno);
		
		claudio2.dibujar(entorno);
		for (Piso p : piso) {
			p.dibujar(entorno);
		}
		
		if (detectarApoyo(claudio2, piso)) {
			claudio2.estaApoyado = true;
		}
		else {
			claudio2.estaApoyado = false;
			seMovio2 = true;
		}
		if (detectarColision(claudio2, piso)) {
			claudio2.saltando = false;
			claudio2.contadorSalto = 0;
		}
		
		Iterator<Monstruo> it = enemigos.iterator();   
													   
		
		while (it.hasNext()) {						   
		   Monstruo enemigo = it.next();        	   
		    if (enemigo != null) {					   
		    	enemigo.mover(entorno);
		        enemigo.dibujar(entorno);
		        enemigo.caerSubir(entorno);

		        if (detectarApoyo(enemigo, piso)) {
		            enemigo.estaApoyado = true;
		        } else {
		            enemigo.estaApoyado = false;
		        }
		        if (detectarColision(claudio, enemigo)) {
		            vidasClaudio--;
		            if (vidasClaudio > 0) {
		            	claudio = new Personaje(400.0, 520.0);
		            }
		            else {
		            	claudio = null;
		            }
		        }
				if (detectarColision(claudio2, enemigo)) {
					vidasClaudio2--;
					if (vidasClaudio2 > 0) {
						claudio2 = new Personaje2(400.0, 520.0);
					}
					else {
						claudio2 = null;
					}
				}
		        if (enemigo.bomba != null && detectarColision(claudio, enemigo.bomba)) {
	                vidasClaudio--;
	                if (vidasClaudio > 0) {
	                	claudio = new Personaje(400.0, 520.0);
	                }
	                else {
	                	claudio = null;
	                }
		        }
		        if (enemigo.bomba != null && detectarColision(claudio2, enemigo.bomba)) {
		        	vidasClaudio2--;
		        	if(vidasClaudio > 0) {
		        		claudio2 = new Personaje2(400.0, 520.0);
		        	}
		        	else {
		        		claudio2 = null;
		        	}
		        }
		        if (disparo != null && enemigo.bomba != null && detectarColision(enemigo.bomba, disparo)) {
		            disparo = null;
		            enemigo.bomba = null;
		            claudio.estaDisparando = false;
		            break;
		        }
		        if (disparo != null && detectarColision(disparo, enemigo)) {
		            disparo = null;
		            claudio.estaDisparando = false;
		            it.remove();
		            enemigosEliminados ++;
		            puntaje += 2;
		            break;
		        }
		    }
		}
        
		entorno.cambiarFont("Consolas", 12, Color.WHITE);  							 
        entorno.escribirTexto("Enemigos eliminados: " + enemigosEliminados, 5, 15); 
        entorno.escribirTexto("Puntaje: " + puntaje, 5, 30);
        entorno.cambiarFont("Consolas", 12, Color.RED);
        entorno.escribirTexto("Claudio: " + vidasClaudio, 720, 15);
        entorno.escribirTexto("Claudio2: " + vidasClaudio2, 713, 30);
	    if (enemigos.isEmpty()) {
	    	generarEnemigos(8);
	    }
	}
	

	
	public boolean detectarColision(Personaje p, Monstruo m) {
	    return p != null && m != null &&
	           Math.abs(p.getX() - m.getX()) < p.getAncho() / 2 + m.getAncho() / 2 &&
	           Math.abs(p.getY() - m.getY()) < p.getAlto() / 2 + m.getAlto() / 2;
	}
	
	public boolean detectarColision(Personaje2 p, Monstruo m) {
	    return p != null && m != null &&
	           Math.abs(p.getX() - m.getX()) < p.getAncho() / 2 + m.getAncho() / 2 &&
	           Math.abs(p.getY() - m.getY()) < p.getAlto() / 2 + m.getAlto() / 2;
	}
	
	public boolean detectarColision(Shooting disparo, Monstruo enemigo) {
	    return disparo != null && enemigo != null &&
	           Math.abs(disparo.x - enemigo.x) < disparo.getAncho() / 2 + enemigo.getAncho() / 2 &&
	           Math.abs(disparo.y - enemigo.y) < disparo.getAlto() / 2 + enemigo.getAlto() / 2;
	}
	
	public boolean detectarColision(Bomba bala, Shooting disparo) {
	    return Math.abs(bala.x - disparo.x) < (bala.getAncho() + disparo.getAncho()) / 2 &&
	           Math.abs(bala.y - disparo.y) < (bala.getAlto() + disparo.getAlto()) / 2;
	}
	
	public boolean detectarColision(Personaje p, Bomba b) {
	    return b != null &&            
	    		Math.abs(p.getX() - b.getX()) < p.getAncho() / 2 + b.getAncho() / 2 &&
	            Math.abs(p.getY() - b.getY()) < p.getAlto() / 2 + b.getAlto() / 2;
	}
	
	public boolean detectarColision(Personaje2 p, Bomba b) {
	    return b != null &&            
	    		Math.abs(p.getX() - b.getX()) < p.getAncho() / 2 + b.getAncho() / 2 &&
	            Math.abs(p.getY() - b.getY()) < p.getAlto() / 2 + b.getAlto() / 2;
	}
	public boolean detectarApoyo(Monstruo m, Bloque b) {
		return (Math.abs(m.getPiso() - b.getTecho()) < 2) &&
			   (m.getDerecha() > b.getIzquierda()) &&
			   (m.getIzquierda() < b.getDerecha());
	}
		
	public boolean detectarApoyo(Monstruo m, Piso so) {
		for (int i = 0; i < so.piso.length; i++) {
			if (so.piso[i] != null && detectarApoyo(m, so.piso[i])) {
				return true;
			}
		}
		return false;
	}
		
	public boolean detectarApoyo(Monstruo m, Piso[] l) {
		for (int i = 0; i < l.length; i++) {
			if (detectarApoyo(m, l[i])) {
				return true;
			}
		}
		return false;
	}
		
	public boolean detectarColision(Monstruo m, Bloque b) {
		return (Math.abs(m.getPiso() - b.getTecho()) < 8) &&
			   (m.getDerecha() > b.getIzquierda()) &&
			   (m.getIzquierda() < b.getDerecha());
	}
		
	public boolean detectarColision(Monstruo m, Piso so) {
		for (int i = 0; i < so.piso.length; i++) {
			if(so.piso[i] != null && detectarColision(m, so.piso[i])) {
				return true;
			}							
		}								
		return false;
	}
		
	public boolean detectarColision(Monstruo m, Piso[] l) {
		for (int i = 0; i < l.length; i++) {
			if (detectarColision(m, l[i])) {
				return true;
			}
		}
		return false;
	}
	
		
	public boolean detectarApoyo(Personaje p, Bloque b) {
		return (Math.abs(p.getPiso() - b.getTecho()) < 2) &&
			   (p.getDerecha() > b.getIzquierda()) && 
			   (p.getIzquierda() < b.getDerecha());
	}
	
	public boolean detectarApoyo(Personaje2 p, Bloque b) {
		return (Math.abs(p.getPiso() - b.getTecho()) < 2) &&
			   (p.getDerecha() > b.getIzquierda()) && 
			   (p.getIzquierda() < b.getDerecha());
	}

	public boolean detectarApoyo(Personaje p, Piso so)	{
		for (int i=0; i < so.piso.length; i++) {
			if(so.piso[i] != null && detectarApoyo(p, so.piso[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarApoyo(Personaje2 p, Piso so)	{
		for (int i=0; i < so.piso.length; i++) {
			if(so.piso[i] != null && detectarApoyo(p, so.piso[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean detectarApoyo(Personaje p, Piso[] l) {
		for (int i=0; i < l.length; i++) {
			if(detectarApoyo(p, l[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarApoyo(Personaje2 p, Piso[] l) {
		for (int i=0; i < l.length; i++) {
			if(detectarApoyo(p, l[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarColision(Personaje p, Bloque b) {
		return (Math.abs(p.getTecho() - b.getPiso()) < 5) &&
			   (p.getDerecha() > b.getIzquierda() ) && 
			   (p.getIzquierda() < b.getDerecha());
	}
	
	public boolean detectarColision(Personaje2 p, Bloque b) {
		return (Math.abs(p.getTecho() - b.getPiso()) < 5) &&
			   (p.getDerecha() > b.getIzquierda() ) && 
			   (p.getIzquierda() < b.getDerecha());
	}
	
	public boolean detectarColision(Personaje p, Piso so)	{
		for (int i=0; i < so.piso.length; i++) {
			if(so.piso[i] != null && detectarColision(p, so.piso[i])) {
				if(so.piso[i].rompible) {
					so.piso[i]=null;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarColision(Personaje2 p, Piso so)	{
		for (int i=0; i < so.piso.length; i++) {
			if(so.piso[i] != null && detectarColision(p, so.piso[i])) {
				if(so.piso[i].rompible) {
					so.piso[i]=null;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarColision(Personaje p, Piso[] l) {
		for (int i=0; i < l.length; i++) {
			if(detectarColision(p, l[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarColision(Personaje2 p, Piso[] l) {
		for (int i=0; i < l.length; i++) {
			if(detectarColision(p, l[i])) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

























