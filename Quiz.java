import java.util.ArrayList;
import java.util.List;
import java.rmi.RemoteException;
import java.lang.ClassCastException;
import java.lang.UnsupportedOperationException;

/**
* Allows you to set up a quiz
*/ 
public interface Quiz{
	
/**
* Returns a unique quiz ID number
*
* @return int a unique quiz ID number
*/
int getId();

/**
*Sets the quiz ID number
*
* @param the id number to be set for this quiz
*/
void setId(int id);

/**
* Adds a question to the quiz. Note; questions have answers.
*
* @param the question to be added
*/
void addQuestion(Question question);

/**
* Gets the highest score of the quiz which includes the players id for that score
*
* @return the Score with the highest player score
*/
Score getHighScore();

/**
* Gets the name of the quiz
*
* @return String the quiz name
*/
String getQuizName();

/**
* Gets the total number of questions in the quiz
*
* @return int the number of questions
*/
int getNumOfQuestions();

/**
* Sets the total number of questions in the quiz
*/
void setNumOfQuestions();

/**
* Gets the questions for the quiz
*
* @return List<Question> the list of questions
*/
List<Question> getQuestions();

/**
* Gets the top scores of a quiz
*
* @return List<Score> the list of top scores
*/
List<ScoreImpl> getOrderedScores() throws ClassCastException, UnsupportedOperationException;

/**
* Adds a players Score (Score of variables int score and int playerId) to a quiz
*
* @param Score the Score to add 
*/
void addScore(Score score);

}
















