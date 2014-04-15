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
import java.io.EOFException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;



public class QuizGameImpl extends UnicastRemoteObject implements QuizGame{
	private static final String FILENAME = "QuizMaster.txt";
	private List<Quiz> quizzes = new CopyOnWriteArrayList<Quiz>();
	private List<Player> players = new CopyOnWriteArrayList<Player>();
	private int uniqueId = 0;

	public QuizGameImpl() throws RemoteException{
		try{	
			if(!new File(FILENAME).createNewFile()){	
				getData();
			}
		}catch(IOException ex){
       		ex.printStackTrace();
    	}	
    }


private void getData(){
	ObjectInputStream input = null;
	try{ 	
       	input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(FILENAME)));
       	quizzes = (List<Quiz>) input.readObject();
       	players = (List<Player>) input.readObject();
       	uniqueId = (int) input.readObject();
    }catch(EOFException ex){
 		System.out.println("EOFException in getData() - expected - empty file.");
    }catch(FileNotFoundException ex){
       	ex.printStackTrace();
    }catch(IOException ex){
       	ex.printStackTrace();
    }catch(ClassNotFoundException ex){
       	ex.printStackTrace();
    }finally{
       	try{
       		if(input != null){
       			input.close();
       		}
       	}catch(IOException ex){
       		ex.printStackTrace();
       	}
    }
}

//write to file
private void writeToFile(){
	ObjectOutputStream output = null;
	try{
		output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(FILENAME, false)));
		output.writeObject(quizzes);
		output.writeObject(players);
		output.writeObject(uniqueId);
		output.flush();
	}catch(IOException ex){
		ex.printStackTrace();
	}finally{
		try{
			if(output != null){
				output.close();
			}
       	}catch(IOException ex){
       		ex.printStackTrace();   	
		}
	}
}


@Override
public List<String> sendResult(Score score, int quizId) throws RemoteException, ClassCastException, UnsupportedOperationException{
	addQuizScore(score, quizId);
	List<String> topFive = getTopFiveScores(quizId);
	writeToFile();
	return topFive;
}

private List<String> getTopFiveScores(int quizId) throws RemoteException{
	int count = 1;
	List<ScoreImpl> orderedScores = getQuiz(quizId).getOrderedScores();
	List<String> topFive = new CopyOnWriteArrayList<String>();
		for(ScoreImpl score : orderedScores){
			if(count < 6){
				topFive.add(count + ".   " + "Player: " + score.getPlayerId() + "   " + getPlayerDetails(score.getPlayerId()) + "   Score: " + score.getPlayerScore());
				count = count + 1;
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
public List<ScoreImpl> closeQuiz(int quizId) throws RemoteException{
	List<ScoreImpl> highScores = null;
	for(Quiz quiz: quizzes){
		if(quiz.getId() == quizId){
			highScores = quiz.getHighScore();
			quizzes.remove(quiz);
			writeToFile();
			return highScores;
		}
	}
	return highScores;
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
	writeToFile();
	return quiz.getId();
}

private void launch(){
	if (System.getSecurityManager() == null) {
	System.setSecurityManager(new RMISecurityManager());
	}
	try {
	LocateRegistry.createRegistry(1099);
	QuizGame server = new QuizGameImpl();
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


//Main method
	public static void main (String[] args){
		try{
			QuizGameImpl quizGame = new QuizGameImpl();
			quizGame.launch();	
		}catch(RemoteException ex){
			ex.printStackTrace();
		}
	}	

}