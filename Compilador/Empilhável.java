// Tudo o que pode ser manipulável pela máquina virtual
interface Empilhável {
	String Show();
}

// Tipos numéricos
abstract class Número {
	abstract double val();
}

class Inteiro extends Número implements Empilhável {
	int v;
	Inteiro(int n) {
		v = n;
	}
	double val() {return (double)v;}

	public String Show() {
		return Integer.toString(v);
	}
}

class Real extends Número implements Empilhável {
	double v;
	Real(double n) {
		v = n;
	}
	double val() {return v;}
	public String Show() {
		return Double.toString(v);
	}
}

// endereço de instruções na máquina virtual
class Endereço implements Empilhável {
	int v;
	Endereço(int n) {
		v = n;
	}
	int  val() {return v;}
	public String Show() {
		return "> "+v;
	}
}

// Cadeia de caracteres
class Cadeia implements Empilhável {
	String v;
	Cadeia(String s) { v = s;}
	String val() {return v;}
	public String Show() {
		return "\"" + v + "\"";
	}
}