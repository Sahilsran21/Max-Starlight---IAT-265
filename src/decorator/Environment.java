package decorator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PVector;

public class Environment {

	
	
//Fields
	private PVector pos;
	public double scale;
	public double angle;
	
	private Rectangle2D.Double sky;
	private Rectangle2D.Double ground;
	

	
//Methods
	private void setAttributes() {
		sky.setFrame(0, 0, 1200, 600);
		ground.setFrame(0, 450, 1200, 250);		
	}
	
	
	
	
	
	
	public void draw(Graphics2D g2)
	{
		AffineTransform tr = g2.getTransform();
		g2.translate(pos.x, pos.y);	
		
		
		g2.setColor(new Color(0, 32, 128));
		g2.fill(sky);
		
		g2.setColor(new Color(8, 74, 77));
		g2.fill(ground);

		g2.setTransform(tr);		
	}

	
	public void drawDayBreak(Graphics2D g2)
	{
		AffineTransform tr = g2.getTransform();
		g2.translate(pos.x, pos.y);	
		
		
		g2.setColor(new Color(51, 204, 255));
		g2.fill(sky);
		
		g2.setColor(new Color(102, 255, 102));
		g2.fill(ground);

		g2.setTransform(tr);
	}
	
	
	

//Constructor
	public Environment(double x, double y)
	{
		pos = new PVector((float) x, (float) y);
		
		sky = new Rectangle2D.Double();
		ground = new Rectangle2D.Double();        

	
		
		
		
		setAttributes();
	}
	
	
	
}
	
