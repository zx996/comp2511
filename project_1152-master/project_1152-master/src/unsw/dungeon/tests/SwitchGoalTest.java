package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.LeafGoal;
import unsw.dungeon.Player;
import unsw.dungeon.Switch;

class SwitchGoalTest {

	@Test
	void switchTestIncomplete() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Switch s = new Switch(2, 0);
		
		dungeon.addEntity(s);

		LeafGoal g = new LeafGoal("switch");
		
		dungeon.addGoalObserver(g);
		dungeon.setGoal(g);
				
		player.moveUp();
		
		assertEquals(false, dungeon.goalsCompleted());
	}
	
	@Test
	void singleSwitchTestUpdates() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Boulder boulder = new Boulder(dungeon, 1,0);
		Switch s = new Switch(2, 0);
		
		dungeon.addEntity(boulder);
		dungeon.addEntity(s);

		LeafGoal g = new LeafGoal("switch");
		
		dungeon.addGoalObserver(g);
		dungeon.setGoal(g);
		
		player.moveRight();
		
		assertEquals(2, boulder.getX());
		assertEquals(0, player.getX());
				
		assertEquals(true, dungeon.goalsCompleted());
		
		player.moveRight();
		player.moveRight();
		
		assertEquals(3, boulder.getX());
		assertEquals(1, player.getX());
		
		assertEquals(false, dungeon.goalsCompleted());
	}	
	
	@Test
	void singleSwitchTestComplete() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Boulder boulder = new Boulder(dungeon, 1,0);
		Switch s = new Switch(2, 0);
		
		dungeon.addEntity(boulder);
		dungeon.addEntity(s);

		LeafGoal g = new LeafGoal("switch");
		
		dungeon.addGoalObserver(g);
		dungeon.setGoal(g);
		
		player.moveRight();
		
		assertEquals(2, boulder.getX());
		assertEquals(0, player.getX());
				
		assertEquals(true, dungeon.goalsCompleted());
	}	
	
	@Test
	void multipleSwitchTestComplete() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Boulder boulderA = new Boulder(dungeon, 1, 0);
		Boulder boulderB = new Boulder(dungeon, 0, 1);
		Switch sA = new Switch(2, 0);
		Switch sB = new Switch(0, 2);
		
		dungeon.addEntity(boulderA);
		dungeon.addEntity(sA);
		dungeon.addEntity(boulderB);
		dungeon.addEntity(sB);

		LeafGoal g = new LeafGoal("switch");
		
		dungeon.addGoalObserver(g);
		dungeon.setGoal(g);

		assertEquals(false, dungeon.goalsCompleted());
		
		player.moveRight();
		
		assertEquals(false, dungeon.goalsCompleted());

		player.moveDown();
		
		assertEquals(true, dungeon.goalsCompleted());
	}	

}
