package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GamePanel;
import main.KeyHandler;
import object.ObjFireball;
import object.ObjKey;
import object.ObjShieldWood;
import object.ObjSwordNormal;

public class Player extends Entity{
	
	KeyHandler keyH;
	
	public final int SCREEN_X;
	public final int SCREEN_Y; 	
	public boolean attackCanceled = false;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		SCREEN_X = gp.SCREEN_WIDTH/2 -(gp.TILE_SIZE/2);
		SCREEN_Y = gp.SCREEN_HEIGHT/2-(gp.TILE_SIZE/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y =16;
		solidAreaDefaultX =solidArea.x;
		solidAreaDefaultY =solidArea.y;
		solidArea.height = 32;
		solidArea.width = 32;
		
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItem();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.TILE_SIZE*23;
		worldY = gp.TILE_SIZE*21;		
//		worldX = gp.TILE_SIZE*12;
//		worldY = gp.TILE_SIZE*13;
		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		
		//PLAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 4;
		mana = maxMana; 
		ammo = 10;
		strength = 1; // The more strength he has the more damage he gives
		dexterity = 1; //The more dexterity he has the less damage he receives
		exp = 0;
		nextLevelExp = 5;
		coin = 500;
		currentWeapon = new ObjSwordNormal(gp); 
		currentShield = new ObjShieldWood(gp);
		projectile = new ObjFireball(gp);
		//projectile = new ObjRock(gp);
		attack = getAttack();		//The total attack decided by strength and weapon
		defense = getDefense();		//The total defense decided by dexterity and shield
	}
	
	public void setDefaultPosition() {
		
		worldX = gp.TILE_SIZE*23;
		worldY = gp.TILE_SIZE*21;
		direction = "down";
		
		
		
	}
	
