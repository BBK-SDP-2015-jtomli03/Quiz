import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.rmi.RemoteException;
import java.lang.ClassCastException;
import java.lang.UnsupportedOperationException;
import java.util.concurrent.CopyOnWriteArrayList;



public class QuizImpl implements Quiz, Serializable{
	private static final long serialVersionUID = 1;
	private int id = 0;
	private String quizName = ""; 
	private int numOfQuestions = 0;
	private List<Question> questions = new ArrayList<Question>();
	private List<Score> scores = new ArrayList<Score>();


public QuizImpl(String quizName) throws RemoteException{
	this.quizName = quizName;
}

@Override
public List<ScoreImpl> getHighScore(){
	List<ScoreImpl> topScores = new CopyOnWriteArrayList<ScoreImpl>();
	if(!scores.isEmpty()){
		List<ScoreImpl> sortedScores = getOrderedScores(); 
		int maxScore = sortedScores.get(0).getPlayerScore();
		for(ScoreImpl score : sortedScores){
			if(score.getPlayerScore() == maxScore){
				topScores.add(score);
			}
			else{
				return topScores;
			}
		}
	}
	return topScores;
}

//Returns an ordered list of scores
@Override
public List<ScoreImpl> getOrderedScores() throws ClassCastException, UnsupportedOperationException{
	List<ScoreImpl> scoresToSort = new ArrayList<ScoreImpl>();
	for(Score score : scores){
		ScoreImpl downCast = (ScoreImpl) score;
		scoresToSort.add(downCast);
	}
	Collections.sort(scoresToSort);
	return scoresToSort;
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

//get quiz name
@Override
public String getQuizName(){
	return quizName;
}

//get number of questions
@Override
public int getNumOfQuestions(){
	return numOfQuestions;
}

@Override
public void setNumOfQuestions(){
	numOfQuestions = questions.size();
}

@Override
public List<Question> getQuestions(){
	return questions;
}

@Override
public void addScore(Score score){
	scores.add(score);
}

}










