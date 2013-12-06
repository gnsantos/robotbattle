class StackFrame {
    StackableInterface[] frame;
    
    StackFrame (int n) {
        frame = new StackableInterface[n];
    }
    
    StackFrame () {
        frame = new StackableInterface[10];
    }
    
    StackableInterface get(int n) {
		return frame[n];
	}
    
	StackableInterface set(StackableInterface stackable, int n) {
		return frame[n] = stackable;
	}

    
    void Show() {
        System.out.print(":::\n");
		for (int i = 0; frame[i]!=null; i++) {
            System.out.print("  ");
            frame[i].printYourself();
            System.out.print("\n");
        }
	}
}