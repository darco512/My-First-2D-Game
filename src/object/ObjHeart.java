package object;

import entity.Entity;
import main.GamePanel;

public class ObjHeart extends Entity{
	
	GamePanel gp;
	
	public ObjHeart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = TYPE_PICKUP_ONLY;
		name = "Heart";
		value =2;
		down1 = setup("/objects/heart_full", gp.TILE_SIZE,gp.TILE_SIZE);
		image = setup("/objects/heart_full", gp.TILE_SIZE,gp.TILE_SIZE);
		image2= setup("/objects/heart_half", gp.TILE_SIZE,gp.TILE_SIZE);
		image3= setup("/objects/heart_blank", gp.TILE_SIZE,gp.TILE_SIZE);
	}
	
	public boolean use(Entity entity) {
		
		gp.playSE(2);
		gp.ui.addMessage("Life + "+ value);
		entity.life += value;
		return true;
	}
}
