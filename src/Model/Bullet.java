package Model;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * the bullet class used to characterize how the bullets
 * should be look like including its image,movement,update and 
 * bounding.
 * @author 
 */
public class Bullet extends GameObject implements java.io.Serializable{
    private static final long serialversionU=1L;
	private transient ImageView bullet;
	private transient Image bulletImage;
	private double speed;
	
	
	public Bullet() {
		bulletImage = new Image("/Model/bullet.png", true);
		bullet = new ImageView();
		bullet.setImage(bulletImage);
		bullet.setTranslateX(450);
		bullet.setTranslateY(500);
		speed = 0;
	}
	/**
         * a constructor used to create bullet 
         * @param positionX
         * @param positionY 
         */
	public Bullet(double positionX, double positionY) {
		bulletImage = new Image("/Model/bullet.png", true);
		bullet = new ImageView();
		bullet.setImage(bulletImage);
		bullet.setTranslateX(positionX);
		bullet.setTranslateY(positionY);
		speed = 0;
	}
        
        /**
         * a method used to set an image in bullet
         */
        
        public void setImageView() {
            bulletImage = new Image("/Model/bullet.png", true);
		bullet = new ImageView();
		bullet.setImage(bulletImage);
        }
	
	/**
         * a method used to create bullet node
         * @return 
         */
	
	public Node getBullet() {
		return bullet;
	}
	
	public Node getBulletNode() {
		return bullet;
	}
	
	@Override
	public void setX(double speed) {
		bullet.setTranslateX(speed);
	}

	/**
         * a method used to update bullet state
         */
	public void updateBullet() {
		double position = bullet.getTranslateY();
		speed -= 5;
		bullet.setTranslateY(position + speed);	
	}
	
	@Override
	public void setY(double speed) {
		double position = bullet.getTranslateY();
		speed -= 1;
		bullet.setTranslateY(position + speed);	
	}
	
	
	@Override
	public double getX() {
		return bullet.getTranslateX();
	}

	@Override
	public double getY() {
		return bullet.getTranslateY();
	}
       /**
        * a method used to check bouncing when bullet collied
        * with enemy.
        * @return 
        */
	public Bounds getBounds() {
		return bullet.getBoundsInParent();
	}
	
	

}
