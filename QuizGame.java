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
*/
void closeQuiz(int quizId) throws RemoteException;
	
}