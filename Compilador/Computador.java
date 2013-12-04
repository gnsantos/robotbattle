import java.util.Stack;
class Computador {
	Pilha p = new Pilha(1024);	// pilha de dados e execução
	Instrução[] Prog;			// programa
	int ip;						// ponteiro de instruções
	Empilhável[] mem;			// memória
	Frame frame;
	Stack<Frame> Contexto = new Stack<Frame>();

	Computador() {
		p   = new Pilha(100);
		mem = new Empilhável[100];
		frame = new Frame(10);
		Contexto.push(frame);
		ip  = 0;
	}

	void Dump(Instrução[] p) {
		for (int i = 0; i < p.length ; i++)
			System.out.printf("%3d: %s\n", i, p[i].Show());
	}

	// Carrega e roda um programa
	Empilhável Roda(Instrução[] prg) {
		Prog = prg;
		p.push(new Endereço(0));
		roda(0);
		return p.pop();
	}

	// Roda o programa a partir da posição iii
	void roda(int ii) {
		ip = ii;
		Endereço ret = (Endereço)p.pop(); // guarda o endereço de retorno

		// RET é tratado explicitamente, para simplificar
		while (!(Prog[ip] instanceof RET))
			ip += Prog[ip].Exec(this);

		// Para prosseguir de onde foi chamado
		frame = Contexto.pop();
		ip = ret.val();
	}
}


// Instância de execução de função
class Frame {
	Empilhável[] loc;			// memória local à função

	Frame(int n) {loc = new Empilhável[n];}
	Frame()   	 {this(10);}

	Empilhável get(int n) {
		return loc[n];
	}
	Empilhável set(Empilhável e, int n) {
		return loc[n] = e;
	}
	String Show() {
		String s = ":::\n";
		for (int i = 0; loc[i]!=null; i++) 
			s += "  "+loc[i].Show() + "\n";
		return s;
	}
}