import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.rmi.RemoteException;
import java.lang.ClassCastException;
import java.lang.UnsupportedOperationException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.lang.Cloneable;

/**
* Represents a quiz. A quiz has a quiz name, a unique quiz ID, a list of Question objects (questions and 
* their multiple choice answers), a list of Score objects (player ID's and their corresponding scores), 
* and the number of questions in the quiz stored as an int field.
*
* @author Jo Tomlinson
*/
public class QuizImpl implements Quiz, Serializable{
	private static final long serialVersionUID = 1;
	private int id = 0;
	private String quizName = ""; 
	private int numOfQuestions = 0;
	private List<Question> questions = new ArrayList<Question>();
	private List<Score> scores = new ArrayList<Score>();

/**
* Creates a new QuizImpl object and sets its quizName.
*
* @param quizName the quiz name.
* @throws RemoteException when there is a communication related error when serializing the QuizImpl to/from a file or between 
* a server and client.
*/
public QuizImpl(String quizName) throws RemoteException{
	this.quizName = quizName;
}

/**
* Gets the highest Score (possibly plural if more than one player has the same top score)
* of the quiz (A Score includes the actual score and the corresponding players unique ID number)
*
* @return a list of the highest Score(s)
*/
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

/**
* Gets an ordered list of ScoreImpl objects (includes the actual score and the corresponding players unique ID number)
* in order of the highest score first
*
* @return a list of the highest ScoreImpl(s)
* @throws ClassCastException if you try to sort a list whose elements cannot be compared to one another.
* @throws UnsupportedOperationException if the Collections.sort() method is not supported.
*/
@Override
public List<ScoreImpl> getOrderedScores() throws ClassCastException, UnsupportedOperationException{
	List<ScoreImpl> scoresToSort = new ArrayList<ScoreImpl>();
	for(Score score : scores){
		Score cloned = getClonedScore(score);
		ScoreImpl downCast = (ScoreImpl) cloned;
		scoresToSort.add(downCast);
	}
	Collections.sort(scoresToSort);
	return scoresToSort;
}

/**
* Makes and returns a cloned copy of a Score
*
* @param the Score to be cloned
* @return a cloned copy of a Score
*/
private Score getClonedScore(Score score){
	Score cloned = new ScoreImpl();
	try{
		cloned = (Score)score.clone();
	}catch(CloneNotSupportedException ex){
		ex.printStackTrace();
	}
	return cloned;
}

/**
* Returns a unique quiz ID number
*
* @return int a unique quiz ID number
*/
@Override
public int getId(){
	return this.id;
}

/**
*Sets the quiz ID number
*
* @param the id number to be set for this quiz
*/
@Override
public void setId(int id){
	this.id = id;
}

/**
* Adds a Question to the quiz. A Question has a quiz question and 4 multiple choice answers.
*
* @param the Question to be added
*/
@Override
public void addQuestion(Question question){
	questions.add(question);
}

/**
* Gets the name of the quiz
*
* @return String the quiz name
*/
@Override
public String getQuizName(){
	return quizName;
}

/**
* Gets the total number of questions in the quiz
*
* @return int the number of questions
*/
@Override
public int getNumOfQuestions(){
	return numOfQuestions;
}

/**
* Sets the int value numOfQuestions as the total number of questions in the quiz
*/
@Override
public void setNumOfQuestions(){
	numOfQuestions = questions.size();
}

/**
* Gets the list of Questions for the quiz (A Question contains a quiz question and 4 multiple choice answers).
*
* @return List<Question> the list of Questions for this Quiz
*/
@Override
public List<Question> getQuestions(){
	return questions;
}

/**
* Adds a players Score (Score of variables int score and int playerId) to a quiz
*
* @param Score the Score to add 
*/
@Override
public void addScore(Score score){
	scores.add(score);
}

/**
* Gets the list of Scores (Score of variables int score and int playerId) for a quiz
*
* @return the list of scores 
*/
private List<Score> getScores(){
	return this.scores;
}

}










