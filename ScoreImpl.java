import java.lang.Comparable;
import java.lang.ClassCastException;
import java.io.Serializable;

public class ScoreImpl implements Score, Comparable<Score>, Serializable{
	int playerId = 0;
	int playerScore = 0;

	public ScoreImpl(int playerId, int playerScore){
		this.playerId = playerId;
		this.playerScore = playerScore;
	}

@Override
	public int getPlayerScore(){
		return playerScore;
	}

@Override
	public int getPlayerId(){
		return playerId;
	}


//compares the players scores
@Override
	public int compareTo(Score score) throws ClassCastException{
		if (!(score instanceof Score)) throw new ClassCastException("A Score object expected.");
		if(this.getPlayerScore() == score.getPlayerScore()){
			return 0;
		}
		else if(this.getPlayerScore() < score.getPlayerScore()){
			return -1;
		}
		else{
			return 1;
		}
	}

}