// Instrucao genérica, nao faz nada.
class Instrucao {
	Empilhavel val;

	Instrucao(Empilhavel v) {
        val = v;
    }
    
	Instrucao() {
        val = null;
    }
    
	int Exec(Computador C) {     // retorna o incremento do ip
        return 1;
    }
    
	String Show() {
		String res =  this.toString().replaceFirst("@.+","");
		if (val != null) res += " " + val.Show();
		return res;
	}
}


// Empilha uma Cadeia com a descricao do Empilhavel
/*class Mostra extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.push(new Cadeia(p.pop().Show()));
		return 1;
	} 
}*/

////////////////////////////////////
// Instrucoes aritméticas

class SOMA extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;

		Empilhavel e1 = p.pop();
		Empilhavel e2 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);

		p.push(new Real(n1.val() + n2.val()));
		return 1;
	}
}

class MULT extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e1 = p.pop();
		Empilhavel e2 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);

		p.push(new Real(n1.val() * n2.val()));
		return 1;
	}
}

class SUB extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e1 = p.pop();
		Empilhavel e2 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n2.val() - n1.val()));
		return 1;
	}
}

class DIV extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e1 = p.pop();
		Empilhavel e2 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(1);
		p.push(new Real(n1.val()/n2.val()));
		return 1;
	}
}

////////////////////////////////////////////////////////////////////////
// Lógicas

class MAIOR extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e2 = p.pop();
		Empilhavel e1 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() > n2.val()? 1:0));
		return 1;
	}
}

class MENOR extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e2 = p.pop();
		Empilhavel e1 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() < n2.val()?1:0));
		return 1;
	}
}

class IGUAL extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e2 = p.pop();
		Empilhavel e1 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() == n2.val()? 1:0));
		return 1;
	}
}

class MAIORIGUAL extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e2 = p.pop();
		Empilhavel e1 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() >= n2.val()? 1:0));
		return 1;
	}
}

class MENORIGUAL extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e2 = p.pop();
		Empilhavel e1 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() <= n2.val()? 1:0));
		return 1;
	}
}

class DIFERENTE extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e2 = p.pop();
		Empilhavel e1 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		p.push(new Real(n1.val() != n2.val()? 1:0));
		return 1;
	}
}

///////////////////////////////////
// Manipulacao da pilha

class PUSH extends Instrucao {
	PUSH(Empilhavel x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.push(val);
		return 1;
	}
}

// Empilha uma variavel
class PUSHV extends Instrucao {
	PUSHV(Empilhavel x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		
		p.push(C.mem[((Endereco)val).val()]);
		return 1;
	}
}

// Atualiza uma variavel
class SETV extends Instrucao {
	SETV(Empilhavel x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int v = ((Endereco)val).val();
		C.mem[v] = p.pop();
		return 1;
	}
}

// Empilha uma variavel local
class PUSHLV extends Instrucao {
	PUSHLV(Empilhavel x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int end = ((Endereco)val).val();

		Frame f = C.frame;
		Empilhavel pp = f.get(end);

		p.push(pp);
		return 1;
	}
}

// Atualiza uma variavel local
class SETLV extends Instrucao {
	SETLV(Empilhavel x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int v = ((Endereco)val).val();
		Frame f = C.frame;
		Empilhavel pp = p.pop();
		f.set(pp,v);
		return 1;
	}
}

// troca os dois elementos do topo
/*class SWAP extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel e1 = p.pop();
		Empilhavel e2 = p.pop();
		p.push(e1);
		p.push(e2);
		
		return 1;
	}
}*/

class POP extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		p.pop();
		return 1;
	}
}

class DUP extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel dd  = p.pop();
		p.push(dd);
		p.push(dd);
		return 1;
	}
}


////////////////////////////////////
// Controle de fluxo

/*class LT extends Instrucao {
	LT(Empilhavel x) {super(x);}
	LT(int n) {super(new Endereco(n));}

	int Exec(Computador C) {
		Pilha p = C.p;
		Empilhavel e2 = p.pop();
		Empilhavel e1 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		if (n1.val() < n2.val())
			return ((Endereco) val).val();
		else return 1;
	}
}*/

/*class GT extends Instrucao {
	GT(Empilhavel x) {super(x);}
	GT(int n) {super(new Endereco(n));}

	int Exec(Computador C) {
		Pilha  p = C.p;
		Endereco end = (Endereco) val;
		Empilhavel e2 = p.pop();
		Empilhavel e1 = p.pop();
		Numero n1,n2;
		if (e1 instanceof Numero && e2 instanceof Numero) {
			n1 = (Numero)e1;
			n2 = (Numero)e2;
		}
		else n1 = n2 = new Real(0);
		if (n1.val() >= n2.val())
			return  end.val();
		else return 1;
	}
}*/

class ZERO extends Instrucao {
	ZERO(Empilhavel x) {
        super(x);
    }
    
	ZERO(int n) {
        super(new Endereco(n));
    }

