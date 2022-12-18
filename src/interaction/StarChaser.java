package interaction;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.Timer;

import processing.core.*;
import util.Util;

//Public class for the user controllable character
public class StarChaser implements ActionListener {

	
	
//Fields
	public PVector pos;
	protected PVector vel;
	public double angle;
	public double scale = 1; //1.5
			
	//Estimates of width and height for hit detection
	public final int HEAD_WIDTH = 70;
	public final int HEAD_HEIGHT = 70;
	public final int BODY_WIDTH = 60;
	public final int BODY_HEIGHT = 115;
					
	public boolean isMoving = true;
	//protected Timer timer;
	//protected int starBoyWaitTime = 300; //2700
			
	public float maxSpeed;
	private double damp = 0.8; //constant damping factor to slow down star chaser

			
	protected boolean alive;
	
	
	private double legAngle = 0;
	private double angleInc = 0.050;
	
	private double armAngle = 1;
	private double armAngleInc = 0.080;

			
	
	//For starChaser's boundary detection
    private final static int CHASER_DETECT_W = 1300;
	private final static int CHASER_DETECT_H = 350;
			
	private final static int CHASER_FIELD_DETECT_X = -475;
	private final static int CHASER_FIELD_DETECT_Y = 110;
	
	
	
    private float alpha = 1f;
	
	
    
    
    
    
    
	
	//Head Features
	private Ellipse2D.Double head;
	private Ellipse2D.Double pupil;
	private Ellipse2D.Double eye;
	private QuadCurve2D.Double mouth;
	
	private Polygon hair1;
	private Polygon hair2;
	private Polygon hair3;
	

	//Body Features
	private Ellipse2D.Double body;
	
	private Ellipse2D.Double arm1;
	private QuadCurve2D.Double hand1;
	
	private Ellipse2D.Double arm2;
	private QuadCurve2D.Double hand2;
	
	private Ellipse2D.Double leg1;
	private Ellipse2D.Double leg2;
	
	
	
	//Running Body Features
	private Ellipse2D.Double runningBody;
	
	private Ellipse2D.Double runningArm1;
	private QuadCurve2D.Double runningHand1;
	
	private Ellipse2D.Double runningArm2;
	private QuadCurve2D.Double runningHand2;
	
	private Ellipse2D.Double runningLeg1;
	private Ellipse2D.Double runningLeg2;
	
	
	
	//Crouching Body Features
	private Ellipse2D.Double crouchingBody;
	
	private Ellipse2D.Double reachingArm1;
	private QuadCurve2D.Double reachingHand1;
	
	private Ellipse2D.Double crouchingLeg1;
	private Ellipse2D.Double crouchingLeg2;
	
	private Ellipse2D.Double crouchingKnee1;
	private Ellipse2D.Double crouchingKnee2;
	
	
	
	
//Methods	
	//Set up head components with reference to the center of the body (0,0)
	private void setAttributes() {
		
	head.setFrame(575, 240, 70, 70);
	pupil.setFrame(634, 265, 6, 18);
	eye.setFrame(619, 263, 20, 23);
	mouth.setCurve(612, 298, 617, 299, 622, 302);
	
	hair1.addPoint(579,260);
	hair1.addPoint(660,275);
	hair1.addPoint(620,230);
	hair2.addPoint(605,225);
	hair2.addPoint(625,280);
	hair2.addPoint(568,260);
	hair3.addPoint(580,235);
	hair3.addPoint(610,280);
	hair3.addPoint(650,240);
	
	body.setFrame(580, 280, 60, 115);
	
	arm1.setFrame(600, 300, 20, 80);
	hand1.setCurve(601, 360, 610, 400, 619, 360);
	
	arm2.setFrame(630, 300, 20, 80);
	hand2.setCurve(601, 360, 610, 400, 619, 360);
	
	leg1.setFrame(605, 360, 30, 100);
	leg2.setFrame(585, 360, 35, 110);
	
	
	
	
	
	runningBody.setFrame(650, 20, 60, 115);
	
	runningArm1.setFrame(600, 300, 20, 80);
	runningHand1.setCurve(601, 360, 610, 400, 619, 360);
	
	runningArm2.setFrame(600, 300, 20, 80);
	runningHand2.setCurve(601, 360, 610, 400, 619, 360);
	
	runningLeg1.setFrame(570, 360, 30, 100);
	runningLeg2.setFrame(550, 360, 35, 110);
	

	
	
	
	

	crouchingBody.setFrame(580, 280, 60, 115);
	
	reachingArm1.setFrame(600, 300, 20, 80);
	reachingHand1.setCurve(601, 360, 610, 400, 619, 360);
	
	crouchingLeg1.setFrame(570, 360, 30, 60);
	crouchingLeg2.setFrame(550, 360, 35, 65);
	
	crouchingKnee1.setFrame(605, 360, 30, 60);
	crouchingKnee2.setFrame(585, 360, 35, 65);
	}
	
	
	//Public method for drawing the standing star chaser
	public void drawStanding(Graphics2D g2) {
		
			
		//Set up initial location for Blue Jay and rotate the drawing space before drawing
		AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);  
		g2.rotate(angle);
		g2.scale(scale,scale);	
		
		AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(acomp);
		
		
		//Mirrors the drawing 
		if (vel.x < 0)
		{
			g2.scale(-1, 1);
			g2.translate(-1210,0);  //1200
		}	
		
		
		g2.setColor(new Color(0, 0, 102));
		g2.fill(leg1);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(5));
		g2.draw(leg1);
		
		g2.setColor(new Color(0, 0, 102));
		g2.fill(leg2);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(5));
		g2.draw(leg2);
		
		g2.setColor(new Color(0, 204, 122));
		g2.fill(body);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(5));
		g2.draw(body);
		
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(5));
		g2.draw(arm1);
		g2.setColor(new Color(0, 204, 122));
		g2.fill(arm1);
		
		g2.setColor(new Color(217, 179, 140));
		g2.fill(hand1);
		
		g2.setColor(new Color(217, 179, 140));
		g2.fill(head);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(new Color(0,0,0));
		g2.draw(head);
		
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(5));
		g2.draw(eye);
		g2.setColor(new Color(255,255,255));
		g2.fill(eye);	
		
		g2.setColor(new Color(102, 51, 0));
		g2.fill(pupil);
		
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(mouth);
	
		g2.setColor(new Color(51, 51, 51));
		g2.fill(hair1);
	
		g2.setColor(new Color(51, 51, 51));
		g2.fill(hair2);
		
		g2.setColor(new Color(51, 51, 51));
		g2.fill(hair3);	
		
		g2.setTransform(transform);		
	}
	
	
	
	
	
	//Public method for drawing the running star Chaser
		public void drawRunning(Graphics2D g2) {
			
				
			//Set up initial location for Blue Jay and rotate the drawing space before drawing
			AffineTransform transform = g2.getTransform();
			g2.translate(pos.x - 0 , pos.y - 100);  
			g2.rotate(angle);
			g2.scale(scale,scale);	
			
			AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	        g2.setComposite(acomp);
			
			//Mirrors the drawing 
			if (vel.x < 0)
			{
				g2.scale(-1, 1);
				g2.translate(-1210,0);  //1200
			}	
			
			g2.rotate(0.5);
			g2.translate(250, -230);
			
			AffineTransform transformPoint = g2.getTransform();
			
			g2.translate(560, 330);	
			legAngle = legAngle + angleInc;
			if(legAngle>0.4 || legAngle<-0.2) angleInc*=-1;
			g2.rotate(legAngle);
			g2.translate(-560, -330);	
			g2.setColor(new Color(0, 0, 102));
			g2.fill(runningLeg1);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(runningLeg1);
		
			
			g2.setTransform(transformPoint);		
				
			
			g2.translate(560, 330);
			legAngle = legAngle + angleInc;
			if(legAngle>0.4 || legAngle<-0.2) angleInc*=-1;
			g2.rotate(-legAngle);
			g2.translate(-560, -330);
			g2.setColor(new Color(0, 0, 102));
			g2.fill(runningLeg2);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(runningLeg2);
			
			g2.setTransform(transformPoint);		
			
			g2.rotate(-0.5);
			g2.translate(-250, 230);
			
			
			
			g2.translate(590,300);
			
			AffineTransform transformPoint2 = g2.getTransform();
			
			armAngle = armAngle + armAngleInc;
			if(armAngle>1.0 || armAngle<-1.0) armAngleInc*=-1;
			g2.rotate(-armAngle);
			g2.translate(-600, -300);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(runningArm2);
			g2.setColor(new Color(0, 204, 122));
			g2.fill(runningArm2);
			
			g2.setColor(new Color(217, 179, 140));
			g2.fill(runningHand2);
			
			
			g2.translate(600, 300);
			
			g2.setTransform(transformPoint2);
			
			g2.translate(-595,-300);
			
			
			g2.rotate(0.4);
			g2.setColor(new Color(0, 204, 122));
			g2.fill(runningBody);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(runningBody);
			g2.rotate(-0.4);
			
		
			g2.translate(595,300);
			
			AffineTransform transformPoint3 = g2.getTransform();
			
			armAngle = armAngle + armAngleInc;
			if(armAngle>1.0 || armAngle<-1.0) armAngleInc*=-1;
			g2.rotate(armAngle);
			g2.translate(-600, -300);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(runningArm1);
			g2.setColor(new Color(0, 204, 122));
			g2.fill(runningArm1);
			
			g2.setColor(new Color(217, 179, 140));
			g2.fill(runningHand1);
			
			g2.translate(600, 300);
			
			g2.setTransform(transformPoint3);
			
			g2.translate(-595, -300);
			
			
			g2.translate(10,0);
			
			g2.setColor(new Color(217, 179, 140));
			g2.fill(head);
			g2.setStroke(new BasicStroke(3));
			g2.setColor(new Color(0,0,0));
			g2.draw(head);
			
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(eye);
			g2.setColor(new Color(255,255,255));
			g2.fill(eye);	
			
			g2.setColor(new Color(102, 51, 0));
			g2.fill(pupil);
			
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(3));
			g2.draw(mouth);
		
			g2.setColor(new Color(51, 51, 51));
			g2.fill(hair1);
		
			g2.setColor(new Color(51, 51, 51));
			g2.fill(hair2);
			
			g2.setColor(new Color(51, 51, 51));
			g2.fill(hair3);	
			
			g2.translate(-10,0);
			
			g2.setTransform(transform);		
		}
	
	

	
	
		
		

		//Public method for drawing the blue jay
		public void drawPickingUp(Graphics2D g2) {
			
				
			//Set up initial location for Blue Jay and rotate the drawing space before drawing
			AffineTransform transform = g2.getTransform();
			g2.translate(pos.x, pos.y);  
			g2.rotate(angle);
			g2.scale(scale,scale);	
			
			AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	        g2.setComposite(acomp);
			
			//Mirrors the drawing 
			if (vel.x < 0)
			{
				g2.scale(-1, 1);
				g2.translate(-1210,0);  //1200
			}	
			
			
			AffineTransform transformPoint2 = g2.getTransform();
			
			g2.rotate(-0.7);
			g2.translate(-360, 330);
			
			g2.setColor(new Color(0, 0, 102));
			g2.fill(crouchingLeg1);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(crouchingLeg1);
			
			g2.rotate(0.7);
			g2.translate(360, -330);
			
			g2.setTransform(transformPoint2);
			
			
			g2.rotate(0.5);
			g2.translate(150, -315);
			
			g2.setColor(new Color(0, 0, 102));
			g2.fill(crouchingKnee1);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(crouchingKnee1);
			
			g2.rotate(-0.5);
			g2.translate(-150, 315);
				
			g2.setTransform(transformPoint2);
			
			
			g2.rotate(-0.7);
			g2.translate(-360, 300);
			
			g2.setColor(new Color(0, 0, 102));
			g2.fill(crouchingLeg2);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(crouchingLeg2);
			
			g2.rotate(-0.7);
			g2.translate(360, -300);
			
			
			g2.setTransform(transformPoint2);
			
			
			g2.rotate(0.5);
			g2.translate(140, -300);
			
			g2.setColor(new Color(0, 0, 102));
			g2.fill(crouchingKnee2);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(crouchingKnee2);
			
			g2.rotate(-0.5);
			g2.translate(-140, 300);
			
			g2.setTransform(transformPoint2);
			
			
			AffineTransform transformPoint1 = g2.getTransform();
			
			g2.rotate(0.5);
			g2.translate(100, -330);
			
			g2.setColor(new Color(0, 204, 122));
			g2.fill(crouchingBody);
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(crouchingBody);
			
			g2.rotate(-0.5);
			g2.translate(-100, 330);
			
			
			g2.setTransform(transformPoint1);
			
			
			g2.rotate(-0.8);
			g2.translate(-460,350);
			g2.scale(1.1, 1.1);
			
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(reachingArm1);
			g2.setColor(new Color(0, 204, 122));
			g2.fill(reachingArm1);
			
			g2.setColor(new Color(217, 179, 140));
			g2.fill(reachingHand1);
			
			g2.rotate(0.8);
			g2.translate(460,-350);
			
			
			g2.setTransform(transformPoint1);
			
			
			g2.rotate(0.3);
			g2.translate(100, -190);
			
			g2.setColor(new Color(217, 179, 140));
			g2.fill(head);
			g2.setStroke(new BasicStroke(3));
			g2.setColor(new Color(0,0,0));
			g2.draw(head);
			
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(5));
			g2.draw(eye);
			g2.setColor(new Color(255,255,255));
			g2.fill(eye);	
			
			g2.setColor(new Color(102, 51, 0));
			g2.fill(pupil);
			
			g2.setColor(new Color(0));
			g2.setStroke(new BasicStroke(3));
			g2.draw(mouth);
		
			g2.setColor(new Color(51, 51, 51));
			g2.fill(hair1);
		
			g2.setColor(new Color(51, 51, 51));
			g2.fill(hair2);
			
			g2.setColor(new Color(51, 51, 51));
			g2.fill(hair3);	
			
			g2.rotate(-0.3);
			g2.translate(-100, 190);
			
			g2.setTransform(transform);		
		}
		
		


		
		
		
		
		
		//Private method for field edge detection
		public void edgeDetection() {
		//Check collision against left or right edge of the field
					
				if (pos.x + (BODY_WIDTH / 2 + HEAD_WIDTH) * scale > (CHASER_FIELD_DETECT_X + CHASER_DETECT_W )) {
					pos.x = (float) (CHASER_FIELD_DETECT_X + CHASER_DETECT_W  - (BODY_WIDTH / 2 + HEAD_WIDTH) * scale);
					vel.x *= -1;
					}

				if (pos.x - (BODY_WIDTH / 2 + HEAD_WIDTH) * scale < CHASER_FIELD_DETECT_X) {
					pos.x = (float) (CHASER_FIELD_DETECT_X +(BODY_WIDTH / 2 + HEAD_WIDTH)* scale);
					vel.x *= -1;
					}
					
		//Check collision against bottom or top edge of field
				if (pos.y + BODY_HEIGHT / 2 * scale > (CHASER_FIELD_DETECT_Y + CHASER_DETECT_H )) {
					pos.y = (float) (CHASER_FIELD_DETECT_Y + CHASER_DETECT_H - BODY_HEIGHT / 2 * scale);
					vel.y *= -1;
					}
							
				if (pos.y - BODY_HEIGHT / 2 * scale < CHASER_FIELD_DETECT_Y) {
					pos.y = (float) (CHASER_FIELD_DETECT_Y + BODY_HEIGHT / 2 * scale); 
					vel.y *= -1;
					}				
		}
				

				
		//Public method for moving
		public void move() {
				if(!isMoving)return;
				vel.mult((float) damp);
				vel.limit(maxSpeed);
				pos.add(vel); 
				edgeDetection();	
		}
		
		
		//Public method for updating speed in relation to the key commands
		public void updateSpeed(PVector acc) {
				vel.add(acc);
		}
				
				
				

		//Public method to make StarChaser stop to pick up the star
		public void stopAndPickUp()
		{
			//isMoving = false;		
					
			//if (isMoving == false)
			//{
			//	timer = new Timer(starBoyWaitTime, this);
			//	timer.start();
			//}
		}
				
				
				
		@Override
		public void actionPerformed(ActionEvent e) 
		{	
			//isMoving = true;
			//These values cause the shaky movement when the character collides with the two
			//stars or the lantern and a star at the same time
			//vel.x = 0;
			//vel.y = 0;
			//timer.stop();
		}

		
	
