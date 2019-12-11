package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;

class PlayerMovementTest {

	@Test
	void PlayerMovementNoObstructions() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		
		player.moveDown();
        assertEquals(1,player.getY());
		player.moveRight();
        assertEquals(1,player.getX());
		player.moveUp();
        assertEquals(0,player.getY());
		player.moveLeft();
        assertEquals(0,player.getX());
	}

	@Test
	void PlayerMovementOutOfBounds() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 0, 0);
		
		player.moveUp();
        assertEquals(0,player.getY());
		player.moveLeft();
        assertEquals(0,player.getX());
        
        for (int i = 0; i < 100; i ++) {
            player.moveRight();
            player.moveDown();
        }
        
        assertEquals(9,player.getX());
        assertEquals(9,player.getY());
	}
	
	@Test
	void PlayerMovementWalls() {
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 5, 5);
		
		for (int i = 0; i < 9; i ++) {
			Wall westWall = new Wall(3,i);
			Wall eastWall = new Wall(7,i);
			Wall northWall = new Wall(i,3);
			Wall southWall = new Wall(i, 7);

			dungeon.addEntity(westWall);
			dungeon.addEntity(eastWall);
			dungeon.addEntity(northWall);
			dungeon.addEntity(southWall);
		}		
		
		for (int i = 0; i < 100; i ++) {
			player.moveRight();
		}
		assertEquals(6,player.getX());

		for (int i = 0; i < 200; i ++) {
			player.moveLeft();
		}
		assertEquals(4,player.getX());

		for (int i = 0; i < 100; i ++) {
			player.moveUp();
		}
		assertEquals(4,player.getY());

		for (int i = 0; i < 200; i ++) {
			player.moveDown();
		}
		assertEquals(6,player.getY());		
	}

	@Test
	void PlayerMovementBoulders() {
		
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 5, 6);
		Boulder boulder = new Boulder(dungeon, 5, 5);
		Boulder boulderBehind = new Boulder(dungeon, 5, 4);

		dungeon.addEntity(boulder);
		dungeon.addEntity(boulderBehind);
		        
        for (int i = 0; i < 10; i ++) {
    		player.moveUp();
        }
        
        assertEquals(6, player.getY());
        assertEquals(5, player.getX());
        
        player.moveLeft();
        player.moveUp();
        player.moveRight();
        
        assertEquals(5, player.getY());
        assertEquals(4, player.getX());
        
        assertEquals(5, boulder.getY());
        assertEquals(6, boulder.getX());
        
        player.moveRight();
        player.moveUp();
        
        assertEquals(5, player.getY());
        assertEquals(5, player.getX());
        
        assertEquals(3, boulderBehind.getY());
        assertEquals(5, boulderBehind.getX());
	}
	
	@Test
	void PlayerDirectoinMovementBoulders() {
		
		Dungeon dungeon = new Dungeon(10, 10);
		Player player = new Player(dungeon, 5, 5);
		Boulder boulderRight = new Boulder(dungeon, 6, 5);
		Boulder boulderLeft = new Boulder(dungeon, 4, 5);
		Boulder boulderUp = new Boulder(dungeon, 5, 6);
		Boulder boulderDown = new Boulder(dungeon, 5, 4);

		dungeon.addEntity(boulderRight);
		dungeon.addEntity(boulderLeft);		        
		dungeon.addEntity(boulderUp);
		dungeon.addEntity(boulderDown);
        
        player.moveLeft();
        player.moveUp();
        player.moveRight();
        player.moveDown();
        
        assertEquals(5, player.getY());
        assertEquals(5, player.getX());

        assertEquals(7, boulderRight.getX());
        assertEquals(5, boulderRight.getY());

        assertEquals(3, boulderLeft.getX());
        assertEquals(5, boulderLeft.getY());
        
        assertEquals(5, boulderDown.getX());
        assertEquals(3, boulderDown.getY());        

        assertEquals(5, boulderUp.getX());
        assertEquals(7, boulderUp.getY());
	}
}
