package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Key;
import unsw.dungeon.Player;
import unsw.dungeon.Sword;

class DoorTest {
   	Dungeon dungeon ;
	Player player;
	Door door ;
	Key key;
	Sword sword;

	@Test
	void test() {
		dungeon = new Dungeon(10, 10);
		player = new Player(dungeon, 5, 5);
		door = new Door(1, 5,6);
		dungeon.addEntity(door);
		
		for (int i = 0; i < 10; i ++) {
			player.moveDown();
		}
		
		assertEquals(5,player.getY());		
		
		player.setKey(1);
		
		for (int i = 0; i < 2; i ++) {
			player.moveDown();
		}

		assertEquals(6,player.getY());		
	}

}
