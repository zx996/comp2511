package unsw.dungeon.tests;

//TODO
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Bomb;
import unsw.dungeon.Boulder;
import unsw.dungeon.ComplexAndGoal;
import unsw.dungeon.ComplexOrGoal;
import unsw.dungeon.Door;
import unsw.dungeon.DummyLoader;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Enemy;
import unsw.dungeon.Exit;
import unsw.dungeon.Goal;
import unsw.dungeon.Key;
import unsw.dungeon.LeafGoal;
import unsw.dungeon.Player;
import unsw.dungeon.Potion;
import unsw.dungeon.Switch;
import unsw.dungeon.Sword;
import unsw.dungeon.Treasure;
import unsw.dungeon.Wall;

class LoaderTest {
	
	// Quick check to ensure that equals methods are implemented properly
	// Where each object is actually equal
	@Test 
	void testEquals() {
		assertEquals(new Dungeon(1,1),new Dungeon(1,1));
		assertNotEquals(new Dungeon (1,2), new Dungeon(1,1));
		//Dungeon d1 = new Dungeon(1, 1);
		//Dungeon d2 = new Dungeon(1, 1);
		//System.out.println(d1.equals(d2));
		assertEquals(new Wall(0,0), new Wall(0,0));
		assertEquals(new Bomb(0,0),new Bomb(0,0));
		assertEquals(new Boulder(null,0,0),new Boulder(null,0,0));
		assertEquals(new Door(1,0,0),new Door(1,0,0));
		assertEquals(new Enemy(null, 0, 0), new Enemy(null, 0, 0));
		assertEquals(new Exit(0,0),new Exit(0,0));
		assertEquals(new Key(1,0,0),new Key(1,0,0));
		assertEquals(new Player(null, 0, 0), new Player(null, 0, 0));
		assertEquals(new Potion(0,0), new Potion(0,0));
		assertEquals(new Switch(0,0),new Switch(0,0));
		assertEquals(new Sword(0,0),new Sword(0,0));
		assertEquals(new Treasure(0,0),new Treasure(0,0));
	}
	
	// The base case for goals
	@Test
	void testSimpleGoals() {
		List<String> goalTypes = new ArrayList<String>();
		goalTypes.add("enemies");
		goalTypes.add("exit");
		goalTypes.add("treasure");
		goalTypes.add("boulders");
		
		for (String type : goalTypes) {
			JSONObject j = new JSONObject();
			j.put("goal", type);
			// Using testing only function
			Goal g = DummyLoader.parseJSONGoal(j);
			assertEquals(g, new LeafGoal(type));
		}
		
	}
	
	// The recursive case for goals
	// Checking every possible combination
	@Test
	void testComplexGoals() {
		List<String> goalTypes = new ArrayList<String>();
		goalTypes.add("enemies");
		goalTypes.add("exit");
		goalTypes.add("treasure");
		goalTypes.add("boulders");
		
		List<String> complexTypes = new ArrayList<String>();
		complexTypes.add("AND");
		complexTypes.add("OR");
		
		for (String complex : complexTypes) {
			for (String type1 : goalTypes) {
				for (String type2 : goalTypes) {
					// Creating goal in json format
					JSONObject j = new JSONObject();
					JSONArray subs = new JSONArray();
					JSONObject sub1 = new JSONObject();
					JSONObject sub2 = new JSONObject();
					sub1.put("goal", type1);
					sub2.put("goal", type2);
					subs.put(sub1);
					subs.put(sub2);
					j.put("goal", complex);
					j.put("subgoals", subs);
					Goal g = DummyLoader.parseJSONGoal(j);
					
					// Creating goal as an object
					Goal l1 = new LeafGoal(type1);
					Goal l2 = new LeafGoal(type2);
					Goal p;
					if (complex.equals("AND")) {
						p = new ComplexAndGoal();
					} else {
						p = new ComplexOrGoal();
					}
					p.addGoal(l1);
					p.addGoal(l2);
					// Making sure both are equal
					assertEquals(g, p);
				}
			}
		}
	}
	
