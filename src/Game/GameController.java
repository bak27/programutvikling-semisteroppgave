package Game;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import Model.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * GameController class controls the game play and contains all necessary
 * methods in order to create, start en enable the player to play the game.
 * 
 *
 */
public class GameController implements Initializable{
	
   @FXML private MenuBar topMenuBar;
   @FXML private Pane gamePane;
    
    private int width = 900;				// Width of the board/Pane
    private int height = 600;				// Height of the board/Pane
    
    private Player player;					// Player
    
    private Timeline gameTimeline;			// Main game timeline
    private Timeline lifeTimeline;			// Timeline to add extra life.
    private Timeline pointTimeline;			// Timeline to add points
    private Timeline enemyTimeline;			// Timeline to add enemies
    
    private HBox pressEnterBox;				// HBox to display start message - center
    private HBox livesBox;					// HBox to display player's life - top right corner
    private HBox gameOverBox;				// HBx to display game over - center
    private HBox pauseBox;					// Display pause message - center
    private HBox pointBox;					// Display player's point - top left corner
    private HBox highscoreBox;				// Display highscore
    
    private int playerSpeed;				// Determines how fast the player moves
    private int enemySpeed;					// Determines how fast the enemies move
    private int bossSpeed;					// Determines how fast the boss will move
    private int lifeAppearanceTime;			// Controls how often extra life appear [Seconds]
    private int pointAppearanceTime;		// Controls how often extra point appear [Seconds]
    private int enemyAppearanceTime;		// Controls how often enemies appear [Seconds]
    private int numberOfEnemiesToAppear;	// Controls how many enemies to appear each cycle
    private int counter;		 			// Counter to determine when to add a boss
    private int scoreConstant;				// Multiplication factor to decide when a boss appear 
    private int numberOfEnemies = 8;		// Number of enemies to appear at start	
    private int countKilledEnemies = 0;		// Counter for how many enemies the player have killed
    private int bossKilledPoints;			// Number of points a player gain by killing a boss
   
    private String scoreList="";
    private int highScore=0;				// Counter for highscore
    
