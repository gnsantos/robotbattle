import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser{
	public static void parseToMe (LinkedList<String[]> programArray, Hashtable<String, Integer> labelsHash, String name) throws IOException{
		BufferedReader file = new BufferedReader(new FileReader(name));
		String line;
	    int pc = 0;
	    Pattern comline = Pattern.compile("(\\b[a-zA-Z]*\\b:\\s*)?(\\b[a-zA-Z]{2,4}\\b)[^:]?((-?\\d+\\.?\\d*|[^#]*|\\s*))\\s*[\n\f#]*");
	    Pattern labeline = Pattern.compile("(\\b[a-zA-Z]*\\b:\\s*)[\n\f#]*$");
	    Pattern other = Pattern.compile("^#.*[\n\f]*|^[ \t\n]*$");
	    while((line = file.readLine()) != null){
	      Matcher matchLabel = labeline.matcher(line);
	      Matcher matchComLine = comline.matcher(line);
	      Matcher matchOther = other.matcher(line);
	      String label;
	      
	      if(matchOther.find()){
	        System.out.print("");
	      }
	      else if(matchLabel.find()){
	        label = matchLabel.group(1).substring(0, matchLabel.group(1).length()-1);
	        labelsHash.put(label, pc);
	        pc++;
	        //System.out.println(label);
	      }
	      
	      else if(matchComLine.find()){
	        String opcode = "", arg="";
	        if(matchComLine.group(1) != null){
	          label = matchComLine.group(1).substring(0, matchComLine.group(1).length()-1);
	          labelsHash.put(label, pc);
	        }
	        if(matchComLine.group(2) != null){
	          opcode =  matchComLine.group(2);
	          //System.out.println(opcode);
	        }
	        if(matchComLine.group(3) != null && !matchComLine.group(3).equals("")){
	          arg = matchComLine.group(3);
	        }
	        pc++;
	        String [] comando = {opcode,arg};
	        programArray.add(comando);
	      }
	      
	      else{System.out.print(line+": ");System.out.println("Syntax Error.")  ;}   
	    }
	}
}