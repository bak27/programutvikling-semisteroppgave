
package Game;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
/**
 *this is Menu class which used to implements methods so as to provide
 * different options for the players like play the game,load the game,
 * check scoreList or see the help guide. 
 * @author 
 */
public class MenuController implements Initializable,java.io.Serializable {
    @FXML
    private Button Play;
   
    @FXML
    private Button Help;
    @FXML
    private Button Exit;
   @FXML
    private Button back;
   
   @FXML
   private Button ScoreList;
   
   
   @FXML
   private Button Loading;
   
  
   
   
    @FXML
    private Pane handleButtonAction(ActionEvent event) throws IOException, ClassNotFoundException{
        
     Stage stage = null; 
    
     
 
     
     
     if(event.getSource()==Play){
        stage=(Stage) Play.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
		Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
		 stage.setScene(scene); 
     }

     
     else if(event.getSource()==Loading){
          
      stage=(Stage) Loading.getScene().getWindow();
  FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadGame.fxml"));
		Parent root = (Parent) loader.load();
		LoadGameController loadgameController = loader.getController();
		
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
                scene.setOnKeyPressed((KeyEvent events) -> {
                    loadgameController.handleArrowKeys(events);
			loadgameController.shoot(events);
			loadgameController.handleKeyEvent(events);
		});
      
}
      
     
     
     
     else if(event.getSource()==Help){
         
         try{
        File file=new File("help.txt");
        FileWriter output=new FileWriter(file);
        BufferedWriter writer=new BufferedWriter(output);
       writer.write("How to play: \n" +
"\n" +
"The purpose of the game is to collect as many stars (points) as possible without dying.\n" +
"\n" +
"N.B Player can play the game by using the up,down,right and left key of the keyboard \n" +
"1. Start the game by pressing enter. \n" +
"2. Pause the game by pressing enter, and restart the game by pressing enter. \n" +
"3. Control the spaceship using the arrow keys. \n" +
"4. Shoot a bullet by using space. The number of enemies you have killed is displayed in the upper right corner.  \n" +
"5. Each player have 3 life at start. The number of life you currently have is displayed in the upper right corner.\n" +
"6. If you collide with an enemy (UFO) you loose 1 life.\n" +
"7. If you have 0 life left then it's Game Over. \n" +
"8. You can gain additional life by moving the spaceship and collect hearts. \n" +
"9. Collect stars to gain points by moving the spaceship and collide with the stars. The number of points  \n" +
"   or the score you have is displayed in the upper left corner. \n" +
"10. After collecting a certain amount of stars, a boss will appear. A boss have more life than\n" +
"    UFOs so you have to shoot it more times in order to kill it.\n" +
"11. If you collide with a boss, you die and it's Game Over.\n" +
"12. Each boss have 3 life, so you need to shoot it three times in order to kill it . \n" +
"13. The boss will continue to reappear randomly again at the top until you are able to kill it.\n" +
"14. If you kill a boss, you earn 10 points.\n" +
"15. The moving speed of the boss will increase according to the number of point you earn. The more\n" +
"    points you have, the faster the boss will move. \n" +
"\n" +
"Save and continue a game: \n" +
"\n" +
"1. You can save a game by pressing a save text on the topMenuBare which finds on the very top of the gameboard.\n" +
"2. You can save as many times as you want during the game, but only the last version will be saved. \n" +
"3. Load or continue your last game by choosing \"Continue last game\" in the main menu window. \n" +
"4. When you continue a old game, you can still save the game by pressing S on the keyboard. You can continue\n" +
"   by going back to the main menu and choose continue your last game. \n" +
"\n" +
"Difficulty levels:\n" +
"\n" +
"There's 3 different levels available. Easy, medium and hard. \n" +
"The difference is in the number of enemies, how fast the enemies appear and how fast the enemies moves. \n" +
"The harder the level, the faster the enemies will appear and move. It will also be more difficult to gain \n" +
"additional life if you choose to play at a more difficult level. \n" +
"\n" +
"\n" +
"\n" +
"GOOD LUCK! ");
       writer.close();
    }
catch(IOException e){
    e.printStackTrace();
}
            
         
         stage=(Stage) Help.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
		Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
		 stage.setScene(scene); 
     }
      else if  (event.getSource()==back){
        stage=(Stage) back.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
		 stage.setScene(scene); 
     
     }
       
     
      
       else if  (event.getSource()==ScoreList){
           stage=(Stage) ScoreList.getScene().getWindow();
           
            Parent root = FXMLLoader.load(getClass().getResource("scoreList.fxml"));
            
           Scene scene = new Scene(root);
	   stage.setScene(scene);
       }
   else  {
     stage=(Stage) Exit.getScene().getWindow();
     System.exit(0);
      
     }
    
     
   
   
    
     
 stage.show();
 
        return null;
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
    }    
    
}

   
      
        
    
   
    
    
 