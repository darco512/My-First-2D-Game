package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.ObjCoinBronze;
import object.ObjHeart;
import object.ObjKey;
import object.ObjManaCrystal;
import object.SuperObject;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	
	Font maruMonica, purisaB;
	BufferedImage heart_full,heart_half,heart_blank, crystal_full, crystal_blank,coin;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0; // 0: the first screen, 1: the second screen
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	public int subState = 0;
	int counter = 0;
	public Entity npc;
	
	
	public UI(GamePanel gp) {
	  
	 this.gp = gp;
	 
	
	try {
		InputStream is =  getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
		maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
		purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		
	} catch (FontFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//CREATE HUD OBJECT
	Entity heart = new ObjHeart(gp);
	heart_full = heart.image;
	heart_half = heart.image2;
	heart_blank = heart.image3;
	Entity crystal = new ObjManaCrystal(gp);
	crystal_full = crystal.image;
	crystal_blank = crystal.image2;
	Entity bronzeCoin = new ObjCoinBronze(gp);
	coin = bronzeCoin.down1;
 }
 
	public void addMessage(String text) {
		
		message.add(text);
		messageCounter.add(0);
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		//g2.setFont(maruMonica);
		g2.setFont(maruMonica);
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		//TITLE STATE
		if(gp.gameState==gp.titleState) {
			drawTitleScreen();
		}
		//PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();
		}
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		//DIALOGUE STATE
		if(gp.gameState ==gp.dialogState) {
			drawDialogScreen();
		}
		//CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
			
		}
		//OPTION STATE
		if(gp.gameState == gp.optionState) {
			drawOptionsScreen();
					
		}
		//GAMEOVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();				
		}
		//TRANSITION STATE
		if(gp.gameState == gp.transitionState) {
			drawTransition();			
		}
		//TRADE STATE
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();			
		}
	}
	
	
	public void drawPlayerLife(){
		int x = gp.TILE_SIZE/2;
		int y = gp.TILE_SIZE/2;
		int i =0;
		//DRAW MAX HEART
		while(i<gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.TILE_SIZE;
		}	
		//RESET
		x = gp.TILE_SIZE/2;
		y = gp.TILE_SIZE/2;
		i =0;
		
		//DRAW CURRENT LIFE
		while(i<gp.player.life) {
				g2.drawImage(heart_half, x, y, null);
				i++;
				if(i < gp.player.life) {
					g2.drawImage(heart_full,x,y,null);
				}
				i++;
				x+= gp.TILE_SIZE;
		}
		
		//DRAW MAX MANA
		x =  gp.TILE_SIZE/2-5;
		y = (int)(gp.TILE_SIZE*1.5);
		i = 0;
		while (i<gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x +=35;
		}
		//DRAW MANA
		x =  gp.TILE_SIZE/2-5;
		y = (int)(gp.TILE_SIZE*1.5);
		i = 0;
		while(i<gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x +=35;
		}
	}
	public void drawMessage() {
		
		int messageX = gp.TILE_SIZE;
		int messageY = gp.TILE_SIZE*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for(int i = 0; i< message.size(); i++) {
			 
			if(message.get(i) != null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1; // messageCounter++
				messageCounter.set(i, counter);// set the counter to the array
				messageY +=50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
				
			}
		}
		
	}
	public void drawTitleScreen() {
		
		if(titleScreenState ==0) {

			g2.setColor(new Color(0,0,0));
			g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
			
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
			String text = "Blue Boy Adventure";
			int x= getXforCenteredText(text);
			int y = gp.TILE_SIZE*3;
			//SHADOW 
			g2.setColor(Color.gray);
			g2.drawString(text, x+5, y+5);
			//MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			//BLUE BOY IMAGE
			x = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE*2)/2;
			y+=gp.TILE_SIZE*2;
			g2.drawImage(gp.player.down1, x, y, gp.TILE_SIZE*2, gp.TILE_SIZE*2,null);
			//MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			
			text= "NEW GAME";
			x= getXforCenteredText(text);
			y+=gp.TILE_SIZE*3.5;
			g2.drawString(text, x, y);
			if(commandNum==0) {
				g2.drawString(">", x-gp.TILE_SIZE, y);
			}
			
			text= "LOAD GAME";
			x= getXforCenteredText(text);
			y+=gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if(commandNum==1) {
				g2.drawString(">", x-gp.TILE_SIZE, y);
			}
			
			text= "QUIT";
			x= getXforCenteredText(text);
			y+=gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if(commandNum==2) {
				g2.drawString(">", x-gp.TILE_SIZE, y);
			}
		}
		else  if(titleScreenState == 1) {
			//CLASS SELECTION SCREEN
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			 
			String text = "Select your class!";
			int x = getXforCenteredText(text);
			int y = gp.TILE_SIZE*3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE*3;
			g2.drawString(text, x, y);
			if(commandNum ==0) {
				g2.drawString(">", x-gp.TILE_SIZE,y);
			}
			text = "Thief";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if(commandNum ==1) {
				g2.drawString(">", x-gp.TILE_SIZE,y);
			}
			text = "Sorcerer";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if(commandNum ==2) {
				g2.drawString(">", x-gp.TILE_SIZE,y);
			}
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if(commandNum ==3) {
				g2.drawString(">", x-gp.TILE_SIZE,y);
			}	
		}
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSED";
		int x =getXforCenteredText(text) ;
		int y = gp.SCREEN_HEIGHT/2;
		g2.drawString(text, x, y);	
	}
	public void drawDialogScreen() {
		//WINDOW
		int x = gp.TILE_SIZE*3;
		int y = gp.TILE_SIZE/2;
		int widht = gp.SCREEN_WIDTH -(gp.TILE_SIZE*6);
		int height = gp.TILE_SIZE*4;
		drawSubWindow(x,y,widht,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
		x+=gp.TILE_SIZE;
		y+= gp.TILE_SIZE;
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+=40;
		}
	}
	public void drawCharacterScreen() {
		//CREATE A FRAME
		final int frameX = gp.TILE_SIZE*2;
		final int frameY = gp.TILE_SIZE;
		final int frameWidth = gp.TILE_SIZE *5;
		final int frameHeight = gp.TILE_SIZE *10;
		drawSubWindow(frameX, frameY,frameWidth,frameHeight);
		
		//TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX +20;
		int textY= frameY +gp.TILE_SIZE;
		final int lineHeight = 35;
		
		//NAMES
		g2.drawString("Level", textX, textY);
		textY+= lineHeight;
		g2.drawString("Life", textX, textY);
		textY+= lineHeight;
		g2.drawString("Mana", textX, textY);
		textY+= lineHeight;
		g2.drawString("Strength", textX, textY);
		textY+= lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY+= lineHeight;
		g2.drawString("Attack", textX, textY);
		textY+= lineHeight;
		g2.drawString("Defense", textX, textY);
		textY+= lineHeight;
		g2.drawString("Exp", textX, textY);
		textY+= lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY+= lineHeight;
		g2.drawString("Coin", textX, textY);
		textY+= lineHeight + 10;
		g2.drawString("Weapon", textX, textY);
		textY+= lineHeight + 15;
		g2.drawString("Shield", textX, textY);
		textY+= lineHeight;
		
		//VALUES
		int tailX = (frameX+frameWidth) - 30;
		//Reset textY
		textY = frameY + gp.TILE_SIZE;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;

		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;

		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value,tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.TILE_SIZE, textY-24, null);
		textY+= gp.TILE_SIZE;

		g2.drawImage(gp.player.currentShield.down1,tailX-gp.TILE_SIZE, textY - 24, null);
	}
	public void drawInventory(Entity entity,boolean cursor){
		
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		
		if(entity == gp.player) {
			frameX = gp.TILE_SIZE*12;
			frameY = gp.TILE_SIZE;
			frameWidth = gp.TILE_SIZE*6;
			frameHeight = gp.TILE_SIZE*5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = gp.TILE_SIZE* 2;
			frameY = gp.TILE_SIZE;
			frameWidth = gp.TILE_SIZE*6;
			frameHeight = gp.TILE_SIZE*5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		//FRAME

		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		//SLOT
		final int slotXStart = frameX +20;
		final int slotYStart = frameY +20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.TILE_SIZE+3;
		
		//DRAW PLAYER'S ITEMS
		for(int i=0; i<entity.inventory.size(); i++) {
			
			//EQUIP CURSOR 
			if(entity.inventory.get(i) == entity.currentWeapon || 
					entity.inventory.get(i) == entity.currentShield) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.TILE_SIZE, gp.TILE_SIZE,10,10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX,slotY,null);
			
			slotX+=slotSize;
			
			if(i == 4 || i == 9|| i == 14) {
				slotX = slotXStart;
				slotY +=slotSize;
				
			}
		}
		
		//CURSOR
		if (cursor == true) {
			int cursorX = slotXStart + (slotSize * slotCol);
			int cursorY = slotYStart + (slotSize * slotRow);
			int cursorWidth = gp.TILE_SIZE;
			int cursorHeight = gp.TILE_SIZE;
			//DRAWCURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			//DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY+frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.TILE_SIZE*3;
			
			//DRAW DESCRIPTION TEXT
			int textX = dFrameX +20;
			int textY = dFrameY + gp.TILE_SIZE;
			g2.setFont(g2.getFont().deriveFont(28F));
			
			int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
			
			if(itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
				
				for(String line : entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY +=32;
				}	
			}
		}
		
	}
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));;
		
		text = "Game Over";
		//SHADOW
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.TILE_SIZE*4;
		g2.drawString(text, x, y);
		//MAIN
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		//RETRY
		g2.setFont(g2.getFont().deriveFont(50f));;
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.TILE_SIZE*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		//BACK TO THE TITLE SCREEN
		text = "Quit";
		x = getXforCenteredText(text);
		y +=55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	
	public void drawOptionsScreen(){
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//SUB WINDOW
		int frameX = gp.TILE_SIZE *6;
		int frameY = gp.TILE_SIZE;
		int frameWidth = gp.TILE_SIZE*8;
		int frameHeight = gp.TILE_SIZE*10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: optionsTop(frameX, frameY);break;
		case 1: optionFullScreenNotification(frameX, frameY);break;
		case 2: optionsControl(frameX, frameY);break;
		case 3: optionsEndGameConfirmation(frameX, frameY);break;
		
		}
		
		gp.keyH.enterPressed = false;
	}
	public void optionsTop(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		String  text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.TILE_SIZE;
		g2.drawString(text, textX, textY);
		
		//FULL SCREEN ON/OFF
		textX = frameX+gp.TILE_SIZE;
		textY += gp.TILE_SIZE*2;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn ==false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
			
		}
		//MUSIC
		textY += gp.TILE_SIZE;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		//SE
		textY += gp.TILE_SIZE;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		//CONTROL
		textY += gp.TILE_SIZE;
		g2.drawString("Control", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState=2;
				commandNum=0;
			}
		}
		//END GAME
		textY += gp.TILE_SIZE;
		g2.drawString("End Game", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum=0;
			}
		}
		//BACK
		textY += gp.TILE_SIZE*2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
			}
		}
		
		//FULL SCREEN CHECK BOX
		textX = frameX+ (int)(gp.TILE_SIZE*4.5);
		textY = frameY + gp.TILE_SIZE*2 + 24;
		g2.setStroke(new BasicStroke(3)); //MAKE LINE THINER 
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		//MUSIC VOLUME
		textY += gp.TILE_SIZE;
		g2.drawRect(textX, textY, 120, 24); //120/5 = 24
		int volumeWidth = 24*gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		//SE VOLUME
		textY += gp.TILE_SIZE;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24*gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	public void optionFullScreenNotification(int frameX, int frameY) {
		int textX = frameX + gp.TILE_SIZE;
		int textY = frameY + gp.TILE_SIZE*3;
		
		currentDialogue = "The change will take \neffect after restarting \nthe game.";
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		
		//BACK
		textY =  frameY + gp.TILE_SIZE*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX -25, textY);
			if(gp.keyH.enterPressed == true) {
				 subState = 0;
			}
		}
	}
	public void optionsControl(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		//TITLE
		String text = "Contol";
		textX = getXforCenteredText(text);
		textY = frameY + gp.TILE_SIZE;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.TILE_SIZE;
		textY +=gp.TILE_SIZE;
		g2.drawString("Move", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("Confirm/Attack", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("Shoot/Cast", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("Character Screen", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("Pause", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("Options", textX, textY); textY +=gp.TILE_SIZE;
		
		textX = frameX + gp.TILE_SIZE*6;
		textY = frameY + gp.TILE_SIZE*2;
		g2.drawString("WASD", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("ENTER", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("F", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("C", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("P", textX, textY); textY +=gp.TILE_SIZE;
		g2.drawString("ESC", textX, textY); textY +=gp.TILE_SIZE;
		
		//BACK 
		textX = frameX + gp.TILE_SIZE;
		textY = frameY + gp.TILE_SIZE*9;
		g2.drawString("BACK", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}
	}
	public void optionsEndGameConfirmation(int frameX, int frameY) {
		
		int textX = frameX + gp.TILE_SIZE;
		int textY = frameY + gp.TILE_SIZE*3;
		
		currentDialogue = "Quit the game and \nreturn to the title screen?";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		//YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY +=gp.TILE_SIZE*3;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.music.stop();
			}
		}
		//NO
		text = "No";
		textX = getXforCenteredText(text);
		textY +=gp.TILE_SIZE;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	
	public void drawTransition() {
		
		counter++;
		g2.setColor(new Color(0,0,0,counter*5));
		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
		
		if(counter == 50) {
			counter =0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.TILE_SIZE * gp.eHandler.tempCol;
			gp.player.worldY = gp.TILE_SIZE * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}

	public void drawTradeScreen(){
		
		switch(subState){
			case 0: tradeSelect();break;
			case 1: tradeBuy();break;
			case 2: tradeSell();break;
		}
		gp.keyH.enterPressed = false;
	}
	public void tradeSelect(){
		
		drawDialogScreen();
		
		//DRAW WINDOW
		int x = gp.TILE_SIZE *15;
		int y = gp.TILE_SIZE *4;
		int width = gp.TILE_SIZE *3;
		int height = (int)(gp.TILE_SIZE*3.5);
		drawSubWindow(x,y,width,height);
		
		//DRAW TEXT
		x+= gp.TILE_SIZE;
		y+= gp.TILE_SIZE;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState =1;
			}
		}
		y+= gp.TILE_SIZE;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState =2;
			}
		}
		y+= gp.TILE_SIZE;
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				commandNum =0;
				gp.gameState = gp.dialogState;
				currentDialogue = "Come again, hehe!";
			}
		}
		
		
	}
	public void tradeBuy() {
		//DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);
		//DRAW NPC INVENTORY
		drawInventory(npc,true);
		
		//DRAW HINT WINDOW
		int x = gp.TILE_SIZE*2;
		int y = gp.TILE_SIZE*9;
		int width = gp.TILE_SIZE*6;
		int height = gp.TILE_SIZE*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("[ESC] Back", x+24, y+60);
		
		//DRAW PLAYER COIN WINDOW
		x = gp.TILE_SIZE*12;
		y = gp.TILE_SIZE*9;
		width = gp.TILE_SIZE*6;
		height = gp.TILE_SIZE*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("Your coin: " + gp.player.coin, x+24, y+60);
		//DRAW PRICE WINDOW 
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			
			x =(int)(gp.TILE_SIZE*5.5);
			y =(int)(gp.TILE_SIZE*5.5);
			width = (int)(gp.TILE_SIZE*2.5);
			height = gp.TILE_SIZE;
			drawSubWindow(x,y,width,height);
			g2.drawImage(coin, x+10, y+8,32,32,null);
			
			int  price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.TILE_SIZE*8-20);
			g2.drawString(text, x, y+34);
			
			//BUY AN ITEM
			if(gp.keyH.enterPressed== true) {
				if(npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					gp.gameState = gp.dialogState;
					currentDialogue="You need more coin to buy that!";
					drawDialogScreen();
				}
				else if(gp.player.inventory.size() == gp.player.maxInventorySize) {
					subState =0;
					currentDialogue="Your inventory is full!";
					drawDialogScreen();
				}
				else {
					gp.player.coin -= npc.inventory.get(itemIndex).price;
					gp.player.inventory.add(npc.inventory.get(itemIndex));
				}
			}
			
		}
	}
	public void tradeSell() {
		drawInventory(gp.player,true);
		
		int x;
		int y;
		int width;
		int height;
		
		//DRAW HINT WINDOW
		x = gp.TILE_SIZE*2;
		y = gp.TILE_SIZE*9;
		width = gp.TILE_SIZE*6;
		height = gp.TILE_SIZE*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("[ESC] Back", x+24, y+60);
		
		//DRAW PLAYER COIN WINDOW
		x = gp.TILE_SIZE*12;
		y = gp.TILE_SIZE*9;
		width = gp.TILE_SIZE*6;
		height = gp.TILE_SIZE*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("Your coin: " + gp.player.coin, x+24, y+60);
		//DRAW PRICE WINDOW 
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if(itemIndex < gp.player.inventory.size()) {
			
			x =(int)(gp.TILE_SIZE*15.5);
			y =(int)(gp.TILE_SIZE*5.5);
			width = (int)(gp.TILE_SIZE*2.5);
			height = gp.TILE_SIZE;
			drawSubWindow(x,y,width,height);
			g2.drawImage(coin, x+10, y+8,32,32,null);
			
			int  price = gp.player.inventory.get(itemIndex).price/2;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.TILE_SIZE*18-20);
			g2.drawString(text, x, y+34);
			
			//SELL AN ITEM
			if(gp.keyH.enterPressed== true) {
				if(gp.player.inventory.get(itemIndex)==gp.player.currentWeapon || 
						gp.player.inventory.get(itemIndex)==gp.player.currentShield) {
					commandNum = 0;
					subState = 0;
					gp.gameState = gp.dialogState; 
					currentDialogue = "You cannot sell an equipped item";
				}
				else {
					gp.player.inventory.remove(itemIndex); 
					gp.player.coin += price;
				}
			}
		}
	}
	
	public int getItemIndexOnSlot(int slotCol,int slotRow) {
		int itemIndex =  slotCol + (slotRow*5);
		return itemIndex;
	}
	public void drawSubWindow(int x,int y, int widht, int height) {
		g2.setColor(new Color(0,0,0,210));
		g2.fillRoundRect(x, y, widht, height, 35, 35);
		
		g2.setColor(new Color(255,255,255));
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, widht-10, height-10,25,25);
	}
	public int getXforCenteredText(String text) {
		int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.SCREEN_WIDTH/2 - lenght/2;
		return x;
	}
	public int getXforAlignToRightText(String text, int tailX) {
		int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - lenght;
		return x;
	}

}

