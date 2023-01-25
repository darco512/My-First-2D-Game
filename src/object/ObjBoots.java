package object;


import entity.Entity;
import main.GamePanel;

public class ObjBoots extends Entity{
	
	public ObjBoots(GamePanel gp) {
		super(gp);
		name = "Boots";
		down1 = setup("/objects/boots", gp.TILE_SIZE,gp.TILE_SIZE);

	}

}
	