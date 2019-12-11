package unsw.dungeon;

public class Key extends pickUpAble {

	private int keyID;
	
	public Key(int id, int x, int y) {
		super(x, y);
		setKeyID(id);
	}

	public int getKeyID() {
		return keyID;
	}

	private void setKeyID(int keyID) {
		this.keyID = keyID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + keyID;
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
		Key other = (Key) obj;
		if (keyID != other.keyID)
			return false;
		return true;
	}

	

}
