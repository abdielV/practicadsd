import java.io.Serializable;

public class Demo implements java.io.Serializable{
	public int a;
	public String b;

	// Default constructor
	public Demo(int a, String b)
	{
		this.a = a;
		this.b = b;
	}
	public void imprimirObjeto(){
		System.out.println("Datos que serán mandados al servidor");
		System.out.println("a: "+this.a);
		System.out.println("b: "+this.b);
	}
}
