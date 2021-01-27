package appearence;

import javax.media.j3d.Material;

public class MyMaterial extends Material{
	public final static int BRASS = 0;
	public final static int GOLD = 1;
	public final static int CHROME = 2;
	
	
	public MyMaterial(int type) {
		switch (type) {
		case BRASS:
			this.setAmbientColor(0.329412f, 0.223529f, 0.027451f);
			this.setDiffuseColor(0.790392f, 0.568627f, 0.113725f);
			this.setSpecularColor(0.992157f, 0.941176f, 0.807843f);
			this.setShininess(27.8974f);
		
			break;

		case GOLD:
			
			this.setAmbientColor(0.24725f, 0.1995f, 0.0275f);
			this.setDiffuseColor(0.751264f, 0.60648f, 0.22648f);
			this.setSpecularColor(0.628281f, 0.55502f, 0.36605f);
			this.setShininess(51.2f);
			break;
			
		case CHROME:
			this.setAmbientColor(0.25f, 0.25f, 0.25f);
			this.setDiffuseColor(0.4f, 0.4f, 0.4f);
			this.setSpecularColor(0.774597f, 0.774597f, 0.774597f);
			this.setShininess(76.8f);
			break;
		}
	}

}
