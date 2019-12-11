package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!(this.getX() == other.getX()))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!(this.getY() == other.getY()))
			return false;
		return true;
	}

//	@Override
//	public String toString() {
//		return "Entity [x=" + x + ", y=" + y + "]";
//	}

	// IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
    	this.x = new SimpleIntegerProperty(x);
    	this.y = new SimpleIntegerProperty(y);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
    	return y;
    }

    public int getY() {
    	return y().get();
    }

    public int getX() {
    	return x().get();
    }
    
    public void setY(int y) {
    	this.y.set(y);;
    }
    
    public void setX(int x) {
    	this.x.set(x);
    }

	public boolean collide(Moveable m) {
		return false;
	}
}
