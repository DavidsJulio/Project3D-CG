package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import util.Axes;

public class Java3D extends Frame{
	
	BoundingSphere bounds = new BoundingSphere(); // Bounds of the scene
	Background background = null;
	ImageComponent2D image = null;
	
	public static void main(String[] args) {
		
		Frame frame = new Java3D();
		frame.setPreferredSize(new Dimension(700, 700));
		frame.setTitle("Java3D - Project");
		frame.pack();
		frame.setResizable(false);
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
		
		// Create first canvas for the first view
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);

		// Add canvas to the frame
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		
		      

		// Create the a simple universe with a standard nominal view
		SimpleUniverse su = new SimpleUniverse(cv);
		//su.getViewingPlatform().setNominalViewingTransform();
		
		
		//Vista
		Transform3D viewTr = new Transform3D();
		viewTr.lookAt(new Point3d(-4.0,  1.5,  0.5), new Point3d(0, 0, 0), new Vector3d(0,1,0));
		viewTr.invert();
		su.getViewingPlatform().getViewPlatformTransform().setTransform(viewTr);;
		
		
		BranchGroup bg = createSceneGraph();
		bg.compile();
		su.addBranchGraph(bg); // Add the content branch to the simple universe
		
//		BranchGroup bg = createSceneGraph();
//		bg.compile();
//		su.addBranchGraph(bg); // Add the content branch to the simple universe


		
//		// Add a OrbitBehavior to control the first view with the mouse
//		OrbitBehavior orbit = new OrbitBehavior(cv);
//		orbit.setSchedulingBounds(bounds);
//		su.getViewingPlatform().setViewPlatformBehavior(orbit);
	}
	
	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();
		
		// Axes
		Shape3D axes = new Axes(new Color3f(Color.WHITE), 5, 0.6f);
		root.addChild(axes);
		
//		PointLight light = new PointLight(new Color3f(Color.WHITE) , new Point3f(1f, 1f, 1f), new Point3f(1f, 0.1f, 0f)); 
//		BoundingSphere bounds = new BoundingSphere();
//		light.setInfluencingBounds(bounds);
//		
//		root.addChild(light); //passar a luz
		
//		//Background
//		Background background = new Background(new Color3f(0.2f, 0.4f, 0.7f));
//		background.setApplicationBounds(bounds);
//		root.addChild(background);
		

		setBackground(root);

		return root;
	}
	
	private void setBackground(BranchGroup root) {
		//background
	    background = new Background(1.0f, 1.0f, 1.0f);
	    background.setApplicationBounds(bounds);
	    //load image
	    URL url = getClass().getClassLoader().getResource("images/stars.png");
	    BufferedImage bi = null;
	    try {
	      bi = ImageIO.read(url);
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	    image = new ImageComponent2D(ImageComponent2D.FORMAT_RGB, bi);    

	    background.setImage(image);
	    root.addChild(background);	
	}

}
