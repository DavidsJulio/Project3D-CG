package shapes;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.vecmath.Point3f;

public class GeometryIQA extends IndexedQuadArray{

	public GeometryIQA() {
		super( 8, GeometryArray.COORDINATES, 28);
	    
		setCoordinate(0, new Point3f(0.0f, 0.0f, 0.0f));
	    setCoordinate(1, new Point3f(1.0f, 0.0f, 0.0f));
	    setCoordinate(2, new Point3f(1.0f, 0.0f, 1.0f));
	    setCoordinate(3, new Point3f(0.0f, 0.0f, 1.0f));
	    
	    setCoordinate(4, new Point3f(0.0f, 2.0f, 0.0f));
	    setCoordinate(5, new Point3f(1.0f, 2.0f, 0.0f));
	    setCoordinate(6, new Point3f(1.0f, 2.0f, 1.0f));
	    setCoordinate(7, new Point3f(0.0f, 2.0f, 1.0f));
	    
	    int[] coords = { 0, 1, 2, 3,
	    				 0, 4, 5, 1,
	    				 1, 5, 6, 2,
	    				 2, 6, 7, 3,
	    				 3, 0, 4, 7,
	    				 6, 5, 4, 7,
	    				 4, 0, 3, 7 };
	    
	    setCoordinateIndices(0, coords);	
	}

}


