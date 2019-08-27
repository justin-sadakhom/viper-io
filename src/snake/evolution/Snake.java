/*
// Author: Justin Sadakhom
// Date: June 11, 2019
// File: Snake.java
*/

package snake.evolution;

import java.util.ArrayList;
import javafx.scene.input.KeyEvent;
import neat.Genome;

/*
// Representation of a Snake, the playable
// character of the game "Snake".
*/
public class Snake {
    
    /* FIELDS */
    
    // Length constants.
    private final int MIN_SIZE = 2;
    private final int MAX_SIZE = Game.GRID_AREA;
    
    // Parts that make up a Snake.
    private ArrayList<Segment> body = new ArrayList<>(MAX_SIZE);
    private Genome genome;
    
    // Direction of travel.
    private int direction;
    
    // Current size.
    private int size = MIN_SIZE;
    
    // Apples consumed.
    private int foodEaten = 0;
    
    // Survival score.
    private int fitness = 0;
    
    // Theoretical points for how close Snake gets to Apple.
    private int score = 0;
    
    // Actual points for how close Snake gets to Apple.
    private int health = 0;
    
    // If Snake has eaten recently.
    private boolean wellFed = true;
    
    /* CONSTRUCTORS */
    
    /*
    // Default constructor.
    //
    // @param genome - Snake's DNA, which determines its behaviour.
    */
    public Snake(Genome genome) {
        
        this.genome = genome;
        
        // Adds first segment (head).
        body.add(new Segment(new Coordinate()));
        
        // Generates a random int between 0-3.
        direction = (int)Math.floor(Math.random() * 4);
        
        // Places next segment in proper orientation.
        loadBody();
    }
    
    /* FIELDS */
    
    /*
    // Using direction of Snake, places its next
    // segment  the corresponding direction. Ex:
    // if direction is North, then segment would
    // be South of the Snake's head.
    */
    private void loadBody() {
        
        // References to head.
        int headX = body.get(0).x();
        int headY = body.get(0).y();
        
        switch(direction) {
            
            // North.
            case 0:
                
                body.add(new Segment(new Coordinate(headX, headY + 1)));
                break;
            
            // East.
            case 1:
                
                body.add(new Segment(new Coordinate(headX - 1, headY)));
                break;
            
            // South.
            case 2:
                
                body.add(new Segment(new Coordinate(headX, headY - 1)));
                break;
            
            // West.
            case 3:
                
                body.add(new Segment(new Coordinate(headX + 1, headY)));
                break;
        }
    }
    
    /*
    // Grows Snake. Method should only be invoked
    // after the Snake eats an Apple.
    */
    public void grow() {
        
        size += 1;
        foodEaten += 1;
        
        wellFed = true;
        
        // Adds a new Segment at the last position of the tail segment.
        body.add(new Segment(new Coordinate(body.get(size - 2).lastX(), body.get(size - 2).lastY())));
    }
    
    /*
    // Checks if Snake's head is on an Apple.
    //
    // @param apple - Apple object that is being checked.
    //
    // @return true if first segment of Snake has same
    // Coordinate as Apple, false otherwise.
    */
    public Boolean onApple(Apple apple) {
        
        return segment(0).onApple(apple);
    }
    
    /*
    // Changes direction of Snake based on
    // input from keystroke.
    //
    // @param event - Keystroke.
    */
    public void changePath(KeyEvent event) {
        
        switch (event.getCode()) {

            // If left arrow key was pressed,
            // changes direction from north to
            // west etc.
            case LEFT:
                
                if (direction - 1 < 0)
                    direction = 3;
                
                else
                    direction -= 1;
                
                break;
                
            // If right arrow key was pressed,
            // changes direction from north to
            // east etc.
            case RIGHT:

                if (direction + 1 > 3)
                    direction = 0;

                else
                    direction += 1;

                break;
        }
    }
    
    /*
    // Finds next Coordinate based on orientation
    // and position of Snake head.
    //
    // @param orientation - Direction from Snake
    // head that's being checked
    // 
    // @return Coordinate of Tile at the direction
    // being checked
    */
    public Coordinate next(String orientation) {
        
        // Reference to head.
        Segment head = segment(0);
        
        switch (orientation) {
            
            // If checking forwards...
            case "Forward":
            
                switch (direction) {

                    case 0: return new Coordinate(head.x(), head.y() - 1);

                    case 1: return new Coordinate(head.x() + 1, head.y());

                    case 2: return new Coordinate(head.x(), head.y() + 1);

                    case 3: return new Coordinate(head.x() - 1, head.y());
                }
            
            // If checking to the left...
            case "Left":
            
                switch (direction) {

                    case 0: return new Coordinate(head.x() - 1, head.y());

                    case 1: return new Coordinate(head.x(), head.y() - 1);

                    case 2: return new Coordinate(head.x() + 1, head.y());

                    case 3: return new Coordinate(head.x(), head.y() + 1);
                }
            
            // If checking to the right...
            case "Right":
            
                switch (direction) {

                    case 0: return new Coordinate(head.x() + 1, head.y());

                    case 1: return new Coordinate(head.x(), head.y() + 1);

                    case 2: return new Coordinate(head.x() - 1, head.y());

                    case 3: return new Coordinate(head.x(), head.y() - 1);
                }
            
            default:
                return null;
        }
    }
    
