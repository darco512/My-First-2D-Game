package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class ObjRock extends Projectile{
	
	GamePanel gp;
	
	public ObjRock(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		name = "Rock";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive  = false;
		getImage();
		
	}
	public void getImage(){
		up1=setup("/projectile/rock_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		up2 = setup("/projectile/rock_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		down1 = setup("/projectile/rock_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		down2 = setup("/projectile/rock_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		left1 = setup("/projectile/rock_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		left2 = setup("/projectile/rock_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		right1 = setup("/projectile/rock_down_1", gp.TILE_SIZE,gp.TILE_SIZE);
		right2 = setup("/projectile/rock_down_1", gp.TILE_SIZE,gp.TILE_SIZE); 
	}
public boolean haveResource(Entity user) {
		
		boolean haveResource = false;
		if(user.ammo>= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.ammo -=useCost;
	}
	public Color getParticleColor(){
		Color color = new Color(40, 50,0);
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
