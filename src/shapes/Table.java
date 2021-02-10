package shapes;

import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Cylinder;

public class Table extends Group{
	
	public Table() {
		Transform3D tr = new Transform3D();
		TransformGroup tg = new TransformGroup();
		
		GeometryIQA leg = new GeometryIQA();
		
		tr = new Transform3D();
		tr.setScale(0.5);
		tr.setTranslation(new Vector3d(0.5, 0.0, 0.5));
		tg = new TransformGroup(tr);
		tg.addChild(leg);
		this.addChild(tg);
		
		leg = new GeometryIQA();
		tr.setTranslation(new Vector3d(-0.5, 0.0, 0.5));
		tg = new TransformGroup(tr);
		tg.addChild(leg);
		this.addChild(tg);
		
		leg = new GeometryIQA();
		tr.setTranslation(new Vector3d(0.5, 0.0, -0.5));
		tg = new TransformGroup(tr);
		tg.addChild(leg);
		this.addChild(tg);
		
		leg = new GeometryIQA();
		tr.setTranslation(new Vector3d(-0.5, 0.0, -0.5));
		tg = new TransformGroup(tr);
		tg.addChild(leg);
		this.addChild(tg);
		
		Cylinder c = new Cylinder(2.5f, 0.2f);
		tr.setTranslation(new Vector3d(0.15, 1, 0));
		tg = new TransformGroup(tr);
		tg.addChild(c);
		this.addChild(tg);
	}
}
