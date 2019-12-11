package unsw.dungeon;

//TODO attatch timing mechanism for the bomb
public class LitBomb extends Entity{
    private int timeleft = 3;
    private Dungeon dungeon;
    
    // Only keeping this not to cause errors
    // TODO refractor this constructor out
    /*
	public LitBomb(int x, int y) {
		super(x, y);
		dungeon = null;
	}*/
	
	// Should be the constructor to use
	public LitBomb(Dungeon d, int x, int y) {
		super(x,y);
		this.dungeon = d;
	}
	
	// Public only for testing purposes
	// For proper use, use tickDown() to increment the counter down until it explodes
	public void explode() {
		// Request to destroy adjacent squares
		System.err.println("Bomb exploding at "+getX()+ " " +getY());
		dungeon.destroy(this.getX(),this.getY()-1);
		dungeon.destroy(this.getX(),this.getY()+1);
		dungeon.destroy(this.getX()-1,this.getY());
		dungeon.destroy(this.getX()+1,this.getY());
		// Delete this entity by removing all references
		dungeon.removeEntity(this);
		dungeon = null;
		System.err.println("Bomb exploded");
	}
	
	public int timeLeft() {
		return timeleft;
	}
	
	// Ticks down the counter of the bomb
	public void tickDown() {
		if (timeleft <= 1) {
			explode();
		}
		timeleft--;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + timeleft;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LitBomb other = (LitBomb) obj;
		if (timeleft != other.timeleft)
			return false;
		return true;
	}

}
