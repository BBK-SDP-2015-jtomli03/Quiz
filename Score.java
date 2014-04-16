import java.lang.Cloneable;

/**
* An interface to implement a Score. A Score object has a players unique ID number and the corresponding players score.
*/
public interface Score{
	

/**
* Gets the players actual overall score.
*
*@return int the players score
*/
public int getPlayerScore();

/**
* Gets the players unique ID number corresponding to a score
*
*@return the players unique ID number
*/
public int getPlayerId();

/**
* Clones a Score 
*
* @return an Object (which then needs to be downcast into a Score object)
* @throws CloneNotSupportedException if an object cannot be cloned.
*/
Object clone() throws CloneNotSupportedException;

}