package snake.evolution;

import javafx.application.Application;
import javafx.stage.Stage;

/*
// Main class that runs the Evoluton application.
*/
public class GameRunner extends Application {
    
    @Override
    public void start(Stage stage) {
        
        Game game = new Game();
        
        stage.setTitle("Snake");
        
        stage.setScene(game.getScene());
        
        stage.setResizable(false);
        
        stage.show();
    }
    
    public static void main(String[] args) {
        
        launch(args);
    }
}