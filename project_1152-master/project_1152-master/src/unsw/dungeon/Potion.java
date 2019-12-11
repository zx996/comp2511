package unsw.dungeon;

public class Potion extends pickUpAble  {
		
		public Potion(int x, int y) {
			super(x, y);
		}
		
		@Override
		public boolean pickUp(int x, int y) {
			//calculate time left
			return super.pickUp(x, y);
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
