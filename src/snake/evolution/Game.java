/*
// Author: Justin Sadakhom
// Date: June 11, 2019
// File: Game.java
*/

package snake.evolution;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.robot.Robot;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.*;

/*
// From NEAT package.
// IMPORTANT: Credit for NEAT code goes to "hydrozoa".
// Code retrieved from https://github.com/hydrozoa-yt/hydroneat
*/
import neat.ConnectionGene;
import neat.Counter;
import neat.Evaluator;
import neat.GenesisGenomeProvider;
import neat.Genome;
import neat.NEATConfiguration;
import neat.NeuralNetwork;
import neat.NodeGene;
import neat.NodeGene.TYPE;

/*
// This class loads initial components of "Snake".
// It also invokes methods from the Manager class
// to update game components, when necessary.
*/
public class Game {

    /* FIELDS */
    
    // No. of squares on side of grid.
    static final int GRID_LENGTH = 8;
    
    // No. of squares in entire grid.
    static final int GRID_AREA = (int)Math.pow(GRID_LENGTH, 2);
    
    // Dimensions of program window.
    private final int WIDTH = (GRID_LENGTH * Entity.SIDE_LENGTH) - 1; // looks weird in debug mode without -1
    private final int HEIGHT = GRID_LENGTH * Entity.SIDE_LENGTH;
    
    // Whether game is paused.
    private boolean paused = false;
    
    // Whether game is being played by a human.
    private boolean humanPlayer = false; // true to play manually
    
    // No. of the snake in the generation.
    private int snakeCount = 0;
    
    // Factor that game speed is increased by.
    private int speedMult = 2; // can change; default 1
    
    // Delay, in milliseconds, before Snake moves.
    private int MOVE_DELAY = 132 / speedMult;
    
    // Game components.
    private Node[] grid;
    private Snake player;
    private Node apple;
    
    // Application objects.
    private Scene scene;
    private Group group;
    
    // Timers.
    private AnimationTimer moveTimer; // animation delay
    private AnimationTimer starveTimer; // starvation delay
    
    // Neural network objects.
    private Evaluator evaluator;
    private NeuralNetwork net;
    private float[] input = new float[6];
    private float[] output = new float[3];
    
    // Objects used to generate random values.
    private Random r = new Random();
    
    /* CONSTRUCTORS */
    
    // Default constructor.
    public Game() {
        
        Robot robot = new Robot();
        
        // Creates game components.
        loadGenetics();
        loadGrid();
        loadSnake();
        loadApple();
        
        // Adds game components to group.
        group = new Group(grid);
        group.getChildren().addAll(player.body());
        group.getChildren().add(apple);
        
        // Initializes scene.
        scene = new Scene(group, WIDTH, HEIGHT);
        
        // Activates feature based on which key was pressed.
        scene.setOnKeyPressed((KeyEvent event) -> {
            
            switch(event.getCode()) {
                
                // If "R", reset game.
                case R:
                    reset();
                    break;
                
                // If "P", pause game.
                case P:
                    paused = !paused;
                    timersOn(!paused);
                    break;
                    
                default:
                    break;
            }
            
            // Gets input for Snake movement.
            Manager.getInput(event, player);
        });
        
        // Clock given delay of MOVE_DELAY milliseconds.
        moveTimer = new Clock(MOVE_DELAY) {
            
            @Override
            public void handle() {
                
                /*
                // Invokes move() method, moving the snake.
                // If move() returns false, game resets.
                */
                if (!Manager.move(player, (Apple)apple, grid))
                    reset();
                
                // Otherwise, game proceeds as normal.
                else {
                    
                    if (!humanPlayer) {
                        
                        // Generates input[] array.
                        input = Manager.processInput(grid, player, (Apple)apple);

                        // Generates output[] array using input[].
                        output = net.calculate(input);

                        // Switch case that analyzes index of largest value in output[].
                        switch(Genetics.largest(output)) {

                            /*
                            // If first index has largest element,
                            // Robot presses left arrow key.
                            */
                            case 1:
                                robot.keyPress(LEFT); 
                                break;

                            /*
                            // If second index has largest element,
                            // Robot presses right arrow key.
                            */
                            case 2:
                                robot.keyPress(RIGHT);
                                break;

                            default:
                                break;
                        }
                    }
                    
                    // If Snake moves closer toward Apple, increment score by 1.
                    if (player.segment(0).getPos().closer(player.segment(0).lastPos(), ((Apple)apple).getPos()))
                        player.addScore(1);
                    
                    // Otherwise, Snake is moving away so decrement score by 2.
                    else
                        player.addScore(-2);
                    
                    // If Snake eats Apple...
                    if (player.onApple((Apple)apple)) {
                        
                        // Increment Snake health based on its score.
                        player.addHealth(player.score());
                        
                        player.resetScore();
                        player.grow();
                        
                        // Updates game grid.
                        grid = Manager.updateGrid(grid, player, (Apple)apple);

                        // If Snake isn't max length...
                        if (player.size() != GRID_AREA) {

                            // Updates previous and current position of apple.
                            ((Apple)apple).setLastPos(((Apple)apple).getPos());
                            ((Apple)apple).setPos(getAppleSpawn());

                            // Snake is removed and added back to root to show updated body.
                            group.getChildren().removeAll(player.body());
                            group.getChildren().addAll(player.body());
                        }

                        // Otheriwise, Snake reaches max length...
                        else {
                            
                            // Apple is removed from game as there's no space to spawn it.
                            group.getChildren().remove(apple);

                            // Snake is removed and added back to root to show updated body.
                            group.getChildren().removeAll(player.body());
                            group.getChildren().addAll(player.body());
                            
                            reset();
                        }
                    }
                }
                
                // Updates grid with new occupied Tiles.
                grid = Manager.updateGrid(grid, player, (Apple)apple);
                
                // Lock no longer active as all processing has finished.
                Manager.toggleLock(false);
            }
        };
        
        // Clock given delay of 5280 / multiplier milliseconds.
        starveTimer = new Clock(5280 / speedMult) {
            
            @Override
            public void handle() {
                
                // If Snake hasn't eaten, game resets.
                if (!player.wellFed())
                    reset();
                
                // Sets Snake to hungry.
                player.setWellFed(false);
            }
        };
        
        // Starts timer after initialization.
        timersOn(true);
        paused = false;
    }
    
