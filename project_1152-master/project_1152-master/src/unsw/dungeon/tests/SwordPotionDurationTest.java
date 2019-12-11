package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
import unsw.dungeon.Potion;
import unsw.dungeon.Sword;

class SwordPotionDurationTest {
	
	@Test
	void SwordUsesTest() {
		
		Dungeon dungeon = new Dungeon(2, 1);
		Player player = new Player(dungeon, 0, 0);
		Sword sword = new Sword(0,0);
		dungeon.addEntity(player);
		dungeon.addEntity(sword);
				
		for (int i = 0; i < 5; i ++) {
			Enemy enemy = new Enemy(dungeon, 1, 0);
			dungeon.addEntity(enemy);
			player.addEnemy(enemy);
		}
		
		assertEquals(true, player.isNormal());		
		player.pickUp(sword);
		assertEquals(false, player.isNormal());
		
		for (int i = 0; i < 10; i ++) {
			player.moveWait();
			ArrayList<Enemy> enemies = dungeon.getEnemies();

			for (Enemy e : enemies) {
				System.out.println(e);
			}
		}
				
		assertEquals(true, player.isNormal());
	}
	
	@Test
	void PotionTimeTest() {
		
		Dungeon dungeon = new Dungeon(1, 1);
		Player player = new Player(dungeon, 0, 0);
		Potion potion = new Potion(0,0);
		dungeon.addEntity(player);
		dungeon.addEntity(potion);
		
		assertEquals(true, player.isNormal());
		player.pickUp(potion);
		assertEquals(false, player.isNormal());
		
		for (int i = 0; i < 19; i ++) {
			player.moveWait();
			assertEquals(false, player.isNormal());
		}

		player.moveWait();
		assertEquals(true, player.isNormal());
	}
}
