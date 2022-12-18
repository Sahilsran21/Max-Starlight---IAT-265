package decorator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import processing.core.PApplet;
import util.Util;

//Public decorator class for drawing the night sky details using perlin noise
public class StarrySky {

	
//Fields
	private float xStart; 
	private float xSeed; 
	private float ySeed; 
	private PApplet pa;
	

	public final static int PANEL_W = 1200;
	public final static int PANEL_H = 445;
	
	
	
//Methods	
	public void drawStarrySky(Graphics2D g2) { 
		float noiseFactor;
		for (int y = 0; y <= PANEL_H; y += 60) { 
			ySeed += 0.1;
			xSeed = xStart;
		
		
			for (int x = 0; x <= PANEL_W; x += 60) { 
				xSeed += 0.1;	
				noiseFactor = pa.noise(xSeed, xSeed);
		
				AffineTransform at = g2.getTransform(); 
				g2.translate(x, y);
		
				g2.rotate(noiseFactor);
				float diameter = noiseFactor * 55;
				
				int darkBlue1  = (int) (0 + (noiseFactor * 50));
				int darkBlue2 = (int) (57 + (noiseFactor * 50));
				int darkBlue3 = (int) (230);
				int darkBlue4 = (int) (150 + (noiseFactor * 105));
				
				g2.setColor(new Color(darkBlue1, darkBlue2, darkBlue3, darkBlue4));
				g2.fill(new Ellipse2D.Float(5, -30, (diameter + 35), (diameter/2 + 35)));
				
				
				int mediumBlue1  = (int) (77 + (noiseFactor * 50));
				int mediumBlue2 = (int) (184);
				int mediumBlue3 = (int) (255);
				int mediumBlue4 = (int) (150 + (noiseFactor * 105));
		
				g2.setColor(new Color(mediumBlue1, mediumBlue2, mediumBlue3, mediumBlue4));
				g2.fill(new Ellipse2D.Float(-diameter/2 - 30, -diameter/4 + 5, (diameter + 10), (diameter/2) + 10));
			
				
				int LightBlue1  = (int) (179 + (noiseFactor * 20));
				int LightBlue2 = (int) (255);
				int LightBlue3 = (int) (255);
				int LightBlue4 = (int) (150 + (noiseFactor * 105));
		
				g2.setColor(new Color(LightBlue1, LightBlue2 , LightBlue3, LightBlue4));
				g2.fill(new Ellipse2D.Float(-diameter/2 - 15, -diameter/4 + 10, (diameter/10 + 15),(diameter/10) + 15));
				
		
				g2.setTransform(at);
			}
		}
	}
	

	
//Constructor
	public StarrySky() {
		xStart = (float) Util.random(10,10); 
		xSeed = xStart; 
		ySeed = (float) Util.random(10,10); 
		pa = new PApplet();
	} 
	
	
}
