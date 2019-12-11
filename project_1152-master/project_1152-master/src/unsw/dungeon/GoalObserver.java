package unsw.dungeon;

public interface GoalObserver {
	public void update(GoalObservable obs, String type, boolean bool);
}
