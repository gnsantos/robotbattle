public enum HexDirection {
    UP (-1, 0),
    DOWN (1, 0),
    LEFTDOWN (1, -1),
    LEFTUP (0, -1),
    RIGHTDOWN (1, 1),
    RIGHTUP (0, 1);

//  As direções estão melhor descritas (desenhadas) no png disponível no git

    private final int x;
    private final int y;

    HexDirection (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }
}