    /* METHODS */
    
    // Creates the game grid.
    private void loadGrid() {
        
        grid = new Tile[GRID_AREA];
        
        /*
        // For every index in grid[], initializes
        // and adds a Tile with coordinates (i,j).
        */
        for (int i = 0; i < GRID_LENGTH; i++)
            for (int j = 0; j < GRID_LENGTH; j++)
                grid[i * Game.GRID_LENGTH + j] = new Tile(new Coordinate(i, j));
    }
    
    // Spawns the Snake.
    private void loadSnake() {
        
        // Initializes player.
        player = Genetics.generation.get(0);
        
        // Initializes neural network.
        net = new NeuralNetwork(player.genome());
        
        // Sets the tiles the player is on to occupied.
        for (int i = 0; i < 2; i++)
            ((Tile)grid[player.body().get(i).getPos().toTile()]).setOccupied(true);
        
        // Increments Snake count to keep track of an individual.
        snakeCount += 1;
        System.out.print("#" + snakeCount);
    }
    
    // Spawns the Apple.
    private void loadApple() {
        
        // Initializes apple, with random coordinate as parameter.
        apple = new Apple(getAppleSpawn());
        
        // Sets the tile the apple is on to occupied.
        ((Tile)grid[((Apple)apple).getPos().toTile()]).setOccupied(true);
    }
    
