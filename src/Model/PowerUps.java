package Model;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;

public abstract class PowerUps  implements java.io.Serializable {
	
	protected boolean hit = false;
	protected transient Image image;
	
	public abstract void setX(double speed);
	public abstract void setY(double speed);
	public abstract double getX();
	public abstract double getY();
	public abstract Bounds getBounds();
	
}
