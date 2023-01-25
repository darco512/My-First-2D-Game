package tile_interactive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity{
	
	GamePanel gp;
	public boolean destructible = false;
	
	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		return isCorrectItem;
	}
	public void playSE() {}
	public InteractiveTile getDestroydForm() {
		InteractiveTile tile = null;
		return tile;
	}
	public void update() {
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter>20) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
	}
	public void draw(Graphics2D g2) {
		
BufferedImage image = null;
		
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
		 
		if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
		   worldX - gp.TILE_SIZE< gp.player.worldX + gp.player.SCREEN_X && 
		   worldY + gp.TILE_SIZE>gp.player.worldY - gp.player.SCREEN_Y && 
		   worldY - gp.TILE_SIZE< gp.player.worldY + gp.player.SCREEN_Y) {
			
		
			g2.drawImage(down1, screenX, screenY, null);
			
		}
	}

}
