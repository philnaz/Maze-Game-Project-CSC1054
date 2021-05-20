// Maze Game Project - Philip Nasralla
import java.lang.*;
import java.util.*;
import java.io.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.Scene.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;


 public class MazeGameProject extends Application 
 {
   //ArrayList to hold maze
   ArrayList<ArrayList<Integer>> userPos = new ArrayList<ArrayList<Integer>>();
      
   FlowPane root;
   GameCanvas gameCanvas;
   GraphicsContext gc;
     
   public int xPos;
   public int yPos;
  
   public void start(Stage primaryStage) 
   {   
      gameCanvas = new GameCanvas();
      gameCanvas.draw();
      gameCanvas.initializeBlock();
      
      root = new FlowPane();
      
      //Adds gameCanvas to root flowpane
      root.getChildren().add(gameCanvas);   
   
      //creating scene and setting window size 
      Scene scene = new Scene(root,525,525);   
      primaryStage.setScene(scene); 
      primaryStage.setTitle("Complete the Maze!");
      primaryStage.show();
      
      //Listener for when key is pressed    
      gameCanvas.setOnKeyPressed(new KeyListener()); 
      
      //Request focus after initializing event-->program looks to click event.
      gameCanvas.requestFocus(); 
      
   }
   
   public class GameCanvas extends Canvas 
   {
   
      GraphicsContext gc = getGraphicsContext2D();
      
      public GameCanvas() 
      {  //maze size
         setWidth(525); 
         setHeight(525);
      }
         
      public void initializeBlock() 
      {
      
         // Starting point of maze based on 0 in top row of array
         for (int j=0; j<21; j++) 
         {
            if (userPos.get(0).get(j) == 0) 
            { 
               yPos = 0;
               xPos = j*25;
            }
         }
            
         gc.setFill(Color.CYAN); //Color of user square
         gc.fillRect(xPos, yPos, 25, 25);
      }
         
      public void draw() 
      {
      
         try 
         {
            // Scanning txt file to create maze
            Scanner scan = new Scanner(new File("maze.txt"));
            
            //initializing inner 2d ArrayList
            for (int i=0; i<21; i++) 
            { 
               ArrayList<Integer> innerList = new ArrayList<Integer>();
               userPos.add(innerList);
            }
            
            //reading the values of arraylist by row - hasNextInt prevents reading past file
            while (scan.hasNextInt()) 
            { 
               for (int i=0; i<21; i++) 
               {
                  for (int j=0; j<21; j++) 
                  {
                     userPos.get(i).add(scan.nextInt());
                  }
               }
            }
            
            //Drawing the maze   
            for (int i=0; i<21; i++) 
            { 
               for (int j=0; j<21; j++) 
               {
                  if (userPos.get(i).get(j) == 1) 
                  {
                     gc.setFill(Color.BLACK);
                     gc.fillRect(j*25,i*25,25,25);
                  }
               }
             }
          }
         
         // catch if maze.txt not found
         catch (FileNotFoundException fnf)  
            {
            System.out.println("File not found.");
            }

        }
    }
   
      
   //KeyListener controls animation timer - reacts to keys pressed.
   public class KeyListener implements EventHandler<KeyEvent> 
   {   
      //Handles block moving 
      public void handle(KeyEvent moveEvent) 
      {  
      
      gc = gameCanvas.getGraphicsContext2D();
      gc.clearRect(0,0,525,525);
      gameCanvas.draw();
      gc.setFill(Color.CYAN);
      
      // will redraw rectangle in new position based on action
      if (moveEvent.getCode().equals((KeyCode.DOWN)))
      {
         if (yPos<500 && userPos.get((yPos/25) + 1).get(xPos/25) != 1) 
            {
               yPos += 25;   
               gc.fillRect(xPos, yPos, 25, 25); 
            }
            else 
            {
               gc.fillRect(xPos, yPos, 25, 25);
            }
      }
      
      else if (moveEvent.getCode().equals((KeyCode.RIGHT)))
      {
         if (xPos<500 && userPos.get(yPos/25).get(xPos/25 + 1) != 1) 
               {  
                  xPos += 25;   
                  gc.fillRect(xPos, yPos, 25, 25);
               }
               else 
               {
                  gc.fillRect(xPos, yPos, 25, 25);
               }
      }
      
      else if (moveEvent.getCode().equals((KeyCode.LEFT)))
      {
         if (xPos>0 && userPos.get(yPos/25).get(xPos/25 - 1) != 1) 
            {    
               xPos -= 25;   
               gc.fillRect(xPos, yPos, 25, 25);
            }
            else 
            {
                gc.fillRect(xPos, yPos, 25, 25);
            }
      }
      
      else if (moveEvent.getCode().equals((KeyCode.UP)))
      {
         if (yPos>0 && userPos.get(yPos/25 - 1).get(xPos/25) != 1) 
            { 
               yPos -= 25;   
               gc.fillRect(xPos, yPos, 25, 25);
            }
            else 
            {
               gc.fillRect(xPos, yPos, 25, 25);
            }
      }

               // Notifies when maze is complete
         for (int i=0; i<21; i++) 
         {
            if (userPos.get(20).get(i) == 0) 
            {
               if (xPos == (i*25) && yPos == 500) 
               {
                  System.out.println("You Win!");
                  //Exits program when maze is complete
                  System.exit(0);
               }
            }
         }
         
      }   
   }
    // Launches Program    
   public static void main(String []args) 
   {
      launch(args);
   }           
}