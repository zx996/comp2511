package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.LeafGoal;
import unsw.dungeon.Player;
import unsw.dungeon.Sword;

class ExitGoalTest {

	@Test
	void exitTestComplete() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Exit exit = new Exit(1,0);
		
		dungeon.addEntity(exit);
		
		LeafGoal g = new LeafGoal("exit");
		
		dungeon.addGoalObserver(g);
		dungeon.setGoal(g);
		
		player.moveRight();
		
		assertEquals(true, dungeon.goalsCompleted());
	}
	
	@Test
	void exitTestIncomplete() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Exit exit = new Exit(1,0);
		
		dungeon.addEntity(exit);
		
		LeafGoal g = new LeafGoal("exit");
		
		dungeon.addGoalObserver(g);
		dungeon.setGoal(g);
		
		player.moveUp();
		
		assertEquals(false, dungeon.goalsCompleted());
	}

	@Test
	void exitTestDoubleCoOrdinate() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Exit exit = new Exit(1,0);
		Sword sword = new Sword(1,0);
		
		dungeon.addEntity(sword);
		dungeon.addEntity(exit);
		
		LeafGoal g = new LeafGoal("exit");
		
		dungeon.addGoalObserver(g);
		dungeon.setGoal(g);
		
		player.moveRight();
		
		assertEquals(true, dungeon.goalsCompleted());
	}
	
	@Test
	void exitTestUpdate() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Exit exit = new Exit(1,0);
		
		dungeon.addEntity(exit);
		
		LeafGoal g = new LeafGoal("exit");
		
		dungeon.addGoalObserver(g);
		dungeon.setGoal(g);
		
		player.moveRight();
		
		assertEquals(true, dungeon.goalsCompleted());

		
		player.moveLeft();
		
		assertEquals(false, dungeon.goalsCompleted());
	}
}
