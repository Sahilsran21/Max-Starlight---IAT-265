package main;
import javax.swing.JFrame;

public class Assignment4App extends JFrame {

	public Assignment4App(String title) {
		super(title);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 100);
		Panel p = new Panel();
		this.add(p);
		this.pack();
		this.setVisible(true);	
	}
	
	public static void main(String[] args) {
		new Assignment4App("Assignment 4: Max Starlight");
	}

}
