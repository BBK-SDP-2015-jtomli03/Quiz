import java.lang.ClassCastException;
import java.io.Serializable;
import java.lang.Cloneable;

public class ScoreImpl implements Score, Comparable<Score>, Serializable, Cloneable{
	private static final long serialVersionUID = 1;
	int playerId = 0;
	int playerScore = 0;

	public ScoreImpl(int playerId, int playerScore){
		this.playerId = playerId;
		this.playerScore = playerScore;
	}

	public ScoreImpl(){
		
	}

@Override
	public int getPlayerScore(){
		return playerScore;
	}

@Override
	public int getPlayerId(){
		return playerId;
	}

@Override
public Object clone() throws CloneNotSupportedException{
	ScoreImpl cloned = (ScoreImpl)super.clone();
	return cloned;
}


//compares the players scores
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