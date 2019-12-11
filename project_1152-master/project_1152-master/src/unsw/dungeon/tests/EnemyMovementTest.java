package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
import unsw.dungeon.Sword;
import unsw.dungeon.Wall;

class EnemyMovementTest {
	
	@Test
	void EnemyMovementNoObstructions() {
		
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Enemy enemy = new Enemy(dungeon, 9, 9);
		player.addEnemy(enemy);
		dungeon.addEntity(enemy);
		        
        for (int i = 0; i < 100; i ++) {
    		player.moveWait();
        }
        
        assertEquals(player.getX(),enemy.getX());
        assertEquals(player.getY(),enemy.getY());
	}

	@Test
	void EnemyMovementBoulderObstructions() {
		
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Enemy enemy = new Enemy(dungeon, 9, 0);
		Boulder boulder = new Boulder(dungeon, 2,0);
		
		player.addEnemy(enemy);
		dungeon.addEntity(player);
		dungeon.addEntity(boulder);
		dungeon.addEntity(enemy);
		        
        for (int i = 0; i < 100; i ++) {
    		player.moveWait();
        }
                
        assertEquals(1, boulder.getX());
        assertEquals(0, boulder.getY());
        
        assertEquals(2,enemy.getX());
        assertEquals(0,enemy.getY());
	}
	
	@Test
	void EnemyMovementWallObstructions() {
		
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Enemy enemy = new Enemy(dungeon, 9, 9);
		
		player.addEnemy(enemy);
		dungeon.addEntity(player);
		dungeon.addEntity(enemy);
		
		for (int i = 0; i < 10; i ++) {
			Wall wall = new Wall(i,5);
			dungeon.addEntity(wall);
		}
		
        for (int i = 0; i < 100; i ++) {
    		player.moveWait();
        }
                
        assertEquals(6,enemy.getX());
        assertEquals(6,enemy.getY());
	}
	
	@Test
	void EnemyMovementFlee() {	
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		Enemy enemy = new Enemy(dungeon, 1, 1);
		Sword sword = new Sword(0,0);
		
		player.addEnemy(enemy);
		dungeon.addEntity(player);
		dungeon.addEntity(sword);
		dungeon.addEntity(enemy);
		
		player.pickUp(sword);
		
        for (int i = 0; i < 20; i ++) {
    		player.moveWait();
        }
        
        assertEquals(9,enemy.getX());
        assertEquals(9,enemy.getY());
	}
}
