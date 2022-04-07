import java.util.Random;

public class PruebaSerialization{
	public static void main(String[] args){
		Random aleatorio = new Random();

		PoligonoIrreg poligono = new PoligonoIrreg();
		poligono.anadeVertice(new Coordenada(aleatorio.nextInt(), aleatorio.nextInt()));
		poligono.anadeVertice(new Coordenada(aleatorio.nextInt(), aleatorio.nextInt()));
		poligono.anadeVertice(new Coordenada(aleatorio.nextInt(), aleatorio.nextInt()));

		byte[] objSerial = SerializationUtils.serialize(poligono);

		PoligonoIrreg nPoligono = (PoligonoIrreg)SerializationUtils.deserialize(objSerial);
		System.out.println(poligono);
		System.out.println("\n" + nPoligono);
}

}