    private transient List<Boss> boss = new ArrayList<Boss>();					// Array to store all boss
    private transient List<Enemy> enemies = new ArrayList<Enemy>();				// Array to store enemies
	private transient List<Bullet> bullets = new ArrayList<Bullet>();			// Array to store bullets
	private transient List<ExtraLife> extraLife = new ArrayList<ExtraLife>();	// Array to store extraLife
    private transient List<Score> score = new ArrayList<Score>();				// Array to store points/stars
    private transient ArrayList<ScoreList> ScoreList = new ArrayList<ScoreList>();// Array to store scoreList
    
       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		initializeGame();
		setGameConstants();
	    gameLoop();		
	    loadHighScore(); //loading the highscore           
    }
    
	/**
    * this is a method which initialize the game. It creates and places
    * player and enemies on the board. Display all status of the game
    * such as life, points, how many enemies are killed. 
    */
	public void initializeGame() {
		player = new Player();
		gamePane.getChildren().add(player.getPlayer());
		
		lifeCountBox();
		pointCountBox();
        highScoreBox();
    
        displayHighscore(loadHighScore());
        displayLife(player.getLife());
		displayPoint(player.getPoint());
		displayStart();
		
		addEnemies(numberOfEnemies);
		setBackground();	
	    CreateSaveData();
    }
	
	/**
     * Sets the "speed" of the enemy that is used to determine
     * the different levels. The higher the number, the faster
     * the enemies move. 
     * @param speedConstant 
     */
	public void setEnemySpeed(int speedConstant) {
		enemySpeed = speedConstant;
	}
	
	/**
     * Set all the constants that controls the game.
     * Created for the purpose of making different levels.
     * The higher the number for each variabeles, the more
     * difficult the game will be. 
     */
	public void setGameConstants() {
		playerSpeed = 25;						
    	bossSpeed = 30;		
		lifeAppearanceTime = 5;
		pointAppearanceTime = 2;
		enemyAppearanceTime = 10;
    	numberOfEnemiesToAppear = 10;	
    	counter = 1;		 		
    	scoreConstant = 5;				
    	numberOfEnemies = 8;		
    	countKilledEnemies = 0;	
    	bossKilledPoints = 3;
	}
	
	/**
	 * GameLoop which controls how the game unfolds
	 * 1. Need to move all objects
	 * 2. Need to check for collision
	 * 		1. Between enemy and player - remove enemies if hit
	 * 		2. Between bullet and enemy - remove both if there's a collision
	 * 		3. Between player and extra life/point - remove life/points is collected
	 * 3. Update the player status such as life, points, enemy killed. 
	 * 4. Stops the game if the player is dead.
	 */
	public void gameLoop() {
		Duration duration = Duration.millis(1000);
		KeyFrame keyframe = new KeyFrame(duration, (ActionEvent e) -> {
			
			if(player.getLife() >= 0) {
				displayLife(player.getLife());
			
            }
            
			// Move all actors in the game
			moveEnemies();
			moveBullets();
			moveBoss();			
			moveExtraLifes();
			moveExtraPoint();

			// Remove enemies that is hit by player
			removeEnemyHitByPlayer(player.getBounds());
			
			//If an enemy is shot, then add a new enemy
            boolean enemyHitByBullet = enemyHitByBullet();
			if(enemyHitByBullet) {
				addEnemies(1);
			}
			
			// Check collision between boss and player, and check is the boss
			// is hit by a bullet. 
			
			checkIfPlayerCollidesWithBoss(player.getBounds());
			checkIfBossIsHitByBullet();

			
			// Add a new boss if player's point is over a certain number
			if(player.getPoint() > counter*scoreConstant) {
				addBoss(1);
				counter++;
			}
			
			// If player manage to get extra life, or points
			// increase life and points and update display
			
			if(getExtraLife(player.getBounds())) {
				player.increaseLife();
				displayLife(player.getLife());
			}
			
			
           if(getExtraPoint(player.getBounds())) {
        	   player.increasePoint();
			   displayPoint(player.getPoint());
			   if(player.getPoint() >= highScore){
				   highScore=player.getPoint();
               }
           }
                        

          // Remove all life from the array and board that player did not manage to get
          // Remove dead bosses and update the player's point
           removeExtraLife();
           removeDeadBoss();
           displayPoint(player.getPoint());
		  
           // If player is dead, update displays, and stop the game
			if(!player.isAlive()) {
				setHighscore();
                displayGameOver();
			    displayLife(player.getLife());
			    stopTimelines();
           }
			
		});
		
		gameTimeline = new Timeline();
		gameTimeline.setCycleCount(Animation.INDEFINITE);
		gameTimeline.getKeyFrames().add(keyframe);
		gameTimeline.setRate(10);
		
		// Controls how often extra life appears
		KeyFrame healthFrame = new KeyFrame(Duration.seconds(lifeAppearanceTime), (ActionEvent e) -> { 
			addExtraLife(1);
		});
		
		// Controls how often stars/points appear
        KeyFrame addPointFrame = new KeyFrame(Duration.seconds(pointAppearanceTime), (ActionEvent e) -> { 
        	addPoint(1);
	    });
        
        // Controls how often new enemies appear
        KeyFrame addEnemyFrame = new KeyFrame(Duration.seconds(enemyAppearanceTime), (ActionEvent e) -> {
        	addEnemies(1);
        });
        
        // Timeline that controls the rate of appearance for life, points and enemies.

        lifeTimeline = new Timeline(healthFrame);
        lifeTimeline.setCycleCount(Animation.INDEFINITE);
		
        pointTimeline = new Timeline(addPointFrame);
        pointTimeline.setCycleCount(Animation.INDEFINITE);
         
        enemyTimeline = new Timeline(addEnemyFrame);
        enemyTimeline.setCycleCount(Animation.INDEFINITE);
        
    }
	
	
	// *****************************************************************
	// METHODS FOR DISPLAYING STATUS ON THE BOARD
	// DISPLAY LIFE
	// ENEMY KILLED COUNT
	// SCORE
	// HIGHSCORE
    // BOSS LIFE
	// PAUSE
    // GAME OVER
    // * ***************************************************************

        
	/**
     * Sets the background to a chosen image
     */
     public void setBackground() {
    	   Image backgroundImage = new Image("/Game/background2.jpg", true);
    	   BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
    	   Background backg = new Background(background);
    	   gamePane.setBackground(backg);	
     }
       
    //***************************************************
    // Methods that stop, play and pause all active 
    // time lines in the game
    // **************************************************
     
     /**
      * Stop all active TimeLines
      */
     public void stopTimelines() {
    	 gameTimeline.stop();
    	 lifeTimeline.stop();
    	 pointTimeline.stop();
    	 enemyTimeline.stop();
     }
      
     /**
      * Start all TimeLines in the game
      */
     public void playTimelines() {
    	 gameTimeline.play();
    	 lifeTimeline.play();
    	 pointTimeline.play();
    	 enemyTimeline.play();
     }
     
     /**
      * Pause all timelines
      */
     public void pauseTimelines() {
    	 gameTimeline.pause();
    	 lifeTimeline.pause();
    	 pointTimeline.pause();
    	 enemyTimeline.pause();
     }
       
       
      /**
      * Displays a start message to the player
      * Instructions on how to start the game
      */
     public void displayStart() {
    	 
		String start = "Press enter to start";
		
		// Create a hbox to add the text on
		pressEnterBox = new HBox();
		pressEnterBox.setTranslateX(200);
		pressEnterBox.setTranslateY(250);
		
		gamePane.getChildren().add(pressEnterBox);
		
		// Load each letter in order to create a fade transition
		
		for(int i = 0; i<start.toCharArray().length; i++) {
			char letters = start.charAt(i);
			
			Text startText = new Text(String.valueOf(letters));
			startText.setFont(Font.font(48));
			startText.setOpacity(0);

            pressEnterBox.getChildren().add(startText);
            
            FadeTransition ft = new FadeTransition(Duration.seconds(0.66), startText);
            ft.setToValue(1);
            ft.setDelay(Duration.seconds(i * 0.04));
            ft.play();
		}
	}	
     	
     	/**
        * Display a pause message when enter is pressed
        * Fade transition on the text
        */
        public void displayPause() {
        	String pause = "GAME PAUSED!";
        	pauseBox = new HBox();
        	pauseBox.setTranslateX(200);
        	pauseBox.setTranslateY(250);
        	gamePane.getChildren().add(pauseBox);
		
            for(int i = 0; i<pause.toCharArray().length; i++) {
            	char letters = pause.charAt(i);
			
            	Text pauseText = new Text(String.valueOf(letters));
            	pauseText.setFont(Font.font(48));
            	pauseText.setOpacity(0);
			
                pauseBox.getChildren().add(pauseText);
  
            
                FadeTransition ft = new FadeTransition(Duration.seconds(0.66), pauseText);
                ft.setToValue(1);
                ft.setDelay(Duration.seconds(i * 0.04));
                ft.play();
          
            }
	
        }	
	
        /**
        *this is the method which displaying a text when the game is over
        * in addition it has two buttons below the text which will pop up when
        * the game is over. The first button is used in order for the player to 
        * save their name and score to a list. The second one is used to start a
        * new game.
        */
       public void displayGameOver() {
        	String gameOver = "G A M E   O V E R";
        	Button saveScoreButton = new Button("Save Score");
        	Button btn= new Button("NewGame");
		
            btn.maxWidth(200);
            btn.maxHeight(70);
            btn.maxWidth(200);
            btn.maxHeight(70);
                
            gameOverBox = new HBox();
            gameOverBox.setTranslateX(200);
            gameOverBox.setTranslateY(250);
                
            btn.setTranslateX(450);
            btn.setTranslateY(310);
		
            saveScoreButton.setTranslateX(300);
            saveScoreButton.setTranslateY(310);
                
            gamePane.getChildren().add(gameOverBox);
            gamePane.getChildren().add(btn);
            gamePane.getChildren().add(saveScoreButton);
                
            btn.setOnAction(e->{
                try {
                   Stage stage=new Stage();
                   stage=(Stage) btn.getScene().getWindow();
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                        
                   Parent root = (Parent) loader.load();
                   Scene scene = new Scene(root); 
                   stage.setScene(scene);
                } catch (IOException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
     
            });
            
            saveScoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e1) {
                // saving the scores to
                String name=JOptionPane.showInputDialog("Please enter your name inorder to save your score");
                scoreList= name + "," + player.getPoint();
                              
                File log = new File("scoreList2.txt");

                try{
                	if(!log.exists()){
                		System.out.println("you had to make a new file.");
                		log.createNewFile();
                	}

                	FileWriter fileWriter = new FileWriter(log, true);

                	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                	bufferedWriter.write(scoreList);
                	bufferedWriter.newLine();
                	bufferedWriter.close();

                	System.out.println("Done");
                } catch(IOException e) {
                	System.out.println("COULD NOT LOG!!");
                }
            }  
            });         

                
                
            for(int i = 0; i<gameOver.toCharArray().length; i++) {
            	char letters = gameOver.charAt(i);
			
            	Text gameOverText = new Text(String.valueOf(letters));
            	gameOverText.setFont(Font.font(48));
            	gameOverText.setOpacity(0);
				

            	gameOverBox.getChildren().add(gameOverText);

            	FadeTransition ft = new FadeTransition(Duration.seconds(0.66), gameOverText);
            	ft.setToValue(1);
            	ft.setDelay(Duration.seconds(i * 0.04));
            	ft.play();
            }
        }
	
        
	
	
       /**
        * Creates a box that display the player's life
        */
       public void lifeCountBox() {
        	livesBox = new HBox();
        	livesBox.setPrefSize(180, 60);
        	livesBox.setStyle("-fx-background-color: #000000;");
        	livesBox.setTranslateX(700);
        	livesBox.setTranslateY(40);
        	livesBox.setAlignment(Pos.CENTER);
		
        	gamePane.getChildren().add(livesBox);
        }
	
       /**
       * this is the method used to create a score box
       * in the play board
       */
      public void pointCountBox() {
		pointBox = new HBox();
		pointBox.setPrefSize(150, 50);
		pointBox.setStyle("-fx-background-color: #000000;");
		pointBox.setTranslateX(30);
		pointBox.setTranslateY(30);
		pointBox.setAlignment(Pos.CENTER);
		
		gamePane.getChildren().add(pointBox);
	}

      

      /**
       *this is a method used to create a highScore box
       * in the play board
       */
       public void highScoreBox() {
    	   highscoreBox = new HBox();
    	   highscoreBox.setPrefSize(150, 50);
    	   highscoreBox.setStyle("-fx-background-color: #000000;");
    	   highscoreBox.setTranslateX(30);
    	   highscoreBox.setTranslateY(70);
    	   highscoreBox.setAlignment(Pos.CENTER);
		
    	   gamePane.getChildren().add(highscoreBox);
       }




       /**
        * Method that updates the player life
        * @param life 
        */
       public void displayLife(int life) {
    	   if(life < 0) {
			return;
    	   }
    	   
    	   // Clear the lifeBox before updates
    	   livesBox.getChildren().clear();
		
    	   Text lifeCount = new Text(50, 20, "Lives: " + life + "\nEnemy killed: " + countKilledEnemies);
    	   lifeCount.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	   lifeCount.setFill(Color.YELLOWGREEN);
		
			// Add the counter again
    	   livesBox.getChildren().add(lifeCount);
       }
	
	
     /**
     * this is the method used to display a score point
     * @param point 
     */
     public void displayPoint(int point) {
		if(point < 0) {
			return;
		}
		// Clear the lifeBox first
		pointBox.getChildren().clear();
		
		Text PointCount = new Text(50, 20, "Score: " + point );
		PointCount.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		PointCount.setFill(Color.YELLOWGREEN);
		
		// Add the counter again
		pointBox.getChildren().add(PointCount);
     }
        
        
      /**
      * this is the method which display the highScore
      * @param highscore 
      */
     public void displayHighscore(int highscore) {
		// Clear the lifeBox first
		highscoreBox.getChildren().clear();
		
		Text scoreCount = new Text(50, 20, "HighScore: " + highscore );
		scoreCount.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		scoreCount.setFill(Color.YELLOWGREEN);
		
		// Add the counter again
		highscoreBox.getChildren().add(scoreCount);
     }
              
     
     //
	 // ********************************************************************
	 // METHODS HANDLING INPUT FROM USER SUCH AS KEYBOARD, MOUSE
	 //*********************************************************************
	 
	

      /**
      * Play a shooting sound
      */
      public void playLaserSound() {
		AudioClip playLaser = new AudioClip(getClass().getResource("/laser.mp3").toString());       
		playLaser.play();
      }
	
	
    /**
     * Moves the player in the direction determined by the arrow 
     * keys on the keyboard 
     * @param arrowKeys
     * @throws ClassNotFoundException 
     */    
    public void handleArrowKeys(KeyEvent arrowKeys) throws ClassNotFoundException {
		KeyCode key = arrowKeys.getCode();
			
		if(key == KeyCode.RIGHT) {
			player.setX(playerSpeed);
		}
			
		if(key == KeyCode.LEFT) {
			player.setX(-playerSpeed);
		}
		
		if(key == KeyCode.UP) {
			player.setY(-playerSpeed);
		}
		
		if(key == KeyCode.DOWN) {
			player.setY(playerSpeed);
		}
    }
    
     /**
     * Fire a bullet when space button is pushed. 
     * A shooting sound is produced whenever a bullet if fired. 
     * @param key 
     */
    public void shoot(KeyEvent key) {
		if(key.getCode() == KeyCode.SPACE) {
			// Get player position
			double playerPositionX = player.getX();
			double playerPositionY = player.getY();
			
			// Create a new bullet with simiar position as the player
			Bullet bullet = new Bullet(playerPositionX+30, playerPositionY-20);
			// Add bullets to the pane and bullet array.
			bullets.add(bullet);
			gamePane.getChildren().add(bullet.getBullet());
			
			playLaserSound();
		}
	}

    /**
     * Start and pauses the game when the player press enter.
     * If the game is running then pressing enter will pause the game
     * a sound is played in pause mode. If it's a new game, pressing
     * enter will start the game.
     * @param e 
     */   
    public void handleKeyEvent(KeyEvent e) {
		if(e.getCode() == KeyCode.ENTER) {
			AudioClip pauseSound = new AudioClip(getClass().getResource("/PauseSound.mp3").toString());
            if(gameTimeline.getStatus() == Status.RUNNING) {
				pauseTimelines();
                displayPause();
                pauseSound.play();     
            }else {
				pauseSound.stop();
                playTimelines();
                gamePane.getChildren().remove(pauseBox);
                gamePane.getChildren().remove(pressEnterBox);
			}
		}
	}

	
    /**
    * this is a method which used to read highScore from the file
    * where the highScore is saved.
    * @return 
    */      
   	public int loadHighScore(){
        try {
          File f = new File("score.txt");
          if(!f.isFile()){
                 CreateSaveData();
          }
        
          BufferedReader reader= new BufferedReader(new InputStreamReader(new FileInputStream(f)));
          highScore=Integer.parseInt(reader.readLine());
          reader.close();
       
        }catch(IOException e){
        	e.printStackTrace();
        }
        
        return highScore;
   	}
            
       

   	/**
   	 * Write a highScore in to the file,and to write a 
   	 * highScore the method used player.getPoint() so as to get the highScore.
   	 */
   	public void setHighscore(){
   		FileWriter output=null;
   		try{
        File f= new File("score.txt");
        output=new FileWriter(f);
        BufferedWriter writer= new BufferedWriter(output);
         if(player.getPoint()<= highScore){
             writer.write(""+highScore);
         }
         else{
             writer.write(""+player.getPoint());
         }
         writer.close();
   		}
   		catch(Exception e){
       e.printStackTrace();
   		}         
   	}

  

   	/**
   	 * this is the method used to create 
   	 * a score file in order to save the highScore
   	 */

   	public void CreateSaveData(){
	try{
        File file=new File("score.txt");
        FileWriter output=new FileWriter(file);
        BufferedWriter writer=new BufferedWriter(output);
        writer.write(""+ highScore);
        writer.close();
    }
	catch(IOException e){
    e.printStackTrace();
	}
   	}

   	/**
     * Save the state of the game so that the player can continue with the game
     * at a later time. 
     * Serialization is used in order to save the different objects. However
     * since we're using TranslateX and TranslateY, we need to save each position 
     * separately. The position is not saved when Serializing the GameObject such
     * as Player, enemies ...
     */
    public void save(ActionEvent event){ 
  
        String filename = "save.txt";
        
        try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
                      
            List<Double> enemiesXPos = new ArrayList<Double>();
            List<Double> enemiesYPos = new ArrayList<Double>();
            List<Double> lifeXPos = new ArrayList<Double>();
            List<Double> lifeYPos = new ArrayList<Double>();
            List<Double> pointXPos = new ArrayList<Double>();
            List<Double> pointYPos = new ArrayList<Double>();
            List<Double> bossXPos = new ArrayList<Double>();
            List<Double> bossYPos = new ArrayList<Double>();
            
            for(int i=0; i<enemies.size(); i++) {
            	enemiesXPos.add(enemies.get(i).getX());
            	enemiesYPos.add(enemies.get(i).getY());
            }
            
            for(int i = 0; i<boss.size(); i++) {
            	bossXPos.add(boss.get(i).getX());
            	bossYPos.add(boss.get(i).getY());
            }
            
            
            for(int i=0; i<extraLife.size(); i++) {
            	lifeXPos.add(extraLife.get(i).getX());
            	lifeYPos.add(extraLife.get(i).getY());
            }
            
            for(int i=0; i<score.size(); i++) {
            	pointXPos.add(score.get(i).getX());
            	pointYPos.add(score.get(i).getY());
            }
            
            
            // Method for serialization of object
            out.writeObject(player);
            out.writeDouble(player.getX());
            out.writeDouble(player.getY());          
            out.writeObject(enemies);
            out.writeObject(enemiesXPos);
            out.writeObject(enemiesYPos);
            out.writeObject(boss);
            out.writeObject(bossXPos);
            out.writeObject(bossYPos);
            out.writeObject(score);
            out.writeObject(pointXPos);
            out.writeObject(pointYPos);
            out.writeObject(extraLife);
            out.writeObject(lifeXPos);
            out.writeObject(lifeYPos);
            out.writeObject(bullets);
            out.writeInt(countKilledEnemies);
            out.writeInt(highScore);
            out.writeInt(playerSpeed);     
            out.writeInt(enemySpeed);
            out.writeInt(bossSpeed);
            out.writeInt(lifeAppearanceTime);
            out.writeInt(pointAppearanceTime);
            out.writeInt(enemyAppearanceTime);
            out.writeInt(numberOfEnemiesToAppear);
            out.writeInt(counter);
            out.writeInt(scoreConstant);
            out.writeInt(numberOfEnemies);
            out.writeInt(bossKilledPoints);
            
            out.close();
            file.close();
 
            System.out.println("Object has been serialized\n"
                                + "Data before Deserialization.");
            // value of static variable changed
        	}catch (IOException ex) {
        		ex.printStackTrace();
        		System.out.println("IOException is caught");
        }
        
    }

   
     //********************************************************************
	 // ENEMY MANAGEMENT METHODS
	 // CREATE ENEMIES, ADD ENEMIES, REMOVE ENEMIES
	 // CHECK COLLISION BETWEEN PLAYER AND ENEMIES
	 //********************************************************************

	
   /**
    * Add new enemy to the array
    * @param newEnemy 
    */
	public void addEnemy(Enemy newEnemy) {
		enemies.add(newEnemy);
		gamePane.getChildren().add(newEnemy.getEnemyNode());
	}
	
	/**
     * this a method which used to add enemies 
     * @param numberOfEnemies 
     */
     public void addEnemies(int numberOfEnemies) {
		for(int i=0; i<numberOfEnemies; i++) {
			Random random = new Random();
			Enemy tempEnemy = new Enemy(random.nextInt(width),50);
			addEnemy(tempEnemy);
		}
	}
	
	/**
     * This method is used in order to move each enemy in the game
     * @param enemySpeed which determines the speed of the enemies.
     */
    public void moveEnemies() {
		for(int i = 0; i<enemies.size(); i++) {
			Enemy tempEnemy = enemies.get(i);
			tempEnemy.move(enemySpeed);
        }
	}
	
    	/**
    	 * Check if the enemy collides with player
    	 * If collision happens, remove the enemy and decrease
    	 * the players life. Also add an extra enemy
    	 * @param playerBounds
    	 */
        public void removeEnemyHitByPlayer(Bounds playerBounds) {
        	Enemy tempEnemy;
        	for(int i=0; i<enemies.size(); i++) {
        		tempEnemy = enemies.get(i);
        		Bounds enemyBounds = tempEnemy.getBounds();
        		if(playerBounds.intersects(enemyBounds)) {
        			player.decreaseLife();
        			enemies.remove(i);
        			gamePane.getChildren().remove(tempEnemy.getEnemyNode());
					addEnemies(1);
        		}
        	}	
        }

	/**
	 * ***********************************************************
	 * BULLET MANAGER
	 * MOVE BULLET
	 * REMOVE BULLET AND KILLED ENEMIES
	 * **************************************************************
	 */
	
       
     /**
      * it used to enable each bullets to move
      */  
     public void moveBullets() {
		if(bullets.size() == 0) {	// if player is not shooting, don't move
			return;
		}
		for(int i=0; i<bullets.size(); i++) {
			Bullet tempBullet = bullets.get(i);
			tempBullet.updateBullet();
		}
     }
	
	/**
	 * Check collision between bullet and enemy.
	 * @return true if a bullet hits an enemy. return false if not hit. 
	 */
     public boolean enemyHitByBullet() {
		for(int i = 0; i<bullets.size(); i++) {
			Bullet tempBullet = bullets.get(i);
			Bounds bulletBounds = tempBullet.getBounds();
				for(int j = 0; j<enemies.size(); j++) {
					Enemy tempEnemy = enemies.get(j);
					Bounds enemyBounds = tempEnemy.getBounds();
					if(bulletBounds.intersects(enemyBounds)) {
						
						gamePane.getChildren().remove(tempBullet.getBulletNode());
						gamePane.getChildren().remove(tempEnemy.getEnemyNode());
						// Remove enemy and bullets from the array
						enemies.remove(tempEnemy);
						bullets.remove(tempBullet);
						// Remove enemy and bullets from the board
						countKilledEnemies++;
						return true;
					}
				}
		}
		return false;
	}
	
	/**
	 * *****************************************************************
	 * Extra life management
	 * Move extra life
	 * add extra life
	 * remove when player catches
	 * *****************************************************************
	 */
	
	/**
	 * Add ExtraLife to the ExtraLife array according to the number 
	 * specified by numberOfLife. In addition, ExtraLife needs to be
	 * added to the GamePane. 
	 * @param numberOfLife
	 */
	public void addExtraLife(int numberOfLife) {
		for(int i=0; i<numberOfLife; i++) {
			ExtraLife life = new ExtraLife();
			extraLife.add(life);
			gamePane.getChildren().add(life.getExtraLifeNode());
		}
	}
	
	/**
	 * Methods that moves all extra life in the ExtraLife-array
	 * Loops through the array and move each one of them. 
	 */
	public void moveExtraLifes() {
		if(extraLife.isEmpty()) {
			return;
		}
		for(int i = 0; i<extraLife.size(); i++) {
			ExtraLife tempLife = extraLife.get(i);
			tempLife.moveExtraLife();
		}
	}
	
	/**
	 * Method that checks if the player managed to get extra life
	 * If a player manages to get a heart, then increase the life variable
	 * @param playerBounds
	 * @return true if the player manages to get an extra life
	 */
	public boolean getExtraLife(Bounds playerBounds) {
		ExtraLife temp;
		for(int i = 0; i < extraLife.size() ; i++) {
			temp = extraLife.get(i);
			Bounds extraLifeBounds = temp.getBounds();
				if(playerBounds.intersects(extraLifeBounds)) {
					System.out.println("Extra Life");
					extraLife.remove(i);
					gamePane.getChildren().remove(temp.getExtraLifeNode());
					return true;
				}
		}
		return false;
	}
	
	/**
	 * Removes extra life that players did not get
	 * If the life (heart) has moved over out of the gamePane (left side)
	 * Then remove it from the array and board to clean up.
	 */
	public void removeExtraLife() {
		for(int i=0; i<extraLife.size(); i++) {
			ExtraLife extralife = extraLife.get(i);
			if(extralife.getX()<0) {
				extraLife.remove(i);
				gamePane.getChildren().remove(extralife.getExtraLifeNode());
			}
		}
	}
	
	/**
	 * *****************************************************************
	 * score management
	 * Move point 
	 * get score point
	 * remove when player catches
	 * *****************************************************************
	 */
	
	/**
	 * Add score to the arrayList<Score> 
	 * @param numberOfLife
	 */
	public void addPoint(int numberOfPoint) {
		for(int i=0; i<numberOfPoint; i++) {
		Score point = new Score();
		score.add(point);
		gamePane.getChildren().add(point.getExtraPoint());
		}
	}
	
	/**
	 * Moves all the life in the array
	 * 
	 */
	
	public void moveExtraPoint() {
		if(score.isEmpty()) {
			return;
		}
		for(int i = 0; i<score.size(); i++) {
			Score tempScore = score.get(i);
			tempScore.moveExtraPoint();
		}
	}
	
	/**
	 * checks if the player got extra life
	 * @param playerBounds
	 * @return true if the player manages to get point
	 */
	public boolean getExtraPoint(Bounds playerBounds) {	
        Score temp;
        for(int i = 0; i < score.size() ; i++) {
			temp = score.get(i);
			Bounds extraPointBounds = temp.getBounds();
				if(playerBounds.intersects(extraPointBounds)) {
					score.remove(i);
					gamePane.getChildren().remove(temp.getExtraPoint());
					return true;                  
				}
		}
		return false;
	}     

	
	/**
	 * Removes extra life that players did not get
	 * @param none,it checks the position of the extra life
	 * @return none
	 */
	 public void removeExtraPoint() {
		for(int i=0; i<score.size(); i++) {
			Score point = score.get(i);
			if(point.getX()<0) {
				score.remove(i);
				gamePane.getChildren().remove(point.getExtraPoint());
			}
		}
	} 
	
        
        
      /**
	 * ****************************************************************
	 * Methods involving BOSS
	 * killBoss - checks if bullet hits boss
	 * playerCollideWithBoss - checks if player collides with boss. 
	 * player will loose 2 life if player hit boss.
	 * ****************************************************************
	 */

	/**
	 * Method that adds a specified number of boss to the boss-array
	 * One can add more bosses to the game according to levels.
	 * A hard level can for instance add several bosses at a time
	 * @param numberOfBoss to be added to the array and the pane
	 */
	private void addBoss(int numberOfBoss) {
		for(int i = 0; i<numberOfBoss; i++) {
			Boss newBoss = new Boss();
			boss.add(newBoss);
			gamePane.getChildren().add(newBoss.getBossNode());
		}
	}
	
	
	/**
	 * Methods that moves all the boss in game
	 * If the boss-array is empty then don't do anything
	 * Otherwise loop through the array and move each individual boss 
	 */
	public void moveBoss() {
		if(boss.isEmpty()) {
			return;
		}
		
		for(int i = 0; i < boss.size(); i++) {
			Boss tempBoss = boss.get(i);
			tempBoss.moveBoss();
		}
	}
	
	/**
	 * Method that checks if the boss is hit by a bullet
	 * 1. Loops through the bullets array and check each single bullet if it hits a boss
	 * If the boss is hit, decrease boss life by one
	 * Remove the bullet from the bullet-array
	 * Remove the bullet from the gamePane.
	 */
	public void checkIfBossIsHitByBullet() {
		
		for(int i = 0; i < bullets.size(); i++) {
			Bullet tempBullet = bullets.get(i);
			Bounds bulletBounds = tempBullet.getBounds();	
				
				for(int j = 0; j < boss.size(); j++ ) {
					Boss tempBoss = boss.get(j);
					Bounds bossBound = tempBoss.getBounds();
						
						// If collision, remove the bullet from the bullet list and from the gamePane
						if(bulletBounds.intersects(bossBound)) {
							tempBoss.decreaseLife();
							System.out.println("Boss loses 1 life");
							gamePane.getChildren().remove(tempBullet.getBulletNode());
							bullets.remove(tempBullet);
						}
				}
			
		}
	}	
	
	/**
	 * Method that checks if any of the bosses are dead.
	 * If the boss is dead then remove it from the boss-array 
	 * Afterward remove it from the gamePane. 
	 * Lastly increase the player's point for killing a boss
	 */
	private void removeDeadBoss() {
		for(int i = 0; i<boss.size(); i++) {
			Boss tempBoss = boss.get(i);
			if(!tempBoss.isAlive()) {
				boss.remove(tempBoss);
				gamePane.getChildren().remove(tempBoss.getBossNode());
				player.increasePoint(bossKilledPoints);
			}
		}
	}
	
	/**
	 * Methods that checks if the player collides with a boss
	 * If a player collides with a boss, set player's life
	 * to zero and status to dead.
	 * 
	 * @param playerBound
	 * @return true if player dies, false if there's no collision
	 */
	public boolean checkIfPlayerCollidesWithBoss(Bounds playerBound) {
		for(int i = 0; i<boss.size(); i++) {
			Bounds bossBounds = boss.get(i).getBounds();
			if(bossBounds.intersects(playerBound)) {
				player.playerDead();
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * this is the methods which leads the player in to help 
	 * scene(help page) and as the same time the method
         * write text document in to file in order to make ready 
         * the help menu ready for player when the need to read
         * something.
         * @param event
	 * @throws IOException 
	 */     
	public void help(ActionEvent event) throws IOException{
		
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
"10. After collecting a certain amount of stars, a boss will appear. A boss have 3 lifes than\n" +
"    UFOs so you have to shoot it 3 times in order to kill it.\n" +
"11. If you collide with a boss, you die and it's Game Over.\n" +
"12. The boss will continue to reappear randomly again at the top until you are able to kill it.\n" +
"13. If you kill a boss, you earn 3 points.\n" +

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
"The harder the level, the faster the enemies will move." +
"\n" +
"\n" +
"\n" +
"GOOD LUCK! ");
       writer.close();
    }
catch(IOException e){
    e.printStackTrace();
}
            
            
            
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Help.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));  
        stage.show();                
	}

  
	/**
	 * this is a method which makes possible for the players
	 * in order to back to Menu page. 
	 * @param event
	 * @throws IOException 
	 */
	public void BacktoMenu(ActionEvent event) throws IOException{
        Stage stage=new Stage();
        stage=(Stage)topMenuBar .getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
    
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
                     
	}
}
