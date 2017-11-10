package bots;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import arena.BattleBotArena;
import arena.BotInfo;
import arena.Bullet;

/**
 * The Drone is a Bot that moves in squares and only ever fires in the direction it is facing.
 * It also turns if it detects that it has hit something. Occasionally puts out a happy drone message.
 *
 * @author sam.scott
 * @version 1.0 (March 3, 2011)
 */
public class BurseyBot extends Bot {
	
	BotHelper help = new BotHelper();
	Bullet closeB;

	/**
	 * My name
	 */
	String name;
	/**
	 * My next message or null if nothing to say
	 */
	String nextMessage = null;
	/**
	 * Array of happy drone messages
	 */
	//private String[] messages = {"I am a drone", "Working makes me happy", "I am content", "I like to vaccuum", "La la la la la...", "I like squares"};
	/**
	 * Image for drawing
	 */
	Image up, down, left, right, current;
	/**
	 * For deciding when it is time to change direction
	 */
	private int counter = 50;
	//private int counB = 10;
	/**
	 * Current move
	 */
	private int move = BattleBotArena.UP;
	/**
	 * My last location - used for detecting when I am stuck
	 */
	private double x, y;
	private double ranf;
	private double ranm=100;
	private double xDis;
	private double yDis;
	
	/**
	 * Draw the current Drone image
	 */
	public void draw(Graphics g, int x, int y) {
		g.drawImage(current, x, y, Bot.RADIUS*2, Bot.RADIUS*2, null);
	}


	/**
	 * Move in squares and fire every now and then.
	 */
	public int getMove(BotInfo me, boolean shotOK, BotInfo[] liveBots,
			BotInfo[] deadBots, Bullet[] bullets) {
		ranm--;
		if (ranm==0) {
			ranm=100;
		}
		closeB=help.findClosest(me, bullets);
		xDis=help.calcDisplacement(me.getX(), closeB.getX());
		yDis=help.calcDisplacement(me.getY(), closeB.getY());

		if(x<=10) {
			move= BattleBotArena.RIGHT;
			ranm=0;
		}
		else if(x>=((BattleBotArena.RIGHT_EDGE)-10)) {
			move= BattleBotArena.LEFT;
			ranm=50;
		}
		else if(y<=10) {
			move= BattleBotArena.DOWN;
			ranm=0;
		}
		else if(y>=((BattleBotArena.BOTTOM_EDGE)-10)) {
			move= BattleBotArena.UP;
			ranm=50;
		}
		if (xDis<=10){
			if(ranm<25){
				move= BattleBotArena.DOWN;
			}
			else if(ranm>=25){
				move= BattleBotArena.UP;
			}
		}
		else {
			move=BattleBotArena.STAY;
			ranf=Math.floor(Math.random()*4);
			if(ranf==0){
				return BattleBotArena.FIREUP;
			}
			else if(ranf==1){
				return BattleBotArena.FIREDOWN;		
						}
			else if(ranf==2){
				return BattleBotArena.FIRELEFT;
			}
			else if (ranf==3){
				return BattleBotArena.FIRERIGHT;
			}
		
		}
		if (yDis<=10){
			if(ranm<25){
				move= BattleBotArena.RIGHT;
			}
			else if(ranm>=25){
				move= BattleBotArena.LEFT;
			}
		}
		else{
			 move=BattleBotArena.STAY;
				ranf=Math.floor(Math.random()*4);
				if(ranf==0){
					return BattleBotArena.FIREUP;
				}
				else if(ranf==1){
					return BattleBotArena.FIREDOWN;		
							}
				else if(ranf==2){
					return BattleBotArena.FIRELEFT;
				}
				else if (ranf==3){
					return BattleBotArena.FIRERIGHT;
				}
			

		}
		counter--;

		
		// change direction when the counter runs down or I detect I am stuck
		// update my record of my most recent position
		x = me.getX();
		y = me.getY();
		// set the image to use for next draw
		if (move == BattleBotArena.UP || move == BattleBotArena.FIREUP)
			current = up;
		else if (move == BattleBotArena.DOWN || move == BattleBotArena.FIREDOWN)
			current = down;
		else if (move == BattleBotArena.LEFT || move == BattleBotArena.FIRELEFT)
			current = left;
		else if (move == BattleBotArena.RIGHT || move == BattleBotArena.FIRERIGHT)
			current = right;
		return move;
	}

	/**
	 * Construct and return my name
	 */
	public String getName()
	{//done
		name ="BurseyBot";
		return name;
	}

	/**
	 * Team Arena!
	 */
	public String getTeamName() {
		return "Arena";
	}

	/**
	 * Pick a random starting direction
	 */
	public void newRound() {
		int i = (int)(Math.random()*4);
		if (i==0)
		{
			move = BattleBotArena.UP;
			current = up;
		}
		else if (i==1)
		{
			move = BattleBotArena.DOWN;
			current = down;
		}
		else if (i==2)
		{
			move = BattleBotArena.LEFT;
			current = left;
		}
		else
		{
			move = BattleBotArena.RIGHT;
			current = right;
		}

	}

	/**
	 * Image names
	 */
	public String[] imageNames()
	{
		String[] images = {"starfish4.png","starfish4.png","starfish4.png","starfish4.png"};
		return images;
	}

	/**
	 * Store the loaded images
	 */
	public void loadedImages(Image[] images)
	{
		if (images != null)
		{
			current = up = images[0];
			down = images[1];
			left = images[2];
			right = images[3];
		}
	}

	/**
	 * Send my next message and clear out my message buffer
	 */
	public String outgoingMessage()
	{
		String msg = nextMessage;
		nextMessage = null;
		return msg;
	}

	/**
	 * Required abstract method
	 */
	public void incomingMessage(int botNum, String msg)
	{

	}

}