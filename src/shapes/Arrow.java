package shapes;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.vecmath.Point3f;

public class Arrow extends IndexedQuadArray{

	public Arrow() {
		super(32, GeometryArray.COORDINATES, 64);
	    	    
		setCoordinate(0, new Point3f(0.5f, 0.0f, 0.0f));
	    setCoordinate(1, new Point3f(-0.5f, 1.0f, 0.0f));
	    setCoordinate(2, new Point3f(0.0f, 1.0f, 0.0f));
	    setCoordinate(3, new Point3f(0.0f, 2.0f, 0.0f));
	    setCoordinate(4, new Point3f(1.0f, 2.0f, 0.0f));
	    setCoordinate(5, new Point3f(1.0f, 1.0f, 0.0f));
	    setCoordinate(6, new Point3f(1.5f, 1.0f, 0.0f));
	    
	    setCoordinate(7, new Point3f(0.0f, 1.0f, 0.5f));
	    setCoordinate(8, new Point3f(0.0f, 2.0f, 0.5f));
	    setCoordinate(9, new Point3f(1.0f, 2.0f, 0.5f));
	    setCoordinate(10, new Point3f(1.0f, 1.0f, 0.5f));
	    setCoordinate(11, new Point3f(1.5f, 1.0f, 0.5f));
	    setCoordinate(12, new Point3f(0.5f, 0.0f, 0.5f));
	    setCoordinate(13, new Point3f(-0.5f, 1.0f, 0.5f));
	    
	    int[] coords = { 2, 3, 4, 5, 6, 0 ,1, 2, 7, 13, 12, 11, 10, 9, 8, 7,
	    				7, 2, 1, 13, 13, 1, 0, 12,
	    				12, 0, 6,11,
	    				11, 6, 5, 10,
	    				10, 5, 4, 9,
	    				9, 4, 3, 8,
	    				8, 3, 2, 7};
	    setCoordinateIndices(0, coords);	
	    
	  
	}

}


