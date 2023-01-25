package object;

import entity.Entity;
import main.GamePanel;

public class ObjShieldBlue extends Entity{

	public ObjShieldBlue(GamePanel gp) {
		super(gp);
		
		type = TYPE_SHIELD;
		name = "Blue Shield";
		down1 = setup("/objects/shield_blue", gp.TILE_SIZE, gp.TILE_SIZE);
		defenseValue = 2;
		description = "[" +name +"]\nA shiny blue shield.";
		price = 200;
	}

}
