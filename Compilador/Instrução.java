// Instrução genérica, não faz nada.
class Instrução {
	Empilhável val;

	Instrução(Empilhável v) {val = v;}
	Instrução() {val = null;}
	int Exec(Computador C) {return 1;} // retorna o incremento do ip
	String Show() {
		String res =  this.toString().replaceFirst("@.+","");
		if (val != null) res += " " + val.Show();
		return res;
	}
}


// Empilha uma Cadeia com a descrição do Empilhável
class Mostra extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.push(new Cadeia(p.pop().Show()));
		return 1;
	} 
}

////////////////////////////////////
// Instruções aritméticas

class SOMA extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;

		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);

		p.push(new Real(n1.val() + n2.val()));
		return 1;
	}
}

class MULT extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);

		p.push(new Real(n1.val() * n2.val()));
		return 1;
	}
}

class SUB extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n2.val() - n1.val()));
		return 1;
	}
}

class DIV extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(1);
		p.push(new Real(n1.val()/n2.val()));
		return 1;
	}
}

////////////////////////////////////////////////////////////////////////
// Lógicas

class MAIOR extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() > n2.val()? 1:0));
		return 1;
	}
}

class MENOR extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() < n2.val()?1:0));
		return 1;
	}
}

class IGUAL extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() == n2.val()? 1:0));
		return 1;
	}
}

class MAIORIGUAL extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() >= n2.val()? 1:0));
		return 1;
	}
}

class MENORIGUAL extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() <= n2.val()? 1:0));
		return 1;
	}
}

class DIFERENTE extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() != n2.val()? 1:0));
		return 1;
	}
}

///////////////////////////////////
// Manipulação da pilha

class PUSH extends Instrução {
	PUSH(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.push(val);
		return 1;
	}
}

// Empilha uma variável
class PUSHV extends Instrução {
	PUSHV(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		
		p.push(C.mem[((Endereço)val).val()]);
		return 1;
	}
}

// Atualiza uma variável
class SETV extends Instrução {
	SETV(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int v = ((Endereço)val).val();
		C.mem[v] = p.pop();
		return 1;
	}
}

// Empilha uma variável local
class PUSHLV extends Instrução {
	PUSHLV(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int end = ((Endereço)val).val();

		Frame f = C.frame;
		Empilhável pp = f.get(end);

		p.push(pp);
		return 1;
	}
}

// Atualiza uma variável local
class SETLV extends Instrução {
	SETLV(Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int v = ((Endereço)val).val();
		Frame f = C.frame;
		Empilhável pp = p.pop();
		f.set(pp,v);
		return 1;
	}
}

// troca os dois elementos do topo
class SWAP extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e1 = p.pop();
		Empilhável e2 = p.pop();
		p.push(e1);
		p.push(e2);
		
		return 1;
	}
}

class POP extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.pop();
		return 1;
	}
}

class DUP extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável dd  = p.pop();
		p.push(dd);
		p.push(dd);
		return 1;
	}
}


////////////////////////////////////
// Controle de fluxo

class LT extends Instrução {
	LT(Empilhável x) {super(x);}
	LT(int n) {super(new Endereço(n));}

	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		if (n1.val() < n2.val())
			return ((Endereço) val).val();
		else return 1;
	}
}

class GT extends Instrução {
	GT(Empilhável x) {super(x);}
	GT(int n) {super(new Endereço(n));}

	int Exec(Computador C) {
		Pilha  p = C.p;
		Endereço end = (Endereço) val;
		Empilhável e2 = p.pop();
		Empilhável e1 = p.pop();
		Número n1,n2;
		if (e1 instanceof Número && e2 instanceof Número) {
			n1 = (Número)e1;
			n2 = (Número)e2;
		}
		else n1 = n2 = new Real(0);
		if (n1.val() >= n2.val())
			return  end.val();
		else return 1;
	}
}

class ZERO extends Instrução {
	ZERO(Empilhável x) {super(x);}
	ZERO(int n) {super(new Endereço(n));}

	int Exec(Computador C) {
		Pilha  p = C.p;
		Endereço end = (Endereço) val;
		Empilhável e1 = p.pop();
		Número n1;
		if (e1 instanceof Número) {
			n1 = (Número)e1;
		}
		else n1 = new Real(0);
		if (n1.val() == 0)
			return  end.val();
		else return 1;
	}
}

class GOTO extends Instrução {
	GOTO (Empilhável x) {super(x);}
	GOTO (int x) {super(new Endereço(x));}

	int Exec(Computador C) {
		return ((Endereço) val).val();
	}
}

class RET extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável r = p.pop();
		Endereço retorno = (Endereço) p.pop();
		p.push(r);
		return retorno.val();
	}
}

class PRINT extends Instrução {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhável x = p.pop();
		if (x instanceof Cadeia) 
			System.out.print(((Cadeia) x).v);
		else if (x instanceof Inteiro) 
			System.out.print(((Inteiro) x).val());
		else if (x instanceof Real) 
			System.out.print(((Real) x).val());
		else if (x instanceof Endereço) 
			System.out.print(((Endereço) x).val());
		return 1;
	}
}

class CALL extends Instrução {
	CALL (Empilhável x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int ip = C.ip;
		Instrução[] Prog = C.Prog;
		Endereço e = (Endereço)Prog[ip].val;
		p.push(new Endereço(ip+1));
		C.roda(e.val());
		return 1;
	} 
}

class ENTRA extends Instrução {
	int Exec(Computador C) {
		C.Contexto.push(C.frame);
		C.frame=new Frame();
		return 1;
	}
}
