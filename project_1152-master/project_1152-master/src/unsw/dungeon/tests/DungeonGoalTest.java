package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.LeafGoal;
import unsw.dungeon.Map;
import unsw.dungeon.Treasure;

// Testing that dungeon notify system works properly
class DungeonGoalTest {

	@Test
	void testMapIsEnemiesRemaining() {
		Dungeon d = null;
		Map m = new Map(3,3);
		// Case: no enemies
		assertEquals(false, m.isEnemyRemaining());
		// Case: 1 enemy
		Enemy e1 = new Enemy(d, 1,1);
		m.addEntity(e1);
		assertEquals(true, m.isEnemyRemaining());
		// Case: more enemies
		Enemy e2 = new Enemy(d, 2,2);
		m.addEntity(e2);
		assertEquals(true, m.isEnemyRemaining());
		// Case: remove 1 enemy, 1 left
		m.removeEntity(e2);
		assertEquals(true, m.isEnemyRemaining());
		// Case: remove 1 enemy, none lfet
		m.removeEntity(e1);
		assertEquals(false, m.isEnemyRemaining());
	}
	
	@Test
	void testMapIsTreasureRemaining() {
		Map m = new Map(3,3);
		// Case: no treasure
		assertEquals(false, m.isTreasureRemaining());
		// Case: 1 treasure
		Treasure t1 = new Treasure(1,1);
		m.addEntity(t1);
		assertEquals(true, m.isTreasureRemaining());
		// Case: more treasure
		Treasure t2 = new Treasure(2,2);
		m.addEntity(t2);
		assertEquals(true, m.isTreasureRemaining());
		// Case: remove 1 treasure, 1 left
		m.removeEntity(t2);
		assertEquals(true, m.isTreasureRemaining());
		// Case: remove 1 treasure, none lfet
		m.removeEntity(t1);
		assertEquals(false, m.isTreasureRemaining());
	}
	
	@Test
	void testDungeonNotifyGoalObserversEnemies() {
		Dungeon d = new Dungeon(3,3);
		LeafGoal obs1 = new LeafGoal("enemies"); 
		d.addGoalObserver(obs1);
		// Checking notifyGoalObservers works
		d.notifyGoalObservers("enemies", false);
		assertEquals(false, obs1.isComplete());
		
		d.notifyGoalObservers("enemies", true);
		assertEquals(true, obs1.isComplete());
		
		d.notifyGoalObservers("enemies", true);
		
		//Case: adding an enemy
		Enemy e1 = new Enemy(null, 1,1);
		d.addEntity(e1);
		assertEquals(false, obs1.isComplete());
		
		//Case: adding another enemy
		Enemy e2 = new Enemy(null, 2,2);
		d.addEntity(e2);
		assertEquals(false, obs1.isComplete());
		
		//Case: removing an enemy, one left
		d.removeEntity(e2);
		assertEquals(false, obs1.isComplete());
		
		//Case: removing an enemy, none left
		d.removeEntity(e1);
		assertEquals(true, obs1.isComplete());
	}
	
	@Test
	void testDungeonNotifyGoalObserversTreasure() {
		Dungeon d = new Dungeon(3,3);
		LeafGoal obs1 = new LeafGoal("treasure"); 
		d.addGoalObserver(obs1);
		// Checking notifyGoalObservers works
		d.notifyGoalObservers("treasure", false);
		assertEquals(false, obs1.isComplete());
		
		d.notifyGoalObservers("treasure", true);
		assertEquals(true, obs1.isComplete());
		
		d.notifyGoalObservers("treasure", true);
		
		//Case: adding an enemy
		Treasure e1 = new Treasure(1,1);
		d.addEntity(e1);
		assertEquals(false, obs1.isComplete());
		
		//Case: adding another enemy
		Treasure e2 = new Treasure(2,2);
		d.addEntity(e2);
		assertEquals(false, obs1.isComplete());
		
		//Case: removing an enemy, one left
		d.removeEntity(e2);
		assertEquals(false, obs1.isComplete());
		
		//Case: removing an enemy, none left
		d.removeEntity(e1);
		assertEquals(true, obs1.isComplete());
	}
}
