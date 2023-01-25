package object;

import entity.Entity;
import main.GamePanel;

public class ObjPotionRed extends Entity {
	GamePanel gp;
	
	public ObjPotionRed(GamePanel gp) {
		super(gp);
		
		this.gp= gp;
		type = TYPE_CONSUMABLE;
		name = "Red Potion";
		value = 5;
		down1 = setup("/objects/potion_red",gp.TILE_SIZE,gp.TILE_SIZE);
		description = "[" + name+ "]\nRestore HP by " + value + ".";
		price = 25;
		
	}
	
	public boolean use(Entity entity) {
		
		gp.gameState = gp.dialogState;
		gp.ui.currentDialogue = "You drink the " + name +"!\n"
				+ "Your life was recovered by " + value + ".";
		entity.life +=value;
		gp.playSE(2);
		return true;
	}
}
