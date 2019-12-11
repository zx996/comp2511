package unsw.dungeon;

public interface GoalObservable {
	public void notifyGoalObservers(String type, boolean bool);
	public void addGoalObserver(GoalObserver obs);
	public void removeGoalObserver(GoalObserver obs);	
}
