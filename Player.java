/**
* A Player is someone who wants to play a game. A player has a userName and a unique player ID.
*/ 
public interface Player{
	
/**
* Returns a unique player ID number
*
* @return the players unique ID number
*/
int getId();

/**
*Sets the player ID number
*
* @param id the unique id number to be set for this player
*/
void setId(int id);

/**
*Gets the players userName
*
* @return the players username
*/
public String getUserName();

}