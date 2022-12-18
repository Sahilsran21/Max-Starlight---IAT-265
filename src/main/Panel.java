package main;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import decorator.Environment;
import decorator.GrassBunch;
import interaction.Lantern;
import processing.core.PVector;
import star.PurpleStar;
import star.Star;
import interaction.StarChaser;
import decorator.StarrySky;
import star.YellowStar;
import util.Util;


public class Panel extends JPanel implements ActionListener, MouseListener {
	
	
//Fields
	public ArrayList<StarChaser> starChaser;
	public ArrayList<Star> star;
	public ArrayList<Lantern> lantern;
	
	private Environment environment;
	
	private GrassBunch grassBunch1;
	private GrassBunch grassBunch2;
	private GrassBunch grassBunch3;
	private GrassBunch grassBunch4;
	private GrassBunch grassBunch5;
	private GrassBunch grassBunch6;
	private GrassBunch grassBunch7;
	private GrassBunch grassBunch8;
	
	private StarrySky starrySky;
	
	private Timer starBoyTimer;

	public final static int PANEL_W = 1200;
	public final static int PANEL_H = 700;
	
	public final static int FIELD_X = 20;
	public final static int FIELD_Y = 20;

	private final static int STARCHASER_COUNT = 1;
	private final static int STAR_COUNT = 1;
	private final static int STAR_LIMIT = 6;
	private final static int LANTERN_COUNT = 1;
	
	
	
	
	//States for each part of the simulation
	private static final int INTRO_SCREEN = 0; 
	private static final int RUNNING_SIMULATION = 1; 
	private static final int LANTERN_CUTSCENE = 2; 
	private static final int DAYBREAK_CUTSCENE = 3;
	private static final int ENDING_SCREEN = 4;
	
	private int state = INTRO_SCREEN;
	private static String maxStarlight = "Max Starlight";
	private static String bySahilSran = "by Sahil Sran";
	private static String introMessagePt1 = "Help Max gather up falling stars and light the ";
	private static String introMessagePt2 = "lantern to put an end to the everlasting night!";
	private static String start = "START";
	
	private static String Congratulations = "Congratulations!";
	private static String outroMessage = "The endless night has been vanquished!";
	private static String restart = "RESTART";
	
	
	//For mouse hit detection with the intro button
	private final static int BUTTON_WIDTH = 140;
	private final static int BUTTON_HEIGHT = 55;
	
	//Fields for controlling fractural grass blades
	private double length = 55;
	private double width = 5;
	private int depth = 2;
	

	
	//Fields for enabling character to move through key commands
	protected boolean up, down, left, right;
	
	private double starChaserSpeedX = 2.5;
	private double  starChaserSpeedY = 2.5;
	
	PVector upAcc = new PVector(0, (float) -starChaserSpeedY);
	PVector downAcc = new PVector(0, (float) starChaserSpeedY);
	PVector leftAcc = new PVector((float) -starChaserSpeedX, 0);
	PVector rightAcc = new PVector((float) starChaserSpeedX, 0);
	
	
	//Setup Timer for initial star fall
	private int starFallTimer = 0;
	private int starFallWaitTime = 40; //80
	
	//Setup Timer and Tracker for star fading away
	private int yellowStarFadeTimer = 0;
	private int purpleStarFadeTimer = 0;
	private int yellowStarFadeWaitTime = 100;
	private int purpleStarFadeWaitTime = 50; 
	
	//Boolean to control "picking up" state of character upon collision with star
	private boolean starDetection;
	
    //Setup Timer for star Detection to control character "picking up" state
    private int starDetectionTimer = 0;
    private int starDetectionWaitTime = 30;
    
	//Setup Timer to control the time the character stops to pick up objects
    private int starBoyWaitingTimer = 0;
    private int starBoyWaitingWaitTime = 30;
	
	//Calculates a random number so there's a 1/2 chance to see a purple star
	private int starRarityTracker;
	
	//Setup timer for perlin noise decorator to update
	private int starrySkyTimer = 0;
	private int starrySkyWaitTime = 10;
	
	//Setup tracker for number of stars carried
	private int starCapacityTracker;
	private int starCapacityLimit = 3;
	
	//Setup tracker for number of points accumulated by stars
	private int starPointTracker;
	private int lanternPointTracker;
	private int lanternPointLimit = 10;
	
