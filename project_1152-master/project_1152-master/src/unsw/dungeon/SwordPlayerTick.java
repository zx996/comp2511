package unsw.dungeon;

public class SwordPlayerTick implements TickingBehaviour {
	
	private Player player;
	private int usesLeft;
	
	public SwordPlayerTick(Player player) {
		this.player = player;
		this.usesLeft = 5;
	}

	@Override
	public void tick() {		
		if (player.destroyAdjacentEnemies()) {
			usesLeft --;
		}
		player.completeExitGoal();
		player.enemiesFlee();
		player.notifyBombs();
		this.checkUses();
	}
	
	private void checkUses() {
		if (usesLeft == 0) {
			player.switchToNormal();
		}
	}
}
