package Model;

import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * the score class used to characterize how the star objects
 * should be look like including its image,movement,update and 
 * bounding and its relations with the player class.
 * @author 
 */
public class Score extends PowerUps implements java.io.Serializable{
        private static final long serialversionU=1L;
	private transient ImageView starImage;
	private transient Image image;
        double speed;
	Random r = new Random();
	/**
         * this is a constructor which used to create 
         * a score object.
         * 
         */
	public Score () {
		image = new Image("/Model/star2.png", true);
		starImage = new ImageView();
		starImage.setImage(image);
		starImage.setTranslateX(900+r.nextInt(10));
		starImage.setTranslateY(r.nextInt(600));
		speed = 0;
	}
	/**
         * this is a method used to set an image
         * for a score object.
         * @param x
         * @param y 
         */
	public void setImageView(double x,double y) {
            image = new Image("/Model/star2.png", true);
            starImage = new ImageView(image);
            starImage.setTranslateX(x);
            starImage.setTranslateY(y);
        
        
        }
	
        
        /**
         * used to create a score node
         * @return 
         */
        
        public Node getExtraPoint() {
		return starImage;
	}
	/**
         *  a method which enables score to move
         * @param speed 
         */
	public void moveExtraPoint() {
		double position = starImage.getTranslateX();
		speed = -10;
		
		if(position<0) {
			starImage.setTranslateX(900+r.nextInt(10));
                                starImage.setTranslateY(r.nextInt(600));
		}else {
		starImage.setTranslateX(position + speed);
		}
	}
	
	@Override
	public void setX(double speed) {
		starImage.setTranslateX(speed);	
	}

	@Override
	public void setY(double speed) {
		starImage.setTranslateY(speed);
	}

	@Override
	public double getX() {
		return starImage.getTranslateX();
	}

	@Override
	public double getY() {
		return starImage.getTranslateY();
	}
	

	@Override
	
        /**
         * this is a method make bounding when 
         * collision existed.
         * @return 
         */
        public Bounds getBounds() {
		return starImage.getBoundsInParent();
	}

    

}

