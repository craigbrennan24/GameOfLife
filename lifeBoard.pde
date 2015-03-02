class lifeBoard
{
  boolean[][] current;
  boolean[][] next;
  //IMPORTANT:
  // i,j is the same as the cartesian: x,y
  // but when in an array use j,i
  int boardWidth, boardHeight;
  color alive, dead;
  
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
  
  void on(int x, int y)
  {
    current[x][y] = true;
  }
  
  void off(int x, int y)
  {
    current[x][y] = false;
  }
  
  void toggle( int x, int y )
  {
    current[x][y] = !current[x][y];
  }
  
  boolean isOn(int x, int y)
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
  
  boolean[][] clearBoard( boolean[][] board )
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
  
  void randomize()
  {
    for( int i = 0; i < boardWidth; i++ )
    {
      for( int j = 0; j < boardHeight; j++ )
      {
        int k = int(random(0, 100));
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
  
  int countLiveProxCells(int x, int y)
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
  
  void draw()
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
  
  void update()
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
  
  boolean determineLife( int proxCells, int x, int y )
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
