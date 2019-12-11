package unsw.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */

public class Player extends Entity implements Moveable {

    private Dungeon dungeon;
    private List<Enemy> enemies;
    private List<LitBomb> litBombs;
    private int storedKey;
    private ArrayList<pickUpAble> carry;
	private TickingBehaviour tickingBehaviour;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        carry = new ArrayList<pickUpAble>();
        this.dungeon = dungeon;
        this.enemies = new ArrayList<>();
        this.storedKey = -1;
        this.tickingBehaviour = new NormalPlayerTick(this);
        this.litBombs = new ArrayList<LitBomb>();
    }
    
	public int getStoredKey() {
		return storedKey;
	}
    
    public void setKey(int newKey) {
    	storedKey = newKey;
    }
    
    public void openDoor(Door door) {
    	if (door.openDoor(storedKey)) {
    		storedKey = -1;
    	}
    }

    public boolean haveKey(int doorID) {
        return (storedKey == doorID);
    }
    
    public void tryPick() {
		pickUpAble thing = dungeon.getPickable(this.getX(),this.getY());
    	if (thing != null) {
    		pickUp(thing);
    	}
    }
      
    public boolean pickUp(pickUpAble pickupable) {
    	if (pickupable.pickUp(this.getX(),this.getY())) {
    		if (pickupable instanceof Potion) {
    			this.tickingBehaviour = new InvinciblePlayerTick(this);
    			return true;
    		} else if (pickupable instanceof Key) {
        		Key k = (Key) pickupable;
        		storedKey = k.getKeyID();
        			
        		dungeon.removeEntity(pickupable);
       			return true;
        	} else if (pickupable instanceof Bomb) {
       		    carry.add(pickupable);
       		    dungeon.removeEntity(pickupable);
       		    return true;
        	} else if (pickupable instanceof Sword) {
    			this.tickingBehaviour = new SwordPlayerTick(this);
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public boolean destroyAdjacentEnemies() {
		if (
			(dungeon.destroyEnemy(this.getX()+1,this.getY()+1) == 1)
			|| (dungeon.destroyEnemy(this.getX()+1,this.getY()) == 1)
			|| (dungeon.destroyEnemy(this.getX()+1,this.getY()-1) == 1)
			|| (dungeon.destroyEnemy(this.getX(),this.getY()+1) == 1)
			|| (dungeon.destroyEnemy(this.getX(),this.getY()) == 1)
			|| (dungeon.destroyEnemy(this.getX(),this.getY()-1)  ==1 )
			|| (dungeon.destroyEnemy(this.getX()-1,this.getY()+1) == 1)
			|| (dungeon.destroyEnemy(this.getX()-1,this.getY()) == 1)
			|| (dungeon.destroyEnemy(this.getX()-1,this.getY()-1) == 1)
			) {
			return true;
		}
    	
    	return false;
    }    
    
    public boolean useBomb() {
    	for(pickUpAble bomb: carry) {
    		if (bomb instanceof Bomb) {
    			Bomb k = (Bomb) bomb;
                LitBomb newbomb = k.useBomb(dungeon,this.getX(), this.getY());
        		dungeon.addEntity(newbomb); //left a lit bomb in map
        		k = null; //delete the bomb 
        		return true;	
    		}
    	}
    	
    	return false;
    }
    
    @Override
    public void move(int x, int y) {
    	if (x < 0 || x > dungeon.getWidth()-1 || y < 0 || y > dungeon.getHeight()-1) {
            this.tickingBehaviour.tick();
    		return;
    	}
    	
		if (dungeon.calculateCollisions(this, x, y)) {
			this.setX(x);
			this.setY(y);
		}
        
        this.tickingBehaviour.tick();
    }
    
    @Override
    public void moveUp() {
    	this.move(this.getX(), this.getY()-1);
    }

    @Override
    public void moveDown() {
    	this.move(this.getX(), this.getY()+1);
    }
    
    @Override
    public void moveLeft() {
    	this.move(this.getX()-1, this.getY());
    }

    @Override
    public void moveRight() {
    	this.move(this.getX()+1, this.getY());
    }
    
    @Override
    public void moveWait() {
    	this.move(this.getX(), this.getY());
    }
    
    public void completeExitGoal() {
    	if (this.dungeon.hasExit(getX(), getY())) {
    		this.dungeon.notifyGoalObservers("exit", true);
    	} else {
    		this.dungeon.notifyGoalObservers("exit", false);
    	}
    }
        
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	public void switchToNormal() {
		this.tickingBehaviour = new NormalPlayerTick(this);
	}
	
	public void notifyBombs() {
		for (LitBomb lb : litBombs) {
			lb.tickDown();
		}
	}
	
    public void enemiesFollow() {
    	Iterator<Enemy> enemyItr = this.enemies.iterator();
    	
    	while (enemyItr.hasNext()) {
    		Enemy curr = enemyItr.next();
    		curr.followPlayer(this);
    	}
    }

	public void enemiesFlee() {
    	Iterator<Enemy> enemyItr = this.enemies.iterator();
    	
    	while (enemyItr.hasNext()) {
    		Enemy curr = enemyItr.next();
    		curr.fleePlayer(this);
    	}		
	}
	
	public boolean isNormal() {
		return (this.tickingBehaviour instanceof NormalPlayerTick);
	}
	
	@Override
	public boolean collide(Moveable m) {
		return true;
	}
}
