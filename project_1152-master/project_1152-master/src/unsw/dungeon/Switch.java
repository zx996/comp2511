package unsw.dungeon;

// TODO logic wiht boulders
public class Switch extends Entity {

	public Switch(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
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