    /*
    // Checks if there's an Apple in a certain
    // direction from the head.
    //
    // @param apple - Apple being checked for
    // @param orientation - Direction of check
    //
    // @return true is Apple is in that direction,
    // false otherwise
    */
    public boolean has(Apple apple, String orientation) {
        
        // Reference to head.
        Segment head = segment(0);
        
        switch (orientation) {
            
            // If checking forward...
            case "Forward":
                
                switch (direction) {
                    
                    case 0: return head.x() == apple.x() && head.y() > apple.y();
                        
                    case 1: return head.y() == apple.y() && head.x() > apple.x();
                    
                    case 2: return head.x() == apple.x() && head.y() < apple.y();
                    
                    case 3: return head.y() == apple.y() && head.x() < apple.x();
                }
            
            // If checking to left...
            case "Left":
                
                switch (direction) {
                    
                    case 0: return head.y() == apple.y() && head.x() > apple.x();
                    
                    case 1: return head.x() == apple.x() && head.y() > apple.y();
                    
                    case 2: return head.y() == apple.y() && head.x() < apple.x();
                    
                    case 3: return head.x() == apple.x() && head.y() < apple.y();
                }
            
            // If checking to right...
            case "Right":
                
                switch (direction) {
                    
                    case 0: return head.x() == apple.x() && head.x() < apple.x();
                    
                    case 1: return head.y() == apple.y() && head.y() < apple.y();
                    
                    case 2: return head.x() == apple.x() && head.x() > apple.x();
                    
                    case 3: return head.y() == apple.y() && head.y() > apple.y();
                }
            
            // Default case prints error message to console.
            default:
                System.out.println("ERROR: DEFAULT CASE REPORTED.");
                return false;
        }
    }
    
    /*
    // Calculates angle from Snake head
    // to Apple.
    //
    // @param apple - Apple being referenced.
    //
    // @return - Calculated angle.
    */
    public double angleToApple(Apple apple) {
        
        // Reference to head.
        Segment head = segment(0);
        
        // More than 180 degrees would flip right to left and vice versa.
        double maxAngle = 180;
        
        // Settings radians to return value of atan2().
        double radians = Math.atan2(apple.y() - head.y(), apple.x() - head.x());
        
        // Converting radians to degrees ( atan() returns value in radians).
        double degrees = Math.toDegrees(radians);
        
        // Adding 90 degrees since angle needs to be referenced from North axis.
        double angle = degrees + 90;
        
        // Making sure angle isn't greater than 180.
        if (angle > maxAngle)
            angle = angle - 360;
        
        // Adds degrees to account for direction
        // of travel of Snake.
        switch (direction) {
            
            // East
            case 1:
                angle += 270;
                break;
            
            // South
            case 2:
                angle += 180;
                break;
            
            // West
            case 3:
                angle += 90;
                break;
            
            default: break;
        }

        // Making sure angle isn't greater than 180, after switch statement.
        if (angle > maxAngle)
            angle = angle - 360;
        
        return angle;
    }
    
    // Getter for body.
    public ArrayList<Segment> body() {
        
        return body;
    }
    
    // Getter for element in body.
    public Segment segment(int index) {
        
        return body.get(index);
    }
    
    // Getter for score.
    public int score() {
        
        return score;
    }
    
    // Resets score to 0.
    public void resetScore() {
        
        score = 0;
    }
    
    // Increments score.
    public void addScore(int value) {
        
        if (score + value < 0)
            score = 0;
        
        else
            score += value;
    }
    
    // Increments health.
    public void addHealth(int value) {
        
        health += value;
    }
    
    // Getter for fitness.
    public int fitness() {
        
        return fitness;
    }
    
    // Calculates fitness and updates genome fitness
    // to that value. Also returns value.
    public int calcFitness() {
 
        fitness = foodEaten * 10 + health;
        genome.fitness = fitness;
        
        return fitness;
    }
    
    // Whether segment is out of grid.
    public boolean outOfBounds() {
        
        return segment(0).outOfBounds();
    }
    
    // Getter for size.
    public int size() {
        
        return size;
    }
    
    // Getter for direction.
    public int direction() {
        
        return direction;
    }
    
    // Getter for genome.
    public Genome genome() {
        
        return genome;
    }
    
    // Getter for wellFed.
    public boolean wellFed() {
        
        return wellFed;
    }
    
    // Setter for wellFed.
    public void setWellFed(boolean fed) {
        
        wellFed = fed;
    }
}