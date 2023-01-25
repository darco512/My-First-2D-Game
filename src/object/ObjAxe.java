package object;

import entity.Entity;
import main.GamePanel;

public class ObjAxe extends Entity{

	public ObjAxe(GamePanel gp) {
		super(gp);
		
		type = TYPE_AXE;
		name = "Woodcutter's Axe";
		down1 = setup("/objects/axe", gp.TILE_SIZE, gp.TILE_SIZE);
		attackValue =2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]\nA bit rusty, but still\ncan cut some trees.";
		price = 75;
		knockBackPower =10;	
	}
	

}