	// Testing custom 1 by 1 dungeon I created via json
	@Test 
	void test1by1JSON() throws FileNotFoundException {
		DungeonLoader dl = new DummyLoader("1by1.json");
        Dungeon d = dl.load();
        
        // Recreating what should have been created
        Dungeon cpy = new Dungeon(1,1);
        cpy.addEntity(new Wall(0,0));
        LeafGoal g = new LeafGoal("exit");
        cpy.addGoalObserver(g);
        cpy.setGoal(g);
        //System.out.println(d);
        //System.out.println(cpy);
        assertEquals(d.toString(), cpy.toString());
        assertEquals(d, cpy);
	}
	
	// Testing custom dungeon with one of each entity created via json
	@Test
	void testEntitiesJSON() throws FileNotFoundException {
		DungeonLoader dl = new DummyLoader("entities.json");
        Dungeon d = dl.load();
        
        // Recreating what should have been created
        Dungeon c = new Dungeon(1,12);
        LeafGoal g = new LeafGoal("exit");
        c.setGoal(g);
        c.addGoalObserver(g);
        Player player = new Player(c, 0,0);
        c.addEntity(player);
        c.setPlayer(player);
        c.addEntity(new Wall(0,1));
        c.addEntity(new Exit(0,2));
        c.addEntity(new Treasure(0,3));
        c.addEntity(new Door(1,0,4));
        c.addEntity(new Key(1,0,5));
        c.addEntity(new Boulder(c, 0,6));
        c.addEntity(new Switch(0, 7));
        c.addEntity(new Bomb(0, 8));
        c.addEntity(new Enemy(c,0,9));
        c.addEntity(new Sword(0,10));
        c.addEntity(new Potion(0, 11));
        //System.out.println(d);
        //System.out.println(c);
        //assertEquals(d.toString(), c.toString());
        
        assertEquals(d,c);
	}
	
