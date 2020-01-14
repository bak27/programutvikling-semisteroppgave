
package Game;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 *this is the class which reads player's score point,
 *in which the method reads score list from the text file
 *and presented the names of player with its score in the scene.
 * @author 
 */
public class scoreController implements Initializable{

    @FXML
    public TextArea scoreName;
    
    @FXML
    public TextArea scoreScore;
    
    @FXML
    public Button back;
    
     @FXML
    private Pane handleButtonAction(ActionEvent event) throws IOException, ClassNotFoundException{
       Stage stage=new Stage();
        stage=(Stage) back.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
		 stage.setScene(scene); 
        return null;
    }
        
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
			File file = new File("scoreList2.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBufferName = new StringBuffer();
                        StringBuffer stringBufferScore = new StringBuffer();
                        
			String line;
			
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] string = line.split(",");
                            stringBufferName.append(string[0]);
                            stringBufferName.append("\n");
                            stringBufferScore.append(string[1]);
                            stringBufferScore.append("\n");
                            
                        }
                        scoreName.setText( stringBufferName.toString());
                        scoreScore.setText(stringBufferScore.toString());
			fileReader.close();
			System.out.println("Contents of file:");
			
                        //System.out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
              
        
    }
    
    
}
