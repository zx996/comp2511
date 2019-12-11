package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Exit;
import unsw.dungeon.LitBomb;
import unsw.dungeon.Player;
import unsw.dungeon.Potion;
import unsw.dungeon.Switch;

class LitBombTest {

	@Test
	void testExplodeEmptyDungeon() {
		Dungeon d = new Dungeon(3,3);
		LitBomb b = new LitBomb(d,1,1);
		d.addEntity(b);
		assertEquals(1, b.getX());
		assertEquals(1, b.getY());
		b.explode();
		assertEquals(null, d.getEntity(1,1));	// Checking bomb disappears after explosion
	}
	
	@Test
	void testExplodeBoulders() {
		Dungeon d = new Dungeon(3,3);
		Boulder b1 = new Boulder(d,0,1);
		Boulder b2 = new Boulder(d,1,0);
		Boulder b3 = new Boulder(d,1,2);
		Boulder b4 = new Boulder(d,2,1);
		Boulder untouched = new Boulder(d,0,0);
		d.addEntity(b1);
		d.addEntity(b2);
		d.addEntity(b3);
		d.addEntity(b4);
		d.addEntity(untouched);
		LitBomb lb = new LitBomb(d,1,1);
		d.addEntity(lb);
		assertEquals(1, lb.getX());
		assertEquals(1, lb.getY());
		lb.explode();
		assertEquals(null, d.getEntity(1,1));
		// Checking boulders disappear
		assertEquals(null, d.getEntity(0, 1));
		assertEquals(null, d.getEntity(1, 0));
		assertEquals(null, d.getEntity(1, 2));
		assertEquals(null, d.getEntity(2, 1));
		// Checking boulder in corner is untouched
		assertEquals(0, untouched.getX());
		assertEquals(0, untouched.getY());
	}
	
	@Test
	void testExplodeEnemies() {
		Dungeon d = new Dungeon(3,3);
		Enemy b1 = new Enemy(d,0,1);
		Enemy b2 = new Enemy(d,1,0);
		Enemy b3 = new Enemy(d,1,2);
		Enemy b4 = new Enemy(d,2,1);
		Enemy untouched = new Enemy(d,0,0);
		d.addEntity(b1);
		d.addEntity(b2);
		d.addEntity(b3);
		d.addEntity(b4);
		d.addEntity(untouched);
		LitBomb lb = new LitBomb(d,1,1);
		d.addEntity(lb);
		assertEquals(lb, d.getEntity(1, 1));
		lb.explode();
		assertEquals(null, d.getEntity(1, 1));	// Checking bomb disappears after explosion
		// Checking Enemies disappear
		assertEquals(d.getEntity(0,1), null);	
		assertEquals(d.getEntity(1,0), null);
		assertEquals(d.getEntity(1,2), null);
		assertEquals(d.getEntity(2,1), null);
		// Checking Enemy in corner is untouched
		assertEquals(d.getEntity(0, 0), untouched);
	}
	
	// Testing that explosions will kill players
	@Test
	void testExplodePlayer() {
		Dungeon d = new Dungeon(3,3);
		Player p = new Player(d,1,0);
		d.addEntity(p);
		d.setPlayer(p);
		LitBomb b = new LitBomb(d,1,1);
		d.addEntity(b);
		b.explode();
		assertEquals(d.getEntity(1, 0), null);
	}
	
	// Testing that anything which isn't a player/boulder/enemy will not get destroyed
	@Test
	void testExplodeNonDestructables() {
		Dungeon d = new Dungeon(3,3);
		Door door = new Door(1,1,0);
		d.addEntity(door);
		Exit exit = new Exit(0,1);
		d.addEntity(exit);
		Switch s = new Switch(1,2);
		d.addEntity(s);
		Potion p = new Potion(2,1);
		d.addEntity(p);
		
		LitBomb b = new LitBomb(d,1,1);
		d.addEntity(b);
		b.explode();
		
		assertEquals(d.getEntity(1, 0), door);
		assertEquals(d.getEntity(0, 1), exit);
		assertEquals(d.getEntity(1, 2), s);
		assertEquals(d.getEntity(2, 1), p);
	}
	
	// Triggering explosion via tickdown instead of manual
	@Test
	void testTickDown() {
		Dungeon d = new Dungeon(3,3);
		LitBomb b = new LitBomb(d,1,1);
		d.addEntity(b);
		assertEquals(b, d.getEntity(1, 1));
		assertEquals(b.timeLeft(), 3);
		
		b.tickDown();
		assertEquals(b.timeLeft(), 2);
		assertEquals(b, d.getEntity(1, 1));
		
		b.tickDown();
		assertEquals(b.timeLeft(), 1);
		assertEquals(b, d.getEntity(1, 1));
		
		b.tickDown();
		assertEquals(null, d.getEntity(1, 1));	// Checking bomb disappears after explosion
	}

}
