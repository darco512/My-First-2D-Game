package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.ObjCoinBronze;
import object.ObjHeart;
import object.ObjManaCrystal;
import object.ObjRock;

public class MonGreenSlime extends Entity {
	
	GamePanel gp;

	public MonGreenSlime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		name = "Green Slime";
		type = TYPE_MONSTER;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 4;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp =  2; 
		projectile = new ObjRock(gp);
			
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();
	}
	public void getImage() {
		
		up1=setup("/monster/greenslime_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		up2=setup("/monster/greenslime_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
		down1=setup("/monster/greenslime_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		down2=setup("/monster/greenslime_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
		left1=setup("/monster/greenslime_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		left2=setup("/monster/greenslime_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
		right1=setup("/monster/greenslime_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		right2=setup("/monster/greenslime_down_2", gp.TILE_SIZE,gp.TILE_SIZE);	
	}
	
	public void update() {
		
		super.update();
		
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance =(xDistance + yDistance)/gp.TILE_SIZE;
		
		if(onPath == false && tileDistance <5) {
			int i = new Random().nextInt(100)+1;
			if(i > 50) {
				onPath = true;
			}
		}
		if(onPath  == true &&  tileDistance>20) {
			onPath = false;
		}
	}
	
	public void setAction() {
		
		if(onPath == true) {

			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.TILE_SIZE;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.TILE_SIZE;
			
			searchPath(goalCol,goalRow);
			
			int i = new Random().nextInt(200)+1;
			if (i>197 && projectile.alive == false && shotAvailableCounter ==30 ) {
				
				projectile.set(worldX, worldY, direction, true, this);
				
				//CHECK VACANCY
				for(int ii=0; ii< gp.projectile[1].length; ii++) {
					if(gp.projectile[gp.currentMap][ii] == null) {
						gp.projectile[gp.currentMap][ii] = projectile;
						break;
					}
				}
				
				
				shotAvailableCounter = 0;
			}
		}
		else {
			actionLockCounter++;
		
			if(actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100)+1;//pick up a number from 1 to 100
			
				if (i<=25) {
					direction = "up";
				
				}
				if (i> 25 && i<=50) {
					direction = "down";
				
				}
				if (i> 50 && i<=75) {
					direction = "left";
				
				}
				if (i> 75 && i<=100) {
					direction = "right";
				
				}
				actionLockCounter=0;
				}
			}

	}
	
	public void damageReaction() {
		
		actionLockCounter = 0;
	//	direction = gp.player.direction;
		onPath = true;
	}
	public void checkDrop() {
		
		//CAST AT DIE
		int i = new Random().nextInt(100) +1;
		
		//SET A MONSTER DROP
		if(i<50) {
			dropItem(new ObjCoinBronze(gp));
		}
		if (i>=50 && i < 75) {
			dropItem(new ObjHeart(gp));
		}
		if (i>=75 && i < 100) {
			dropItem(new ObjManaCrystal(gp));
		}
	}
}