	// Test using given dungeon "maze"
	@Test
	void testMazeJSON() throws FileNotFoundException {
		DungeonLoader dl = new DummyLoader("maze.json");
        Dungeon d1 = dl.load();
        
        Dungeon d = new Dungeon(20,18);
        
        d.addEntity(new Wall(0,0));
        d.addEntity(new Wall(1,0));
        d.addEntity(new Wall(2,0));
        d.addEntity(new Wall(3,0));
        d.addEntity(new Wall(4,0));
        d.addEntity(new Wall(5,0));
        d.addEntity(new Wall(6,0));
        d.addEntity(new Wall(7,0));
        d.addEntity(new Wall(8,0));
        d.addEntity(new Wall(9,0));
        d.addEntity(new Wall(10,0));
        d.addEntity(new Wall(11,0));
        d.addEntity(new Wall(12,0));
        d.addEntity(new Wall(13,0));
        d.addEntity(new Wall(14,0));
        d.addEntity(new Wall(15,0));
        d.addEntity(new Wall(16,0));
        d.addEntity(new Wall(17,0));
        d.addEntity(new Wall(18,0));
        d.addEntity(new Wall(19,0));
        d.addEntity(new Wall(0,1));
        Player p = new Player(d,1,1);
        d.addEntity(p);
        d.setPlayer(p);
        d.addEntity(new Wall(2,1));
        d.addEntity(new Wall(15,1));
        d.addEntity(new Wall(16,1));
        d.addEntity(new Wall(17,1));
        d.addEntity(new Wall(18,1));
        d.addEntity(new Wall(19,1));
        d.addEntity(new Wall(0,2));
        d.addEntity(new Wall(2,2));
        d.addEntity(new Wall(4,2));
        d.addEntity(new Wall(5,2));
        d.addEntity(new Wall(6,2));
        d.addEntity(new Wall(7,2));
        d.addEntity(new Wall(8,2));
        d.addEntity(new Wall(9,2));
        d.addEntity(new Wall(10,2));
        d.addEntity(new Wall(11,2));
        d.addEntity(new Wall(12,2));
        d.addEntity(new Wall(13,2));
        d.addEntity(new Wall(19,2));
        d.addEntity(new Wall(0,3));
        d.addEntity(new Wall(7,3));
        d.addEntity(new Wall(13,3));
        d.addEntity(new Wall(14,3));
        d.addEntity(new Wall(15,3));
        d.addEntity(new Wall(16,3));
        d.addEntity(new Wall(17,3));
        d.addEntity(new Wall(19,3));
        d.addEntity(new Wall(0,4));
        d.addEntity(new Wall(1,4));
        d.addEntity(new Wall(3,4));
        d.addEntity(new Wall(5,4));
        d.addEntity(new Wall(8,4));
        d.addEntity(new Wall(9,4));
        d.addEntity(new Wall(11,4));
        d.addEntity(new Wall(13,4));
        d.addEntity(new Wall(19,4));
        d.addEntity(new Wall(0,5));
        d.addEntity(new Wall(1,5));
        d.addEntity(new Wall(3,5));
        d.addEntity(new Wall(5,5));
        d.addEntity(new Wall(6,5));
        d.addEntity(new Wall(9,5));
        d.addEntity(new Wall(11,5));
        d.addEntity(new Wall(13,5));
        d.addEntity(new Wall(14,5));
        d.addEntity(new Wall(15,5));
        d.addEntity(new Wall(16,5));
        d.addEntity(new Wall(17,5));
        d.addEntity(new Wall(18,5));
        d.addEntity(new Wall(19,5));
        d.addEntity(new Wall(0,6));
        d.addEntity(new Wall(1,6));
        d.addEntity(new Wall(3,6));
        d.addEntity(new Wall(5,6));
        d.addEntity(new Wall(6,6));
        d.addEntity(new Wall(7,6));
        d.addEntity(new Wall(9,6));
        d.addEntity(new Wall(11,6));
        d.addEntity(new Wall(13,6));
        d.addEntity(new Wall(19,6));
        d.addEntity(new Wall(0,7));
        d.addEntity(new Wall(1,7));
        d.addEntity(new Wall(3,7));
        d.addEntity(new Wall(5,7));
        d.addEntity(new Wall(6,7));
        d.addEntity(new Wall(7,7));
        d.addEntity(new Wall(9,7));
        d.addEntity(new Wall(11,7));
        d.addEntity(new Wall(12,7));
        d.addEntity(new Wall(13,7));
        d.addEntity(new Wall(15,7));
        d.addEntity(new Wall(16,7));
        d.addEntity(new Wall(17,7));
        d.addEntity(new Wall(19,7));
        d.addEntity(new Wall(0,8));
        d.addEntity(new Wall(3,8));
        d.addEntity(new Wall(7,8));
        d.addEntity(new Wall(9,8));
        d.addEntity(new Wall(13,8));
        d.addEntity(new Wall(15,8));
        d.addEntity(new Wall(17,8));
        d.addEntity(new Wall(19,8));
        d.addEntity(new Wall(0,9));
        d.addEntity(new Wall(2,9));
        d.addEntity(new Wall(3,9));
        d.addEntity(new Wall(5,9));
        d.addEntity(new Wall(7,9));
        d.addEntity(new Wall(9,9));
        d.addEntity(new Wall(13,9));
        d.addEntity(new Wall(15,9));
        d.addEntity(new Wall(17,9));
        d.addEntity(new Wall(19,9));
        d.addEntity(new Wall(0,10));
        d.addEntity(new Wall(2,10));
        d.addEntity(new Wall(5,10));
        d.addEntity(new Wall(7,10));
        d.addEntity(new Wall(9,10));
        d.addEntity(new Wall(10,10));
        d.addEntity(new Wall(11,10));
        d.addEntity(new Wall(13,10));
        d.addEntity(new Wall(15,10));
        d.addEntity(new Wall(17,10));
        d.addEntity(new Wall(19,10));
        d.addEntity(new Wall(0,11));
        d.addEntity(new Wall(2,11));
        d.addEntity(new Wall(5,11));
        d.addEntity(new Wall(7,11));
        d.addEntity(new Wall(11,11));
        d.addEntity(new Wall(13,11));
        d.addEntity(new Wall(15,11));
        d.addEntity(new Wall(17,11));
        d.addEntity(new Wall(19,11));
        d.addEntity(new Wall(0,12));
        d.addEntity(new Wall(2,12));
        d.addEntity(new Wall(3,12));
        d.addEntity(new Wall(5,12));
        d.addEntity(new Wall(7,12));
        d.addEntity(new Wall(8,12));
        d.addEntity(new Wall(9,12));
        d.addEntity(new Wall(11,12));
        d.addEntity(new Wall(13,12));
        d.addEntity(new Wall(15,12));
        d.addEntity(new Wall(17,12));
        d.addEntity(new Wall(19,12));
        d.addEntity(new Wall(0,13));
        d.addEntity(new Wall(3,13));
        d.addEntity(new Wall(5,13));
        d.addEntity(new Wall(9,13));
        d.addEntity(new Wall(11,13));
        d.addEntity(new Wall(13,13));
        d.addEntity(new Wall(15,13));
        d.addEntity(new Wall(17,13));
        d.addEntity(new Wall(19,13));
        d.addEntity(new Wall(0,14));
        d.addEntity(new Wall(1,14));
        d.addEntity(new Wall(3,14));
        d.addEntity(new Wall(6,14));
        d.addEntity(new Wall(7,14));
        d.addEntity(new Wall(8,14));
        d.addEntity(new Wall(9,14));
        d.addEntity(new Wall(11,14));
        d.addEntity(new Wall(13,14));
        d.addEntity(new Wall(15,14));
        d.addEntity(new Wall(17,14));
        d.addEntity(new Wall(19,14));
        d.addEntity(new Wall(0,15));
        d.addEntity(new Wall(1,15));
        d.addEntity(new Wall(3,15));
        d.addEntity(new Wall(4,15));
        d.addEntity(new Wall(9,15));
        d.addEntity(new Wall(11,15));
        d.addEntity(new Wall(13,15));
        d.addEntity(new Wall(17,15));
        d.addEntity(new Wall(19,15));
        d.addEntity(new Wall(0,16));
        d.addEntity(new Wall(1,16));
        d.addEntity(new Wall(6,16));
        d.addEntity(new Wall(7,16));
        d.addEntity(new Wall(8,16));
        d.addEntity(new Wall(15,16));
        d.addEntity(new Wall(16,16));
        d.addEntity(new Wall(17,16));
        d.addEntity(new Exit(18,16));
        d.addEntity(new Wall(19,16));
        d.addEntity(new Wall(0,17));
        d.addEntity(new Wall(1,17));
        d.addEntity(new Wall(2,17));
        d.addEntity(new Wall(3,17));
        d.addEntity(new Wall(4,17));
        d.addEntity(new Wall(5,17));
        d.addEntity(new Wall(6,17));
        d.addEntity(new Wall(7,17));
        d.addEntity(new Wall(8,17));
        d.addEntity(new Wall(9,17));
        d.addEntity(new Wall(10,17));
        d.addEntity(new Wall(11,17));
        d.addEntity(new Wall(12,17));
        d.addEntity(new Wall(13,17));
        d.addEntity(new Wall(14,17));
        d.addEntity(new Wall(15,17));
        d.addEntity(new Wall(16,17));
        d.addEntity(new Wall(17,17));
        d.addEntity(new Wall(18,17));
        d.addEntity(new Wall(19,17));
        LeafGoal g = new LeafGoal("exit");
        d.setGoal(g);
        d.addGoalObserver(g);
        
        //System.out.println(d);
        //System.out.println(d1);
        //System.out.println(d.getMap().equals(d1.getMap()));
        //System.out.println(d.equals(d1));
        assertEquals(d, d1);
       
	}
	
