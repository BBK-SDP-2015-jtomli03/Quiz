import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.io.Serializable;

public class PlayerClientImpl implements Serializable, SetUpClient{
	
	public PlayerClientImpl(){

	}

//launch
private QuizGame launch() throws NotBoundException, MalformedURLException, RemoteException{
	if (System.getSecurityManager() == null){
		System.setSecurityManager(new RMISecurityManager());
	}
	Remote service = Naming.lookup("//127.0.0.1:1099/QuizMaster");		
	QuizGame quizGame = (QuizGame) service;
	String receivedEcho = quizGame.echo("*****Welcome to Quiz Master!*****");
	System.out.println(receivedEcho);
	return quizGame;
}

@Override
public void Options(SetUpClient newSetUpClient, QuizGame quizGame) throws RemoteException{
		String userName = "", choice = "";
		int playerId = 0;
		System.out.println("");
		System.out.println("Are you a returning player? Y or N.");
		choice = System.console().readLine();
		switch (choice){
			case "Y":
			System.out.println("");
			System.out.println("Please key in your player ID number.");
			playerId = Integer.parseInt(System.console().readLine());
			break;

			case "N":
			System.out.println("");
			System.out.println("To set up a player account please enter your username, followed by the return key.");
			userName = System.console().readLine();
			//method to add player to players list and return the player ID
			System.out.println("");
			System.out.println("Your username is " + userName + " and your player ID number = " + playerId + " (Please keep this safe as you will need this to play future quizzes.)");
			break;

			default:
			//return to start
			break;

			//method to input player ID and get a list of quizzes
			//choose quiz
			//play quiz
			//submit quiz
			//score returned
			//?play another quiz/quit
		}
	}

public static void main (String[] args) throws NotBoundException, MalformedURLException, RemoteException{
		PlayerClientImpl newPlayerClient = new PlayerClientImpl();
		QuizGame quizGame = newPlayerClient.launch();
		newPlayerClient.Options(newPlayerClient, quizGame);
		
	}


}