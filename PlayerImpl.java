public class PlayerImpl implements Player{
	String userName = "";
	int playerId = 0;

	public Player(String userName){
		this.userName = userName;
	}

//Gets the player ID number
@Override
public int getId(){
	return this.playerId;
}

//Sets the player ID number
@Override
public void setId(int id){
	this.id = playerId;
}

//gets the userName
@Override
public String getUserName(){
	return userName;
}
}