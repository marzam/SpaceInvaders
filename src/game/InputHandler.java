package game;

import java.awt.event.KeyEvent;

import actors.Player;

/**
 * creates a thread to process player input
 * @author ghast
 *
 */
public class InputHandler extends Thread {
	
	private Invaders invaders = null;
	private Player player  = null;
	public Action action;
	public KeyEvent event;
	
	public InputHandler(Invaders invaders, Player player) {
		this.invaders = invaders;
		this.player = player;
	}
	
	public void run() {
		if (action == Action.PRESS) {
			if (KeyEvent.VK_ENTER == event.getKeyCode()) {
				if (invaders.gameOver || invaders.gameWon) {
					invaders.initWorld();
					invaders.game();
				}
			}else if (KeyEvent.VK_ESCAPE == event.getKeyCode()){
				System.exit(0);
			}else{
				player.keyPressed(event);
			}
			
			if ((event.getKeyCode() >= 65) && (event.getKeyCode() <= 76))
				invaders.setColumns(event.getKeyCode() - 65);
			
		}
		else if (action == Action.RELSEASE)
			player.keyReleased(event);		
	}
	
	public enum Action {
		PRESS,
		RELSEASE
	}
}
