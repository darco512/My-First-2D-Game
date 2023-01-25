package object;

import entity.Entity;
import main.GamePanel;

public class ObjSwordNormal extends Entity{

	public ObjSwordNormal(GamePanel gp) {
		super(gp);

		type = TYPE_SWORD;
		name = "Normal Sword";
		down1 = setup("/objects/sword_normal",gp.TILE_SIZE,gp.TILE_SIZE);
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "["+name+"]\n" + "An old, rusty sword";
		price = 20;
		knockBackPower = 2;	
	}

}
