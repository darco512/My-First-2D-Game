package entity;

import main.GamePanel;
import object.ObjAxe;
import object.ObjKey;
import object.ObjPotionRed;
import object.ObjShieldBlue;
import object.ObjShieldWood;
import object.ObjSwordNormal;

public class NPCMerchant extends Entity{
	public NPCMerchant(GamePanel gp) {
		
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
		setItems();
		
		
	}
	public void getImage() {
		
		up1=setup("/npc/merchant_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		up2 = setup("/npc/merchant_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
		down1 = setup("/npc/merchant_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		down2 = setup("/npc/merchant_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
		left1 = setup("/npc/merchant_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		left2 = setup("/npc/merchant_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
		right1 = setup("/npc/merchant_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		right2 = setup("/npc/merchant_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
	}
	public void setDialogue() {
		
		dialogues[0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";
		
	}
	public void setItems(){
		
		inventory.add(new ObjPotionRed(gp));
		inventory.add(new ObjKey(gp));
		inventory.add(new ObjSwordNormal(gp));
		inventory.add(new ObjAxe(gp));
		inventory.add(new ObjShieldWood(gp));
		inventory.add(new ObjShieldBlue(gp));
	}
	public void speak() {
		
		super.speak();
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}
}
