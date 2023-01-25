	package object;

import entity.Entity;
import main.GamePanel;

public class ObjDoor extends Entity {
	
	GamePanel gp; 
	
	public ObjDoor(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = TYPE_OBSTACLE;
		name = "Door";
		down1 = setup("/objects/door", gp.TILE_SIZE,gp.TILE_SIZE);
		collision=true;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32; 
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	public void interact() {
		
		gp.gameState = gp.dialogState;
		gp.ui.currentDialogue = "You need a key, to open this";
		}
}
