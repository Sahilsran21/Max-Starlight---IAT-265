package decorator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

import processing.core.PVector;
import util.Util;




//Public class to draw fractal decorator Grass Blade
public class GrassBlade {

	
	
//Fields
	public static final double angleLeft = -0.5;
	public static final double angleRight = 0.5;
	public static final double centerCoef = 1.0;
	public static final double sideCoef = 3;
	
	private double length;
	private double width;
	public double scale;
	public double angle;
	private int depth;
		
	
	private QuadCurve2D.Double grassBody;
	private Line2D.Double grassStrand;
		
	
	private GrassBlade left = null;
	private GrassBlade center = null;
	private GrassBlade right = null;
	
	//Boolean for changing the color of the grass upon daybreak
	protected boolean grassColorChange;

	
	
	
//Methods	
	private void setAttributes() 
	{
		grassBody.setCurve(15, 500, 0, 480, 15, 460);
		grassStrand.setLine(16, 500, 16, 460);
	}
	
	
	
	
	
	
	
	//Use Fractal pattern to draw grass
	public void drawGrass(Graphics2D g2)
	{
	AffineTransform tr = g2.getTransform();
	//g2.translate(pos.x, pos.y);		
	g2.scale(scale, scale);	
	g2.translate(0,0);
	
	g2.translate(220, 660);
	g2.rotate(angle);
	g2.translate(-220, -660);
	
	g2.translate(205,220);
	//g2.rotate(0.1);
	g2.setStroke(new BasicStroke(3));
	g2.setColor(new Color(0));
	g2.draw(grassStrand);
	
	
	if (grassColorChange = false)
	{
		g2.setColor(new Color(0, 128, 96));
	}
	
	else if (grassColorChange = true)
	{
		g2.setColor(new Color(0,0, 0));		 //204
	}
	
	
	g2.setColor(new Color(0, 128, 96));		
	g2.fill(grassBody);
	g2.setColor(new Color(0));
	g2.draw(grassBody);
	
	
	
	g2.translate(-160, -length - 60);
	
	if (left != null) left.drawGrass(g2);
	if (right != null) right.drawGrass(g2);	
	if (center != null) center.drawGrass(g2);
	
	g2.setTransform(tr);		
	}
	
	
	
	
	
//Constructor 	
	public GrassBlade(double l, double w, double a, int d, double s)
	{	
		length = l;
		width = w;
		angle = a;
		depth = d;
		scale = s;
	
		
		grassBody = new QuadCurve2D.Double();
		grassStrand = new Line2D.Double();
	
	

		//For Primary Branches
		if (depth >= 1) {
			if (Util.random(0,1) > 0.03) 
				left = new GrassBlade(length - 30 *sideCoef, width*sideCoef, angleLeft+Util.random(-angleLeft*0.2, angleLeft*0.2), depth-1, scale - 0.1);
			if (Util.random(0,1) > 0.03)
				right = new GrassBlade(length - 30 *sideCoef, width*sideCoef, angleRight+Util.random(-angleRight*0.2, angleRight*0.2), depth-1, scale - 0.1);
			if (Util.random(0,1) > 0.03)
				center = new GrassBlade(length -80 *centerCoef, width*sideCoef, Util.random(-0.2, 0.3), depth-1, scale - 0.1);
		}
		
		setAttributes();
	}	
}
