import java.lang.*;
import java.util.*;
 
 public class MemoryArray{
 	private StackableInterface[] mem;
 	private int size;
 	public MemoryArray(int max){
 		this.mem = new StackableInterface[max];
 		this.size = max;
 	}

 	public MemoryArray(){
 		this.mem = new StackableInterface[1000];
 		this.size = 1000;
 	}
 	public int size(){
 		return this.size;
 	}
 	public StackableInterface get(int pos){
 		return this.mem[pos];
 	}

 	public void printMemoryPos(int pos){
 		if(this.mem[pos] != null){
 			this.mem[pos].printYourself();
 		}
 		else{
 			System.out.println("Empty Position!");
 		}
 	}

 	public void set(int pos, StackableInterface foo){
 		if(pos > size()){
 			
 			StackableInterface[] aux = new StackableInterface[2*pos];
 			for(int i = 0; i < size(); i++){
 				aux[i] = mem[i];
 			}
 			this.size = 2*pos;
 			// System.out.println("Tive que aumentar : " + this.size);
 			mem = aux;
 		}
 		mem[pos] = foo;
 	}

 }