package unsw.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// TODO movement - minor
public class Map {
    private List<Entity> entities;
    private int width, height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addEntity(Entity entity) {
    	if (entity == null) return;
        entities.add(entity);
    }
    
    public pickUpAble getPickable(int x, int y) {
    	Iterator<Entity> entityItr = entities.iterator();
    	
    	while (entityItr.hasNext()) {
    		Entity curr = entityItr.next();
    		
    		if (curr.getX() == x && curr.getY() == y && (curr instanceof pickUpAble)) {
    			pickUpAble thing = (pickUpAble) curr;
    			return thing;
    		}
    	}
    	
    	return null;
    }

    public Entity getEntity(int x, int y) {
    	Iterator<Entity> entityItr = entities.iterator();
    	
    	while (entityItr.hasNext()) {
    		Entity curr = entityItr.next();
    		
    		if (curr.getX() == x && curr.getY() == y) {
    			return curr;
    		}
    	}
    	
    	return null;
    }

    public ArrayList<Entity> getEntities(int x, int y) {
    	ArrayList<Entity> results = new ArrayList<Entity>();
    	
    	Iterator<Entity> entityItr = entities.iterator();
    	
    	while (entityItr.hasNext()) {
    		Entity curr = entityItr.next();
    		
    		if (curr.getX() == x && curr.getY() == y) {
    			results.add(curr);
    		}
    	}

    	return results;
    }

    
    public Enemy getEnemy(int x, int y) {
    	Iterator<Entity> entityItr = entities.iterator();
    	
    	while (entityItr.hasNext()) {
    		Entity curr = entityItr.next();
    		
    		if (curr.getX() == x && curr.getY() == y && (curr instanceof Enemy)) {
    			Enemy enemy = (Enemy) curr;
    			return enemy;
    		}
    	}
    	
    	return null;
    }
    
	public boolean hasWall(int x, int y) {
		Entity givenEntity = getEntity(x,y);
		
		if (givenEntity instanceof Wall) {
			return true;
		}
		
		return false;
	}    

	public boolean hasBoulder(int x, int y) {
		ArrayList<Entity> givenEntity = getEntities(x,y);
		
		Iterator<Entity> itr = givenEntity.iterator();
		
		while (itr.hasNext()) {			
			if (itr.next() instanceof Boulder) {
				return true;
			}
		}
				
		return false;
	}    
	
    public void moveEntityUp(Moveable entity) {    	
    	entity.moveUp();
    }

    public void moveEntityDown(Moveable entity) {    	
    	entity.moveDown();
    }

    public void moveEntityRight(Moveable entity) {    	
    	entity.moveRight();
    }

    public void moveEntityLeft(Moveable entity) {    	
    	entity.moveLeft();
    }

	public boolean hasClosedDoor(int x, int y) {
		Entity givenEntity = getEntity(x,y);
		
		if (givenEntity instanceof Door) {
			return !((Door) givenEntity).isOpen();
		}
		
		return false;
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}
	
	public boolean isEnemyRemaining() {
		for (Entity e : entities) {
			if (e instanceof Enemy) {
				return true;
			}
		}
		return false;
	}

	public boolean isTreasureRemaining() {
		for (Entity e : entities) {
			if (e instanceof Treasure) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entities == null) ? 0 : entities.hashCode());
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public String toString() {
		return "Map [entities=" + entities + ", width=" + width + ", height=" + height + "]";
	}
	
	// Used to help with equals implementation when comparing list of entities
	// Needed since we don't care about our list order
	public static boolean areEntityListsEqual(List<Entity> list1, List<Entity> list2) {
	    if (list1 == null) {
	        return list2 == null;
	    }

	    if (list2 == null) {
	        return false;
	    }

	    for (Entity t : list1) {
	    	if (!list2.contains(t)) {
	    		System.err.println("Diff: "+t);
	    		return false;
	    	}
	    }
	    return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Map other = (Map) obj;
		if (entities == null) {
			if (other.entities != null)
				return false;
		} else if (!Map.areEntityListsEqual(entities, other.entities)) {
			System.err.println("Map.equals() diff - entites");
			return false;
		} if (height != other.height) {
			System.err.println("Map.equals() diff - height "+ height + "!="+other.height);
			return false;
		} if (width != other.width) {
			System.err.println("Map.equals() diff - width "+ width + "!="+other.width);
			return false;
		} return true;
	}

	private int countSwitches() {
		int count = 0;
		
		for (Entity e : this.entities) {
			if (e instanceof Switch)
				count += 1;
		}
		
		return count;
	}

	public boolean checkSwitches() {
		int totalSwitchCount = this.countSwitches();
		int currSwitchCount = 0;
		
		for (Entity e : this.entities) {
			if (e instanceof Boulder && ((Boulder) e).onSwitch()) {
				currSwitchCount += 1;
			}
		}
						
		return (totalSwitchCount == currSwitchCount);
	}

	public boolean hasSwitch(int x, int y) {
		for (Entity e : this.entities) {
			if (e instanceof Switch && e.getX()==x && e.getY()==y) {
				return true;
			}
		}

		return false;
	}

	public boolean calculateCollisions(Moveable m, int x, int y) {
		Boolean freePath = true;
		ArrayList<Entity> foundEntities = this.getEntities(x, y);
		
		for (Entity e : foundEntities) {
			if (e.collide(m)) {
				freePath = false;
			}
		}
		
		return freePath;
	}

	public boolean hasExit(int x, int y) {
		for (Entity e : this.entities) {
			if (e.getX() == x && e.getY() == y && e instanceof Exit) return true;
		}

		return false;
	}

	public ArrayList<Enemy> getEnemies() {
		ArrayList<Enemy> r = new ArrayList<Enemy>();
		
		for (Entity e : entities) {
			if (e instanceof Enemy) {
				r.add((Enemy) e);
			}
		}
		return r;
	}
}
