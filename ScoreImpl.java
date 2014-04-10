public class ScoreImpl implements Score{
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

}