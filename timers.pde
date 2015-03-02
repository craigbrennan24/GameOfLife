void timers()
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
