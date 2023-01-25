package main;

import entity.NPCMerchant;
import entity.NPCOldMan;
import monster.MonGreenSlime;
import object.ObjAxe;
import object.ObjChest;
import object.ObjDoor;
import object.ObjHeart;
import object.ObjKey;
import object.ObjManaCrystal;
import object.ObjPotionRed;
import object.ObjShieldBlue;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	public void setObject() {
		
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new ObjHeart(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE*25;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE*19;
		i++;
		gp.obj[mapNum][i] = new ObjManaCrystal(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE*21;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE*19;
		i++;
		gp.obj[mapNum][i] = new ObjAxe(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE*26;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE*21;
		i++;
		gp.obj[mapNum][i] = new ObjShieldBlue(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE*26;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE*22;
		i++;
		gp.obj[mapNum][i] = new ObjPotionRed(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE*22;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE*22;
		i++;
		gp.obj[mapNum][i] = new ObjDoor(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE*14;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE*28;
		i++;
		gp.obj[mapNum][i] = new ObjDoor(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE*12;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE*12;
		i++;
		gp.obj[mapNum][i] = new ObjChest(gp,new ObjKey(gp));
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE*23;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE*25;
		i++;
	}
	
	public void setNPC() {
		
		int mapNum = 0;
		int i =0;
		gp.npc[mapNum][i] = new NPCOldMan(gp);
		gp.npc[mapNum][i].worldX = gp.TILE_SIZE*21;
		gp.npc[mapNum][i].worldY = gp.TILE_SIZE*21;
		i++;
		
		mapNum = 1;
		i=0;
		gp.npc[mapNum][i] = new NPCMerchant(gp);
		gp.npc[mapNum][i].worldX = gp.TILE_SIZE*12;
		gp.npc[mapNum][i].worldY = gp.TILE_SIZE*7;
		i++;
	}

	public void setMonster() {
		
		int mapNum = 0;
		int i =0;
		gp.monster[mapNum][i] = new MonGreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*23;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*36;
		i++;
		gp.monster[mapNum][i] = new MonGreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*23;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*37;
		i++;
		gp.monster[mapNum][i] = new MonGreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*24;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*37;
		i++;
		gp.monster[mapNum][i] = new MonGreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*34;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*42;
		i++;
		gp.monster[mapNum][i] = new MonGreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*38;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*42;
		i++;
	}
	public void  setInteractiveTile() {
		
		int mapNum = 0;
		int i =0;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,27,12); 
		i++; 
		gp.iTile[mapNum][i] = new IT_DryTree(gp,28,12); 
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,29,12); 
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,30,12); 
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,31,12); 
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,32,12); 
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,33,12); 
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp,31,21); 
		i++;
	//	gp.iTile[mapNum][i] = new IT_DryTree(gp,18,40); 
		i++;
	//	gp.iTile[mapNum][i] = new IT_DryTree(gp,17,40); 
		i++;
	//	gp.iTile[mapNum][i] = new IT_DryTree(gp,16,40); 
		i++;
	//	gp.iTile[mapNum][i] = new IT_DryTree(gp,15,40); 
		i++;
		//gp.iTile[mapNum][i] = new IT_DryTree(gp,14,40); 
		i++;
	//	gp.iTile[mapNum][i] = new IT_DryTree(gp,13,40); 
		i++;
		//gp.iTile[mapNum][i] = new IT_DryTree(gp,13,41); 
		i++;
		//gp.iTile[mapNum][i] = new IT_DryTree(gp,12,41); 
		i++;
		//gp.iTile[mapNum][i] = new IT_DryTree(gp,11,41); 
		i++;
		//gp.iTile[mapNum][i] = new IT_DryTree(gp,10,41); 
		i++;
		//gp.iTile[mapNum][i] = new IT_DryTree(gp,10,40); 
		i++;

	}
}	
