import java.rmi.Remote;
import java.rmi.RemoteException;
import java.lang.ClassCastException;
import java.lang.UnsupportedOperationException;
import java.util.List;

/**
* An implementation of the QuizGame server which manages the quizzes list, scores, players list, and unique ID count.
* Data is persisted to a txt file to which the server reads and writes.
*/ 
public interface QuizGame extends Remote{

/**
* Adds a Quiz to the list of quizzes and persists it to the QuizMaster.txt file.
*
* @param the Quiz to be added.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return the unique ID number for the quiz.
*/
int addQuiz(Quiz quiz) throws RemoteException;

/**
* Closes a quiz by removing it from the quizzes list. The QuizMaster.txt file is updated.
*
* @param quizId the unique ID number of the quiz to be closed.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @param a list containing the top score(s) with corresponding player details.
*/
List<ScoreImpl>  closeQuiz(int quizId) throws RemoteException;

/**
* Creates a player, sets their unique ID number, adds them to the players list, and returns their unique ID number.
*
* @param userName the players username.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @param the unique player ID assigned to the player.
*/
int addPlayer(String userName) throws RemoteException;

/**
* Checks if a player exists in the players list.
*
* @param playerId the players ID to check.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return boolean true if the player exists, false if not.
*/
boolean checkPlayerId(int playerId) throws RemoteException;

/**
* Gets a copy of the list of quizzes as an Array.
*
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return an Array of the quiz list.
*/
Quiz[] getQuizList() throws RemoteException;

/**
* Checks if a quiz is still open. If it is adds a players Score to it and returns a list of the top 5 scores. If not
* returns null. Writes the data to file. 
*
* @param score the Score (player score and id) to be added to a quiz.
* @param quizId the id of the quiz to which the score is to be added.
* @throws ClassCastException if the method getTopFiveScores returns a list of anything other than Strings.
* @throws UnsupportedOperationException if a requsted operation is made on the list of Strings topFive.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return a list of the top 5 scores to a quiz (or less if there less than 5).
* @return null if the quiz has closed.
*/
List<String> sendResult(Score score, int quizId) throws RemoteException, ClassCastException, UnsupportedOperationException;

/**
* Gets the players details by their unique ID number.
*
* @param the players unique ID number
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return a Sring containing the players user name, or a "details not found" message if not.
*/
String getPlayerDetails(int playerId) throws RemoteException;	

}












