package star;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import processing.core.*;

//Public class for the purple star collectible
public class PurpleStar extends Star{

//Fields
	protected int colourCode1 = 153;
	protected int colourCode2 = 0;
	protected int colourCode3 = 204;
	
	private QuadCurve2D.Double starPiece1;
	private QuadCurve2D.Double starPiece2;
	private QuadCurve2D.Double starPiece3;
	private QuadCurve2D.Double starPiece4;
	private QuadCurve2D.Double starPiece5;
	private Rectangle2D.Double starPiece6;
	
	private double angle = 1;
	private double angleIncrement = 0.025;
		
	private float alpha = 1f;
	

//Methods
	private void setAttributes() {
		starPiece1.setCurve(850, 500, 875, 370, 900, 500);
		starPiece2.setCurve(850, 500, 875, 370, 900, 500);
		starPiece3.setCurve(850, 500, 875, 370, 900, 500);
		starPiece4.setCurve(850, 500, 875, 370, 900, 500);
		starPiece5.setCurve(850, 500, 875, 370, 900, 500);
		starPiece6.setFrame(865, 498, 20, 20);
	}
		
	
	public void draw(Graphics2D g2)
	{
		//Set up initial location for Blue Jay and rotate the drawing space before drawing
		AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);  
			
		AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(acomp);
        
        
         
		if (isFalling == true)   //*Already rotates without this, perhaps due to being nested				
		{						//inside of yellow star in paintComponent
		g2.rotate(angle);
		angle = angle - angleIncrement;
		}
			 
		else if (isFalling == false)
		{
		g2.rotate(0);	
		
		//Fade the star away
		alpha += -0.02f;
			if (alpha <= 0) {
				alpha = 0;
			}
		}

		
		g2.scale(scale,scale);	
		g2.translate(-854, -510);  
		
		if (notCaptured) 
		{			
			
		g2.setColor(new Color(colourCode1, colourCode2, colourCode3));
		g2.fill(starPiece6);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(starPiece6);
				
		g2.setColor(new Color(colourCode1, colourCode2, colourCode3));
		g2.fill(starPiece1);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(starPiece1);
			
		g2.translate(1030, -490);  
		g2.rotate(1.2);
			
		g2.setColor(new Color(colourCode1, colourCode2, colourCode3));
		g2.fill(starPiece2);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(starPiece2);
			
		g2.translate(1050, -485);  
		g2.rotate(1.22);
			
		g2.setColor(new Color(colourCode1, colourCode2, colourCode3));
		g2.fill(starPiece3);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(starPiece3);
			
		g2.translate(1050, -480);  
		g2.rotate(1.22);
				
		g2.setColor(new Color(colourCode1, colourCode2, colourCode3));
		g2.fill(starPiece4);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(starPiece4);
			
		g2.translate(1138, -466);    
		g2.rotate(1.32);
			
		g2.setColor(new Color(colourCode1, colourCode2, colourCode3));
		g2.fill(starPiece5);
		g2.setColor(new Color(0));
		g2.setStroke(new BasicStroke(3));
		g2.draw(starPiece5);
			 		
		}	
		g2.setTransform(transform);			
	}
		

			
			
			
//Constructor
		public PurpleStar(double x, double y, double velX, double velY, double s)
		{
		super(x,y,velX,velY,s);
		
		//Instantiate Features
		starPiece1 = new QuadCurve2D.Double();
		starPiece2 = new QuadCurve2D.Double();
		starPiece3 = new QuadCurve2D.Double();
		starPiece4 = new QuadCurve2D.Double();
		starPiece5 = new QuadCurve2D.Double();
		starPiece6 = new Rectangle2D.Double();

		
		setAttributes();
		}
				
}

