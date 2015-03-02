void keyPressed()
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



void mouseDragged()
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

void mouseReleased()
{
  if( drawing )
  {
    drawing = false;
  }
}

void mousePressed()
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
