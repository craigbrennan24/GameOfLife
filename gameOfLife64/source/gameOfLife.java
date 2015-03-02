import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class gameOfLife extends PApplet {

//globals
int cellSize = 5;
boolean paused = false;
boolean pauseFlag = true;
int lastPaused, lastUpdated;
int randomPercent = 50;
boolean allowUpdate;
boolean helpScreen = false;
boolean helpScreenFlag = true;
int lastHelpScreen;
float secBetweenUpdates = 0.05f;
int generation = 1;
boolean showBorders = false;
boolean showBordersFlag = true;
int lastShowBorders;
boolean randomizeCells = false;
boolean randomizeCellsFlag = true;
int lastRandomize;
boolean drawing = false;
lifeBoard Board;
int numCells;

public void setup()
{
  frameRate(60);
  randomSeed( hour() + second() + millis() );
  size( 1024, 600 );
  rectMode(CENTER);
  Board = new lifeBoard();
  numCells = Board.boardWidth*Board.boardHeight;
}

public void draw()
{
  if( showBorders == true )
  {
    stroke(0);
    strokeWeight(0);
  }
  else
  {
    noStroke();
  }
  if( randomizeCells == true )
  {
    Board.randomize();
    randomizeCells = false;
    generation = 1;
  }
  frame.setTitle("Game of Life - " + numCells + " cells (" + Board.boardWidth + "x" + Board.boardHeight + ")" + " - Generation: " + generation + " - Press H for help!");
  if( !helpScreen )
  {
    Board.draw();
    if( allowUpdate == true )
    {
      Board.update();
    }
    if( paused )
    {
      drawPauseMessage();
    }
  }
  else
  {
    drawHelpScreen();
  }
  timers();
}

public void pause()
{
  if( pauseFlag == true )
  {
    lastPaused = millis();
    if( paused == true)
    {
      paused = false;
    }
    else
    {
      paused = true;
    }
    pauseFlag = false;
  }
}


public void drawPauseMessage()
{
  PVector pos = new PVector( width/2, 100 );
  int c = color(100, 100, 100, 170);
  pushMatrix();
  translate( pos.x, pos.y );
  fill(c);
  rect( 0, 3, 100, 25, 10 );
  fill( 255, 170 );
  textAlign(CENTER, CENTER);
  textSize(25);
  text( "PAUSED", 0, 0);
  popMatrix();
}

public void drawHelpScreen()
{
  background(255);
  textSize( 30 );
  textAlign( CENTER, CENTER);
  fill(0);
  text( "John Conway's Game of Life", width/2, 25 );
  textSize(20);
  text("Controls:", width/2, 75 );
  text("Leftclick - toggle cell on/off", width/2, 100 );
  text("Leftclick drag - draw on board", width/2, 125 );
  text("Rightclick - Pause", width/2, 150 );
  text("Up/Down - Change game speed(NOT IMPLEMENTED)", width/2, 175 );
  text("B - Show/Hide cell borders", width/2, 200 );
  text("R - randomize all cells", width/2, 225 );
  text("C - clear board", width/2, 250 );
  text("Press H to return!", width/2, 275 );
}
public void keyPressed()
{
  if( key == 'h' || key == 'H' )
  {
    if( helpScreenFlag == true )
    {
      helpScreen = !helpScreen;
      lastHelpScreen = millis();
      helpScreenFlag = false;
    }
  }
  if( key == '1' )
  {
    spawnGlider();
  }
  if( key == '2' )
  {
    spawnLWSS();
  }
  if( key == '3' )
  {
    spawnPulsar();
  }
  if( !helpScreen )
  {
    if( key == ' ' || key == 'p' || key == 'P' )
    {
      pause();
    }
    if( key == 'b' || key == 'B' )
    {
      if( showBordersFlag == true )
      {
        showBorders = !showBorders;
        lastShowBorders = millis();
        showBordersFlag = false;
      }
    }
    if( key == 'r' || key == 'R' )
    {
      if( randomizeCellsFlag == true )
      {
        randomizeCells = !randomizeCells;
        lastRandomize = millis();
        randomizeCellsFlag = false;
      }
    }
    if( key == 'c' || key == 'C' )
    {
      Board.current = Board.clearBoard(Board.current);
      Board.next = Board.clearBoard(Board.next);
      generation = 1;
    }
  }
}



public void mouseDragged()
{
  if( drawing )
  {
    int x = mouseX/cellSize;
    int y = mouseY/cellSize;
    if( x <= Board.boardWidth && x >= 0 )
    {
      if( y <= Board.boardHeight && y >= 0 )
      {
        Board.on( x, y);
      }
    }
  }
}

public void mouseReleased()
{
  if( drawing )
  {
    drawing = false;
  }
}

public void mousePressed()
{
  if( mouseButton == LEFT )
  {
    int x = mouseX/cellSize;
    int y = mouseY/cellSize;
    if( x <= Board.boardWidth && x >= 0 )
    {
      if( y <= Board.boardHeight && y >= 0 )
      {
        Board.toggle( x, y );
        drawing = true;
      }
    }
  }
  else if( mouseButton == RIGHT )
  {
    pause();
  }
}
class lifeBoard
{
  boolean[][] current;
  boolean[][] next;
  //IMPORTANT:
  // i,j is the same as the cartesian: x,y
  // but when in an array use j,i
  int boardWidth, boardHeight;
  int alive, dead;
  
  lifeBoard()
  {
    boardWidth = width/cellSize;
    boardHeight = height/cellSize;
    current = new boolean[boardWidth][boardHeight];
    next = new boolean[boardWidth][boardHeight];
    alive = color(60, 155, 45);
    dead = color(255);
    randomize();
  }
  
  public void on(int x, int y)
  {
    current[x][y] = true;
  }
  
  public void off(int x, int y)
  {
    current[x][y] = false;
  }
  
  public void toggle( int x, int y )
  {
    current[x][y] = !current[x][y];
  }
  
  public boolean isOn(int x, int y)
  {
    if( current[x][y] == true )
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public boolean[][] clearBoard( boolean[][] board )
  {
    for( int i = 0; i < boardWidth; i++ )
    {
      for( int j = 0; j < boardHeight; j++ )
      {
        board[i][j] = false;
      }
    }
    return board;
  }
  
  public void randomize()
  {
    for( int i = 0; i < boardWidth; i++ )
    {
      for( int j = 0; j < boardHeight; j++ )
      {
        int k = PApplet.parseInt(random(0, 100));
        if( k <= randomPercent )
        {
          on(i, j);
        }
        else
        {
          off(i, j);
        }
      }
    }
  }
  
  public int countLiveProxCells(int x, int y)
  {
    int proximityCells = 0;
    int minX, maxX, minY, maxY;
    minX = -1;
    maxX = 2;
    minY = -1;
    maxY = 2;
    if( x == 0 )
    {
      minX = 0;
    }
    if( y == 0 )
    {
      minY = 0;
    }
    if( x == (boardWidth-1) )
    {
      maxX = 1;
    }
    if( y == (boardHeight-1) )
    {
      maxY = 1;
    }
    //Searches column by column
    for( int i = minX; i < maxX; i++ )
    {
      for( int j = minY; j < maxY; j++ )
      {
        if( isOn( (x+i), (y+j) ) )
        {
          if( !( i == 0 && j == 0 ) )
          {
            proximityCells++;
          }
        }
      }
    }
    return proximityCells;
  }
  
  public void draw()
  {
    float x, y;
    for( int i = 0; i < boardWidth; i++ )
    {
      for( int j = 0; j < boardHeight; j++ )
      {
        if( isOn( i, j ) )
        {
          fill(alive);
        }
        else
        {
          fill(dead);
        }
        x = (cellSize/2)+((cellSize)*i);
        y = (cellSize/2)+((cellSize)*j);
        rect( x, y, cellSize, cellSize );
      }
    }
  }
  
  public void update()
  {
    if( paused == false )
    {
      next = clearBoard(next);
      //Apply rules from current to next
      for( int i = 0; i < boardWidth; i++ )
      {
        for( int j = 0; j < boardHeight; j++ )
        {
          next[i][j] = determineLife( countLiveProxCells( i, j ), i, j );
        }
      }
      for( int i = 0; i < boardWidth; i++ )
      {
        for( int j = 0; j < boardHeight; j++ )
        {
          current[i][j] = next[i][j];
        }
      }
      generation++;
      allowUpdate = false;
      lastUpdated = millis();
    }
  }
  
  public boolean determineLife( int proxCells, int x, int y )
  {
    boolean live = false;
    if( proxCells < 2 )
    {
      live = false;
    }
    else if( proxCells == 2 )
    {
      if( isOn( x, y ) )
      {
        live = true;
      }
      else
      {
        live = false;
      }
    }
    else if( proxCells == 3 )
    {
      live = true;
    }
    else if( proxCells > 3 )
    {
      live = false;
    }
    return live;
  }
  
}
public void spawnGlider()
{
  int x = mouseX/cellSize;
  int y = mouseY/cellSize;
  if( x >= 1 && x <= ( Board.boardWidth - 1 ) )
  {
    if( y >= 1 && y <= ( Board.boardHeight - 1 ) )
    {
      //clear 3x3 area around mouse position
      for( int i = -1; i <= 1; i++ )
      {
        for( int j = -1; j <= 1; j++ )
        {
          Board.off( ( x + i ), ( y + j ) );
        }
      }
      //Spawn
      Board.on( ( x - 1 ), ( y - 1 ) );
      Board.on( x, y );
      Board.on( ( x + 1 ), y );
      Board.on ( ( x - 1), ( y + 1 ) );
      Board.on( x, ( y + 1 ) );
    }
  }
}

public void spawnLWSS()
{
  int x = mouseX/cellSize;
  int y = mouseY/cellSize;
  if( x >= 2 && x <= ( Board.boardWidth - 2 ) )
  {
    if( y >= 1 && y <= ( Board.boardHeight - 2 ) )
    {
      //clear 3x3 area around mouse position
      for( int i = -2; i <= 2; i++ )
      {
        for( int j = -1; j <= 2; j++ )
        {
          Board.off( ( x + i ), ( y + j ) );
        }
      }
      //Spawn
      Board.on( ( x - 2 ), ( y - 1 ) );
      Board.on( ( x + 1 ), ( y - 1 ) );
      Board.on( ( x + 2 ), y );
      Board.on ( ( x - 2 ), ( y + 1 ) );
      Board.on( ( x + 2 ), ( y + 1 ) );
      Board.on( ( x - 1 ), ( y + 2 ) );
      Board.on( x, ( y + 2 ) );
      Board.on( ( x + 1 ), ( y + 2 ) );
      Board.on( ( x + 2 ), ( y + 2 ) );
    }
  }
}

public void spawnPulsar()
{
  int x = mouseX/cellSize;
  int y = mouseY/cellSize;
  if( x >= 6 && x <= ( Board.boardWidth - 6 ) )
  {
    if( y >= 6 && y <= ( Board.boardHeight - 6 ) )
    {
      //clear 3x3 area around mouse position
      for( int i = -6; i <= 6; i++ )
      {
        for( int j = -6; j <= 6; j++ )
        {
          Board.off( ( x + i ), ( y + j ) );
        }
      }
      //Spawn
      //top side
      Board.on( ( x - 4 ), ( y - 6 ) );
      Board.on( ( x - 3 ), ( y - 6 ) );
      Board.on( ( x - 2 ), ( y - 6 ) );
      Board.on ( ( x + 2 ), ( y - 6 ) );
      Board.on( ( x + 3 ), ( y - 6 ) );
      Board.on( ( x + 4 ), ( y - 6 ) );
      Board.on( ( x - 6 ), ( y - 4 ) );
      Board.on( ( x - 1 ), ( y - 4 ) );
      Board.on( ( x + 1 ), ( y - 4 ) );
      Board.on( ( x + 6 ), ( y - 4 ) );
      Board.on( ( x - 6 ), ( y - 3 ) );
      Board.on( ( x - 1 ), ( y - 3 ) );
      Board.on( ( x + 1 ), ( y - 3 ) );
      Board.on( ( x + 6 ), ( y - 3 ) );
      Board.on( ( x - 6 ), ( y - 2 ) );
      Board.on( ( x - 1 ), ( y - 2 ) );
      Board.on( ( x + 1 ), ( y - 2 ) );
      Board.on( ( x + 6 ), ( y - 2 ) );
      Board.on( ( x - 4 ), ( y - 1 ) );
      Board.on( ( x - 3 ), ( y - 1 ) );
      Board.on( ( x - 2 ), ( y - 1 ) );
      Board.on( ( x + 4 ), ( y - 1 ) );
      Board.on( ( x + 3 ), ( y - 1 ) );
      Board.on( ( x + 2 ), ( y - 1 ) );
      //bot side
      Board.on( ( x - 4 ), ( y + 6 ) );
      Board.on( ( x - 3 ), ( y + 6 ) );
      Board.on( ( x - 2 ), ( y + 6 ) );
      Board.on ( ( x + 2 ), ( y + 6 ) );
      Board.on( ( x + 3 ), ( y + 6 ) );
      Board.on( ( x + 4 ), ( y + 6 ) );
      Board.on( ( x - 6 ), ( y + 4 ) );
      Board.on( ( x - 1 ), ( y + 4 ) );
      Board.on( ( x + 1 ), ( y + 4 ) );
      Board.on( ( x + 6 ), ( y + 4 ) );
      Board.on( ( x - 6 ), ( y + 3 ) );
      Board.on( ( x - 1 ), ( y + 3 ) );
      Board.on( ( x + 1 ), ( y + 3 ) );
      Board.on( ( x + 6 ), ( y + 3 ) );
      Board.on( ( x - 6 ), ( y + 2 ) );
      Board.on( ( x - 1 ), ( y + 2 ) );
      Board.on( ( x + 1 ), ( y + 2 ) );
      Board.on( ( x + 6 ), ( y + 2 ) );
      Board.on( ( x - 4 ), ( y + 1 ) );
      Board.on( ( x - 3 ), ( y + 1 ) );
      Board.on( ( x - 2 ), ( y + 1 ) );
      Board.on( ( x + 4 ), ( y + 1 ) );
      Board.on( ( x + 3 ), ( y + 1 ) );
      Board.on( ( x + 2 ), ( y + 1 ) );
    }
  }
}
public void timers()
{
  if( pauseFlag == false && (millis() - lastPaused) >= 100 )
  {
    pauseFlag = true;
  }
  if( allowUpdate == false && (millis() - lastUpdated) >= secBetweenUpdates*1000 )
  {
    allowUpdate = true;
  }
  if( helpScreenFlag == false && (millis() - lastHelpScreen) >= 250 )
  {
    helpScreenFlag = true;
  }
  if( showBordersFlag == false && (millis() - lastShowBorders) >= 250 )
  {
    showBordersFlag = true;
  }
  if( randomizeCellsFlag == false && (millis() - lastRandomize) >= 300 )
  {
    randomizeCellsFlag = true;
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "gameOfLife" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
