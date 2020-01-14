package Model;


import java.io.Serializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * the player class used to characterize how player
 * should be look like including its image,movement,update and 
 * its bounding method in relation to the different classes.
 */

public class Player extends GameObject implements Serializable {
	
	private static final long serialversionU=1L;
	private transient ImageView player;
       
	private  transient Image image;
	
        /**
         * this is a constructor which used to create 
         * a player object.
         
         */
        public Player() {
          
		image = new Image("/Model/player2.png", true);
		player = new ImageView();
		player.setImage(image);
		player.setTranslateX(450);
		player.setTranslateY(500);
		life = 3;
		point=0;
                
                isAlive = true;
	}
        
      public void setImageView(ImageView img) {
            player = img;
        }
        /**
         * this is a method used to set an image
         * for a player object.
         * @param x
         * @param y 
         */
        public void setImageView(double positionX, double positionY) {
            image = new Image("/Model/player2.png", true);
		player = new ImageView();
		player.setImage(image);
                player.setTranslateX(positionX);
		player.setTranslateY(positionY);
        
        
        }
	
	public Node getPlayer() {
		return player;
	}
	
	@Override
	public Bounds getBounds() {
		return player.getBoundsInParent();
	}
	
	@Override
	public void setX(double speed) {	
		double position = player.getTranslateX();
		
		// Make sure the player does not go out of bounds
		
		if(position <= 0) {					// Left side
			player.setTranslateX(1);	
		}else if(position >= 900-60) {		// Right side
			player.setTranslateX(900-61);
		}else {
			player.setTranslateX(position + speed);
		}
		
	}
	
	@Override
	public void setY(double speed) {
		double position = player.getTranslateY();
		
		// Make sure the player does not go out of bounds
		if(position <= 0) {
			player.setTranslateY(1);
		}else if(position > 600-60) {
			player.setTranslateY(600-61);
		}else {
			player.setTranslateY(position+speed);
		}
	}
	
	@Override
	public double getX() {
		return player.getTranslateX();
	}

	@Override
	public double getY() {
		return player.getTranslateY();
	}
	/**
         * this is a method that increase players life
         */
	public void increaseLife() {
		life++;
	}
	/**
         * this is a method that increase players point
         */
	public void increasePoint() {
		point++;
	}
        
	public void increasePoint(int numberOfPoints) {
		point = point + numberOfPoints;
	}
	
        /**
         * this is a method that decrease players life
         */
        public void decreaseLife() {
		if(life<=0) {
			life = 0;
			isAlive = false;
		}else if(life==1) {	 // If only one life left, then player will die
			life--;
			isAlive = false;
		}else {
			life--;
		}
	}
	/**
         * this is a method that decrease players life
         */
	public void decreaseLife(int number) {
		if(life<=0) {
			life = 0;
			isAlive = false;
		}else if(life==1) {				// If only one life left, then player will die
			life--;
			isAlive = false;
		}else {
			life = life - number;
		}
	}
	
	public int getLife() {
		return life;
	}
	public int getPoint() {
		return point;
	}
	
    public void playerDead() {
    	life = 0;
    	isAlive = false;
    }
        /**
         * a boolean method that return true if player is alive
         * or return false if player is not alive
         * @return 
         */
        public boolean isAlive() {
		return isAlive;
	}
	


	
}
