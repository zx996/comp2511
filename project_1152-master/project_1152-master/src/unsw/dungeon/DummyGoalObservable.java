package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class DummyGoalObservable implements GoalObservable{

	private List<GoalObserver> observers;
	
	public DummyGoalObservable() {
		super();
		observers = new ArrayList<GoalObserver>();
	}
	
	@Override
	public void notifyGoalObservers(String type, boolean bool) {
		for (GoalObserver obs : observers) {
			obs.update(this, type, bool);
		}
		
	}

	@Override
	public void addGoalObserver(GoalObserver obs) {
		observers.add(obs);
	}

	@Override
	public void removeGoalObserver(GoalObserver obs) {
		observers.remove(obs);
	}

}
