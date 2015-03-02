void spawnGlider()
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

void spawnLWSS()
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

void spawnPulsar()
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