    /*
    // @author hydrozoa
    //
    // Loads the genetics components required to
    // use NEAT algorithm and neural network.
    //
    // Besides tweaks and my comments, code for
    // this method is attributed to "hydrozoa".
    */
    private void loadGenetics() {
        
        // Counts how many node innovations are in a genome.
        Counter nodeInn = new Counter();
        
        // Counts how many connection innovations are in a genome.
        Counter connInn = new Counter();
        
        // Initializes genome.
        Genome genome = new Genome();
        
        // Adds 6 nodes for use in the input layer.
        for (int i = 0; i < 6; i++)
            genome.addNodeGene(new NodeGene(TYPE.INPUT, nodeInn.getInnovation()));
        
        // Adds 3 nodes for use in the output layer.
        for (int j = 0; j < 3; j++)
            genome.addNodeGene(new NodeGene(TYPE.OUTPUT, nodeInn.getInnovation()));
        
        // Adds connections for every unique combination of input + output node.
        for (int i = 0; i < 6; i++)
            for (int j = 6; j < 9; j++)
                genome.addConnectionGene(new ConnectionGene(i, j, 1f, true, connInn.getInnovation()));
        
        // Object that generates the random genomes for first generation of Snakes.
        GenesisGenomeProvider provider = () -> {
            
            // Creates copy of "genome".
            Genome g = new Genome(genome);
            
            // Sets weight of each connection to a random value.
            g.getConnectionGenes().values().forEach((connection) -> {
                connection.setWeight((float)r.nextGaussian());
            });
            
            // Returns genome.
            return g;
        };
        
        // Generates NEAT algorithm that uses GEN_SIZE
        // as the number of Snakes in each generation.
        NEATConfiguration conf = new NEATConfiguration(Genetics.GEN_SIZE);
        
        // Evaluator object that evaluates the performance
        // of each Snake and generation of Snakes.
        evaluator = new Evaluator(conf, provider, nodeInn, connInn) {
            
            @Override
            public float evaluateGenome(Genome g) {
                
                // Returns g.fitness as its evaluation of fitness.
                return g.fitness;
            }
        };
        
        // Initializes arrayList used to store Snakes.
        Genetics.generation = new ArrayList<>();
        
        // Adds Snake with corresponding genome to the arrayList.
        for (int i = 0; i < Genetics.GEN_SIZE; i++)
            Genetics.generation.add(new Snake(evaluator.genomes.get(i)));
    }
    
    /*
    // Generates a random Coordinate for an
    // Apple object. Always ensures that the
    // Coordinate is on a non-occupied Tile.
    //
    // @return Coordinate of next Apple.
    */
    private Coordinate getAppleSpawn() {
        
        ArrayList<Tile> freeTiles = new ArrayList<>();
        
        for (int i = 0; i < GRID_AREA; i++)
            freeTiles.add((Tile)grid[i]);
        
        for (int i = 0; i < freeTiles.size(); i++) {
            
            if (freeTiles.get(i).occupied) {
                
                freeTiles.remove(i);
                i -= 1;
            }
        }

        Tile randomTile = new Tile(freeTiles.get((int)Math.floor(Math.random() * freeTiles.size())));
        
        return new Coordinate(randomTile.x(), randomTile.y());
    }
    
    // Resets the game to prepare for a new Snake.
    private void reset() {
        
        // Transfers current score of Snake into health.
        player.addHealth(player.score());
        
        // Calculates final fitness of Snake.
        player.calcFitness();
        System.out.print(" Fitness: " + player.fitness() + "\n");
        
        // Turns timers off.
        timersOn(false);
        
        // Loads new grid.
        loadGrid();
        
        // Removes game components.
        group.getChildren().removeAll(player.body());
        group.getChildren().remove(apple);
        
        // Adds current Snake to "dead" arrayList.
        Genetics.dead.add(Genetics.generation.get(0));
        
        // Removes current Snake from "generation" arrayList.
        Genetics.generation.remove(0);
        
        // If the current generation has all died...
        if (Genetics.generation.isEmpty()) {
            
            snakeCount = 0;
            
            // Sorts dead Snakes using Insertion sort.
            Genetics.dead = Genetics.sort(Genetics.dead);
            
            // Evaluates generation, scoring each with a fitness
            // and generating a new genome based on the most fit
            // Snakes.
            evaluator.evaluateGeneration(r);
            
            // Adds a Snake with their corresponding genome.
            for (int i = 0; i < Genetics.GEN_SIZE; i++)
                Genetics.generation.add(new Snake(evaluator.genomes.get(i)));
            
            // Clears dead Snake arrayList, using recursion.
            Genetics.dead = Genetics.clean(Genetics.dead);
        }
        
        // Spawns game components.
        loadSnake();
        loadApple();
        
        // Adds game components back to program.
        group.getChildren().addAll(player.body());
        group.getChildren().add(apple);
        
        // Timers turn back on again.
        timersOn(true);
    }
    
    /*
    // Turns timers on / off.
    //
    // @param on - State timers will be set to.
    */
    private void timersOn(boolean on) {
        
        if (on) {
            
            moveTimer.start();
            starveTimer.start();
        }
        
        else {
            
            moveTimer.stop();
            starveTimer.stop();
        }
    }
    
    // Getter for scene.
    public Scene getScene() {
        
        return scene;
    }
}