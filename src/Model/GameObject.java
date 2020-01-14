package Model;
/**
 * this is the class which defines the main variables and methods 
 * which goon be inherited and implemented by the sub classes like
 * player class, enemy class and soon on.
 * @author 
 */
import javafx.geometry.Bounds;
import javafx.scene.image.Image;

public abstract class GameObject implements java.io.Serializable {
	private static final long serialversionU=1L;
	protected boolean isAlive;		//
	protected int life;
	protected int point;
    protected  transient Image image;
	
	public GameObject() {};
	public abstract void setX(double speed);
	public abstract void setY(double speed);
	public abstract double getX();
	public abstract double getY();
	public abstract Bounds getBounds();
}
