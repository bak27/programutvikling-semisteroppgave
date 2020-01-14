
package Game;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import Model.*;
/**
 *this class totally devoted to do task related to the different
 *levels,so her there is three levels, and these levels try to
 *manage the number of enemies and their speeds,so handleButtonAction
 *method handle all the three levels, each of  the levels accept speed 
 * as parameter via an object from GameController which
 * is called gameController, and this object called method that is
 * gameController.setEnemySpeed(enemySpeed) and as the same time the number
 * of enemies adjust according to the speed of the game .
 * @author
 */
public class LevelController implements Initializable, ActionListener {
    @FXML private Button Level1;
    @FXML private Button Level2;
    @FXML private Button Level3;
   // public int numberOfEnemies ;
    public double speed=0;
    
    
    @Override
  
public void initialize(URL location, ResourceBundle resources) {
        
        
        
        
    }
  
 @FXML
 private void handleButtonAction(ActionEvent event) throws IOException{
        
     Stage stage ; 
		
     if(event.getSource()==Level1){
    	 
        stage=(Stage) Level1.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
		Parent root = (Parent) loader.load();
		GameController gameController = loader.getController();
		int enemySpeed = 30;
		gameController.setEnemySpeed(enemySpeed);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                scene.setOnKeyPressed((KeyEvent events) -> {
          try {
              gameController.handleArrowKeys(events);
          } catch (ClassNotFoundException ex) {
              Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
          }
			gameController.shoot(events);
			gameController.handleKeyEvent(events);
		});
      
     }
     
     else if(event.getSource()==Level2){
     stage=(Stage) Level2.getScene().getWindow();
                
     FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
		Parent root = (Parent) loader.load();
		GameController gameController = loader.getController();
		int enemySpeed = 50;
		gameController.setEnemySpeed(enemySpeed);
                Scene scene = new Scene(root);
		stage.setScene(scene);
                scene.setOnKeyPressed((KeyEvent events) -> {
          try {
              gameController.handleArrowKeys(events);
          } catch (ClassNotFoundException ex) {
              Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
          }
			gameController.shoot(events);
			gameController.handleKeyEvent(events);
		});
     
                
     }
     else {
       
        
         stage=(Stage) Level3.getScene().getWindow();
               
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
		Parent root = (Parent) loader.load();
		GameController gameController = loader.getController();
		int enemySpeed = 70;
		gameController.setEnemySpeed(enemySpeed);
	        Scene scene = new Scene(root);
		stage.setScene(scene);
                scene.setOnKeyPressed((KeyEvent events) -> {
            try {
                gameController.handleArrowKeys(events);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
            }
			gameController.shoot(events);
			gameController.handleKeyEvent(events);
		});
      }
                
                
                
        
               
	        stage.show();
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
        

}
