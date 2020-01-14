package Model;
/**
 * this is the class which manage the score class by 
 * defining a list of array and different methods which
 * goon be implemented by the score class.
 */
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;

public class PointControl implements java.io.Serializable{
	
	
	private transient List<Score> score = new ArrayList<Score>();
        private int width = 900;
	
	
	public PointControl() {
		
	        createExtraPoint(6);
        }
	
	
	
	public void createExtraPoint(int numberOfExtraPoint) {
		for(int i = 0; i<numberOfExtraPoint; i++) {
			Score point = new Score();
			score.add(point);
		}
	}
        
        
        
	public void addExtraPoint() {
		Score point = new Score();
		score.add(point);
	}
	
        
       
	
	public List<Score> getAllExtraPoint(){
		return score;
	}
        
        
        
	
	public void moveExtraPoints() {
		for(int i = 0; i<score.size(); i++) {
			Score tempPoint = score.get(i);
			tempPoint.moveExtraPoint();
		}
	}
        
        
        
        
	 
public Score getCollidedPoint(Bounds playerBounds) {
    Score temp;
for(int i = 0; i < score.size() ; i++) {
			temp = score.get(i);
			Bounds extraPointBounds = temp.getBounds();
				if(playerBounds.intersects(extraPointBounds)) {
					System.out.println("EXtrapoint");
					score.remove(i);
					return temp;
				}
		}
		return null;
	}


}
