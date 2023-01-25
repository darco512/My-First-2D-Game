package object;


import entity.Entity;
import main.GamePanel;

public class ObjKey extends Entity{

	GamePanel gp;
	
	public ObjKey(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = TYPE_CONSUMABLE;
		name = "Key";
		down1 = setup("/objects/key", gp.TILE_SIZE,gp.TILE_SIZE);
		description = "["+name+"]\n" + "It opens a door";
		price = 100;
	}
	
	public boolean use(Entity entity) {
		
		gp.gameState = gp.dialogState;
		
		int objIndex = getDetected(entity, gp.obj , "Door");
		
		if(objIndex != 999) {
			gp.ui.currentDialogue = "You use the key " + name + " and open the door";
			gp.playSE(3);
			gp.obj[gp.currentMap][objIndex] = null;
			return true;
		}
		else {
			gp.ui.currentDialogue = "What are you doing?";
			return false;
		}
	}
}
