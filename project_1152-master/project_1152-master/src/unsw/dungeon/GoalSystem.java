package unsw.dungeon;

// Deprecated
public class GoalSystem {
	private Goal goal;
	
	public GoalSystem(Goal g) {
		setGoal(g);
	}
	
	public boolean isCompleted() {
		return goal.isComplete();
	}
	
	// Set main goal to goals
	private void setGoal(Goal g) {
		if (g == null) return;
		goal = g;
	}
	
}
