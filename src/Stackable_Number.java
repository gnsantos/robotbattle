import java.lang.*;
import java.util.*;

public class Stackable_Number implements StackableInterface{
	private double myValue;

	public Stackable_Number(double value){
		this.myValue = value;
	}
	public double getValue(){
		return this.myValue;
	}
	public void printYourself(){
		System.out.println("My Value is : " + this.myValue);
	}
}