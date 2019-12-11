package unsw.dungeon;

public class Bomb extends pickUpAble {

	public Bomb(int x, int y) {
		super(x, y);
	}
	
	public LitBomb useBomb(Dungeon d, int x, int y) {
		LitBomb litbomb = new LitBomb(d,x,y);
	    return litbomb;
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
}
