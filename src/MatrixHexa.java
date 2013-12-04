import java.awt.Point;

public class MatrixHexa {
    
    private Terrain[][] terrains;
    
    private Point size;
    
    public MatrixHexa (char[][] map) {
        
        size = new Point(map.length, map[0].length);
        
        terrains = new Terrain[size.x][size.y];
        
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                char a = map[i][j];
                terrains[i][j] = new Terrain(a);
            }
        }
        
    }
    
    public Point size() {
        return size;
    }
    
    public char terrainChar(int x, int y) {
        return terrains[x][y].character();
    }
    
}