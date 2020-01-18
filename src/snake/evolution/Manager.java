package snake.evolution;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

/*
// Controls any changes made to the game.
*/
public class Manager {
    
    // Whether new key inputs are processed.
    private static boolean keyLock = false;
    
    /*
    // Invokes Snake's changePath() method if
    // processing is not locked.
    //
    // @param event - Keystroke input.
    // @param player - Player-controlled character.
    */
    public static void getInput(KeyEvent event, Snake player) {
        
        if (!keyLock) {
            
            // Locks processing.
            keyLock = true;

            // Updates player direction.
            player.changePath(event);
        }
    }
    
    /*
    // Update the game grid when changes occur
    // to its tiles.
    //
    // @param grid: grid being processed
    // @param player: Snake playing on the grid
    // @param apple: Apple on the grid
    //
    // @return Grid with up-to-date info on Tiles
    // and which ones are occupied.
    */
    public static Node[] updateGrid(Node[] grid, Snake player, Apple apple) {
        
        // Store copies of elements in grid.
        Node[] gridClone = new Node[Game.GRID_AREA];
        
        // Iterate through all tiles, adding each tile to gridClone.
        for (int i = 0; i < Game.GRID_AREA; i++)
            gridClone[i] = grid[i];
        
        // Set every tile that snake's body is on to "occupied".
        for (int i = 0; i < player.size(); i++)
            ((Tile)gridClone[player.segment(i).getPos().toTile()]).setOccupied(true);

        // Set last tile of LAST segment to "not occupied".
        if (player.segment(player.size() - 1).lastPos() != null) // null if lastPos doesn't yet exist
            ((Tile)gridClone[player.segment(player.size() - 1).lastPos().toTile()]).setOccupied(false);
        
        // Set tile of apple to "occupied".
        ((Tile)gridClone[apple.getPos().toTile()]).setOccupied(true);
        
        if (apple.lastPos() != null) { // null if lastPos doesn't yet exist
            
            // Snake body isn't initially on apple.
            boolean onApple = false;
            
            // Iterate through each segment in snake body...
            for (int i = 0; i < player.size(); i++)
                
                // If segment and apple's last position are the same...
                if (apple.lastPos().equals(player.segment(i).getPos()))
                    
                    // Snake body is on apple.
                    onApple = true;
            
            // If on apple...
            if (onApple)
                ((Tile)gridClone[apple.lastPos().toTile()]).setOccupied(true);
            
            else
                ((Tile)gridClone[apple.lastPos().toTile()]).setOccupied(false);
        }
        
        Node[] newGrid = new Node[Game.GRID_AREA];
        
        // Iterate through all tiles in gridClone, adding back each tile to newGrid.
        for (int i = 0; i < Game.GRID_AREA; i++)
            newGrid[i] = gridClone[i];
        
        return newGrid;
    }
    
    /*
    // Move Snake's body to a new position.
    //
    // @param grid: game grid
    // @param snake: character that is moving
    // @param apple: Food object
    //
    // @return true if movement is successful, false if Snake
    // collides with an obstacle.
    */
    public static boolean move(Snake snake, Apple apple, Node[] grid) {
        
        // Reference to first segment.
        Segment head = snake.segment(0);
        
        // Set last position of head to current position.
        head.setLastPos(head.getPos());
        
        /*
        // If Snake's next forward movement would
        // result in it being out of bounds, movement
        // is obstructed.
        */
        if (snake.next("Forward").outOfBounds())
            return false;
        
        /*
        // Else if Snake's next forward movement is
        // onto an occupied Tile and that Tile does
        // not have an Apple, also obstructed.
        */
        else if (((Tile)grid[snake.next("Forward").toTile()]).occupied && !snake.next("Forward").equals(apple.getPos()))
            return false;
        
        // Otherwise, no obstruction.
        else {
            
            // Move head forward.
            head.setPos(snake.next("Forward"));
            
            /*
            // Each Segment in Snake has its last position
            // set to current position. Then, position is
            // updated to match the last position of its
            // corresponding previous Segment.
            */
            for (int i = 1; i < snake.size(); i++) {

                snake.segment(i).setLastPos(snake.segment(i).getPos());
                snake.segment(i).setPos(snake.segment(i - 1).lastX(), snake.segment(i - 1).lastY());
            }
        }

        return true;
    }
    
    /*
    // Create input[] array for use in processing
    // by neural network.
    //
    // @param grid: grid being processed
    // @param player: Snake playing on the grid
    // @param apple: Apple on the grid
    */
    public static float[] processInput(Node[] grid, Snake player, Apple apple) {
        
        // Array which will hold final return values.
        float input[] = new float[Genetics.INPUT_SIZE];
        
        // If forward Tile is out of the game grid
        // or is a Segment, input[0] is 1, otherwise 0.
        input[0] = player.next("Forward").outOfBounds() ||
                (((Tile)grid[player.next("Forward").toTile()]).occupied
                && !apple.getPos().equals(player.next("Forward"))) ? 1 : 0;
        
        // If left Tile is out of the game grid
        // or is a Segment, input[1] is 1, otherwise 0.
        input[1] = player.next("Left").outOfBounds() ||
                (((Tile)grid[player.next("Left").toTile()]).occupied
                && !apple.getPos().equals(player.next("Left"))) ? 1 : 0;
        
        // If left Tile is out of the game grid
        // or is a Segment, input[2] is 1, otherwise 0.
        input[2] = player.next("Right").outOfBounds() ||
                (((Tile)grid[player.next("Right").toTile()]).occupied
                && !apple.getPos().equals(player.next("Right"))) ? 1 : 0;
        
        // Sets angle as return value of angleToApple().
        double angle = player.angleToApple(apple);
        
        // If angle meets conditions, input[3] is 1, else 0;
        input[3] = angle > -45 && angle < 45 ? 1 : 0;
        
        // If angle meets conditions, input[4] is 1, else 0;
        input[4] = angle <= -45 ? 1 : 0;
        
        // If angle meets conditions, input[5] is 1, else 0;
        input[5] = angle >= 45 ? 1 : 0;
        
        return input;
    }
    
    // Setter for keyLock.
    public static void toggleLock(boolean state) {
        
        keyLock = state;
    }
}