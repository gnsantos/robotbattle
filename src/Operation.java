import java.lang.*;
import java.util.*;

public class Operation{
	public StackableInterface add(StackableInterface x, StackableInterface y){
		StackableInterface sum = new Stackable_Number(x.getValue() + y.getValue());
		return sum;
	}

	public StackableInterface sub(StackableInterface x, StackableInterface y){
		StackableInterface sum = new Stackable_Number(x.getValue() - y.getValue());
		return sum;
	}

	public StackableInterface mul(StackableInterface x, StackableInterface y){
		StackableInterface sum = new Stackable_Number(x.getValue() * y.getValue());
		return sum;
	}

	public StackableInterface div(StackableInterface x, StackableInterface y){
		if (y.getValue() != 0){
			StackableInterface sum = new Stackable_Number(x.getValue() / y.getValue());
			return sum;
		}
		else{
			//Arrumar isso depois com mais calma e prudência!!!!
			return new Stackable_Number(0);
		}
	}

	// Logical Operations das gatinhas!
	public StackableInterface equal(StackableInterface x, StackableInterface y){
		return new Stackable_Number((x.getValue() == y.getValue())? 1: 0);
	}
	public StackableInterface greatThan(StackableInterface x, StackableInterface y){
		return new Stackable_Number((y.getValue() > x.getValue())? 1: 0);
	}
	public StackableInterface greatEqual(StackableInterface x, StackableInterface y){
		return new Stackable_Number((y.getValue() >= x.getValue())? 1: 0);
	}
	public StackableInterface notEqual(StackableInterface x, StackableInterface y){
		return new Stackable_Number((y.getValue() != x.getValue())? 1: 0);
	}
	public StackableInterface lessThan(StackableInterface x, StackableInterface y){
		return new Stackable_Number((y.getValue() < x.getValue())? 1: 0);
	}
	public StackableInterface lessEqual(StackableInterface x, StackableInterface y){
		return new Stackable_Number((y.getValue() <= x.getValue())? 1: 0);
	}
	public StackableInterface itsTrue(StackableInterface x){
		return new Stackable_Number((x.getValue() != 0)? 1: 0);
	}
	public StackableInterface itsFalse(StackableInterface x){
		return new Stackable_Number((x.getValue() == 0)? 1: 0);
	}

}
//qlq coisa transformar em uma interface com operation na head!