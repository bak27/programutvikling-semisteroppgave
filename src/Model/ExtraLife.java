package Model;

import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * the ExtraLife class used to characterize how the heart objects
 * should be look like including its image,movement,update and 
 * bounding and its relations with the player class.
 * @author 
 */
public class ExtraLife extends PowerUps implements java.io.Serializable{
	private static final long serialversionU=1L;
	private transient ImageView lifeImage;
	private transient Image image;
        private double speed;
	private Random r = new Random();
	/**
         * this is a constructor which used to create 
         * an ExtraLife object.
         * 
         */
	public ExtraLife() {
		image = new Image("/Model/heart_small.png", true);
		lifeImage = new ImageView();
		lifeImage.setImage(image);
		lifeImage.setTranslateX(900+r.nextInt(10));
		lifeImage.setTranslateY(r.nextInt(600));
		speed = 0;
	}
	
	/**
         * this is a method used to set an image
         * for a ExtraLife object.
         * @param x
         * @param y 
         */
       public void setImageView(double x,double y) {
            image = new Image("/Model/heart_small.png", true);
            lifeImage = new ImageView(image);
            lifeImage.setTranslateX(x);
            lifeImage.setTranslateY(y);
       
       }
        
        
        public Node getExtraLifeNode() {
		return lifeImage;
	}
	
	public void moveExtraLife() {
		double position = lifeImage.getTranslateX();
		speed = -10;
		lifeImage.setTranslateX(position + speed);
	}
	
	@Override
	public void setX(double speed) {
		lifeImage.setTranslateX(speed);	
	}

	@Override
	public void setY(double speed) {
		lifeImage.setTranslateY(speed);
	}

	@Override
	public double getX() {
		return lifeImage.getTranslateX();
	}

	@Override
	public double getY() {
		return lifeImage.getTranslateY();
	}
	
	@Override
	public Bounds getBounds() {
		return lifeImage.getBoundsInParent();
	}

}
