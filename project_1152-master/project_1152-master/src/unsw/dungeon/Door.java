package unsw.dungeon;

public class Door extends Entity {

	private int doorID;
	private boolean opened;

	public Door(int doorID, int x, int y) {
		super(x, y);
		this.doorID = doorID;
		this.opened = false;
	}
	
	public int getDoorID() {
		return doorID;
	}
	
	public Boolean openDoor(int keyID) {
		if (keyID == doorID) {
			opened = true;
		}

		return isOpen();
	}
	
	public Boolean isOpen() {
		return opened;
	}
	
	@Override
	public boolean collide(Moveable m) {
		if (m instanceof Player && !isOpen()) {
			((Player) m).openDoor(this);
			return true;
		}
		
		return (!isOpen());
	}
}
