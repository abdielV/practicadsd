import java.util.ArrayList;
import java.io.*;

public class PoligonoIrreg implements java.io.Serializable {
	private ArrayList<Coordenada> vertices = new ArrayList<>();

	public void anadeVertice(Coordenada coordenada) {
		vertices.add(coordenada);
	}

	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		for (Coordenada c : vertices)
			cadena.append("\t" + c + "\n");
		return cadena.toString();
	}
}