//Constructor
		public StarChaser(double x, double y, double velX, double velY, double s) {
		
			while (maxSpeed == 0)
		 		this.maxSpeed = (float) Util.random(20,20); //3,5
		 
			//Instantiate attributes
			pos = new PVector((float) x, (float) y);
			vel = new PVector((float) velX, (float) velY);
			scale = s;	
	 
			//Instantiate Features
			head = new Ellipse2D.Double();
			pupil = new Ellipse2D.Double();
			eye = new Ellipse2D.Double();
			mouth = new QuadCurve2D.Double();
		
			hair1 = new Polygon();
			hair2 = new Polygon();
			hair3 = new Polygon();
			
			body = new Ellipse2D.Double();
			
			arm1 = new Ellipse2D.Double();
			hand1 = new QuadCurve2D.Double();  
			
			arm2 = new Ellipse2D.Double();
			hand2 = new QuadCurve2D.Double(); 
			
			leg1 = new Ellipse2D.Double();
			leg2 = new Ellipse2D.Double();	
			
			
			
			
			runningBody = new Ellipse2D.Double();
			
			runningArm1 = new Ellipse2D.Double();
			runningHand1 = new QuadCurve2D.Double();  
			
			runningArm2 = new Ellipse2D.Double();
			runningHand2 = new QuadCurve2D.Double(); 
			
			runningLeg1 = new Ellipse2D.Double();
			runningLeg2 = new Ellipse2D.Double();	
			
			
			
			
			
			crouchingBody = new Ellipse2D.Double();
			
			reachingArm1 = new Ellipse2D.Double();
			reachingHand1 = new QuadCurve2D.Double();  
			
			crouchingLeg1 = new Ellipse2D.Double();
			crouchingLeg2 = new Ellipse2D.Double();	
			
			crouchingKnee1 = new Ellipse2D.Double();
			crouchingKnee2 = new Ellipse2D.Double();	
			
			
			
			
			
			setAttributes();		
	}
}

