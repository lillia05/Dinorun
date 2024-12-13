package Dinorun;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainGameController {
   @FXML
    private AnchorPane root;

    @FXML
    private Pane gameArea;

    @FXML
    private ImageView background;

    @FXML
    private ImageView dino;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button restartButton;

    @FXML
    private Button balikMenu;

    private ImageView kayu, batu; 
    private int score = 0;
    private double obstacleSpeed = 5.0;
    private boolean isGameRunning = true;
    boolean isDown = false;

    private double velocityY = 0;
    private final double GRAVITY = 0.1;

    private final Image kayuImage = new Image(getClass().getResource("/resource/kayu.png").toExternalForm());
    private final Image batuImage = new Image(getClass().getResource("/resource/batu.png").toExternalForm());
    private final Random random = new Random();

    private long lastUpdateTime = 0; 
    private long lastSpawnTime = 0;
    private static final long SPEED_INCREASE_INTERVAL = 1000000000L; 
    private static final long OBSTACLE_SPAWN_INTERVAL = 1500000000L; 
        
    Dir dir = Dir.botbot; 
    Message message = new Message();
    AlertAlert alertalert = new AlertAlert();
   
    enum Dir{
        upup,botbot;
    }

   @FXML
    private void backMenu(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("beranda.fxml"));
            Parent mainMenuRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Dashboard Dinorun !");

            Scene scene = new Scene(mainMenuRoot);
            stage.setScene(scene);

            stage.show();
       } catch (Exception e) {
           e.printStackTrace(); 
        }
    }

     public void initialize() {

        kayu = new ImageView(kayuImage);
        batu = new ImageView(batuImage);

        configureObstacle(kayu, 900);
        configureObstacle(batu, 1100);

        kayu.setVisible(false);
        batu.setVisible(false);

        gameArea.getChildren().addAll(kayu, batu);

        root.setOnKeyPressed(this::handleKeyPress);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isGameRunning) {
                    moveObstacle(kayu);
                    moveObstacle(batu);

                    updateDinoPosition();
                    checkCollision();

                    if (now - lastSpawnTime > OBSTACLE_SPAWN_INTERVAL) {
                        spawnObstacle();
                        lastSpawnTime = now;
                    }

                    if (now - lastUpdateTime > SPEED_INCREASE_INTERVAL) {
                        increaseObstacleSpeed();
                        lastUpdateTime = now;
                    }
                    updateScore();
                }
            }
        };
        gameLoop.start();
    }

   private void configureObstacle(ImageView obstacle, double x) {
        obstacle.setFitWidth(100);
        obstacle.setFitHeight(70);
        obstacle.setLayoutX(x);
        obstacle.setLayoutY(460);
        obstacle.setPreserveRatio(true);
    }

    private void moveObstacle(ImageView obstacle) {
        if (obstacle.isVisible()) {
            obstacle.setLayoutX(obstacle.getLayoutX() - obstacleSpeed);

            if (obstacle.getLayoutX() + obstacle.getFitWidth() < 0) {
                obstacle.setVisible(false);
            }
        }
    }

    private void spawnObstacle() {
        if (!kayu.isVisible() && !batu.isVisible()) {
            if (random.nextBoolean()) {
                kayu.setLayoutX(gameArea.getPrefWidth());
                kayu.setVisible(true);
            } else {
                batu.setLayoutX(gameArea.getPrefWidth());
                batu.setVisible(true);
            }
            ensureMinDistance();
        }
    }

   private void updateDinoPosition() {
        velocityY += GRAVITY;                             
        
        if(dino.getLayoutY() <= 250){ 
            dir = Dir.botbot;
        }
      
        if(isDown) dino.setLayoutY(dino.getLayoutY() + 9); 
        
        if(dir == Dir.upup){ 
            dino.setLayoutY(dino.getLayoutY() - 13); 
        }
        else if(dir == Dir.botbot){ 
            dino.setLayoutY(dino.getLayoutY() + velocityY);
        }
      
        if (dino.getLayoutY() > 400) {
            dino.setLayoutY(400);
            velocityY = 0;
        }
    }

   private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                if (dino.getLayoutY() == 400 ) dir = Dir.upup;
                isDown = false;
                break;
            case DOWN:
               
                dir = Dir.botbot;
                isDown = true;
                break;
        }
    }

   private void updateScore() {
    double dinoRight = dino.getLayoutX() + dino.getFitWidth();

    if (batu.isVisible()) {
        double batuRight = batu.getLayoutX() + batu.getFitWidth();
        if (dinoRight > batuRight ){
            score++;
            batu.setManaged(true); 
        }
    }

    if (kayu.isVisible()) {
        double kayuRight = kayu.getLayoutX() + kayu.getFitWidth();
        if (dinoRight > kayuRight ){
            score++;
            kayu.setManaged(true); 
        }
    }
    scoreLabel.setText("Score: " + score);
}

   @FXML
    private void restartGame() {
        isGameRunning = true;
        score = 0;
        scoreLabel.setText("Score: 0");
        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(10);                          
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: normal; -fx-text-fill: black; -fx-alignment: center;");

        restartButton.setVisible(false);
        dino.requestFocus();

        kayu.setLayoutX(900);
        kayu.setVisible(false);
        batu.setLayoutX(1100);
        batu.setVisible(false);

        obstacleSpeed = 5.0;
        lastUpdateTime = System.nanoTime();
        lastSpawnTime = System.nanoTime();
    }
    
    @FXML
    void downDINO(KeyEvent event) {}
    @FXML
    void moveDINO(KeyEvent event) {}
