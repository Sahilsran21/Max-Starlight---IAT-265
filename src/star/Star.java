package star;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.Timer;

import interaction.StarChaser;
import processing.core.*;

//Public abstract class to manage collectible
public abstract class Star {

//Fields
	public PVector pos;
	public PVector vel;
	public double scale;
	
	protected boolean notCaptured;
	protected boolean selected;
	
	public int BODY_WIDTH = 10; //10
	public int BODY_HEIGHT = 15; //15
	
	public boolean isFalling;
	
	//For star's boundary detection
	public final static int STAR_DETECT_W = 1240;
	public final static int STAR_DETECT_H = 700;
			
	public final static int STAR_FIELD_DETECT_X = -30;
	public final static int STAR_FIELD_DETECT_Y = -20;
	




//Methods	
	
		//Private method for field edge detection
		public void edgeDetection() {
		//Check collision against left or right edge of the field and move to the other end
				
				//since stars are currently traveling only left, this statement is not yet used
				//if (pos.x + (BODY_WIDTH / 2 + BODY_WIDTH) * scale > (STAR_FIELD_DETECT_X + STAR_DETECT_W )) {
					//pos.x = (float) (STAR_FIELD_DETECT_X + STAR_DETECT_W  - (BODY_WIDTH / 2 + BODY_WIDTH) * scale);
					//vel.x *= -1;
					//pos.x = -30;
					//}

				if (pos.x - (BODY_WIDTH / 2 + BODY_WIDTH) * scale < STAR_FIELD_DETECT_X) {
					pos.x = (float) (STAR_FIELD_DETECT_X +(BODY_WIDTH / 2 + BODY_WIDTH)* scale);
					//vel.x *= -1;
					pos.x = 1220;
					}
					
		//Check collision against bottom or top edge of field
				if (pos.y + BODY_HEIGHT / 2 * scale > (STAR_FIELD_DETECT_Y + STAR_DETECT_H )) {
					pos.y = (float) (STAR_FIELD_DETECT_Y + STAR_DETECT_H  - BODY_HEIGHT / 2 * scale);
					
					pos.y = -20;
					}
							
				if (pos.y - BODY_HEIGHT / 2 * scale < STAR_FIELD_DETECT_Y) {
					pos.y = (float) (STAR_FIELD_DETECT_Y + BODY_HEIGHT / 2 * scale); 
					vel.y *= -1;
					}				
		}

		
		
		
			
		//Public method for moving
				public void move() {
						if(!isFalling)return;
						pos.add(vel);
						edgeDetection();
				}

				
				
				
				


				public boolean detectGroundCollision(Star star)
				{
					boolean land = false;
					if (notCaptured) {
						if (star.pos.y > 455 && star.pos.y < 460)
						{
							land = true;
						}	
						
					}	
					return land;	
				}
				
				
				
				

			
				
				//Detect collision between the star and the starChaser
				public boolean detectCollision(StarChaser starChaser){
						boolean bump = false;
						//if (notCaptured) {
							if (Math.abs((pos.x) - starChaser.pos.x - 425) < ((BODY_WIDTH / 2 + (BODY_WIDTH * 4)) * scale + 
												(starChaser.HEAD_WIDTH / 2 + (BODY_WIDTH/2) / 2) * starChaser.scale)
										&& Math.abs((pos.y) - starChaser.pos.y - 250) < (BODY_HEIGHT * 12 * scale + 
												starChaser.HEAD_HEIGHT / 2 * starChaser.scale)) {
								bump = true;
								
								
							}
						//}
						return bump;
					}
			
		


					
			
		//Constructor 
			 public Star(double x, double y, double velX, double velY, double s) {
				 
					//Instantiate Star's attributes
					pos = new PVector((float) x, (float) y);
					vel = new PVector((float) velX, (float) velY);
					//angle = a;
					//angleIncrement = a;
					scale = s;
					notCaptured = true;	
			
			 }
	}
