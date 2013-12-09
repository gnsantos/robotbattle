class Pilha {
	private Empilhavel[] Stack;
	private int topo=0;

	Pilha(int n) {
		Stack = new Empilhavel[n];
		topo=0;
	}

	void push(Empilhavel x) {
		Stack[topo++] = x;
	}

	Empilhavel pop() {
		return Stack[--topo];
	}
	void Mostra() {
		for (int i=0; i < topo; i++) 
			System.out.println(Stack[i]);
	}
}

