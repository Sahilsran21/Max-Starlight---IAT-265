package interaction;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import processing.core.*;

//Public class for the Star Lantern
public class Lantern {

	
//Fields
	private Rectangle.Double lanternCase;
	private QuadCurve2D.Double lanternTop;
	private QuadCurve2D.Double lanternBottom;
	private Rectangle2D.Double lanternOpening;

	
	public PVector pos;
	private PVector vel;
	public double scale;
	private double angle;

	
	public static final int BODY_WIDTH = 40; //10
	public static final int BODY_HEIGHT = 100; //15
	
	//boolean to control the inside of the lantern lighting up when stars are placed inside
	//(Follows the lanternDetectionTimer since both processes are connected)
	public boolean lanternColorChange;

	

//Methods
	private void setAttributes() {
		lanternCase.setFrame(550, 400, 80, 90);
		lanternTop.setCurve(550, 402, 590, 380, 630, 402);
		lanternBottom.setCurve(550, 488, 590, 510, 630, 488);
		lanternOpening.setFrame(560, 410, 60, 60);
	}
			
		
	public void draw(Graphics2D g2)
	{
		//Set up initial location for Blue Jay and rotate the drawing space before drawing
		AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);  
		g2.scale(scale, scale);	
		g2.rotate(angle);	
				
		g2.setColor(new Color(153, 115, 0));
		g2.fill(lanternCase);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(lanternCase);
					
		g2.setColor(new Color(153, 115, 0));
		g2.fill(lanternTop);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(lanternTop);
				
		g2.setColor(new Color(153, 115, 0));
		g2.fill(lanternBottom);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(lanternBottom);	
		
		
		if (lanternColorChange == true)
		{
			g2.setColor(new Color(255, 219, 77));
		}
		
		else if (lanternColorChange == false)
		{
			g2.setColor(new Color(255, 191, 0));
		}
		
		
		g2.fill(lanternOpening);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(lanternOpening);
		
				
		g2.setTransform(transform);			
	}
	

	
	
	
	//Detect collision between the lantern and the starChaser
	public boolean detectCollision(StarChaser starChaser){
			boolean bump = false;
				if (Math.abs((pos.x) - starChaser.pos.x + 170) < ((BODY_WIDTH / 2 + (BODY_WIDTH/2)) * scale + 
							(starChaser.HEAD_WIDTH / 2 + (BODY_WIDTH/2) / 2) * starChaser.scale)
						&& Math.abs((pos.y) - starChaser.pos.y) < (BODY_HEIGHT * 2) * scale + 
								starChaser.HEAD_HEIGHT / 2 * starChaser.scale) {
					bump = true;	
				}
			return bump;
		}
				
	

	
	//Public method for moving
	public void move() {	
		pos.add(vel);
	}
				
				
//Constructor
		public Lantern(int x, int y, int velX, int velY, double s)
		{	
		pos = new PVector(x, y);
		vel = new PVector(velX, velY);
		scale = s;
			
			
		//Instantiate Features
		lanternCase = new Rectangle2D.Double();
		lanternTop = new QuadCurve2D.Double();
		lanternBottom = new QuadCurve2D.Double();
		lanternOpening = new Rectangle2D.Double();
		
			
		setAttributes();
		}
					
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
