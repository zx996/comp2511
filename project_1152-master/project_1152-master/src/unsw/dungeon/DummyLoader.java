package unsw.dungeon;

import java.io.FileNotFoundException;

public class DummyLoader extends DungeonLoader {

	public DummyLoader(String filename) throws FileNotFoundException {
		super(filename);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoad(Entity player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Wall wall) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Exit exit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Treasure treasure) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Door door) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Key key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Boulder boulder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Switch switchEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Bomb bomb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Enemy enemy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Sword sword) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(Potion potion) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) throws FileNotFoundException {
       DungeonLoader dl = new DummyLoader("advanced.json");
       dl.load();
    }

}