	int Exec(Computador C) {
		Pilha  p = C.p;
		Endereco end = (Endereco) val;
		Empilhavel e1 = p.pop();
		Numero n1;
		if (e1 instanceof Numero) {
			n1 = (Numero)e1;
		}
		else n1 = new Real(0);
		if (n1.val() == 0)
			return  end.val();
		else return 1;
	}
}

class GOTO extends Instrucao {
	GOTO (Empilhavel x) {
        super(x);
    }
    
	GOTO (int x) {
        super(new Endereco(x));
    }

	int Exec(Computador C) {
		return ((Endereco) val).val();
	}
}

class RET extends Instrucao {           // Dá pop no valor de retorno da função,
                                        // dá pop no endereço pro qual ele tem que voltar,
                                        // pusha de volta o valor de retorno e
                                        // jumpa pra esse endereço.
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel r = p.pop();
		Endereco retorno = (Endereco) p.pop();
		p.push(r);
		return retorno.val();
	}
}

class PRINT extends Instrucao {
	int Exec(Computador C) {
		Pilha  p = C.p;
		Empilhavel x = p.pop();
		if (x instanceof Cadeia) 
			System.out.print(((Cadeia) x).s);
		else if (x instanceof Inteiro) 
			System.out.print(((Inteiro) x).val());
		else if (x instanceof Real) 
			System.out.print(((Real) x).val());
		else if (x instanceof Endereco) 
			System.out.print(((Endereco) x).val());
		return 1;
	}
}

class CALL extends Instrucao {          // Empilha o índice atual + 1 e jumpa pro
                                        // endereço da declaração da função
	CALL (Empilhavel x) {super(x);}
	int Exec(Computador C) {
		Pilha  p = C.p;
		int ip = C.ip;
		Instrucao[] Prog = C.Prog;
		Endereco e = (Endereco)Prog[ip].val;
		p.push(new Endereco(ip+1));
		C.roda(e.val());
		return 1;
	} 
}

class ENTRA extends Instrucao {     // Cria uma nova f
	int Exec(Computador C) {
		C.Contexto.push(C.frame);
		C.frame=new Frame();
		return 1;
	}
}

// ====================================
// SYSCALLS
// ====================================

// Syscalls particulares
class WLK extends Instrucao {
    WLK (String dir) {
        super(new Direcao(dir));
    }
}
class FIRE extends Instrucao {
    FIRE (String dir) {
        super(new Direcao(dir));
    }
}
class BOMBA extends Instrucao {
    BOMBA (String dir) {
        super(new Direcao(dir));
    }
    
    String Show(){
	String s = "BOMB "+ val.Show();
	return s;
    }
}
class TAKE extends Instrucao {
    TAKE (String dir) {
        super(new Direcao(dir));
    }
}
class DROP extends Instrucao {
    DROP (String dir) {
        super(new Direcao(dir));
    }
}
class LOOK extends Instrucao {
    LOOK (String dir) {
        super(new Direcao(dir));
    }
}
class ASK extends Instrucao {
    ASK (String dir) {
        super(new Direcao(dir));
    }
}

// Syscall genérica
class SYSCALL extends Instrucao {
    
    // Devolve uma nova instrucao da classe certa, de acordo com a syscall
    // passada pela String syscall. É como um construtor, só que melhor :D
    static Instrucao newSyscall (String syscall, String param) {
        // Traduz a string param da linguagem alta para a baixa
        String newParam = correctParameter(param);
        
        // Cria e retorna o objeto adequado
        if (syscall.equals("WALK")) {
            return new WLK(newParam);
        }
        else if (syscall.equals("FIRE")) {
            return new FIRE(newParam);
        }
        else if (syscall.equals("BOMB")) {
            return new BOMBA(newParam);
        }
        else if (syscall.equals("TAKE")) {
            return new TAKE(newParam);
        }
        else if (syscall.equals("DROP")) {
            return new DROP(newParam);
        }
        else if (syscall.equals("LOOK")) {
            return new LOOK(newParam);
        }
        else if (syscall.equals("ASK")) {
            return new ASK(newParam);
        }
        return null;
    }
    
    // Traduz os parametros recebidos da linguagem alta para a baixa
    static String correctParameter (String param) {
        String newParam;
        // Directions
        if (param.equals("East"))
            newParam = "E";
        else if (param.equals("West"))
            newParam = "W";
        else if (param.equals("Southwest"))
            newParam = "SW";
        else if (param.equals("Southeast"))
            newParam = "SE";
        else if (param.equals("Northeast"))
            newParam = "NE";
        else if (param.equals("Northwest"))
            newParam = "NW";
        // Ask Options
        else if (param.equals("Health"))
            newParam = "MY_HEALTH";
        else if (param.equals("Crystals"))
            newParam = "NUMBER_OF_CRYSTAL";
        else if (param.equals("Enemy_base_distance"))
            newParam = "ENEMY_BAS_DISTANCE";
        else if (param.equals("Base_distance"))
            newParam = "TEMA_BASE_DISTANCE";
        else if (param.equals("Is_in_enemy_base"))
            newParam = "IS_IN_ENEMY_BASE";
        else 
            newParam = param;
        
        return newParam;
    }
}

