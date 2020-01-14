package Model;

import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *the boss class used to characterize how the boss
 * should be look like including its image,movement,update and 
 * bounding.
 * @author 
 */
public class Boss extends Enemy implements java.io.Serializable{
	
	private static final long serialversionU=1L;
	private transient ImageView boss;
	private transient Image image;
        private double speed;
	/**
         * this is a constructor which used to create 
         * a boss object.
         * 
         */
	public Boss() {
		life = 3;
		image = new Image("/Model/boss3.png", true);
		boss = new ImageView(image);
		Random r = new Random();
		boss.setTranslateX(r.nextInt(900));
		boss.setTranslateY(0);
		isAlive = true;
	}
        
      /**
       * this is a method used to implement an image
       * for boss object.
       */
    public void setImageView() {
    	image = new Image("/Model/boss3.png", true);
    	boss = new ImageView(image);   
    }
	/**
         * this is a method used to set an image
         * for a boss object.
         * @param x
         * @param y 
         */
    public void setImageView(double x, double y) {
    	image = new Image("/Model/boss3.png", true);
    	boss = new ImageView(image);
    	boss.setTranslateX(x);
    	boss.setTranslateY(y);
    }
    
	/**
         * used to create a boss node
         * @return 
         */
    public Node getBossNode() {
		return boss;
	}
	
         /**
         *  a method which enables boss to move
         * @param speed 
         */
    public void moveBoss() {
		double position = boss.getTranslateY();
		speed = Math.random()*30;
		if(position > 600-70) {
			Random r = new Random();
			boss.setTranslateY(-6);
			boss.setTranslateX(r.nextInt(900));
		}else {
		boss.setTranslateY(position + speed);
		}
	}
	
       /**
         * this is a method that decrease boss life
         */
	public void decreaseLife() {
		if(life<=0) {
			life = 0;
			isAlive = false;
		}else if(life==1) {				// If only one life left, then player will die
			life--;
			isAlive = false;
		}else {
			life--;
		}
	}
	/**
         * a boolean method that return true if boss is alive
         * or return false if boss is not alive
         * @return 
         */
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setX(double speed) {
		boss.setTranslateX(speed);
	}
	
	public void setY(double speed) {
		boss.setTranslateY(speed);
	}
	
	public double getX() {
		return boss.getTranslateX();
	}
	
	public double getY() {
		return boss.getTranslateY();
	}
	/**
         * this is a method make bounding when 
         * collision existed.
         * @return 
         */
	public Bounds getBounds() {
		return boss.getBoundsInParent();
	}
	
}
