package actors;

import java.awt.Graphics;

import game.ResourceLoader;
import game.Stage;

public class Invader extends Actor {
	
	private static final int POINT_VALUE = 10;
	protected static final double FIRING_FREQUENCY = 0.01;
	protected static final double FIRING_WEIGHT = 10.0f;
	 
	private int leftWall = 0;
	private int rightWall = 0;

	
	private String[] isprites = null; 
	private boolean invisible = false;
	 
	private int column = -1;

	//private int step = 0;
	//private int advanceTime = 1000;
	
	private Player player;
	
	public void setPlayer(Player p){
		this.player = p;
	}

	public void setColumn(int c){
		this.column = c;
	}
	
	public int getColumn(){
		return this.column;
	}

	public void setInvisible (boolean s){
		this.invisible = s;
	 
	}
	
	public Invader(Stage stage) {
		super(stage, "Invader");
		
		if (((int)(Math.random()*10))%2 == 0){ 
			sprites = new String[]{"invader1.gif", "invader2.gif"};
			isprites = new String[]{"invader5.gif", "invader6.gif"};
		}else{ 
			sprites = new String[]{"invader3.gif", "invader4.gif"};
			isprites = new String[]{"invader7.gif", "invader8.gif"};
		}
		
		frame = 0;
		frameSpeed = 50;
		actorSpeed = 100;
		width = 20;
		height = 20;
		posX = Stage.WIDTH/2;
		posY = Stage.HEIGHT/2;
	}
	
	public void paint(Graphics g) {
		if (invisible)
			g.drawImage(ResourceLoader.getInstance().getSprite(isprites[frame]), posX, posY, stage);
			
		else{
			g.drawImage(ResourceLoader.getInstance().getSprite(sprites[frame]), posX, posY, stage);
		}
	}
	
	
	public void fire() {
		InvaderShot shot = new InvaderShot(stage);			
		shot.setX(posX + width/2);
		shot.setY(posY + shot.getHeight());
		 
		stage.actors.add(shot);
	}
	
	public void act() {
		
		super.act();
		float weight = 1.0f;
		if (this.invisible){
			weight *= FIRING_WEIGHT;
		}
		
		if (Math.random() < (FIRING_FREQUENCY * weight)) {
			if (Math.random() < (FIRING_FREQUENCY * weight))
				fire();
		}
		
		
		updateXSpeed();
		
	}
		
	public void setLeftWall(int leftWall) {
		this.leftWall = leftWall;
	}
	
	public void setRightWall(int rightWall) {
		this.rightWall = rightWall;
	}
	
	private void updateXSpeed() {
		if (time % actorSpeed == 0) {
			posX += getVx();
			if (posX <= leftWall || posX >= rightWall){
				updateYSpeed();
				setVx(-getVx());
			}
		}
	}
	
	private void updateYSpeed() {
		posY += (height / 2);
			 
		 

		if (posY == stage.getHeight()) 
			stage.endGame();
	}

	public void collision(Actor a) {
		if (a instanceof InvaderShot)
			return;
		
		playSound("explosion.wav");
		if (a instanceof Shot)
			setMarkedForRemoval(true);
	}
	
	public int getPointValue() {
		return Invader.POINT_VALUE;
	}	
}
