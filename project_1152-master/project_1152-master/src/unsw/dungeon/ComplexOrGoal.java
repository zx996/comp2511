package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class ComplexOrGoal implements Goal {

	private List<Goal> goals;
	
	public ComplexOrGoal () {
		goals = new ArrayList<Goal>();
	}

	// Uses disjunction ie only one of its goals has to be complete to return true
	@Override
	public boolean isComplete() {
		for (Goal g : goals) {
			if (g.isComplete()) {
				return true;
			}
		}
		return false;
	}
	
	// Adds goal to current goal
	public void addGoal(Goal g) {
		if (g == null) return;
		goals.add(g);
	}

	@Override
	public String toString() {
		return "ComplexOrGoal [completed=" +isComplete()+", goals=" + goals + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goals == null) ? 0 : goals.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexOrGoal other = (ComplexOrGoal) obj;
		if (goals == null) {
			if (other.goals != null)
				return false;
		} else if (!goals.equals(other.goals))
			return false;
		return true;
	}

}
