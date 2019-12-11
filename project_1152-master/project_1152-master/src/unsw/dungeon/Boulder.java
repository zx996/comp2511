package unsw.dungeon;

public class Boulder extends Entity implements Moveable {

	private Dungeon dungeon;

	public Boulder(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
	}

	@Override
	public void move(int x, int y) {
		if (x < 0 || x > dungeon.getWidth()-1 || y < 0 || x > dungeon.getHeight()-1) {
			return;
		}
		
		if (dungeon.calculateCollisions(this, x, y)) {
			this.setX(x);
			this.setY(y);
		}
		
		this.completeSwitchGoal();
	}
	
    @Override
    public void moveUp() {
    	move(this.getX(), this.getY()-1);
    }

    @Override
    public void moveDown() {  
    	move(this.getX(), this.getY()+1);
    }

    @Override
    public void moveLeft() {
    	move(this.getX()-1, this.getY());
    }

    @Override
    public void moveRight() {
    	move(this.getX()+1, this.getY());                
    }

	@Override
	public void moveWait() {
		move(this.getX(), this.getY());
	}
	
	private void completeSwitchGoal() {
		if (this.dungeon.checkSwitches()) {
			this.dungeon.notifyGoalObservers("switch", true);
		} else {
			this.dungeon.notifyGoalObservers("switch", false);
		}
	}

	public boolean onSwitch() {
		return this.dungeon.hasSwitch(getX(), getY());
	}
		
	@Override
	public boolean collide(Moveable m) {
		if (m instanceof Boulder) return true;
				
		int mX = ((Entity) m).getX();
		int mY = ((Entity) m).getY();
		
		int newX = this.getX() + this.getX() - mX;
		int newY = this.getY() + this.getY() - mY;

		move(newX, newY);
		
		return true;
	}
}
