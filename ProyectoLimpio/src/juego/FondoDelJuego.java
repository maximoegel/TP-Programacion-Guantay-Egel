package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class FondoDelJuego {
	int x;
	int y;
	double escala;
	Image fondoJuego;
	Entorno entorno;
	
	public FondoDelJuego() {
		this.x = 400;
		this.y = 300;
		fondoJuego = Herramientas.cargarImagen("fondo.jpg");
	}
	public void dibujarFondo(Entorno entorno) {
		entorno.dibujarImagen(fondoJuego, this.x, this.y, 0, 1);
	}
}
