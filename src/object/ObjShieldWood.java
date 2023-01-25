package object;

import entity.Entity;
import main.GamePanel;

public class ObjShieldWood extends Entity{

	public ObjShieldWood(GamePanel gp) {
		super(gp);

		type = TYPE_SHIELD;
		name = "Wood Shield";
		down1 = setup("/objects/shield_wood",gp.TILE_SIZE,gp.TILE_SIZE);
		defenseValue = 1;
		description = "["+name+"]\n" + "An old wooden shield";
		price = 35;
	} 

}
