import java.awt.Point;

public class Entity {
    
    private Point position;
    char type;
    
    public Entity (char type, int x, int y) {
        this.type = type;
        this.position = new Point(x, y);
    }
    
    public Point position() {
        return position;
    }
    
    public char type() {
        return type;
    }
    
}