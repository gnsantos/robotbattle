import java.util.Stack;
class Computador {
	Pilha p = new Pilha(1024);	// pilha de dados e execucao
	Instrucao[] Prog;			// programa
	int ip;						// ponteiro de instrucões
	Empilhavel[] mem;			// memória
	Frame frame;
	Stack<Frame> Contexto = new Stack<Frame>();

	Computador() {
		p   = new Pilha(100);
		mem = new Empilhavel[100];
		frame = new Frame(10);
		Contexto.push(frame);
		ip  = 0;
	}

	void Dump(Instrucao[] p) {
		for (int i = 0; i < p.length ; i++)
			System.out.printf("%3d: %s\n", i, p[i].Show());
	}

	// Carrega e roda um programa
	Empilhavel Roda(Instrucao[] prg) {
		Prog = prg;
		p.push(new Endereco(0));
		roda(0);
		return p.pop();
	}

	// Roda o programa a partir da posicao iii
	void roda(int ii) {
		ip = ii;
		Endereco ret = (Endereco)p.pop(); // guarda o endereco de retorno

		// RET é tratado explicitamente, para simplificar
		while (!(Prog[ip] instanceof RET))
			ip += Prog[ip].Exec(this);

		// Para prosseguir de onde foi chamado
		frame = Contexto.pop();
		ip = ret.val();
	}
}


// Instância de execucao de funcao
class Frame {
	Empilhavel[] loc;			// memória local à funcao

	Frame(int n) {
        loc = new Empilhavel[n];
    }
    
	Frame() {
        this(10);
    }

	Empilhavel get(int n) {
		return loc[n];
	}
    
	Empilhavel set(Empilhavel e, int n) {
		return loc[n] = e;
	}
    
	String Show() {
		String s = ":::\n";
		for (int i = 0; loc[i]!=null; i++) 
			s += "  "+loc[i].Show() + "\n";
		return s;
	}
}