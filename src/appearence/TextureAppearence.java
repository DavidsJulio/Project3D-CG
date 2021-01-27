package appearence;

import java.awt.Component;
import java.net.URL;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;

import com.sun.j3d.utils.image.TextureLoader;

public class TextureAppearence extends Appearance{
	
	public TextureAppearence(String file, boolean textCore, Component observer) {
		
		//ler o ficheiro
		URL filename = getClass().getClassLoader().getResource(file);
		TextureLoader loader = new TextureLoader(filename, observer);
		ImageComponent2D image = loader.getImage();
		if (image == null) {
			System.out.println("can't find texture file.");
		}
		
		//objet textura
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
		this.setTexture(texture);
		
//		// combine texture and lighting
		TextureAttributes texatt = new TextureAttributes();
		texatt.setTextureMode(TextureAttributes.COMBINE);
		this.setTextureAttributes(texatt);
		this.setMaterial(new Material());
		
		if(textCore) {
			TexCoordGeneration tcg = new TexCoordGeneration();
			tcg.setGenMode(TexCoordGeneration.OBJECT_LINEAR);
			this.setTexCoordGeneration(tcg);
		}
		
	}
	

}
