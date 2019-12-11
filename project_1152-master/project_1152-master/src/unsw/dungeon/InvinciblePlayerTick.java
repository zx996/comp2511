package unsw.dungeon;

public class InvinciblePlayerTick implements TickingBehaviour {
	
	private Player player;
	private int timeLeft;
	
	public InvinciblePlayerTick(Player player) {
		this.player = player;
		this.timeLeft = 20;
	}

	@Override
	public void tick() {
		this.timeLeft -= 1;
		player.completeExitGoal();
		player.enemiesFollow();
		player.destroyAdjacentEnemies();
		player.notifyBombs();
		this.checkTime();
	}
	
	private void checkTime() {
		if (this.timeLeft == 0) {
			player.switchToNormal();
		}
	}

}