	// Test using given dungeon "boulders"
	@Test
	void testBouldersJSON() throws FileNotFoundException {
		DungeonLoader dl = new DummyLoader("boulders.json");
        Dungeon actual = dl.load();
        //System.out.println("Height " + actual.getHeight());
        
        Dungeon d = new Dungeon(8,9);
        d.addEntity(new Wall(2,0));
        d.addEntity(new Wall(3,0));
        d.addEntity(new Wall(4,0));
        d.addEntity(new Wall(5,0));
        d.addEntity(new Wall(6,0));
        d.addEntity(new Wall(0,1));
        d.addEntity(new Wall(1,1));
        d.addEntity(new Wall(2,1));
        d.addEntity(new Wall(6,1));
        d.addEntity(new Wall(0,2));
        d.addEntity(new Switch(1,2));
        d.addEntity(new Wall(6,2));
        d.addEntity(new Wall(0,3));
        d.addEntity(new Wall(1,3));
        d.addEntity(new Wall(2,3));
        d.addEntity(new Switch(5,3));
        d.addEntity(new Wall(6,3));
        d.addEntity(new Wall(0,4));
        d.addEntity(new Switch(1,4));
        d.addEntity(new Wall(2,4));
        d.addEntity(new Wall(3,4));
        d.addEntity(new Wall(6,4));
        d.addEntity(new Wall(0,5));
        d.addEntity(new Wall(2,5));
        d.addEntity(new Switch(4,5));
        d.addEntity(new Wall(6,5));
        d.addEntity(new Wall(7,5));
        d.addEntity(new Wall(0,6));
        d.addEntity(new Switch(3,6));
        d.addEntity(new Switch(6,6));
        d.addEntity(new Wall(7,6));
        d.addEntity(new Wall(0,7));
        d.addEntity(new Switch(4,7));
        d.addEntity(new Wall(7,7));
        d.addEntity(new Wall(0,8));
        d.addEntity(new Wall(1,8));
        d.addEntity(new Wall(2,8));
        d.addEntity(new Wall(3,8));
        d.addEntity(new Wall(4,8));
        d.addEntity(new Wall(5,8));
        d.addEntity(new Wall(6,8));
        d.addEntity(new Wall(7,8));
        Player p = new Player(d,2,2);
        d.addEntity(p);
        d.setPlayer(p);
        d.addEntity(new Boulder(d,3,2));
        d.addEntity(new Boulder(d,4,3));
        d.addEntity(new Boulder(d,4,4));
        d.addEntity(new Boulder(d,1,6));
        d.addEntity(new Boulder(d,3,6));
        d.addEntity(new Boulder(d,4,6));
        d.addEntity(new Boulder(d,5,6));
        LeafGoal l = new LeafGoal("boulders");
        d.addGoalObserver(l);
        d.setGoal(l);
        
        assertEquals(actual, d);
	}
	
