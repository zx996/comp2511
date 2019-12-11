/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements GoalObservable {

    private Player player;
    private Map dungeonMap;
    private Goal headGoal;
	private List<GoalObserver> goalObservers;

    public Dungeon(int width, int height) {
        this.player = null;
        this.dungeonMap = new Map(width, height);
        this.headGoal = null;
        this.goalObservers = new ArrayList<GoalObserver>();
    }
    
    //check if player overlap with pickupable things in the map
    public pickUpAble getPickable(int x, int y) {
		return dungeonMap.getPickable(x,y);
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dungeon other = (Dungeon) obj;
		if (dungeonMap == null) {
			if (other.dungeonMap != null)
				return false;
		} else if (!dungeonMap.equals(other.dungeonMap)) {
			System.err.println("Dungeon.equals() - diff: dungeonMap");
			return false;
		} if (goalObservers == null) {
			if (other.goalObservers != null) {
				System.err.println("Dungeon.equals() - diff: goalObservers");
				return false;
			}
		} else if (!goalObservers.equals(other.goalObservers)) {
			System.err.println("Dungeon.equals() - diff: goalObservers");
			return false;
		}
		if (headGoal == null) {
			if (other.headGoal != null) {
				System.err.println("Dungeon.equals() - diff: headGoal");
				return false;
			}
		} else if (!headGoal.equals(other.headGoal)) {
			System.err.println("Dungeon.equals() - diff: headGoal");
			return false;
		}if (player == null) {
			if (other.player != null) {
				System.err.println("Dungeon.equals() - diff: player");
				return false;
			}
		} else if (!player.equals(other.player)) {
			System.err.println("Dungeon.equals() - diff: player");
			return false;
		}
		return true;
	}

    public void removeEntity(Entity e) {
    	dungeonMap.removeEntity(e);
    	// If entity being removed affects goals notify them
    	if (e instanceof Enemy) {
    		if (!dungeonMap.isEnemyRemaining()) {
    			//System.err.println("Removing last enemy, notifying true");
    			notifyGoalObservers("enemies", true);
    		}
    	} else if (e instanceof Treasure) {
    		if (!dungeonMap.isTreasureRemaining()) {
    			//System.err.println("Removing last treasure, notifying true");
    			notifyGoalObservers("treasure", true);
    		}
    	}
    }
    
    public int getWidth() {
        return dungeonMap.getWidth();
    }

    public int getHeight() {
        return dungeonMap.getHeight();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
    	// Logic for goals, only notify if it will change whether goal is true or not
    	// eg when adding treasure when there was no treasure left before
    	if (entity instanceof Enemy) {
    		if (!dungeonMap.isEnemyRemaining()) {
    			//System.err.println("Adding first enemy, notifying false");
    			notifyGoalObservers("enemies", false);
    		}
    	} else if (entity instanceof Treasure) {
    		if (!dungeonMap.isTreasureRemaining()) {
    			//System.err.println("Adding first treasure, notifying false");
    			notifyGoalObservers("treasure", false);
    		}
    	}
    	
        dungeonMap.addEntity(entity);
        
    }
    
	@Override
	public String toString() {
		return "Dungeon [player=" + player + ", dungeonMap=" + dungeonMap + ", headGoal=" + headGoal
				+ ", goalObservers=" + goalObservers + "]";
	}
	
    public ArrayList<Entity> getEntities(int x, int y) {
    	return dungeonMap.getEntities(x, y);
    }
	
	public Enemy getEnemy(int x, int y) {
		return dungeonMap.getEnemy(x,y);
	}

	public boolean hasClosedDoor(int x, int y) {
		return dungeonMap.hasClosedDoor(x,y);
	}
	
	@Override
	public void notifyGoalObservers(String type, boolean bool) {
		for (GoalObserver obs : goalObservers) {
			//System.out.println(obs);
			obs.update(this, type, bool);
		}
	}

	@Override
	public void addGoalObserver(GoalObserver obs) {
		if (obs != null) {
			goalObservers.add(obs);
		}
	}

	@Override
	public void removeGoalObserver(GoalObserver obs) {	
		if (obs != null) {
			goalObservers.remove(obs);
		}
	}
	
	public void setGoal(Goal headGoal) {
		if (headGoal == null) return;
		this.headGoal = headGoal;
	}
	
	public Goal getGoal() {
		return this.headGoal;
	}

	// Requests to destroy player/boulder/enemy at given coordinates
	public void destroy(int x, int y) {
		// Range checking
		if (0 > x || x >= getWidth() || 0 > y || y >= getHeight()) {
			return;
		}
		System.err.println("Destroy "+x+" "+y);
		ArrayList<Entity> eList = getEntities(x,y);
		
		for (Entity e : eList) {
			// Special case: player is the entity - remove it
			if (e instanceof Player) {
				removeEntity(e);
				player = null;
				// Spec doesn't specify what to do if player dead, what till clarification
			} else if (e instanceof Boulder) {	// Destroy boulder
				System.err.println("Destroying boulder");
				removeEntity((Boulder) e);
			} else if (e instanceof Enemy) {	// Destroy enemy
				removeEntity(e);
			} else if (e == null) {
				System.err.println("Nothing was there");
			} else {
				System.err.println(e.getClass() +" was there");
			}
		}
	}
	
	public int destroyEnemy(int x, int y) {
		// copy from destroyEntity
		if (0 > x || x >= getWidth() || 0 > y || y >= getHeight()) {
			return 0;
		}
		System.err.println("Destroy "+x+" "+y);
		Entity e = getEnemy(x,y);
		if (e instanceof Enemy) {
			removeEntity(e);
			return 1;
		} else if (e == null) {
			System.err.println("Nothing was there");
		} else {
			System.err.println(e.getClass() +" was there");
		}
		return 3;
	}
	// Here temporarily for testing purposes
	//public Map getMap() { return dungeonMap; }

	public boolean goalsCompleted() {
		return this.headGoal.isComplete();
	}

	public boolean checkSwitches() {
		return dungeonMap.checkSwitches();
	}

	public boolean hasSwitch(int x, int y) {
		return this.dungeonMap.hasSwitch(x, y);
	}

	public boolean calculateCollisions(Moveable m, int x, int y) {
		return this.dungeonMap.calculateCollisions(m, x, y);
	}

	public boolean hasExit(int x, int y) {
		return this.dungeonMap.hasExit(x, y);
	}

	public Entity getEntity(int x, int y) {
		return this.dungeonMap.getEntity(x, y);
	}

	public Boolean hasWall(int x, int y) {
		return this.dungeonMap.hasExit(x, y);
	}

	public ArrayList<Enemy> getEnemies() {
		return this.dungeonMap.getEnemies();
	}
}
