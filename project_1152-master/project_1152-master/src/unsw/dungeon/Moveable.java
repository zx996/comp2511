package unsw.dungeon;

public interface Moveable {
	public void move(int x, int y);
    public void moveUp();
    public void moveDown();
    public void moveLeft();
    public void moveRight();
    public void moveWait();
}
