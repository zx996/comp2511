package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Bomb;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Entity;
import unsw.dungeon.Key;
import unsw.dungeon.Player;
import unsw.dungeon.Sword;

class EntityTest {
   	Dungeon dungeon ;
	Player player;
	Door door ;
	Key key;
	Sword sword;
	Bomb bomb;
	Bomb bomb2;
	Enemy e;
	
	@BeforeEach
	void init() {
		dungeon = new Dungeon(10, 10);
		player = new Player(dungeon, 5, 5);
		door = new Door(1, 5,6);
		key = new Key(1,5,5);
		dungeon.addEntity(door);
		dungeon.addEntity(key);
		
		sword = new Sword(5,5);
		dungeon.addEntity(sword);
		
		bomb = new Bomb(1,1);
		bomb2 = new Bomb(5,5);
		dungeon.addEntity(bomb);
		dungeon.addEntity(bomb2);
		
		e = new Enemy(dungeon, 5, 4);
		
	}
	

	@Test
	void testKey() {
		Boolean test = key instanceof Entity;
		Boolean test1 = key instanceof Key;
		assertEquals(true,test);
		assertEquals(true, test1);
		assertEquals(true, player.pickUp(key));
		assertEquals(true, player.haveKey(door.getDoorID()));
	}
	
	@Test
	void testDoor() {
		assertEquals(true, true);
		assertEquals(true, door.openDoor(key.getKeyID()));

		
	}
	
	@Test
	void testSword() {
		assertEquals(true, player.pickUp(sword));
		assertEquals(true, player.destroyAdjacentEnemies());
		assertEquals(true, player.destroyAdjacentEnemies());
		assertEquals(true, player.destroyAdjacentEnemies());
		assertEquals(true, player.destroyAdjacentEnemies());
		assertEquals(true, player.destroyAdjacentEnemies());
		assertEquals(false, player.destroyAdjacentEnemies());
		assertEquals(false, player.destroyAdjacentEnemies());
	}
	
	// Picking up the bomb
	@Test
	void testBomb() {
		assertEquals(false, player.pickUp(bomb));
		assertEquals(true, player.pickUp(bomb2));
		assertEquals(true, player.useBomb());
	}

}
