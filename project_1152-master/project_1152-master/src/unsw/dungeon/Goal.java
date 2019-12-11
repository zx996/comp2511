package unsw.dungeon;

public interface Goal {
	public void addGoal(Goal g);
	public boolean isComplete();
	public boolean equals(Object o);
}
