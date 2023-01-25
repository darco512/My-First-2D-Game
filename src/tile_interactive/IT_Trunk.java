package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile{

GamePanel gp;
	
	public IT_Trunk(GamePanel gp, int col, int row) {
		super(gp,col, row);
		this.gp = gp;
		
		this.worldX = gp.TILE_SIZE * col;
		this.worldY = gp.TILE_SIZE * row;
		
		down1 = setup("/tiles_interactive/trunk", gp.TILE_SIZE, gp.TILE_SIZE);

		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
