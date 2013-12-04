import java.util.*;

/** Tabela de simbolos.*/
public class TabSim {
    HashMap<String,Simbolo> H;
	int pos=0;
	
    TabSim() {
		H = new HashMap<String,Simbolo>(128);
    }

	public void add(String s, Simbolo o) {
		H.put(s,o);
		if (o instanceof Variavel)
			o.SetPos(pos++);
	}
	
    public Simbolo get(String s) {
		return (Simbolo) H.get(s);
    }
	
    public boolean exists(String s) {
		return H.containsKey(s);
    }
}


