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
float secBetweenUpdates = 0.05;
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

void setup()
{
  frameRate(60);
  randomSeed( hour() + second() + millis() );
  size( 1024, 600 );
  rectMode(CENTER);
  Board = new lifeBoard();
  numCells = Board.boardWidth*Board.boardHeight;
}

void draw()
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

void pause()
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


