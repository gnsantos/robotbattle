import java.util.Vector;

// Simbolo: pode ser nome de variavel ou funcao
class Simbolo {
	Endereco pos;				// posicao na memória: endereco
	Simbolo(int n) {
		pos = new Endereco(n);
	}
	void SetPos(int n) {pos = new Endereco(n);}
}

// Funcao
class Funcao extends Simbolo {
	Vector<String> args = new Vector<String>(); // lista dos argumentos
	TabSim Vars = new TabSim();					// nomes dos argumentos

	Funcao(int n) {
		super(n);
	}

	// inclui um argumento
	void addarg(String a) {
		Variavel v = new Variavel();
		args.add(a);
		Vars.add(a,v);
	}

	// pega o nome do argumento na posicao n
	String getarg(int n) {
		return args.get(n);
	}

	// retorna a variavel de nome a
	Variavel get(String a) {
		return (Variavel) Vars.get(a);
	}

	// verifica a existência
	Boolean exists(String a) {
		return  Vars.exists(a);
	}
}

// Variavel
class Variavel extends Simbolo {
	static int nvars=0;			// numero de variaveis globais

	Variavel() {super(nvars++);}
}
