public class ScoreImpl implements Score{
	
	int playerId = 0;
	int score = 0;

	public ScoreImpl(int playerId, int score){
		this.playerId = playerId;
		this.score = score;
	}
}