void drawPauseMessage()
{
  PVector pos = new PVector( width/2, 100 );
  color c = color(100, 100, 100, 170);
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

void drawHelpScreen()
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
