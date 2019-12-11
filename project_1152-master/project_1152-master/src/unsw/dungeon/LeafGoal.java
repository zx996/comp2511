package unsw.dungeon;

public class LeafGoal implements Goal, GoalObserver {
	
	private boolean completed;
	private String type;
	//private String id;
	
	public LeafGoal(String type){
		super();
		//id = null;
		this.type = type;
		completed = false;
	}
	
	@Override
	public String toString() {
		return "LeafGoal [completed=" + completed + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		LeafGoal other = (LeafGoal) obj;
		if (completed != other.completed)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public boolean isComplete() {
		return completed;
	}
	
	@Override
	public void update(GoalObservable obs, String type, boolean bool) {
		if (this.type.equals(type)){
			completed = bool;
		}
	}
	
	

	@Override
	public void addGoal(Goal g) {
		// Dummy
	}
}
