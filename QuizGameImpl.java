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
import java.lang.Comparable;
import java.lang.ClassCastException;
import java.lang.UnsupportedOperationException;



public class QuizGameImpl extends UnicastRemoteObject implements QuizGame{
	private List<Quiz> quizzes = new CopyOnWriteArrayList<Quiz>();
	private List<Player> players = new CopyOnWriteArrayList<Player>();
	private int uniqueId = 0;

	public QuizGameImpl() throws RemoteException{
		
	}

@Override
public String echo(String s) throws RemoteException {
	System.out.println("Client connected.");
	return s;
}

@Override
public List<String> sendResult(Score score, int quizId) throws RemoteException, ClassCastException, UnsupportedOperationException{
	addQuizScore(score, quizId);
	List<String> topFive = getTopFiveScores(quizId);
	return topFive;
}

private List<String> getTopFiveScores(int quizId) throws RemoteException{
	int count = 1;
	List<ScoreImpl> orderedScores = getQuiz(quizId).getOrderedScores();
	List<String> topFive = new CopyOnWriteArrayList<String>();
		for(ScoreImpl score : orderedScores){
			if(count < 6){
				topFive.add(count + ".   " + "Player: " + score.getPlayerId() + "   " + getPlayerDetails(score.getPlayerId()) + "   Score: " + score.getPlayerScore());
			}
			else{
				return topFive;
			}
		}
	return topFive;
}

public void addQuizScore(Score score, int quizId){
	for(Quiz quiz : quizzes){
		if(quiz.getId() == quizId){
			quiz.addScore(score);
		}
	}
}

private Quiz getQuiz(int quizId){
	for(Quiz quiz : quizzes){
		if(quiz.getId() == quizId){
			return quiz;
		}
	}
	return null;
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
@Override
public String getPlayerDetails(int playerId) throws RemoteException{
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