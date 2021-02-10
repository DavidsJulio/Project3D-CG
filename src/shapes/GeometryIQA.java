package shapes;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

import appearence.MyMaterial;

public class GeometryIQA extends Shape3D{

	public GeometryIQA() {
//		super( 8, GeometryArray.COORDINATES, 28);
	    
		IndexedQuadArray iqa = new IndexedQuadArray(8, GeometryArray.COORDINATES, 28);
		iqa.setCoordinate(0, new Point3f(0.0f, 0.0f, 0.0f));
	    iqa.setCoordinate(1, new Point3f(0.8f, 0.0f, 0.0f));
	    iqa.setCoordinate(2, new Point3f(0.8f, 0.0f, 0.8f));
	    iqa.setCoordinate(3, new Point3f(0.0f, 0.0f, 0.8f));
	    
	    iqa.setCoordinate(4, new Point3f(0.0f, 2.0f, 0.0f));
	    iqa.setCoordinate(5, new Point3f(0.8f, 2.0f, 0.0f));
	    iqa.setCoordinate(6, new Point3f(0.8f, 2.0f, 0.8f));
	    iqa.setCoordinate(7, new Point3f(0.0f, 2.0f, 0.8f));
	    
	    int[] coords = { 0, 1, 2, 3,
	    				 0, 4, 5, 1,
	    				 1, 5, 6, 2,
	    				 2, 6, 7, 3,
	    				 3, 0, 4, 7,
	    				 6, 5, 4, 7,
	    				 4, 0, 3, 7 };
	    
	    
	    iqa.setCoordinateIndices(0, coords);	
	    
	    
	    GeometryInfo gi = new GeometryInfo(iqa);
		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(gi);

		this.setGeometry(gi.getGeometryArray());
		Appearance app = new Appearance();
		app.setMaterial(new MyMaterial(MyMaterial.BRASS));
		this.setAppearance(app);
	}

}


