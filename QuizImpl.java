import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.rmi.RemoteException;


public class QuizImpl implements Quiz, Serializable{
	private int id = 0;
	private String quizName = ""; 
	private List<Question> questions = new ArrayList<Question>();
	private List<Score> scores = new ArrayList<Score>();


public QuizImpl(String quizName) throws RemoteException{
	this.quizName = quizName;
}


//Returns the highest score
@Override
public Score getHighScore(){
	Score maxScore = null;
	int max = 0;
	if (scores.isEmpty()){
		return maxScore;
	}
	else{
		for(Score score : scores){
			if(score.getPlayerScore() > max){
				maxScore = score;
				max = score.getPlayerScore();
			}
		}
	return maxScore;
	}
}

//Gets the quiz ID number
@Override
public int getId(){
	return this.id;
}

//Sets the quiz ID number
@Override
public void setId(int id){
	this.id = id;
}

//add question
@Override
public void addQuestion(Question question){
	questions.add(question);
}




}