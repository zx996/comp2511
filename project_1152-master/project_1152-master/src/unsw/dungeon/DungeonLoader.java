package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        JSONObject jsonGoal = json.getJSONObject("goal-condition");
        //System.out.println(jsonGoal);
        Goal headGoal = parseJSONGoal(jsonGoal, dungeon);
        
        dungeon.setGoal(headGoal);
        return dungeon;
    }
    
    // Function used for testing ONLY
    public static Goal parseJSONGoal(JSONObject json) {
    	return parseJSONGoal(json, new Dungeon(1,1));
    }
    
    private static Goal parseJSONGoal(JSONObject jsonGoal, Dungeon d) {
    	
    	String type = jsonGoal.getString("goal");
    	//System.out.println(type);
    	Goal g;
    	JSONArray jsonSubgoals;
    	switch(type) {
    	// Base cases
    	case "exit":
    		g = new LeafGoal(type);
    		d.addGoalObserver((GoalObserver) g);
    		break;
    	case "enemies":
    		g = new LeafGoal(type);
    		d.addGoalObserver((GoalObserver) g);
    		break;
    	case "treasure":
    		g = new LeafGoal(type);
    		d.addGoalObserver((GoalObserver) g);
    		break;
    	case "boulders":
    		g = new LeafGoal(type);
    		d.addGoalObserver((GoalObserver) g);
    		break;
    	// Recusrive cases
    	case "AND":
    		g = new ComplexAndGoal();
    		// Getting jsonObject subgoals then parsing each to a Goal, then adding each to the parent goal
    		jsonSubgoals = jsonGoal.getJSONArray("subgoals");
    		//System.out.println(jsonSubgoals);
    		for (int i = 0; i < jsonSubgoals.length(); i++) {
                JSONObject jsonSubgoal = jsonSubgoals.getJSONObject(i);
                Goal subgoal = parseJSONGoal(jsonSubgoal,d);
                g.addGoal(subgoal);
            }
    		break;
    		
    	case "OR":
    		g = new ComplexOrGoal();
    		// Getting jsonObject subgoals then parsing each to a Goal, then adding each to the parent goal
    		jsonSubgoals = jsonGoal.getJSONArray("subgoals");
    		for (int i = 0; i < jsonSubgoals.length(); i++) {
                JSONObject jsonSubgoal = jsonSubgoals.getJSONObject(i);
                Goal subgoal = parseJSONGoal(jsonSubgoal,d);
                g.addGoal(subgoal);
            }
    		break;
    		
    	default:
    		System.err.println("Dungon loader: parsing goals - error");
    		g = null;
    	}
    	
    	//System.err.println(g);
    	return g;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        //System.out.println(json);
        int id;
        //System.out.println(type + " " + x + " "+y);

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
        	Exit exit = new Exit(x, y);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x, y);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "door":
        	id = json.getInt("id");
        	Door door = new Door(id,x,y);
        	onLoad(door);
        	entity = door;
        	break;
        case "key":
        	id = json.getInt("id");
        	Key key = new Key(id, x, y);
        	onLoad(key);
        	entity = key;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(dungeon, x, y);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "switch":
        	Switch switchEntity = new Switch(x, y);
        	onLoad(switchEntity);
        	entity = switchEntity;
        	break;
        case "bomb":
        	Bomb bomb = new Bomb(x, y);
        	onLoad(bomb);
        	entity = bomb;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(dungeon, x, y);
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "sword":
        	Sword sword = new Sword(x, y);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "invincibility":
        	Potion potion = new Potion(x, y);
        	onLoad(potion);
        	entity = potion;
        	break;
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);
    public abstract void onLoad(Wall wall);
    public abstract void onLoad(Exit exit);
	public abstract void onLoad(Treasure treasure);
	public abstract void onLoad(Door door);
	public abstract void onLoad(Key key);
	public abstract void onLoad(Boulder boulder);
	public abstract void onLoad(Switch switchEntity);
	public abstract void onLoad(Bomb bomb);
	public abstract void onLoad(Enemy enemy);
	public abstract void onLoad(Sword sword);
	public abstract void onLoad(Potion potion);
}
