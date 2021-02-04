package application;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Enumeration;

import javax.media.j3d.BackgroundSound;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.MediaContainer;
import javax.media.j3d.Node;
import javax.media.j3d.Sound;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3f;

import shapes.ToyTruck;


public class KeyControl extends Behavior {
	
	private TransformGroup moveTg = null;
	private Node node = null;
	
	//objeto para armazenar a wakeup Condition
	private WakeupCondition wakeupCondition = null;
	
	private boolean collision = false;
	private int lastKey;
	private boolean enable;
	private BackgroundSound sound;
	private boolean onOff = false;
	
	public KeyControl(TransformGroup moveTg, Node node, boolean enable, BackgroundSound sound) {
		this.moveTg = moveTg;
		this.node = node;
		this.enable = enable;
		this.sound = sound;
		sound.setCapability(Sound.ALLOW_ENABLE_WRITE);
		sound.setCapability(Sound.ALLOW_ENABLE_READ);
		
	}
	
	//metodo chamado inicialmente para chamar a condição despertar
	
	@Override
	public void initialize() {
		//funcao para configurar uma wakeup condition
		//wakeupOn(new WakeupOnElapsedTime(500)); //despertar a cada 500ms //condicion simples
		
		//criacao da lista de criterios
		WakeupCriterion[] events = new WakeupCriterion[4]; //2 dos criterios não vamos programar..
		events[0] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED); //primir a tecla do teclado 
		events[1] = new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED); //despertar caso a tecla seja libertada (não programamos)
		events[2] = new WakeupOnCollisionEntry(node, WakeupOnCollisionEntry.USE_GEOMETRY); //detetar colisão //colisao baseada na geometria, ou colisao baseada no bounds (aqui bounds não serve)
		events[3] = new WakeupOnCollisionExit(node, WakeupOnCollisionExit.USE_GEOMETRY); //quando a colisão deixa de ocorrer
		
		//se acontecer algum dos criterios despertar
		wakeupCondition = new WakeupOr(events);
		wakeupOn(wakeupCondition);
		
		
	}

	@Override
	public void processStimulus(Enumeration criteria) {
		//lista de criterios
		
		WakeupCriterion wakeupCriterion;
		AWTEvent[] awtEvents;
		
		while(criteria.hasMoreElements()) { //devolve um true or false - enquanto houver elementos vou processalos
			wakeupCriterion = (WakeupCriterion) criteria.nextElement(); //devolver o proximo elemento
			
			if(wakeupCriterion instanceof WakeupOnAWTEvent) {
				
				awtEvents = ((WakeupOnAWTEvent) wakeupCriterion).getAWTEvent();				
				
				for(int i = 0; i < awtEvents.length; i++) {
					int id = awtEvents[i].getID();
					
					if(id == KeyEvent.KEY_PRESSED) {
						
						if(enable) {
							keyPressed( (KeyEvent) awtEvents[i]);
						}
						
					}else if(id == KeyEvent.KEY_RELEASED) {
						
					}
				}
			
			}else if(wakeupCriterion instanceof WakeupOnCollisionEntry){
				collision = true;	
			}else if(wakeupCriterion instanceof WakeupOnCollisionExit) {
				collision = false;
			}
			
		}
		wakeupOn(wakeupCondition);	
		
	}
	
	private void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		
		//estamos interessados em 4 codes, teclas right up, down left
		switch (keyCode) {
		
		case KeyEvent.VK_LEFT:
			if(!collision || (collision && lastKey != KeyEvent.VK_LEFT) ) {
				doRotationY(Math.toRadians(1.0));		
			}
			break;
			
		case KeyEvent.VK_RIGHT:
			if(!collision || (collision && lastKey != KeyEvent.VK_RIGHT)) {
				doRotationY(Math.toRadians(-1.0));	
			}
			break;
					
		case KeyEvent.VK_UP:
			if(!collision || (collision && lastKey != KeyEvent.VK_UP)) {
				doTranslation(new Vector3f(0.01f, 0f, 0f));
			}
			break;
			
		case KeyEvent.VK_DOWN:
			if(!collision || (collision && lastKey != KeyEvent.VK_DOWN)) {
				doTranslation(new Vector3f(-0.01f, 0f, 0f));
			}
			break;	
			
		case KeyEvent.VK_P:
			onOff = !onOff;
			sound.setEnable(onOff);
			
			break;
			
		}
		
		//guardar a ultima tecla
		lastKey = keyCode;
	}
	
	public void doRotationY(double t) {
		
		//implementação da rotacao
		Transform3D newTr = new Transform3D();
		newTr.rotY(t);
		
		Transform3D oldTr = new Transform3D();
		moveTg.getTransform(oldTr);
		
		oldTr.mul(newTr); //se multiplicar ao contrário não dá a mesma coisa
		
		moveTg.setTransform(oldTr);
		

	}

	
	private void doTranslation(Vector3f v) {
		
		//implementação da rotacao
		Transform3D newTr = new Transform3D();
		newTr.setTranslation(v);
		
		Transform3D oldTr = new Transform3D();
		moveTg.getTransform(oldTr);
		
		oldTr.mul(newTr); //se multiplicar ao contrário não dá a mesma coisa
		
		moveTg.setTransform(oldTr);

	}
	
	public void changeEnable() {
		enable = true; 
	}
	
	
}