	public void restoreLifeAndMana() {
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	
	public void setItem() {
		
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new ObjKey(gp));
		
	}
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
		
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	
	public void getPlayerImage() {
	
		up1=setup("/player/boy_up_1", gp.TILE_SIZE,gp.TILE_SIZE);
		up2 = setup("/player/boy_up_2", gp.TILE_SIZE,gp.TILE_SIZE);
		down1 = setup("/player/boy_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		down2 = setup("/player/boy_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
		left1 = setup("/player/boy_left_1", gp.TILE_SIZE,gp.TILE_SIZE);
		left2 = setup("/player/boy_left_2", gp.TILE_SIZE,gp.TILE_SIZE);
		right1 = setup("/player/boy_right_1", gp.TILE_SIZE,gp.TILE_SIZE);
		right2 = setup("/player/boy_right_2", gp.TILE_SIZE,gp.TILE_SIZE);
	}

	public void getPlayerAttackImage() {
		
		if(currentWeapon.type == TYPE_SWORD) {
		attackUp1  = setup("/player/boy_attack_up_1", gp.TILE_SIZE,gp.TILE_SIZE*2);
		attackUp2  = setup("/player/boy_attack_up_2", gp.TILE_SIZE,gp.TILE_SIZE*2);
		attackDown1  = setup("/player/boy_attack_down_1", gp.TILE_SIZE,gp.TILE_SIZE*2);
		attackDown2  = setup("/player/boy_attack_down_2", gp.TILE_SIZE,gp.TILE_SIZE*2);
		attackLeft1  = setup("/player/boy_attack_left_1", gp.TILE_SIZE*2,gp.TILE_SIZE);
		attackLeft2  = setup("/player/boy_attack_left_2", gp.TILE_SIZE*2,gp.TILE_SIZE);
		attackRight1  = setup("/player/boy_attack_right_1", gp.TILE_SIZE*2,gp.TILE_SIZE);
		attackRight2  = setup("/player/boy_attack_right_2", gp.TILE_SIZE*2,gp.TILE_SIZE);
		}
		if(currentWeapon.type == TYPE_AXE) {
			attackUp1  = setup("/player/boy_axe_up_1", gp.TILE_SIZE,gp.TILE_SIZE*2);
			attackUp2  = setup("/player/boy_axe_up_2", gp.TILE_SIZE,gp.TILE_SIZE*2);
			attackDown1  = setup("/player/boy_axe_down_1", gp.TILE_SIZE,gp.TILE_SIZE*2);
			attackDown2  = setup("/player/boy_axe_down_2", gp.TILE_SIZE,gp.TILE_SIZE*2);
			attackLeft1  = setup("/player/boy_axe_left_1", gp.TILE_SIZE*2,gp.TILE_SIZE);
			attackLeft2  = setup("/player/boy_axe_left_2", gp.TILE_SIZE*2,gp.TILE_SIZE);
			attackRight1  = setup("/player/boy_axe_right_1", gp.TILE_SIZE*2,gp.TILE_SIZE);
			attackRight2  = setup("/player/boy_axe_right_2", gp.TILE_SIZE*2,gp.TILE_SIZE);
			}
		
	}
	
	public void update() {
		
		if(attacking == true) {
			attacking();
		}
		//make move player
		else   if(keyH.upPressed == true || keyH.downPressed==true ||
				keyH.leftPressed==true|| keyH.rightPressed || keyH.enterPressed == true)//to make animation of player moving only when key pressed
			{
			if(keyH.upPressed == true) {
				direction = "up";
				
			}
			else if(keyH.downPressed == true) {
				direction = "down";
				
			}
			else if(keyH.leftPressed==true) {
				direction = "left";
				
			}
			else if(keyH.rightPressed==true) {
				direction = "right";
				
			}
			//CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTail(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			//CHECK INTERACTIVE TILE COLLISON
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			
			//CHECK EVENT
			gp.eHandler.checkEvent();
			
			
			//IF COLLION IS FALSE PLAYER CAN MOVE
			if(collisionOn == false && keyH.enterPressed == false)
			{
				switch(direction) {
				case"up":
					worldY-=speed;
					break;
				case"down":
					worldY+=speed;
					break;
				case"left":
					worldX-=speed;
					break;
				case"right":
					worldX+=speed;
					break;
				}
			}
			
			if(keyH.enterPressed == true && attackCanceled == false) {
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCanceled = false;
			gp.keyH.enterPressed = false;
			
			spriteCounter++;
			if(spriteCounter>12) {
				if(spriteNum == 1) {
					spriteNum=2;
				}
				else if(spriteNum==2) {
					spriteNum =1;
			}
			spriteCounter =0;
			}
		}
		
		if(gp.keyH.shotKeyPressed==true && projectile.alive == false && shotAvailableCounter ==30 
				&& projectile.haveResource(this) == true) {
			
			// SET DEFAULT COORDITANES, DIRECTION AND USER
			projectile.set(worldX, worldY, direction, true,this);
			
			//SUBTRACT THE COST(MANA, AMMO, ETC.
			projectile.subtractResource(this);
			


			for(int i = 0; i < gp.projectile[1].length; i++) {
				if(gp.projectile[gp.currentMap][i] == null ) {
					gp.projectile[gp.currentMap][i] = projectile;
					break;
				}
			}
			
			shotAvailableCounter =0;
			
			gp.playSE(9);
			
		}
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter>60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		if(life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
		if(life <=0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.stopMusic();
			gp.playSE(11);
		}
	}
	public void attacking() {
		
		spriteCounter++;
		if(spriteCounter<=5) {
			spriteNum = 1;
		}
		if(spriteCounter >5 && spriteCounter<=25 ) {
			spriteNum =2;
			
			//Save the current wolrldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//Adjust player worldX/Y for the attacArea
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			// atackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			//Check monster collision with updated WorldX, WorldY and solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex,attack,currentWeapon.knockBackPower);
			
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);

			int projectileIndex  = gp.cChecker.checkEntity(this, gp.projectile);
			
			damageProjectile(projectileIndex);
			
			//After checking collision restore the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter> 25) {
			spriteNum = 1;
			spriteCounter =0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int i) {
		if(i!=999) {
			//PICKUP ONLY ITEMS
			if(gp.obj[gp.currentMap][i].type == TYPE_PICKUP_ONLY) {
				
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			}
			//OBSTACLE
			else if(gp.obj[gp.currentMap][i].type == TYPE_OBSTACLE) {
				if(keyH.enterPressed == true) {
					attackCanceled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}
			//INVENTORY ITEMS
			else {
				String text;
				
				if(inventory.size() != maxInventorySize) {
					inventory.add(gp.obj[gp.currentMap][i]);
					gp.playSE(1);
					text = "Got a "+gp.obj[gp.currentMap][i].name +"!";
				}
				else {
					text = "You cannot cary any more!";
				}
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
			}
			
		}
	}
	public void interactNPC(int i) {
		if(gp.keyH.enterPressed == true) {
			if(i!=999) {
				attackCanceled = true;
				gp.gameState = gp.dialogState;
				gp.npc[gp.currentMap][i].speak();	
			}
		}
		
	}
	public void contactMonster(int i) {
		if(i!=999) {
			if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
			gp.playSE(6);
			
			int damage = gp.monster[gp.currentMap][i].attack - defense;
			if(damage < 0 ) {
				damage = 0;
			}
			life -= damage ;
			gp.ui.addMessage("You recieve " + damage + " damage!");
			invincible=true;
			}
		}
	}
	public void damageMonster(int i,int attack, int knockBackPower) {
		if(i != 999) {
			if(gp.monster[gp.currentMap][i].invincible == false) {
				gp.playSE(5);
				
				if(knockBackPower>0) {
					knockBack(gp.monster[gp.currentMap][i],knockBackPower);
				}
				
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if(damage < 0 ) {
					damage = 0;
				}
				gp.monster[gp.currentMap][i].life -=damage;
				gp.ui.addMessage("You hit "+ damage+" damage!");
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				
				if(gp.monster[gp.currentMap][i].life <=0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage("You killed the " + gp.monster[gp.currentMap][i].name + "!");
					exp += gp.monster[gp.currentMap][i].exp;
					gp.ui.addMessage("You recieve " + gp.monster[gp.currentMap][i].exp + "exp!");
					checkLevelUp();
				}
			}
		}
	}
	
	public void knockBack(Entity entity, int knockBackPower) {
		
		entity.direction = direction;
		entity.speed +=knockBackPower;
		entity.knockBack = true;
	}
	
	public void damageInteractiveTile(int i) {
		
		if( i !=999 && gp.iTile[gp.currentMap][i].destructible == true && gp.iTile[gp.currentMap][i].invincible == false
				&& gp.iTile[gp.currentMap][i].isCorrectItem(this) == true) {
			
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);
			
			if(gp.iTile[gp.currentMap][i].life ==0) {
			gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroydForm()	;
			}
		}
	}
	
	public void damageProjectile(int i) {
		
		if(i!=999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.alive = false;
			generateParticle(projectile,projectile);
		}
	}
	
	public void checkLevelUp() {
		
		if(exp >= nextLevelExp) {
			
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(7);
			life = maxLife;
			mana = maxMana;
			gp.gameState = gp.dialogState;
			gp.ui.currentDialogue = "You are level " + level + "now!\n" +
			"You feel stronger!";
		}
	}

	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == TYPE_SWORD || selectedItem.type == TYPE_AXE) {
				
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
				
			}
			if(selectedItem.type == TYPE_SHIELD) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == TYPE_CONSUMABLE) {
				
				if(selectedItem.use(this)==true) {
					inventory.remove(itemIndex);
				}
			}
		}
	}
	public void draw(Graphics2D g2){
		BufferedImage image = null;
		int tempScreenX = SCREEN_X;
		int tempScreenY = SCREEN_Y;
		
		switch(direction) {
		case "up" :
			if(attacking == false) {
				if(spriteNum==1) {
					image = up1;
				}
				if(spriteNum==2) {
					image = up2;
				}
			}
			if (attacking ==true) {
				tempScreenY = SCREEN_Y - gp.TILE_SIZE;
				if(spriteNum==1) {
					image = attackUp1;
				}
				if(spriteNum==2) {
					image = attackUp2;
				}
			}
			break;
		case "down" :
			if(attacking == false) {
				if(spriteNum==1) {
					image = down1;
				}
				if(spriteNum==2) {
					image = down2;
				}
			}
			if (attacking ==true) {
				if(spriteNum==1) {
					image = attackDown1;
				}
				if(spriteNum==2) {
					image = attackDown2;
				}
			}
			break;
		case "left" :
			if(attacking == false) {
				if(spriteNum==1) {
					image = left1;
				}
				if(spriteNum==2) {
					image = left2;
				}
			}
			if (attacking ==true) {
				tempScreenX = SCREEN_X - gp.TILE_SIZE;
				if(spriteNum==1) {
					image = attackLeft1;
				}
				if(spriteNum==2) {
					image = attackLeft2;
				}
			}
			break;
		case "right" :
			if(attacking == false) {
				if(spriteNum==1) {
					image = right1;
				}
				if(spriteNum==2) {
					image = right2;
				}
			}
			if (attacking ==true) {
				if(spriteNum==1) {
					image = attackRight1;
				}
				if(spriteNum==2) {
					image = attackRight2;
				}
			}
			break;
		}
		if(invincible==true&&((invincibleCounter <12)
				|| (invincibleCounter >24 && invincibleCounter <36)||(invincibleCounter >48 && invincibleCounter <60))) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
		}
		g2.drawImage(image, tempScreenX,tempScreenY,null);
		
		//Reset Alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
	}

}
