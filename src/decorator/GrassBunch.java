package decorator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GrassBunch {
	
	
//Fields
	private GrassBlade grassBunch;
	private int posX, posY;
	
	//Boolean to draw lighter color grass during daybreak
	public boolean dayGrass;

		
//Methods
	public void drawGrassBunch(Graphics2D g2) {
		AffineTransform tr = g2.getTransform();
		g2.translate(posX, posY);

		grassBunch.drawGrass(g2);
		
		g2.setTransform(tr);
	}
		
		
		
//Constructor
	public GrassBunch(int x, int y, double len, double wid, double ang, int depth, double scale) {
		posX = x;
		posY = y;
		grassBunch = new GrassBlade(len, wid, ang, depth, scale);
	}


}

