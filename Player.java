/**
* Implements a player
*/ 
public interface Player{
	
/**
* Returns a unique player ID number
*
* @return int a unique player ID number
*/
int getId();

/**
*Sets the player ID number
*
* @param the id number to be set for this player
*/
void setId(int id);

/**
*Gets the players username
*
* @return the players username
*/
public String getUserName();

}