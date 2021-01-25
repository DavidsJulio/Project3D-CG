package util;



import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class Axes extends Shape3D{
	public Axes(Color3f color, int thickness, float length) {
		
		LineArray la = new LineArray(6, LineArray.COORDINATES);
		la.setCoordinate(0, new Point3f(0f, 0f, 0f));
		la.setCoordinate(1, new Point3f(length, 0f, 0f));
		la.setCoordinate(2, new Point3f(0f, 0f, 0f));
		la.setCoordinate(3, new Point3f(0f, length, 0f));
		la.setCoordinate(4, new Point3f(0f, 0f, 0f));
		la.setCoordinate(5, new Point3f(0f, 0f, length));
		
		Appearance ap = new Appearance();
		ap.setColoringAttributes(new ColoringAttributes(color, ColoringAttributes.SHADE_FLAT));
		
		LineAttributes lAp = new LineAttributes(thickness, LineAttributes.PATTERN_SOLID, true);
		ap.setLineAttributes(lAp);
		
		this.setGeometry(la);
		this.setAppearance(ap);
	}
}