import java.io.Serializable;

public class PlayerImpl implements Player, Serializable{
	private static final long serialVersionUID = 1;
	String userName = "";
	int playerId = 0;

	public PlayerImpl(String userName){
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
	this.playerId = id;
}

//gets the userName
@Override
public String getUserName(){
	return userName;
}
}