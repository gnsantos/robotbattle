import java.lang.*;
import java.util.*;

public class Stackable_String implements StackableInterface{
	public String myValue;

	public Stackable_String(String value){
		myValue = value;
	}
	public void printYourself(){
		System.out.print(myValue);
	}

	public double getValue(){
		return myValue.length();
	}

}