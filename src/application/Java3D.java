package application;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.AudioDevice;
import javax.media.j3d.Background;
import javax.media.j3d.BackgroundSound;
import javax.media.j3d.Billboard;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.MediaContainer;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Screen3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Sound;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;

import appearence.MyMaterial;
import appearence.TextureAppearence;
import shapes.Arrow;
import shapes.Table;
import shapes.ToyTruck;

public class Java3D extends JPanel implements MouseListener{
	
	BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 5f); // Bounds of the scene
	Background background = null;
	ImageComponent2D image = null;
	PickCanvas pickCanvas;
	boolean enable = false;

	ToyTruck truck;
	KeyControl control;
	BranchGroup root = new BranchGroup();
	String info = "Clique na Carrrinha!";
	BackgroundSound bSound;
	BackgroundSound bColision;
	
	Point3d eye = new Point3d(0.0, 1.0,  6);
	Point3d center = new Point3d(0, 0, 0);
	Vector3d up =  new Vector3d(0,1,0);
	
	URL url;

	boolean light = true;
	PointLight ptlight;
	
	View view;
	Canvas3D cv;
	Canvas3D offScreenCanvas;

	public Java3D() {
		
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		cv = new Canvas3D(gc);

		// Add canvas to the frame
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		cv.addMouseListener(this);
		
		SimpleUniverse su = new SimpleUniverse(cv);
		
		//Vista
		Transform3D viewTr = new Transform3D();

		viewTr.lookAt(eye, center, up);

		viewTr.invert();
		su.getViewingPlatform().getViewPlatformTransform().setTransform(viewTr);
	
	
		AudioDevice audioDev = new JavaSoundMixer(su.getViewer().getPhysicalEnvironment());
		audioDev.initialize();
		
		BranchGroup bg = createSceneGraph();
		bg.compile();
		su.addBranchGraph(bg);

		//PICKING
		pickCanvas = new PickCanvas(cv, bg);
		pickCanvas.setMode(PickTool.GEOMETRY);
		
		//Camara
		OrbitBehavior orbit = new OrbitBehavior(cv, OrbitBehavior.REVERSE_ROTATE);
		orbit.setSchedulingBounds(bounds);
		su.getViewingPlatform().setViewPlatformBehavior(orbit);
		
		
		
		// create off screen canvas
	    view = su.getViewer().getView();
	    offScreenCanvas = new Canvas3D(gc, true);
	    Screen3D sOn = cv.getScreen3D();
	    Screen3D sOff = offScreenCanvas.getScreen3D();
	    Dimension dim = sOn.getSize();
	    sOff.setSize(dim);
	    sOff.setPhysicalScreenWidth(sOn.getPhysicalScreenWidth());
	    sOff.setPhysicalScreenHeight(sOn.getPhysicalScreenHeight());
	    Point loc = cv.getLocationOnScreen();
	    offScreenCanvas.setOffScreenLocation(loc);
	    // button
	    
	    Button button = new Button("Save image");
	    add(button, BorderLayout.SOUTH);
	    button.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
			        BufferedImage bi = capture();
			        save(bi);
		      }
	    });
	}
	
	//OFFSCREEN
	public BufferedImage capture() {
	    // render off screen image
		Dimension dim = cv.getSize();
	    view.stopView();
	    view.addCanvas3D(offScreenCanvas);
	    BufferedImage bImage =
	    new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
	    ImageComponent2D buffer =
	    new ImageComponent2D(ImageComponent.FORMAT_RGB, bImage);
	    offScreenCanvas.setOffScreenBuffer(buffer);
	    view.startView();
	    offScreenCanvas.renderOffScreenBuffer();
	    offScreenCanvas.waitForOffScreenRendering();
	    bImage = offScreenCanvas.getOffScreenBuffer().getImage();
	    view.removeCanvas3D(offScreenCanvas);
	    return bImage;
	  }
  
  public void save(BufferedImage bImage) {
	// save image to file
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
      File oFile = chooser.getSelectedFile();
      try {
        ImageIO.write(bImage, "jpeg", oFile);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

	private BranchGroup createSceneGraph() {

		root.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		root.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		//AXES
//		root.addChild(new Axes(new Color3f(Color.RED), 3, 14f));
		
		//FLOOR
		TextureAppearence carpet = new TextureAppearence("images/wood.jpg", false, this);
		Box floor = new Box(3.5f, 0.01f, 3.5f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, carpet);
		TransformGroup tgFloor = new TransformGroup();
		tgFloor.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgFloor.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgFloor.setCollidable(false);
		tgFloor.addChild(floor);
		root.addChild(tgFloor);
		
		//lightSwitch
		TextureAppearence lightSwitch = new TextureAppearence("images/wood.jpg", false, this);
		Box lights = new Box(0.1f, 0.1f, 0.05f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, lightSwitch);
		lights.setName("lights");
		Transform3D trLight = new Transform3D();
		trLight.setRotation(new AxisAngle4d(0, -1, 0, Math.toRadians(90)));
		trLight.setTranslation(new Vector3f(-3.4f, 0.5f, 2.5f));
		TransformGroup tgLights = new TransformGroup(trLight);
		tgLights.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tgLights.addChild(lights);
		root.addChild(tgLights);
		

		//WALLS
		walls();
		
		//TRUCK
		//Appearence ToyTruck
		Appearance frontTruckApp = new Appearance();
		frontTruckApp.setMaterial(new MyMaterial(MyMaterial.CHROME));
		TextureAppearence backTruckApp = new TextureAppearence("images/lego7.jpg", false, this);
		Appearance wheels = new Appearance();
		Color3f col1 = new Color3f(0.128f, 0.128f, 0.128f); 
		ColoringAttributes ca1 = new ColoringAttributes(col1, ColoringAttributes.NICEST);
		wheels.setColoringAttributes(ca1);

		truck = new ToyTruck(backTruckApp, frontTruckApp, wheels);
		
		Transform3D tr = new Transform3D();
		tr.setTranslation(new Vector3f(0f, 0.36f, 0.0f));
		
		TransformGroup tg = new TransformGroup(tr);
		tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		TransformGroup moveTG = new TransformGroup();
		moveTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		moveTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		moveTG.addChild(truck);
		tg.addChild(moveTG);
		root.addChild(tg);
	
		
		//SOUND
		bSound = new BackgroundSound();
		url = this.getClass().getClassLoader().getResource("images/CarHorn.wav");

		MediaContainer mc = new MediaContainer(url);
		bSound.setSoundData(mc);
		bSound.setLoop(Sound.INFINITE_LOOPS);
		bSound.setSchedulingBounds(bounds);
		bSound.setInitialGain(0.01f);
		bSound.setEnable(enable);
		root.addChild(bSound);
		
		//Colision
		bColision = new BackgroundSound();
		url = this.getClass().getClassLoader().getResource("images/crashSound.wav");

		MediaContainer mc1 = new MediaContainer(url);
		bColision.setSoundData(mc1);
		bColision.setLoop(Sound.INFINITE_LOOPS);
		bColision.setSchedulingBounds(bounds);
		bColision.setInitialGain(0.01f);
		bColision.setEnable(enable);
		root.addChild(bColision);

		
		//KeyControl
		control = new KeyControl(moveTG, truck, enable, bSound, bColision);
		control.setSchedulingBounds(bounds);	
		root.addChild(control);
		
		//Geometry - Arrow
		Appearance apShape = new Appearance();
		Color3f col = new Color3f(2.55f, 0.0f, 0.0f); 
		ColoringAttributes ca = new ColoringAttributes(col, ColoringAttributes.NICEST);
		apShape.setColoringAttributes(ca);
		
		TransformGroup tgSpin = new TransformGroup();
		tgSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(tgSpin);
		
		Shape3D shape = new Shape3D(new Arrow(), apShape);
		tr = new Transform3D();
		tr.setTranslation(new Vector3d(0.0, 1.0, 0.0));
		tr.setScale(0.25);
		TransformGroup tgShape = new TransformGroup(tr);
		
		tgSpin.addChild(tgShape);
		tgShape.addChild(shape);
		
		Alpha alpha = new Alpha(-1, 2000);
		RotationInterpolator rotator = new RotationInterpolator(alpha, tgSpin);
		rotator.setSchedulingBounds(bounds);
		tgSpin.addChild(rotator);

		//FONT
		Appearance textApp = new Appearance();
		textApp.setMaterial(new MyMaterial(MyMaterial.GOLD));
		createFont(root, info, textApp, tgSpin, tr);
		
		Transform3D trONOFF = new Transform3D();
		tr.setTranslation(new Vector3d(-3.4f, 1f, 2.5f));
		TransformGroup tgONOFF = new TransformGroup(trONOFF);
		Appearance textLight = new Appearance();
		textLight.setMaterial(new MyMaterial(MyMaterial.PEWTER));
		createFont2(root, "On/OFF", textLight, tgONOFF, tr);
		
		
		// LIGHT
		AmbientLight ablight = new AmbientLight(true, new Color3f(Color.WHITE));
		ablight.setInfluencingBounds(bounds);
		root.addChild(ablight);

		ptlight = new PointLight(new Color3f(Color.WHITE), new Point3f(0f, 3f, 3f), new Point3f(1f, 0f, 0f));
		ptlight.setCapability(PointLight.ALLOW_STATE_READ);
		ptlight.setCapability(PointLight.ALLOW_STATE_WRITE);
		ptlight.setEnable(light);
		ptlight.setInfluencingBounds(bounds);
		root.addChild(ptlight);

		//BACKGROUND
		setBackground(root);
		
		//TABLE
		Table table = new Table();
		tr = new Transform3D();
		tr.setScale(0.5f);
		tr.setTranslation(new Vector3f(-2f, 0f, -2f));
		tg = new TransformGroup(tr);
		tg.setCollidable(true);
		tg.addChild(table);
		// root.addChild(tg);
		root.addChild(tg);
		
		
		return root;
	}
	
						//----------------------------------------------------------AUXILIAR--------------------------------------------//
	
	
	
	//FONT
	private void createFont(BranchGroup root, String msg, Appearance app, TransformGroup tgFont, Transform3D trFont) {
			
		Font font = new Font("SansSerif", Font.BOLD, 1);
		FontExtrusion extrusion = new FontExtrusion();
		Font3D font3d = new Font3D(font, extrusion);
		
		Text3D text = new Text3D(font3d, msg);	
		Shape3D shapeText = new Shape3D(text, app);
			
		TransformGroup bbTg = new TransformGroup();
		bbTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(bbTg);

		Billboard bb = new Billboard(bbTg, Billboard.ROTATE_ABOUT_POINT, new Point3f(0f, 0f, 0f));
		bb.setSchedulingBounds(bounds);
		bbTg.addChild(bb);

		trFont = new Transform3D();
		trFont.setScale(0.3f);
		trFont.setTranslation(new Vector3d(-1.3f, 1.7f, 0f));
		
		tgFont = new TransformGroup(trFont);
		

		tgFont.addChild(shapeText);
		tgFont.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgFont.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		bbTg.addChild(tgFont);
	}
	
	private void createFont2(BranchGroup root, String msg, Appearance app, TransformGroup tgFont, Transform3D trFont) {
		
		Font font = new Font("SansSerif", Font.PLAIN, 1);
		FontExtrusion extrusion = new FontExtrusion();
		Font3D font3d = new Font3D(font, extrusion);
		
		Text3D text = new Text3D(font3d, msg);	
		Shape3D shapeText = new Shape3D(text, app);
			
		TransformGroup bbTg = new TransformGroup();
		bbTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(bbTg);

		Billboard bb = new Billboard(bbTg, Billboard.ROTATE_ABOUT_POINT, new Point3f(-3.4f, 1f, 2.5f));
		bb.setSchedulingBounds(bounds);
		bbTg.addChild(bb);

		trFont = new Transform3D();
		trFont.setScale(0.3f);
		trFont.setTranslation(new Vector3d(-3.4f, 1f, 2.5f));
		
		tgFont = new TransformGroup(trFont);
		

		tgFont.addChild(shapeText);
		tgFont.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgFont.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		bbTg.addChild(tgFont);
	}
	//Create walls
	public void walls() {
		
		TextureAppearence wallApp = new TextureAppearence("images/woodType1.jpg", true, this);
		Box wall = new Box(3.5f, 0.5f, 0.1f, Box.GENERATE_TEXTURE_COORDS | Box.GENERATE_NORMALS, wallApp);
		Transform3D trWall = new Transform3D();
		trWall.setTranslation(new Vector3f(0f, 0.5f + 0.001f, -3.5f));
		TransformGroup tgWall = new TransformGroup(trWall);
		tgWall.addChild(wall);
		root.addChild(tgWall);
		
		Box wall2 = new Box(3.5f, 0.5f, 0.1f, Box.GENERATE_TEXTURE_COORDS | Box.GENERATE_NORMALS, wallApp);
		trWall.setRotation(new AxisAngle4d(0, -1, 0, Math.toRadians(90)));
		trWall.setTranslation(new Vector3f(3.5f, 0.5f + 0.001f, 0f));
		TransformGroup tgWall2 = new TransformGroup(trWall);
		tgWall2 = new TransformGroup(trWall);
		tgWall2.addChild(wall2);
		root.addChild(tgWall2);
		
		Box wall3 = new Box(3.5f, 0.5f, 0.1f, Box.GENERATE_TEXTURE_COORDS | Box.GENERATE_NORMALS, wallApp);
		trWall.setRotation(new AxisAngle4d(0, -1, 0, Math.toRadians(90)));
		trWall.setTranslation(new Vector3f(-3.5f, 0.5f + 0.001f, 0f));
		TransformGroup tgWall3 = new TransformGroup(trWall);
		tgWall3 = new TransformGroup(trWall);
		tgWall3.addChild(wall3);
		root.addChild(tgWall3);
	
	}

	
	//BACKGROUND
	private void setBackground(BranchGroup root) {
	    background = new Background();
	    background.setApplicationBounds(bounds);
	    
	    //load image
	    URL url = getClass().getClassLoader().getResource("images/sky1.jpg");
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

	
							//----------------------------------------------------------RATO--------------------------------------------//

	@Override
	public void mouseClicked(MouseEvent e) {
		pickCanvas.setShapeLocation(e);
		PickResult result = pickCanvas.pickClosest(); 

		Box nodeB = (Box) result.getNode(PickResult.GROUP);
		
		Shape3D nodeS = (Shape3D) result.getNode(PickResult.SHAPE3D);
		
		if(nodeS != null) {
			if(nodeB.toString().contains("ToyTruck")) {
				control.changeEnable();
				
			}
		}
		
		if(nodeS != null) {
			if(nodeB.toString().contains("lights")) {
				light = !light;
				ptlight.setEnable(light);
			}
		}
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
