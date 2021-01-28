package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.PointLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import appearence.MyMaterial;
import appearence.TextureAppearence;
import shapes.ToyTruck;
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
		viewTr.lookAt(new Point3d(0.0,  1.5,  3.5), new Point3d(0, 0, 0), new Vector3d(0,2,0));
		viewTr.invert();
		su.getViewingPlatform().getViewPlatformTransform().setTransform(viewTr);;
		
		
		BranchGroup bg = createSceneGraph();
		bg.compile();
		su.addBranchGraph(bg); // Add the content branch to the simple universe
	

		//Camara
		// Add a OrbitBehavior to control the first view with the mouse
		OrbitBehavior orbit = new OrbitBehavior(cv);
		orbit.setSchedulingBounds(bounds);
		su.getViewingPlatform().setViewPlatformBehavior(orbit);
	}
	
	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();
		
		// Axes
		root.addChild(new Axes(new Color3f(Color.RED), 3, 14f));
		
		//Carpet
		TextureAppearence carpet = new TextureAppearence("images/carpet.jpg", false, this);
		Box floor = new Box(3.5f, 0.01f, 3.5f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, carpet);
		floor.setCollidable(false);
		root.addChild(floor);
		
		
		//Appearence ToyTruck
		Appearance frontTruckApp = new Appearance();
		frontTruckApp.setMaterial(new MyMaterial(MyMaterial.CHROME));

		Appearance wheels = new Appearance();
		wheels.setMaterial(new MyMaterial(MyMaterial.BRASS));

		TextureAppearence backTruckApp = new TextureAppearence("images/woodType1.jpg", false, this);
		
		//Truck
		ToyTruck truck = new ToyTruck(backTruckApp, frontTruckApp, wheels);
		Transform3D tr = new Transform3D();
		tr.setTranslation(new Vector3f(0f, 0.31f, 0.7f));
		TransformGroup tg = new TransformGroup(tr);
		
		TransformGroup moveTg = new TransformGroup();
		moveTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 
		moveTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);		
		moveTg.addChild(truck);
		tg.addChild(moveTg);
		root.addChild(tg);

		
		
		
		// Lights
		AmbientLight ablight = new AmbientLight(true, new Color3f(Color.WHITE));
		ablight.setInfluencingBounds(bounds);
		root.addChild(ablight);

		PointLight ptlight = new PointLight(new Color3f(Color.WHITE), new Point3f(0f, 3f, 3f), new Point3f(1f, 0f, 0f));
		ptlight.setInfluencingBounds(bounds);
		root.addChild(ptlight);

		//Background
		setBackground(root);

		return root;
	}
	
	
	private void setBackground(BranchGroup root) {
		//background
	    background = new Background(1.0f, 1.0f, 1.0f);
	    background.setApplicationBounds(bounds);
	    //load image
	    URL url = getClass().getClassLoader().getResource("images/sky.jpg");
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
