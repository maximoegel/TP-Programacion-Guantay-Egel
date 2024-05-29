package juego;

import entorno.Entorno;

public class Piso {
  Bloque[] piso;
    
  public Piso(double y) {
	  piso=new Bloque[20];
	 Bloque aux=new Bloque(0.0,0.0);
	 double ancho=aux.getAncho();
	  for(int i=0; i < piso.length;i++) {
		  piso[i]=new Bloque((i+0.5)*ancho,y);
	  }
  }
  public void dibujar(Entorno e) {
	  for(int i=0; i < piso.length; i++) {
		  if (piso[i] != null) {
		  piso[i].dibujar(e);
		  }
	  }
  }
}