	//Boolean to control "dropping off" state of character upon collision with lantern
	private boolean lanternDetection;
	
	//Setup Timer for star Detection to control character "picking up" state
	private int lanternDetectionTimer = 0;
	private int lanternDetectionWaitTime = 12; //30 //12

	//Setup Timer for the length of the lantern cutscene
	private int lanternCutsceneTimer = 0;
	private int lanternCutsceneWaitTime = 300;
	
	//Setup Timer for the length of the dayBreak cutscene
	private int dayBreakCutsceneTimer = 0;
	private int dayBreakCutsceneWaitTime = 100;
	
	//Boolean for determining when the lantern should start glowing
	private boolean isGlowing; 
	
	private int ballOfLightLength = 60;
	private int ballOfLightWidth = 60;
	private int ballOfLightPosX = 560;
	private int ballOfLightPosY = 410;
	private int ballOfLightScale = 1;
	
	
//Methods
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		AffineTransform transform = g2.getTransform();
		//
		//System.out.println(starDetection);	
		//System.out.println(starCapacityTracker);	
		//System.out.println(lanternDetection);	
		//System.out.println(lantern.lanternColorChange);	
		//System.out.println(lanternCapacityTracker);	
	
		
		//System.out.println(starDetectionTimer);	
		//System.out.println(lanternCapacityTracker);	
		
//Also, draw the environment without beginning any process yet
if (state == INTRO_SCREEN || state == RUNNING_SIMULATION)
{	
	
		environment.draw(g2);
		
		starrySky.drawStarrySky(g2);
		
		grassBunch1.drawGrassBunch(g2);
		grassBunch2.drawGrassBunch(g2);
		grassBunch3.drawGrassBunch(g2);
		grassBunch4.drawGrassBunch(g2);
		grassBunch5.drawGrassBunch(g2);
		grassBunch6.drawGrassBunch(g2);
		grassBunch7.drawGrassBunch(g2);
		grassBunch8.drawGrassBunch(g2);
		
	    isGlowing = false;
		
		for (int i = 0; i < lantern.size(); i++)
			if (lantern.get(i) instanceof Lantern) {
				Lantern lanternl = (Lantern) lantern.get(i);
				{
					lanternl.draw(g2);
				}
			}		
			
}		


//The lantern is rising and illuminating the land
if (state == LANTERN_CUTSCENE)
{
	
	environment.draw(g2);
	
	starrySky.drawStarrySky(g2);
	
	grassBunch1.drawGrassBunch(g2);
	grassBunch2.drawGrassBunch(g2);
	grassBunch3.drawGrassBunch(g2);
	grassBunch4.drawGrassBunch(g2);
	grassBunch5.drawGrassBunch(g2);
	grassBunch6.drawGrassBunch(g2);
	grassBunch7.drawGrassBunch(g2);
	grassBunch8.drawGrassBunch(g2);
	
	isGlowing = true;
	lanternCutsceneTimer++;

	for (int i = 0; i < lantern.size(); i++)
			if (lantern.get(i) instanceof Lantern) {
				Lantern lanternl = (Lantern) lantern.get(i);

				
				lanternl.draw(g2);
				
				//The lanturn rises up
				if (lanternl.pos.y > -320)
				{		
				lanternl.pos.y = (float) (lanternl.pos.y - 2);
				ballOfLightPosY = ballOfLightPosY - 2;
				
				}
				
				
				//Drawing the ball of light 
				g2.scale(ballOfLightScale, ballOfLightScale);
				g2.setColor(new Color(255, 219, 77));
				g2.fillOval(ballOfLightPosX, ballOfLightPosY, ballOfLightLength, ballOfLightWidth);	  
				    
				ballOfLightLength = ballOfLightLength + 2;
				ballOfLightWidth = ballOfLightWidth + 2;
				
				ballOfLightScale = (int) (ballOfLightScale + 0.2);
			
				ballOfLightPosX = ballOfLightPosX - 1;
				ballOfLightPosY = ballOfLightPosY - 1;			
			}
	
	
	//Conditional to end the first part of the cutscene and show the day
	if (lanternCutsceneTimer > lanternCutsceneWaitTime)
	{
	state = DAYBREAK_CUTSCENE;
	lanternCutsceneTimer = 0;
	}	

}
	

//The land transforms to day and the simulation ends
if (state == DAYBREAK_CUTSCENE || state == ENDING_SCREEN)
{
	
	environment.drawDayBreak(g2);
	
	dayBreakCutsceneTimer++;
	
	for (int i = 0; i < lantern.size(); i++)
		if (lantern.get(i) instanceof Lantern) {
			Lantern lanternl = (Lantern) lantern.get(i);
			{
				lanternl.draw(g2);
			}
		}	
	
	
	//Conditional to end the cutscene and show the end screen
	if (dayBreakCutsceneTimer > dayBreakCutsceneWaitTime)
	{
	state = ENDING_SCREEN;
	dayBreakCutsceneTimer = 0;
	}	

}





//At start of the simulation, present the intro screen to the player
if (state == INTRO_SCREEN)
{
	g.setColor(new Color(179, 236, 255));
	g.fillRect(310, 130, 600, 440);
	
	Font font1 = new Font("Helvetica", Font.BOLD, 48);
	g2.setFont(font1);
	//FontMetrics metrics = g.getFontMetrics(font1);
	g2.setColor(new Color(0, 32, 128));
	g2.drawString(maxStarlight ,455, 200);
	
	Font font2 = new Font("Helvetica", Font.PLAIN, 40);
	g2.setFont(font2);
	g2.setColor(new Color(0, 32, 128));
	g2.drawString(bySahilSran ,485, 270);
	
	Font font3 = new Font("Helvetica", Font.PLAIN, 26);
	g2.setFont(font3);
	g2.setColor(new Color(0, 57, 230));
	g2.drawString(introMessagePt1 ,353, 360);
	
	Font font4 = new Font("Helvetica", Font.PLAIN, 26);
	g2.setFont(font4);
	g2.setColor(new Color(0, 57, 230));
	g2.drawString(introMessagePt2 ,355, 400);
	
	
	g2.setColor(new Color(0, 172, 230));
	g2.fillRect(540, 480, 140, 55);
	g2.setColor(new Color(0));
	g2.setStroke(new BasicStroke(3));
	g2.drawRect(540, 480, BUTTON_WIDTH, BUTTON_HEIGHT);
	
	Font font5 = new Font("Helvetica", Font.BOLD, 32);
	g2.setFont(font5);
	g2.setColor(new Color(0, 32, 128));
	g2.drawString(start ,555, 520);	
	
g2.setTransform(transform);	


//Reseting the lantern attribute here
	for (int i = 0; i < lantern.size(); i++)
		if (lantern.get(i) instanceof Lantern) {
			Lantern lanternl = (Lantern) lantern.get(i);
			{
				lanternl.pos.y = 0;
			}
		}
}

	
	
	






//At end of the simulation, present the ending screen to the player
if (state == ENDING_SCREEN)
{
	g.setColor(new Color(179, 236, 255));
	g.fillRect(310, 220, 600, 310);
	
	Font font1 = new Font("Helvetica", Font.BOLD, 48);
	g2.setFont(font1);
	g2.setColor(new Color(0, 32, 128));
	g2.drawString(Congratulations, 415, 290);
	
	Font font2 = new Font("Helvetica", Font.PLAIN, 30);
	g2.setFont(font2);
	g2.setColor(new Color(0, 57, 230));
	g2.drawString(outroMessage, 340, 380);
	
	g2.setColor(new Color(0, 172, 230));
	g2.fillRect(520, 450, 180, 55);
	g2.setColor(new Color(0));
	g2.setStroke(new BasicStroke(3));
	g2.drawRect(520, 450, BUTTON_WIDTH + 40, BUTTON_HEIGHT);
	
	Font font3 = new Font("Helvetica", Font.BOLD, 32);
	g2.setFont(font3);
	g2.setColor(new Color(0, 32, 128));
	g2.drawString(restart ,535, 490);	
	
g2.setTransform(transform);	


//Resetting the trackers here to ensure the capacity doen't carry over in the new game
	starCapacityTracker = 0;
	lanternPointTracker = 0;
	starPointTracker = 0;

//Resetting the ball of light attributes here
	ballOfLightLength = 60;
	ballOfLightWidth = 60;
	ballOfLightPosX = 560;
	ballOfLightPosY = 410;
	ballOfLightScale = 1;
	
}




//If simulation is running, then begin star collecting and all other processes
if (state == RUNNING_SIMULATION)	
{	
		
	//Drawing the common yellow star
		for (int i = 0; i < star.size(); i++)
			if (star.get(i) instanceof YellowStar) {
				YellowStar stari = (YellowStar) star.get(i);
		
				stari.draw(g2);
				stari.isFalling = true;
			
			if (stari.detectGroundCollision(stari))
			{	
				star.remove(i);	
				star.add(new YellowStar(Util.random(20, 1180), Util.random(460, 680),
				0, 0, 0.7 - 0.4));	
				stari.isFalling = false;
				
			}
			
			
			//Time the fading stars using a counter 
			if (yellowStarFadeTimer > yellowStarFadeWaitTime && stari.vel.x == 0 && stari.vel.y == 0)
			{
			yellowStarFadeTimer = 0;
			star.remove(i);	
			}
			
			if (stari.vel.x == 0 && stari.vel.y == 0)
			{
			yellowStarFadeTimer++;
			stari.isFalling = false;
			}	
			
				
			
			//Hit detection with the StarChaser
			for (int k = 0; k < starChaser.size(); k++)
				if (starChaser.get(k) instanceof StarChaser) {
					StarChaser starChaserk = (StarChaser) starChaser.get(k);
		
						if (stari.detectCollision(starChaserk)) 
						{
							if (starCapacityTracker < starCapacityLimit)
							{
								starCapacityTracker++;
								starPointTracker++;
								starDetection = true;
								starChaserk.isMoving = false;	
								star.remove(i);				
							}
						}	
						
						if (starDetection == true)
						{
							starDetectionTimer++;	
						}
						
						if (starDetectionTimer > starDetectionWaitTime)
						{
							starDetectionTimer = 0;
							starDetection = false;	
						}
				
								
				}
			}
	
		
		
		
		
	
			
		
			
			//Drawing the rare purple star
				for (int j = 0; j < star.size(); j++)
					if (star.get(j) instanceof PurpleStar) {
						PurpleStar starj = (PurpleStar) star.get(j);
						
					
						starj.draw(g2);
						starj.isFalling = true;
					
					if (starj.detectGroundCollision(starj))
					{		
						star.remove(j);	
						star.add(new PurpleStar(Util.random(20, 1180), Util.random(460, 680),
						0, 0, 0.7 - 0.4));	
						starj.isFalling = false;							
					}
					
					
					//Time the fading stars using a counter 
					if (purpleStarFadeTimer > purpleStarFadeWaitTime && starj.vel.x == 0 && starj.vel.y == 0)
					{
					purpleStarFadeTimer = 0;
					
					star.remove(j);	
					}
					
					if (starj.vel.x == 0 && starj.vel.y == 0)
					{
					purpleStarFadeTimer++;
					starj.isFalling = false;
					}
					
				
					
					
					for (int k = 0; k < starChaser.size(); k++)
						if (starChaser.get(k) instanceof StarChaser) {
							StarChaser starChaserk = (StarChaser) starChaser.get(k);
						
				
								
								if (starj.detectCollision(starChaserk)) 
								{								
									if (starCapacityTracker < starCapacityLimit)
									{
										starCapacityTracker++;
										starPointTracker = starPointTracker + 2;
										starDetection = true;
										starChaserk.isMoving = false;	
										star.remove(j);	
									}
										
								}
								
								if (starDetection == true)
								{
									starDetectionTimer++;		
								}
								
								if (starDetectionTimer > starDetectionWaitTime)
								{
									starDetectionTimer = 0;
									starDetection = false;
								}
								
								if (starChaserk.isMoving == false)
								{
									starBoyWaitingTimer++;
								}
								
								if (starBoyWaitingTimer > starBoyWaitingWaitTime)
								{
									starBoyWaitingTimer = 0;
									starChaserk.isMoving = true;
								}
								
						}
								
					}
					
					
					
				
				
				
				
				
				//Drawing the Lantern
					for (int k = 0; k < starChaser.size(); k++)
						if (starChaser.get(k) instanceof StarChaser) {
							StarChaser starChaserk = (StarChaser) starChaser.get(k);
							{
								for (int l = 0; l < lantern.size(); l++)
									if (lantern.get(l) instanceof Lantern) {
										Lantern lanternl= (Lantern) lantern.get(l);	
										{
										
								
											if (lanternl.detectCollision(starChaserk))
											{			
												if (starCapacityTracker > 0)
												{
												//Fill up lantern based on points
												lanternPointTracker = lanternPointTracker + starPointTracker;
												starCapacityTracker = 0;
												starPointTracker = 0;
									
												lanternl.lanternColorChange = true;
												lanternDetection = true;
												starChaserk.isMoving = false;	
												}
											}
							
											if (lanternDetection == true)
											{
												lanternDetectionTimer++;
											}
								
											if (lanternDetectionTimer > lanternDetectionWaitTime)
											{
												lanternDetectionTimer = 0;
												lanternl.lanternColorChange = false;
												lanternDetection = false;
											}
											
											
											if (starChaserk.isMoving == false)
											{
												starBoyWaitingTimer++;
											}
											
											if (starBoyWaitingTimer > starBoyWaitingWaitTime)
											{
												starBoyWaitingTimer = 0;
												starChaserk.isMoving = true;
											}
								
										}
									}
							}
								
								
								//Condition to end the running simulation and begin the lantern cutscene						
								if (lanternPointTracker > lanternPointLimit)
								{
								state = LANTERN_CUTSCENE;		
								}				
					}
							
						
		
		
		
		
		
		
		for (int j = 0; j < starChaser.size(); j++)
		{
			
			//System.out.println(starDetection);
			
			if (up == true || down == true || left == true || right == true)
			{			
				
				if (starDetection == true || lanternDetection == true)
				{
				starChaser.get(j).drawPickingUp(g2);
				}
				
				else if (starDetection == false || lanternDetection == false)
				{
				starChaser.get(j).drawRunning(g2);
				}	
	
			}		
			
			
			
			else if (up == false && down == false && left == false && right == false)
			{
			
				if (starDetection == true || lanternDetection == true)
				{
				starChaser.get(j).drawPickingUp(g2);
				}
			
				else if (starDetection == false || lanternDetection == false)
				{
				starChaser.get(j).drawStanding(g2);
				}	
			}
			
			
			
		
		}
		
	

}		

		
	
		
		
	
	//Time the falling stars using a counter 
	if (state == RUNNING_SIMULATION)
	{
		if (starFallTimer > starFallWaitTime)
		{
		starFallTimer = 0;
		
			if (star.size() < STAR_LIMIT) {
				
				if (starRarityTracker < 5)
				for (int i = 0; i < STAR_COUNT; i++) {
					star.add(new YellowStar(Util.random(0, 1200), Util.random(-20,-20),
							Util.random(-5,-1), Util.random(-1, -3), 0.7 - 0.4));
				}
				
			
				else if (starRarityTracker > 5)
				for (int j = 0; j < STAR_COUNT; j++) {
					star.add(new PurpleStar(Util.random(0, 1200), Util.random(-20,-20),
							Util.random(-7,-3), Util.random(-3, -5), 0.7 - 0.4));
				}
				
				
				starRarityTracker = (int) Util.random(1,10);
				//System.out.println(starRarityTracker);
			}
			
		}
		
		starFallTimer++;
}
		
		
		
		
		
		
		//Time every time the perlin noise decorator updates using a counter 
		if (starrySkyTimer > starrySkyWaitTime)
		{
		starrySkyTimer = 0;
		starrySky = new StarrySky();
		}	
			
			
		starrySkyTimer++;
		
			

}	
					
			
	
	
	


	
	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < starChaser.size(); i++) 
		{
			starChaser.get(i).move();		
			
			if (up == true)
			{
			starChaser.get(i).updateSpeed(upAcc);
			}
			
			if (down == true)
			{
			starChaser.get(i).updateSpeed(downAcc);
			}
			
			if (left == true)
			{
			starChaser.get(i).updateSpeed(leftAcc);
			}
			
			if (right == true)
			{
			starChaser.get(i).updateSpeed(rightAcc);
			}		
		}	
		
		
		
		for (int i = 0; i < star.size(); i++)
			if (star.get(i) instanceof YellowStar) {
				YellowStar stari = (YellowStar) star.get(i);
					stari.move();	
			}
					
		for (int j = 0; j < star.size(); j++)
			if (star.get(j) instanceof PurpleStar) {
				PurpleStar starj = (PurpleStar) star.get(j);
					starj.move();	
			}
		
		for (int k = 0; k < lantern.size(); k++)
			if (lantern.get(k) instanceof Lantern) {
				Lantern lanternk = (Lantern) lantern.get(k);
					lanternk.move();	
			}

		
		
		repaint();
	}


	
	
	
	
	public class MyKeyListener extends KeyAdapter{
		 public void keyPressed(KeyEvent e) {
		      if (e.getKeyCode() == KeyEvent.VK_UP) {
		    	  up = true;
		      }
		      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		    	  down = true;
		      }
		      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		    	  left = true;
			  }
			  if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				  right = true;
			  }
		 }
		      
		      
		      
		 public void keyReleased(KeyEvent e) {
			  if (e.getKeyCode() == KeyEvent.VK_UP) {
				  up = false;
			  }
			  if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				  down = false;
			  }
			  if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				  left = false;
			  }
			  if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				  right = false;
			  }  
		  }
	
		
	}
	
	

	public void mouseClicked(MouseEvent e) {	
	}
	public void mouseReleased(MouseEvent e) {	
	}
	public void mouseEntered(MouseEvent e) {		
	}
	public void mouseExited(MouseEvent e) {	
	}
	

	
