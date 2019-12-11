package unsw.dungeon;

//TODO goal logic
public class Treasure extends pickUpAble {
        
	public Treasure(int x, int y) {
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
     
	
}
