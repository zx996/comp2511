package unsw.dungeon;

import java.util.ArrayList;
//import javafx.beans.property.IntegerProperty;

public class pickUpAble extends Entity {
	
	private boolean inBag = false; 
	//should be another value
    public pickUpAble(int x, int y) {
        super(x, y);

    }
    
    public boolean pickUp(int x, int y) {

    	if ((x == this.getX()) &&  (y == this.getY())) {
        	inBag = true;
    		return true;
    	}

    	return false;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (inBag ? 1231 : 1237);

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
		pickUpAble other = (pickUpAble) obj;
		if (inBag != other.inBag)
			return false;
		return true;
	}


}
