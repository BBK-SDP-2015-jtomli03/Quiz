import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.lang.Object;

public class QuizGameImpl extends UnicastRemoteObject implements QuizGame{
	private List<Quiz> quizzes = new CopyOnWriteArrayList<Quiz>();
	private List<Player> players = new ArrayList<Player>();
	private int uniqueId = 0;

	public QuizGameImpl() throws RemoteException{
		super();
	}

@Override
public String echo(String s) throws RemoteException {
	System.out.println("Client connected.");
	return s;
}


//Returns a copy of the list of current quizzes as an Array
@Override
public Quiz[] getQuizList() throws RemoteException{
	Quiz[] clonedQuizList = quizzes.toArray(new Quiz[0]);
	return clonedQuizList;
}


//Checks if a player ID exists in the players list
@Override
public boolean checkPlayerId(int playerId) throws RemoteException{
	for(Player player : players){
		if(player.getId() == playerId){
			return true;
		}
	}
	return false;
}

private int getUniqueId(){
	int returnId = this.uniqueId + 1;
	this.uniqueId = returnId;
	return returnId;
}

public int addPlayer(String userName) throws RemoteException{
	Player player = new PlayerImpl(userName);
	player.setId(getUniqueId());
	players.add(player);
	return player.getId();
}



@Override
public String closeQuiz(int quizId) throws RemoteException{
	String reply = "Quiz not found.";
	for(Quiz quiz: quizzes){
		if(quiz.getId() == quizId){
			Score highScore = quiz.getHighScore();
			if(highScore == null){
				reply = "No players entered the quiz";
			}
			else{
				int score = highScore.getPlayerScore();
				int playerId = highScore.getPlayerId();
				reply = "The winner was; " + playerId + getPlayerDetails(playerId) + " with a score of " + score + ".";
			}
			quizzes.remove(quiz);
		}
	}
	return reply;
}

//Gets a players details by their ID
private String getPlayerDetails(int playerId){
	for(Player player : players){
		if(player.getId() == playerId){
			return player.getUserName();
		}
	}
	return "Player details not found.";
}

//Adds a quiz to the Quiz Game Server
@Override
public int addQuiz(Quiz quiz) throws RemoteException{
	quiz.setId(getUniqueId());
	quizzes.add(quiz);
	return quiz.getId();
}



//Main method
	public static void main (String[] args){
		//1. If there is no security manager, start one
		if (System.getSecurityManager() == null) {
		System.setSecurityManager(new RMISecurityManager());
		}
		try {
		// 2. Create the registry if there is not one
		LocateRegistry.createRegistry(1099);
		// 3. Create the server object
		QuizGame server = new QuizGameImpl();
		// 4. Register (bind) the server object on the registy.
		// The registry may be on a different machine
		String registryHost = "//localhost/";
		String serviceName = "QuizMaster";
		Naming.rebind(registryHost + serviceName, server);
		System.out.println("Working");
		} catch (MalformedURLException ex) {
		ex.printStackTrace();
		} catch (RemoteException ex) {
		ex.printStackTrace();
		}

	}
	

}