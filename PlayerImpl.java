import java.io.Serializable;

/**
* Represents a Player of a game.
*
* @author Jo Tomlinson
*/
public class PlayerImpl implements Player, Serializable{
	private static final long serialVersionUID = 1;
	String userName = "";
	int playerId = 0;

/**
* Creates a new PlayerImpl object
*
* @param userName the players username
*/
public PlayerImpl(String userName){
	this.userName = userName;
}

/**
* Returns a unique player ID number
*
* @return the players unique ID number
*/
@Override
public int getId(){
	return this.playerId;
}

/**
*Sets the player ID number
*
* @param id the unique id number to be set for this player
*/
@Override
public void setId(int id){
	this.playerId = id;
}

/**
*Gets the players userName
*
* @return the players username
*/
@Override
public String getUserName(){
	return userName;
}

}