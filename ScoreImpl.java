import java.lang.ClassCastException;
import java.io.Serializable;
import java.lang.Cloneable;

/**
* Represents a Score. A Score has variables playerId (the players ID number) and the 
* corresponding playerScore (the int value of the players score). A Score is Comparable,
* Serializable, and Cloneable.
*
* @author Jo Tomlinson
*/
public class ScoreImpl implements Score, Comparable<Score>, Serializable, Cloneable{
	private static final long serialVersionUID = 1;
	int playerId = 0;
	int playerScore = 0;

/**
* Creates a new ScoreImpl object and sets the playerId and playerScore.
*
* @param playerId the players unique ID number corresponding to the score.
* @param playerScore the players actual overall score.
* 
*/
public ScoreImpl(int playerId, int playerScore){
	this.playerId = playerId;
	this.playerScore = playerScore;
}

/**
* Creates a new ScoreImpl object.
*/
public ScoreImpl(){
		
}

/**
* Gets the players actual overall score.
*
*@return int the players score
*/	
@Override
	public int getPlayerScore(){
		return playerScore;
	}

/**
* Gets the players unique ID number corresponding to a score
*
*@return the players unique ID number
*/
@Override
	public int getPlayerId(){
		return playerId;
	}

/**
* Clones a Score 
*
* @return an Object (which then needs to be downcast into a Score object)
* @throws CloneNotSupportedException if an object cannot be cloned.
*/
@Override
public Object clone() throws CloneNotSupportedException{
	ScoreImpl cloned = (ScoreImpl)super.clone();
	return cloned;
}

/**
* Method to compare Score objects according to the playerScore field.
*
* @param the Score to be compared to this.Score
* @return int 0 if this.Score's playerScore is equal to the @param playerScore
* @return int -1 if this.Score's playerScore is greater than the @param playerScore
* @return int 1 if this.Score's playerScore is less than the @param playerScore
* @throws ClassCastException if you try to sort a list whose elements cannot be compared to one another.
*/
@Override
	public int compareTo(Score score) throws ClassCastException{
		if (!(score instanceof Score)) throw new ClassCastException("A Score object expected.");
		if(this.getPlayerScore() == score.getPlayerScore()){
			return 0;
		}
		else if(this.getPlayerScore() > score.getPlayerScore()){
			return -1;
		}
		else{
			return 1;
		}
	}

}