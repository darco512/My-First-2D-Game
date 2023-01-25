package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	final int ORIGINAL_TILE_SIZE =16; // 16x16 tile
	final int SCALE = 3;
	
	final public int TILE_SIZE =ORIGINAL_TILE_SIZE * SCALE; //48x48
	//final public int MAX_SCREEN_COL = 16;
	final public int MAX_SCREEN_COL = 20;// Full Screen
	final public int MAX_SCREEN_ROW = 12;// Full Screen
	final public int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels // Full screen 960 pixels
	final public int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
	
	//WORLD SETTINGS
	public final int MAX_WORLD_COL = 50;
	public final int MAX_WORLD_ROW = 50;
	public final int MAX_MAP = 10;
	public int currentMap = 0;
	// FOR FULL SCREEN 
	int screenWidth2 = SCREEN_WIDTH;
	int screenHeight2 = SCREEN_HEIGHT;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;
	
	//FPS
	int FPS = 60;
	
	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new  EventHandler(this);
	Config config = new Config(this);
	public PathFinder pFinder  = new PathFinder(this);
	Thread gameThread;
	
	//ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	public Entity obj[][]= new Entity[MAX_MAP][20];
	public Entity npc[][]= new Entity[MAX_MAP][10];
	public Entity monster[][] = new Entity[MAX_MAP][20];
	public InteractiveTile iTile[][] = new InteractiveTile[MAX_MAP][50];
	public Entity projectile[][] = new Entity[MAX_MAP][20];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList <Entity> entityList = new ArrayList<>();
	
	//GAME STATE  
	public int gameState;
	public final int titleState=0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogState = 3;
	public final int characterState = 4;
	public final int optionState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	public void setupGame() {
		
		aSetter.setObject();
		aSetter.setNPC(); 
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		gameState = titleState;
		
		tempScreen = new BufferedImage(SCREEN_WIDTH,SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if(fullScreenOn==true) {
		setFullScreen();
		}
	}
	
	public void retry() {
		 
		player.setDefaultPosition();
		player.restoreLifeAndMana();
		aSetter.setNPC(); 
		aSetter.setMonster();
		
	}
	
	public void restart() {
		
		player.setDefaultValues();
		player.setDefaultPosition();
		player.restoreLifeAndMana();
		player.setItem();
		aSetter.setObject();
		aSetter.setNPC(); 
		aSetter.setMonster();
		aSetter.setInteractiveTile();
	}
	
	public void setFullScreen() {
		
		//GET LOCAL  SCREEN DEVICE
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();	
		GraphicsDevice gd = ge.getDefaultScreenDevice();	
		gd.setFullScreenWindow(Main.window);
		
		// GET FULL SCREEN WIDTH AND HEIGHT
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1_000_000_000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer=0;
		int drawCount = 0;
		 
		while(gameThread!=null) {
		
		
			
			currentTime = System.nanoTime();
			
			delta +=(currentTime-lastTime)/drawInterval;
			timer +=(currentTime-lastTime);
			lastTime=currentTime;
			if(delta>=1) {
			// 1 UPDATE: update information 
			update();
			// 2 DRAW: draw the screen with the updated information
			//repaint();
			drawToTempScreen();// draw everything to the buffered image
			drawToScreen(); // draw the buffered image to the screen  
			delta--;
			drawCount++;
			}
			if(timer>= 1_000_000_000) {
				System.out.println("FPS: "+drawCount);
				drawCount =0;
				timer=0;
			}
	
		}
		
		
	}

	public void update() {
		if (gameState == playState) {
			//PLAYER
			player.update();
			//NPC
			for (int i = 0; i < npc[1].length; i++) {
				if(npc[currentMap][i]!=null) {
					npc[currentMap][i].update();
				}
			}
			for (int i = 0; i < monster[1].length; i++) {
				if(monster[currentMap][i]!=null) {
					if(monster[currentMap][i].alive==true && monster[currentMap][i].dying==false) {
					monster[currentMap][i].update();
					}
					if(monster[currentMap][i].alive==false) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
						}
				}
			}
			for (int i = 0; i < projectile[1].length; i++) {
				if(projectile[currentMap][i]!=null) {
					if(projectile[currentMap][i].alive==true) {
						projectile[currentMap][i].update();
					}
					if(projectile[currentMap][i].alive==false) {
						projectile[currentMap][i] = null;
						}
				}
			}
			for (int i = 0; i < particleList.size(); i++) {
				if(particleList.get(i)!=null) {
					if(particleList.get(i).alive==true) {
						particleList.get(i).update();
					}
					if(particleList.get(i).alive==false) {
						particleList.remove(i);
						}
				}
			}
			for(int i =0; i< iTile[1].length;i++) {
				if(iTile[currentMap][i] != null) {
						iTile[currentMap][i].update(); 
				}
			}
		}
		if (gameState == pauseState) {
			// nothing
		}
			
	}
	
	public void drawToTempScreen() {
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		//TITE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
		}
		else {
			//TILE
			tileM.draw(g2);
			
			for(int i=0; i<iTile[1].length;i++) {
				if(iTile[currentMap][i] != null) {
					iTile[currentMap][i].draw(g2);
				}
			}
			//ADD ENTITY TO THE LIST
			entityList.add(player);
			
			for (int i = 0; i<npc[1].length;i++) {
				if(npc[currentMap][i]!= null) {
					entityList.add(npc[currentMap][i]);
				}
			}
			
			for(int i=0;i<obj[1].length;i++) {
				if(obj[currentMap][i]!= null) {
					entityList.add(obj[currentMap][i]);
				}
			}
			for(int i=0;i<monster[1].length;i++) {
				if(monster[currentMap][i]!= null) {
					entityList.add(monster[currentMap][i]);
				}
			}
			for(int i=0;i<projectile[1].length;i++) {
				if(projectile[currentMap][i]!= null) {
					entityList.add(projectile[currentMap][i]);
				}
			}
			for(int i=0;i<particleList.size();i++) {
				if(particleList.get(i)!= null) {
					entityList.add(particleList.get(i));
				}
			}
			//SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
				
			});
			//DRAW ENTITIES
			for(int i =0; i < entityList.size();i++) {
				entityList.get(i).draw(g2);
			}
			// EMPTY ENTITY LIST
				entityList.clear();
			//UI
			ui.draw(g2);
		}
		

		
		//DEBUG
		if(keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
		g2.drawString("WorldX: "+player.worldX, 10, 280);
		g2.drawString("WorldY: "+player.worldY, 10, 310);
		g2.drawString("WorldCol: "+(player.worldX/TILE_SIZE), 10, 340);
		g2.drawString("WorldRow: "+(player.worldY/TILE_SIZE), 10, 370);
		g2.drawString("Draw Time: "+passed, 10, 400);
		System.out.println("Draw Time: "+passed);
		} 
	
	}
	/*
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
			
			//DEBUG
			long drawStart = 0;
			if(keyH.checkDrawTime == true) {
				drawStart = System.nanoTime();
			}
			
			//TITE SCREEN
			if(gameState == titleState) {
				ui.draw(g2);
			}
			else {
				//TILE
				tileM.draw(g2);
				
				for(int i=0; i<iTile.length;i++) {
					if(iTile[i] != null) {
						iTile[i].draw(g2);
					}
				}
				//ADD ENTITY TO THE LIST
				entityList.add(player);
				
				for (int i = 0; i<npc.length;i++) {
					if(npc[i]!= null) {
						entityList.add(npc[i]);
					}
				}
				
				for(int i=0;i<obj.length;i++) {
					if(obj[i]!= null) {
						entityList.add(obj[i]);
					}
				}
				for(int i=0;i<monster.length;i++) {
					if(monster[i]!= null) {
						entityList.add(monster[i]);
					}
				}
				for(int i=0;i<projectileList.size();i++) {
					if(projectileList.get(i)!= null) {
						entityList.add(projectileList.get(i));
					}
				}
				for(int i=0;i<particleList.size();i++) {
					if(particleList.get(i)!= null) {
						entityList.add(particleList.get(i));
					}
				}
				//SORT
				Collections.sort(entityList, new Comparator<Entity>() {
	
					@Override
					public int compare(Entity e1, Entity e2) {
						int result = Integer.compare(e1.worldY, e2.worldY);
						return result;
					}
					
				});
				//DRAW ENTITIES
				for(int i =0; i < entityList.size();i++) {
					entityList.get(i).draw(g2);
				}
				// EMPTY ENTITY LIST
					entityList.clear();
				//UI
				ui.draw(g2);
			}
			
	
			
			//DEBUG
			if(keyH.checkDrawTime == true) {
				long drawEnd = System.nanoTime();
				long passed = drawEnd - drawStart;
				g2.setColor(Color.white);
			g2.drawString("WorldX: "+player.worldX, 10, 280);
			g2.drawString("WorldY: "+player.worldY, 10, 310);
			g2.drawString("WorldCol: "+(player.worldX/TILE_SIZE), 10, 340);
			g2.drawString("WorldRow: "+(player.worldY/TILE_SIZE), 10, 370);
			g2.drawString("Draw Time: "+passed, 10, 400);
			System.out.println("Draw Time: "+passed);
		}
		g2.dispose();  //to empty space in memory	
	}*/
	
	public void drawToScreen() {
		
		Graphics g =getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
		}
	public void stopMusic() {
		
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
}
