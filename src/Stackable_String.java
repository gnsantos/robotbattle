import java.lang.*;
import java.util.*;

public class Stackable_String implements StackableInterface{
	public String myValue;

	public Stackable_String(String value){
		myValue = value;
	}
	public void printYourself(){
        String newString = myValue.replaceAll("\"","");
        newString = newString.replaceAll("\\\\n","\n");
        newString = newString.replaceAll("\\\\t","\t");
        newString = newString.replaceAll("\\\\b","\b");
        newString = newString.replaceAll("\\\\r","\r");
        newString = newString.replaceAll("\\\\f","\f");
        System.out.print(newString);
	}

	public double getValue(){
		return myValue.length();
	}

}