//Mouse listener for working start and reset buttons
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (state == INTRO_SCREEN)
		{
			if (checkStartButtonHit(e) == true) {		
				state = RUNNING_SIMULATION;
			}
		}
		
		if (state == ENDING_SCREEN)
		{
			if (checkRestartButtonHit(e) == true) {	
				state = INTRO_SCREEN;	
		}
		
	
		}
	}
	
	
	public boolean checkStartButtonHit(MouseEvent e) {
		return (Math.abs(e.getX() - 608) < (BUTTON_WIDTH/2 + 3) && 
				Math.abs(e.getY() - 508) < (BUTTON_HEIGHT/2 + 3));	
	}
	
	
	public boolean checkRestartButtonHit(MouseEvent e) {
		return (Math.abs(e.getX() - 592) < (BUTTON_WIDTH/2 + 40) && 
				Math.abs(e.getY() - 480) < (BUTTON_HEIGHT/2 + 3));	
	}
	
		
	
	
//Constructor
	public Panel() {
		this.setBackground(Color.white);
		setPreferredSize(new Dimension(PANEL_W, PANEL_H));
		addKeyListener(new MyKeyListener());
		setFocusable(true);
		this.addMouseListener(this);
		double scale = 0.7;
		starChaser = new ArrayList<StarChaser>();
		star = new ArrayList<Star>();
		lantern = new ArrayList <Lantern>();
		
		environment = new Environment(0,0);
		grassBunch1 = new GrassBunch(-200, -250, length, width, 0, depth, scale + 0.3);
		grassBunch2 = new GrassBunch(-100, -100, length, width, 0, depth, scale + 0.3);
		grassBunch3 = new GrassBunch(-20, -200, length, width, 0, depth, scale + 0.3);
		grassBunch4 = new GrassBunch(100, -150, length, width, 0, depth, scale + 0.3);
		
		grassBunch5 = new GrassBunch(600, -250, length, width, 0, depth, scale + 0.3);
		grassBunch6 = new GrassBunch(750, -100, length, width, 0, depth, scale + 0.3);
		grassBunch7 = new GrassBunch(800, -200, length, width, 0, depth, scale + 0.3);
		grassBunch8 = new GrassBunch(900, -150, length, width, 0, depth, scale + 0.3);
		
		starrySky = new StarrySky();
		
		starBoyTimer = new Timer(30, this);
		starBoyTimer.start();
		
		
		for (int i = 0; i < STARCHASER_COUNT; i++) {
			starChaser.add(new StarChaser(Util.random(-400,750), Util.random(130,370), 
					0, 0, scale));
		}

		for (int i = 0; i < LANTERN_COUNT; i++) {
			lantern.add(new Lantern(0,0,0,0,1));	
		}

		//Star objects creation is done in paintComponent
	
	}

}
