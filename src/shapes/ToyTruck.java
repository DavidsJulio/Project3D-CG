package shapes;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;

public class ToyTruck extends Group{
	
	//Size
	private float backTruckX= 0.35f;
	private float backTruckY = 0.2f;
	private float backTruckZ = 0.2f;
	
	private float frontTruckX= 0.1f;
	private float frontTruckY = 0.12f;
	private float frontTruckZ = 0.1f;
	
	private float wheelRadius = 0.1f;
	private float wheelHeight = 0.1f;
	

	public ToyTruck(Appearance appTruck, Appearance frontApp, Appearance wheels) {

		
		Box backTruck = new Box(backTruckX, backTruckY, backTruckZ, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, appTruck); //eixoX, eixoY, exioZ
		backTruck.setName("ToyTruck");
		Transform3D trBackTruck = new Transform3D();
		trBackTruck.set(new Vector3f(0f, 0f, 0f));
		TransformGroup tgBackTruck = new TransformGroup(trBackTruck);
		tgBackTruck.addChild(backTruck);
		this.addChild(tgBackTruck);
		
		Box frontTruck = new Box(frontTruckX, frontTruckY, frontTruckZ, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS , frontApp);
		Transform3D trFrontTruck = new Transform3D();
		trFrontTruck.set(new Vector3f(0.45f, -0.08f, 0f));
		TransformGroup tgFrontTruck = new TransformGroup(trFrontTruck);
		tgFrontTruck.addChild(frontTruck);
		this.addChild(tgFrontTruck);
		
		
		//Wheels
		
		//RightFront
		Cylinder wheelRF = new Cylinder(wheelRadius, wheelHeight, wheels);
		Transform3D trWheelRF = new Transform3D();
		trWheelRF.set(new Vector3f(0.25f, -0.2f, 0.175f));

		//ROTAÇÃO
		Transform3D rot = new Transform3D();
		rot.rotX(Math.PI/2);
		trWheelRF.mul(rot);	

		
		TransformGroup tgWheelRF = new TransformGroup(trWheelRF);
		tgWheelRF = new TransformGroup(trWheelRF);
		tgWheelRF.addChild(wheelRF);
		this.addChild(tgWheelRF);
		
		//RightBack
		Cylinder wheelRB = new Cylinder(wheelRadius, wheelHeight, wheels);
		Transform3D trWheelRB = new Transform3D();
		trWheelRB.set(new Vector3f(-0.25f, -0.2f, 0.175f));
		
		trWheelRB.mul(rot);	
		
		TransformGroup tgWheelRB = new TransformGroup(trWheelRB);
		tgWheelRB.addChild(wheelRB);
		this.addChild(tgWheelRB);
		
		//LeftBack
		Cylinder wheelLB = new Cylinder(wheelRadius, wheelHeight, wheels);
		Transform3D trWheelLB = new Transform3D();
		trWheelLB.set(new Vector3f(-0.25f, -0.2f, -0.175f));
		
		trWheelLB.mul(rot);	
		
		TransformGroup tgWheelLB = new TransformGroup(trWheelLB);
		tgWheelLB.addChild(wheelLB);
		this.addChild(tgWheelLB);
		
		//LeftFront
		Cylinder wheelLF = new Cylinder(wheelRadius, wheelHeight, wheels);
		Transform3D trWheelLF = new Transform3D();
		trWheelLF.set(new Vector3f(0.25f, -0.2f, -0.175f));

		trWheelLF.mul(rot);	
		
		TransformGroup tgWheelLF = new TransformGroup(trWheelLF);
		tgWheelLF.addChild(wheelLF);
		this.addChild(tgWheelLF);
	
	}

}
