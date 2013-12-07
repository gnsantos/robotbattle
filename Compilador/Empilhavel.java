// Tudo o que pode ser manipulavel pela maquina virtual
interface Empilhavel {
	String Show();
}

// Tipos numéricos
abstract class Numero {
	abstract double val();
}

class Inteiro extends Numero implements Empilhavel {
	int v;
	Inteiro(int n) {
		v = n;
	}
	double val() {return (double)v;}

	public String Show() {
		return Integer.toString(v);
	}
}

class Real extends Numero implements Empilhavel {
	double v;
	Real(double n) {
		v = n;
	}
	double val() {return v;}
	public String Show() {
		return Double.toString(v);
	}
}

// endereco de instrucões na maquina virtual
class Endereco implements Empilhavel {
	int v;
	Endereco(int n) {
		v = n;
	}
	int  val() {return v;}
	public String Show() {
		return ""+v;
	}
}

// Cadeia de caracteres
class Cadeia implements Empilhavel {
	String s;
    
	Cadeia(String v) {
        s = v;
    }
    
	String val() {
        return s;
    }
    
	public String Show() {
        s = s.replaceAll("\n", "\\\\n");
        s = s.replaceAll("\t", "\\\\t");
        s = s.replaceAll("\b", "\\\\b");
        s = s.replaceAll("\r", "\\\\r");
        s = s.replaceAll("\f", "\\\\f");
        s = s.replaceAll("\\\\", "\\\\");
        
		return "\"" + s + "\"";
	}
}