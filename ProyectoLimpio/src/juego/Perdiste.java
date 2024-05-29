package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Perdiste {
	int x;
	int y;
	double escala;
	Image GameOver;
	Entorno entorno;
	
	public Perdiste() {
		this.x = 400;
		this.y = 300;
		
		GameOver = Herramientas.cargarImagen("GameOver.png");

	}

	public void GameOver(Entorno entorno) {

		entorno.dibujarImagen(GameOver, this.x, this.y, 0, 1);

	}
}
