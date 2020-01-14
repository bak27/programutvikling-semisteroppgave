
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


public class helpController implements Initializable{

    @FXML
    public TextArea help;
    
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
          File file = new File("help.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBufferHelp = new StringBuffer();
             String line;
			
                        while ((line = bufferedReader.readLine()) != null) {
                            
                            stringBufferHelp.append(line);
                            stringBufferHelp.append("\n");
                           
                            
                        }
                        help.setText(stringBufferHelp.toString());
                       
			fileReader.close();
        
        
        }                          
            
        catch(IOException e){
    e.printStackTrace();
}   
              
        
    }
    
    
    
    
    
}
