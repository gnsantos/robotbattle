import java.lang.*;
import java.util.*;

public class StackElement{
	public static final int MAX = 1000;
	public ArrayList<StackableInterface> stackTeste = new ArrayList<StackableInterface>();
	public MemoryArray memorySegment = new MemoryArray(MAX);
	public Operation myOperation = new Operation();

	private boolean itsEmpty(){
		return (stackTeste.size() == 0);
	}
	public void pile(double valor){
		Stackable_Number val = new Stackable_Number(valor);
		stackTeste.add(val);
	}

	public void pile(String texto){
		Stackable_String val = new Stackable_String(texto);
		stackTeste.add(val);
	}

	public StackableInterface pop(){
		StackableInterface foo = stackTeste.remove(stackSize() -1);
		return foo;
	}

	public boolean jumpTrue(){
		StackableInterface foo;
		foo = pop();
		return (foo.getValue() != 0);
	}

	public boolean jumpFalse(){
		StackableInterface foo;
		foo = pop();
		return (foo.getValue() == 0);
	}


	//Duplica o topo
	public void dupTop(){
		StackableInterface top = stackTeste.get(stackSize()-1);
		stackTeste.add(top);
	}
	//Pop da pilha

	public void discartTop(){
		if (!itsEmpty()){
			stackTeste.remove(stackSize() -1);
		}
	}

	public void printTop(){
		StackableInterface foo = pop();
		foo.printYourself();
	}

/*
Pilha
*/
	//Imprime a pilha
	public void printStack(){
		for (int x = 0; x < stackTeste.size() ; x++) {
			System.out.println("-----------");
			stackTeste.get(x).printYourself();
			System.out.println("-----------");
		}

	}
	//Retorna o tamnho da pilha
	public int stackSize(){
		return stackTeste.size();
	}

	//Limpa a pilha
	public void clearStack(){
		stackTeste.clear();
	}

	//Mostra a tamanho da pilha
	public void showStackSize(){
		System.out.println("Stack elements quantity ; "+ stackSize());
	}

/*
Memória
*/
	//Salva na posição de memória fornecida
	public void salveMem(int position){
		int x = stackSize();
		StackableInterface foo = stackTeste.remove(stackSize() -1);
		memorySegment.set(position, foo);
	}
	
	//Remove da memória da posição fornecida e insere no topo da pilha.
	public void retriveMem(int position){
		StackableInterface foo = memorySegment.get(position);
		stackTeste.add(foo);
	}
	//Imprime a posição solicitada da memoria
	public void printMem(int pos){
		memorySegment.printMemoryPos(pos);
	}

	public void operation(int opera){
		StackableInterface foo ,foo2;
		switch (opera) {
			case 0:
				foo = myOperation.add(pop(),pop());
				break;
			case 1:
				foo2 = pop();
				foo = myOperation.sub(pop(),foo2);
				break;
			case 2:
				foo = myOperation.mul(pop(),pop());
				break;
			case 3:
				foo2 = pop();
				foo = myOperation.div(pop(),foo2);
				break;
			case 4:
				foo = myOperation.equal(pop(),pop());
				break;
			case 5:
				foo = myOperation.greatThan(pop(),pop());
				break;
			case 6:
				foo = myOperation.greatEqual(pop(),pop());
				break;
			case 7:
				foo = myOperation.lessThan(pop(),pop());
				break;
			case 8:
				foo = myOperation.lessEqual(pop(),pop());
				break;
			case 9:
				foo = myOperation.notEqual(pop(),pop());
				break;
			case 10:
				foo = myOperation.itsTrue(pop());
				break;
			case 11:
				foo = myOperation.itsFalse(pop());
				break;
			default:
				foo = null;
				break;
		}
		pile(foo.getValue());
	}

	public void eraseData(){
		this.stackTeste.clear();
		this.memorySegment.clear();
	}
}