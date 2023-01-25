package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class ObjFireball extends Projectile{

	GamePanel gp;
	
	public ObjFireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		knockBackPower =0;	
		useCost = 1;
		alive  = false;
		getImage();
		
	}
	public void getImage(){
		up1=setup("/projectile/fireball_up_1", gp.TILE_SIZE,gp.TILE_SIZE);
		up2 = setup("/projectile/fireball_up_2", gp.TILE_SIZE,gp.TILE_SIZE);
		down1 = setup("/projectile/fireball_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		down2 = setup("/projectile/fireball_down_2", gp.TILE_SIZE,gp.TILE_SIZE);
		left1 = setup("/projectile/fireball_left_1", gp.TILE_SIZE,gp.TILE_SIZE);
		left2 = setup("/projectile/fireball_left_2", gp.TILE_SIZE,gp.TILE_SIZE);
		right1 = setup("/projectile/fireball_right_1", gp.TILE_SIZE,gp.TILE_SIZE);
		right2 = setup("/projectile/fireball_right_2", gp.TILE_SIZE,gp.TILE_SIZE); 
	}

	public boolean haveResource(Entity user) {
		
		boolean haveResource = false;
		if(user.mana>= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.mana -=useCost;
	}
	public Color getParticleColor(){
		Color color = new Color(240, 50,0);
		return color;
	}
	public int getParticleSize() {
		int size = 10; //6 pixels
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