	//TODO
	// Test using given dungeon "advanced"
	@Test
	void testAdvancedJSON() throws FileNotFoundException {
		DungeonLoader dl = new DummyLoader("advanced.json");
        Dungeon actual = dl.load();
        //System.out.println(d);
        
        Dungeon d = new Dungeon(18,16);
        d.addEntity(new Wall(0,0));
        d.addEntity(new Wall(1,0));
        d.addEntity(new Wall(2,0));
        d.addEntity(new Wall(3,0));
        d.addEntity(new Wall(4,0));
        d.addEntity(new Wall(5,0));
        d.addEntity(new Wall(6,0));
        d.addEntity(new Wall(7,0));
        d.addEntity(new Wall(8,0));
        d.addEntity(new Wall(9,0));
        d.addEntity(new Wall(10,0));
        d.addEntity(new Wall(11,0));
        d.addEntity(new Wall(12,0));
        d.addEntity(new Wall(13,0));
        d.addEntity(new Wall(14,0));
        d.addEntity(new Wall(15,0));
        d.addEntity(new Wall(16,0));
        d.addEntity(new Wall(17,0));
        d.addEntity(new Wall(0,1));
        Player p = new Player(d,1,1);
        d.addEntity(p);
        d.setPlayer(p);
        d.addEntity(new Sword(6,1));
        d.addEntity(new Wall(17,1));
        d.addEntity(new Wall(0,2));
        d.addEntity(new Wall(3,2));
        d.addEntity(new Wall(6,2));
        d.addEntity(new Wall(7,2));
        d.addEntity(new Wall(8,2));
        d.addEntity(new Wall(9,2));
        d.addEntity(new Wall(12,2));
        d.addEntity(new Wall(13,2));
        d.addEntity(new Wall(14,2));
        d.addEntity(new Wall(15,2));
        d.addEntity(new Wall(17,2));
        d.addEntity(new Wall(0,3));
        d.addEntity(new Wall(3,3));
        d.addEntity(new Wall(4,3));
        d.addEntity(new Wall(15,3));
        d.addEntity(new Wall(17,3));
        d.addEntity(new Wall(0,4));
        d.addEntity(new Wall(8,4));
        d.addEntity(new Bomb(13,4));
        d.addEntity(new Wall(17,4));
        d.addEntity(new Wall(0,5));
        d.addEntity(new Enemy(d,3,5));
        d.addEntity(new Wall(8,5));
        d.addEntity(new Wall(17,5));
        d.addEntity(new Wall(0,6));
        d.addEntity(new Wall(6,6));
        d.addEntity(new Wall(8,6));
        d.addEntity(new Wall(9,6));
        d.addEntity(new Wall(13,6));
        d.addEntity(new Wall(17,6));
        d.addEntity(new Wall(0,7));
        d.addEntity(new Wall(6,7));
        d.addEntity(new Wall(12,7));
        d.addEntity(new Wall(13,7));
        d.addEntity(new Wall(17,7));
        d.addEntity(new Wall(0,8));
        d.addEntity(new Wall(3,8));
        d.addEntity(new Wall(4,8));
        d.addEntity(new Wall(5,8));
        d.addEntity(new Wall(6,8));
        d.addEntity(new Wall(8,8));
        d.addEntity(new Wall(9,8));
        d.addEntity(new Wall(10,8));
        d.addEntity(new Wall(13,8));
        d.addEntity(new Wall(17,8));
        d.addEntity(new Wall(0,9));
        d.addEntity(new Wall(9,9));
        d.addEntity(new Wall(13,9));
        d.addEntity(new Wall(14,9));
        d.addEntity(new Wall(17,9));
        d.addEntity(new Wall(0,10));
        d.addEntity(new Wall(3,10));
        d.addEntity(new Wall(5,10));
        d.addEntity(new Wall(6,10));
        d.addEntity(new Treasure(7,10));
        d.addEntity(new Potion(11,10));
        d.addEntity(new Wall(17,10));
        d.addEntity(new Wall(0,11));
        d.addEntity(new Wall(3,11));
        d.addEntity(new Wall(6,11));
        d.addEntity(new Wall(8,11));
        d.addEntity(new Wall(9,11));
        d.addEntity(new Wall(10,11));
        d.addEntity(new Wall(12,11));
        d.addEntity(new Wall(17,11));
        d.addEntity(new Wall(0,12));
        d.addEntity(new Wall(3,12));
        d.addEntity(new Wall(9,12));
        d.addEntity(new Wall(12,12));
        d.addEntity(new Wall(17,12));
        d.addEntity(new Wall(0,13));
        d.addEntity(new Wall(3,13));
        d.addEntity(new Wall(4,13));
        d.addEntity(new Wall(5,13));
        d.addEntity(new Wall(12,13));
        d.addEntity(new Wall(13,13));
        d.addEntity(new Wall(14,13));
        d.addEntity(new Wall(17,13));
        d.addEntity(new Wall(0,14));
        d.addEntity(new Wall(17,14));
        d.addEntity(new Wall(0,15));
        d.addEntity(new Wall(1,15));
        d.addEntity(new Wall(2,15));
        d.addEntity(new Wall(3,15));
        d.addEntity(new Wall(4,15));
        d.addEntity(new Wall(5,15));
        d.addEntity(new Wall(6,15));
        d.addEntity(new Wall(7,15));
        d.addEntity(new Wall(8,15));
        d.addEntity(new Wall(9,15));
        d.addEntity(new Wall(10,15));
        d.addEntity(new Wall(11,15));
        d.addEntity(new Wall(12,15));
        d.addEntity(new Wall(13,15));
        d.addEntity(new Wall(14,15));
        d.addEntity(new Wall(15,15));
        d.addEntity(new Wall(16,15));
        d.addEntity(new Wall(17,15));
        ComplexAndGoal g = new ComplexAndGoal();
        LeafGoal l1 = new LeafGoal("enemies");
        d.addGoalObserver(l1);
        g.addGoal(l1);
        LeafGoal l2 = new LeafGoal("treasure");
        d.addGoalObserver(l2);
        g.addGoal(l2);
        d.setGoal(g);
        
        assertEquals(actual, d);
        
	}

}
