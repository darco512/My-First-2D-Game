package object;

import entity.Entity;
import main.GamePanel;

public class ObjCoinBronze extends Entity{
	
	GamePanel gp;
	
	
	public ObjCoinBronze(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = TYPE_PICKUP_ONLY;
		name = "Bronze coin";
		value = 1;
		down1 = setup("/objects/coin_bronze", gp.TILE_SIZE,gp.TILE_SIZE);
	}
	
	public boolean use(Entity entity) {
		
		gp.playSE(1);
		gp.ui.addMessage("Coin "+ value);
		gp.player.coin += value;
		return true;
	}

}
