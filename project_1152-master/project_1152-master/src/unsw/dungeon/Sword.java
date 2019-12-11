package unsw.dungeon;

public class Sword extends pickUpAble {
	
    private int useleft = 5; 
	
	public Sword(int x, int y) {
		super(x, y);
	}
	
	public boolean haveSword() {
		if (useleft > 0) {
			return true;
		}
		return false;
	}

	
	public boolean useSword() {
		if (useleft > 0) {
			this.useleft -= 1;
			return true;
		}
		return false;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + useleft;
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
		Sword other = (Sword) obj;
		if (useleft != other.useleft)
			return false;
		return true;
	}
	
	
	

}
