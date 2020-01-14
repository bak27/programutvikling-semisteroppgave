package Model;

import java.io.Serializable;
import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
/**
 * the enemy class used to characterize how the enemies
 * should be look like including its image,movement,update and 
 * bounding.
 * @author 
 */
public class Enemy extends GameObject implements Serializable {
	
	//private Circle enemy;
	private transient ImageView enemy;
    private transient Image image;
    public double speed;				// Speed in x or y direction, enemy is only able to move in one direction

     
	private static final long serialversionU=1L;
	public Enemy() {
		 image = new Image("/Model/enemy.png", true);
		life = 1;
		enemy = new ImageView();
		enemy.setImage(image);
		enemy.setTranslateX(800);
		enemy.setTranslateY(500);
		speed = 0;
	}
	/**
         * this is a constructor which used to create 
         * an enemy object.
         * @param x
         * @param y 
         */
    public Enemy(double x, double y) {
		life = 1;
		image = new Image("/Model/enemy.png", true);
		enemy = new ImageView();
		enemy.setImage(image);
		enemy.setTranslateX(x);
		enemy.setTranslateY(y);
	}
        /**
         * this is a method used to set an image
         * for an enemy object.
         * @param x
         * @param y 
         */
        public void setImageView(double x, double y) {
            image = new Image("/Model/enemy.png", true);
            enemy = new ImageView(image);
            enemy.setTranslateX(x);
            enemy.setTranslateY(y);
        
        }
	
	/**
         * a method used to set speed for an enemy object
         * @param movingSpeed 
         */
	public void setSpeed(double movingSpeed) {
		speed = speed + movingSpeed;
	}
	/**
         * used to create an enemy node
         * @return 
         */
	public Node getEnemyNode() {
		return enemy;
	}
	
	public Node getEnemy() {
		return enemy;
	}
	
	
	/**
         *  a method which enables enemy to move
         * @param speed 
         */
	public void move( double speedConstant) {
		double position = enemy.getTranslateY();
		//double positionX = enemy.getTranslateX();
		speed = Math.random()*speedConstant;
		if(position > 600-70) {
			Random r = new Random();
			enemy.setTranslateX(r.nextInt(900));
			enemy.setTranslateY(-6);
		}else {
		enemy.setTranslateY(position + speed);
		}
	}
	/**
         * a method used to update enemies state
         */
	public void updateEnemy() {
		double position = enemy.getTranslateX();
		speed -= 1;
		enemy.setTranslateX(position + speed);
	}

	@Override
	public void setX(double speed) {
		enemy.setTranslateX(speed);
	}

	@Override
	public void setY(double speed) {
		enemy.setTranslateY(speed);
	}

	@Override
	public double getX() {
		return enemy.getTranslateX();
	}

	@Override
	public double getY() {
		return enemy.getTranslateY();
	}

	@Override
	/**
         * this is a method make bounding when 
         * collision existed.
         * @return 
         */
        
        public Bounds getBounds() {
		return enemy.getBoundsInParent();
	}

   
}
