import java.lang.Cloneable;

/**
* An interface to implement scores
*/
public interface Score{
	

/**
* Gets the players score
*
*@return the players score
*/	
public int getPlayerScore();

/**
* Gets the players ID number
*
*@return the players ID number
*/
public int getPlayerId();

/**
* Gets the players ID number
*
*@return the players ID number
*/
Object clone() throws CloneNotSupportedException;

}