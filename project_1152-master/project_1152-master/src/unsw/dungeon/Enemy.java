package unsw.dungeon;

public class Enemy extends Entity implements Moveable {

	private Dungeon dungeon;
	
	public Enemy(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
	}
		
	public void followPlayer(Player player) {
		int xDiff = Math.abs(player.getX()-this.getX());
		int yDiff = Math.abs(player.getY()-this.getY());
		
		
		if (xDiff > yDiff) {
			if (player.getX() > this.getX() && !this.checkWallRight()) {
				this.moveRight();
			} else if (player.getX() < this.getX() && !this.checkWallLeft()) {
				this.moveLeft();
			} else if (player.getY() > this.getY() && !this.checkWallDown()) {
					this.moveDown();
			} else {
					this.moveUp();
			}
		} else {
			if (player.getY() > this.getY() && !this.checkWallDown()) {
					this.moveDown();
			} else if (player.getY() < this.getY() && !this.checkWallUp()) {
					this.moveUp();
			} else if (player.getX() > this.getX() && !this.checkWallRight()) {
				this.moveRight();
			} else {
				this.moveLeft();
			}	
		}
	}
	
	public void fleePlayer(Player player) {
		int xDiff = Math.abs(player.getX()-this.getX());
		int yDiff = Math.abs(player.getY()-this.getY());
		
		if (xDiff < yDiff) {
			if (player.getX() < this.getX() && !this.checkWallRight()) {
				this.moveRight();
			} else if (player.getX() > this.getX() && !this.checkWallLeft()) {
				this.moveLeft();
			} else if (player.getY() > this.getY() && !this.checkWallDown()) {
					this.moveDown();
			} else {
					this.moveUp();
			}
		} else {
			if (player.getY() < this.getY() && !this.checkWallDown()) {
					this.moveDown();
			} else if (player.getY() > this.getY() && !this.checkWallUp()) {
					this.moveUp();
			} else if (player.getX() < this.getX() && !this.checkWallRight()) {
				this.moveRight();
			} else {
				this.moveLeft();
			}			
		}	
	}	

	public Boolean checkKillPlayer(Player player) {
		return (player.getX() == this.getX() && player.getY() == this.getY());
	}
	
	private Boolean checkWallLeft() {
		return dungeon.hasWall(this.getX()-1,this.getY());
	}

	private Boolean checkWallRight() {
		return dungeon.hasWall(this.getX()+1,this.getY());
	}

	private Boolean checkWallUp() {
		return dungeon.hasWall(this.getX(),this.getY()-1);
	}

	private Boolean checkWallDown() {
		return dungeon.hasWall(this.getX(),this.getY()+1);
	}

	
	@Override
	public void move(int x, int y) {
    	if (x < 0 || x > dungeon.getWidth()-1 || y < 0 || y > dungeon.getHeight()-1) {
    		return;
    	}
    	
		if (dungeon.calculateCollisions(this, x, y)) {
			this.setX(x);
			this.setY(y);
		}		
	}
	
    @Override
    public void moveUp() {
        this.move(getX(), getY()-1);
    }

    @Override
    public void moveDown() {  	
        this.move(getX(), getY()+1);
    }

    @Override
    public void moveLeft() {
        this.move(getX()-1, getY());
    }

    @Override
    public void moveRight() {
        this.move(getX()+1, getY());
    }

	@Override
	public void moveWait() {
        this.move(getX(), getY());
	}
}
