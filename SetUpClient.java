import java.rmi.Remote;
import java.rmi.RemoteException;


/**
* An implementation of the SetUpClient
*/ 
public interface SetUpClient extends Remote{


/**
* Runs through the options for the SetUpClient
*
*@param SetUpClient the SetUpClient calling the method
*@param QuizGame the quizGame_stub
*/ 
void Options(SetUpClient newSetUpClient, QuizGame quizGame) throws RemoteException;



}