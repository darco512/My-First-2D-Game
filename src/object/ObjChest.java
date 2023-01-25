package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class ObjChest extends Entity {
	
	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
	public ObjChest(GamePanel gp, Entity loot) {
		super(gp);
		this.gp = gp;
		this.loot = loot;
		
		type = TYPE_OBSTACLE;
		name = "Chest";
		image = setup("/objects/chest", gp.TILE_SIZE,gp.TILE_SIZE);
		image2 = setup("/objects/chest_opened", gp.TILE_SIZE,gp.TILE_SIZE);
		down1 = image;
		collision = true;
		
		solidArea.x = 4;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

	}
	public void interact() {
		gp.gameState = gp.dialogState;
		if(opened == false) {
			gp.playSE(3);
			
			StringBuilder sb = new	StringBuilder();
			sb.append("You opened the chest and find a " + loot.name + "!");
			
			if(gp.player.inventory.size() == gp.player.maxInventorySize) {
				sb.append("\n...But you cannot carry anymore!" );
			}
			else {
				sb.append("\n You obtain the " + loot.name + "!");
				gp.player.inventory.add(loot);
				down1 = image2;
				opened = true;
			}
			gp.ui.currentDialogue = sb.toString();
		}
		else {
			gp.ui.currentDialogue = "It's empty...";
		}
	}
}
