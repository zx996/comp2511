package unsw.dungeon;

public class NormalPlayerTick implements TickingBehaviour {
	
	private Player player;
	
	public NormalPlayerTick(Player player) {
		this.player = player;
	}

	@Override
	public void tick() {
		player.completeExitGoal();
		player.enemiesFollow();
		player.notifyBombs();
	}
}
