package unsw.dungeon;

public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y);
    }
    
    @Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Wall [x=" + this.getX() + ", y=" + this.getY() + "]";
	}
	
	@Override
	public boolean collide(Moveable m) {
		return true;
	}
}
