package game.model;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Board {

	private Tile tiles[][];
	private int xSize;
	private int ySize;
	private int tileSize = 128;

	public Board(String filename){

		try{
				Scanner s = new Scanner(new File(filename));
				xSize=s.nextInt();
				ySize=s.nextInt();
				tiles = new Tile[xSize][ySize];
				int xPos = 0;
				int yPos = 0;
				while(s.hasNext()){
					if(s.hasNextInt()){
						int i = s.nextInt();
						if(i==0){
							tiles[xPos][yPos] = new BlankTile();
						}else if(i==1){
							tiles[xPos][yPos] = new Wall();
						}else{
							System.out.println("Error loading file (invalid int - " + i + ", xPos = " + xPos + ", yPox = " + yPos + ")");
						}
						//Else square is a player or target
					}else{
						String tok = s.next();
						//Checks string was 1 char
						if(tok.length()!=1){
							s.close();
							throw new IOException("Invalid string");
						}
						//gets char
						char c = tok.charAt(0);
						if(c == 'T'){
							tiles[xPos][yPos] = new Target();
						}else if(c == 'P'){
							//Create player at this point
							tiles[xPos][yPos] = new BlankTile();
						}
					}
			}
		}catch(IOException e){
		}
	}

	public HitDirection checkCollisions(Bullet b){

		return null;
	}

	public BufferedImage draw(){
		BufferedImage boardReturn = new BufferedImage(128*xSize, 128*ySize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)boardReturn.getGraphics();
		for(int x=0; x<xSize; x++){
			for(int y=0; y<ySize; y++){
				if(tiles[x][y] instanceof Wall){
					g.setColor(Color.black);
					g.fillRect(x*tileSize, y*tileSize, tileSize, tileSize);
				}
			}
		}
		return boardReturn;
	}

}
