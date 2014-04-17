import java.util.List;
import java.lang.ClassCastException;
import java.lang.UnsupportedOperationException;

/**
* Allows you to set up a Quiz
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
* Adds a Question to the quiz. A Question has a quiz question and 4 multiple choice answers.
*
* @param the Question to be added
*/
void addQuestion(Question question);

/**
* Gets the highest Score (possibly plural if more than one player has the same top score)
* of the quiz (A Score includes the actual score and the corresponding players unique ID number)
*
* @return a list of the highest Score(s)
*/
List<ScoreImpl> getHighScore();

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
* Sets the int value numOfQuestions as the total number of questions in the quiz
*/
void setNumOfQuestions();

/**
* Gets the list of Questions for the quiz (A Question contains a quiz question and 4 multiple choice answers).
*
* @return List<Question> the list of Questions for this Quiz
*/
List<Question> getQuestions();

/**
* Gets an ordered list of ScoreImpl objects (includes the actual score and the corresponding players unique ID number)
* in order of the highest score first
*
* @return a list of the highest ScoreImpl(s)
* @throws ClassCastException if you try to sort a list whose elements cannot be compared to one another.
* @throws UnsupportedOperationException if the Collections.sort() method is not supported.
*/
List<ScoreImpl> getOrderedScores() throws ClassCastException, UnsupportedOperationException;

/**
* Adds a players Score (Score of variables int score and int playerId) to a quiz
*
* @param Score the Score to add 
*/
void addScore(Score score);

}
















