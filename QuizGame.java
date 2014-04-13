import java.rmi.Remote;
import java.rmi.RemoteException;
import java.lang.ClassCastException;
import java.lang.UnsupportedOperationException;
import java.util.*;

/**
* An implementation of the Quiz Game Service
*/ 
public interface QuizGame extends Remote{

/**
* Returns the same string passed as parameter
* @param String a string
* @return String the same string passed as parameter
*/
public String echo(String s) throws RemoteException;

/**
* Adds a quiz to the Quiz Game Server
*
* @param Quiz the quiz to be added
*
* @return int a unique quiz ID
*/
int addQuiz(Quiz quiz) throws RemoteException;

/**
* Closes a quiz and removes from the Quiz Game Server list of quizzes
*
* @param int the quiz to be removed's ID
*
* @return String the winners details and score
*/
String closeQuiz(int quizId) throws RemoteException;

/**
* Adds a player to the Quiz Game Server list of players
*
* @param String the players username
*
* @return int a unique player ID
*/
int addPlayer(String userName) throws RemoteException;

/**
* Checks if a players ID is in the players list
*
* @param int the players ID number
*
* @return boolean true if the ID exists, false if not
*/
boolean checkPlayerId(int playerId) throws RemoteException;

/**
* Returns a copy of the quizzes list as an array of type Quiz
*
* @return Quiz[] an array of the quiz list
*/
Quiz[] getQuizList() throws RemoteException;

/**
* Returns a quiz result to the server and returns the top scores and player details of the quiz
*
* @param Score the players Score (Score has the variables int playerScore and int playerId)
* @param int the ID of the quiz to which the result is to be added
*
* @return List<String>  a list of the top scores with player details for the quiz just played
*/
List<String> sendResult(Score score, int quizId) throws RemoteException, ClassCastException, UnsupportedOperationException;

/**
* Gets a players details from their ID number
*
* @param int the players ID number
*
* @return String the players details
*/
String getPlayerDetails(int playerId) throws RemoteException;	
}












