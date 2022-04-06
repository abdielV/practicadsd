import java.io.*;

public class Coordenada implements java.io.Serializable {
	private double x, y;

	public Coordenada(){
		x = 0.0;
		y = 0.0;
	}

	public Coordenada(double x, double y) {
		this.x = x;
		this.y = y;
	}

	//Metodo getter de x
	public double getX() {
		return x;
	}

	//Metodo getter de y
	public double getY() {
		return y;
	}

	//Metodo setter de x
	public void setX(double x) {
		this.x = x;
	}

	//Metodo setter de y
	public void setY(double y) {
		this.y = y;
	}

	//Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}
}

