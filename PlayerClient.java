import java.rmi.Remote;
import java.rmi.RemoteException;


/**
* An implementation of the SetUpClient
*/ 
public interface PlayerClient extends Remote{


/**
* Runs through the options for the PlayerClient
*
*@param PlayerClient the PlayerClient calling the method
*@param QuizGame the quizGame_stub
*/ 
void Options(PlayerClient newPlayerClient, QuizGame quizGame) throws RemoteException;

//method to input player ID and get a list of quizzes
//choose quiz
//play quiz
//submit quiz
//score returned
//?play another quiz/quit

}