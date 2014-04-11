import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

/**
* An implementation of the Quiz Game Service
*/ 
public interface QuizGame extends Remote{

/**
* Returns the same string passed as parameter
* @param s a string
* @return the same string passed as parameter
*/
public String echo(String s) throws RemoteException;

/**
* Adds a quiz to the Quiz Game Server
*
* @param the quiz to be added
*
* @return int a unique quiz ID
*/
public int addQuiz(Quiz quiz) throws RemoteException;

/**
* Closes a quiz and removes from the Quiz Game Server list of quizzes
*
* @param the quiz to be removed's ID
*
* @return the winners details and score
*/
String closeQuiz(int quizId) throws RemoteException;

/**
* Adds a player to the Quiz Game Server list of players
*
* @param the players username
*
* @return int a unique player ID
*/
int addPlayer(String userName) throws RemoteException;

/**
* Checks if a players ID is in the players list
*
* @param the players ID number
*
* @return boolean true if the ID exists, false if not
*/
boolean checkPlayerId(int playerId);
	
}