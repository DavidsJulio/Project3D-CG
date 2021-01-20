package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowEvent;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class Java3D extends Frame{

	public static void main(String[] args) {
		
		Frame frame = new Java3D();
		frame.setPreferredSize(new Dimension(400, 400));
		frame.setTitle("Java3D - Project");
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}
	
	public Java3D() {
		
		
	}
	

}
