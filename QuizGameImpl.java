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

/**
* Manages the QuizGame server. Has a list of quizzes that contains currently available quizzes to play, and their score lists.
* Has a list of players, and a unique ID counter for both quizzes and players. Also has a private static final field FILENAME
* that is the filename for the txt document to which the quizzes and scores, players, and uniqueId are persisted. 
*
* @author Jo Tomlinson
*/
public class QuizGameImpl extends UnicastRemoteObject implements QuizGame{
	private static final String FILENAME = "QuizMaster.txt";
	private List<Quiz> quizzes = new CopyOnWriteArrayList<Quiz>();
	private List<Player> players = new CopyOnWriteArrayList<Player>();
	private int uniqueId = 0;

/**
* Creates a QuizGameImpl object and creates a QuizMaster.txt file if one doesn't exist.
* If the file already exists it reads the data from it and puts the objects into its fields.
*
* @exception IOException caught if there is a problem with reading the data from the txt file.
* @throws RemoteException if there is a problem with network connectivity.
*/
public QuizGameImpl() throws RemoteException{
	try{	
		if(!new File(FILENAME).createNewFile()){	
			getData();
		}
	}catch(IOException ex){
    	ex.printStackTrace();
   	}	
}

/**
* Reads the persisted data from the QuizMaster.txt file (quizzes list, players list, and unique ID counter) 
* and puts the objects into the QuizGameImpl fields.
*
* @exception IOException caught if there is a problem with reading the data from the txt file.
* @exception EOFException - expected if the file is initially empty.
* @exception FileNotFoundException if the txt file is not found
* @exception ClassNotFoundException if the class of the object read is not found.
*/
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

/**
* Writes the data to the QuizMaster.txt file (the quizzes and scores, players, and unique ID count).
*
* @exception IOException caught if there is a problem with writing the data to file.
*/
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

/**
* Gets the top 5 scores - highest first - of a quiz (or less if there are less than 5).
*
* @param quizId the id of the quiz to which the scores belong.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return a list of the top 5 scores.
*/
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

/**
* Checks if a quiz is still open.
*
* @param quizId the id of the quiz.
* @return boolean true if the quiz is open, false if not.
*/
private boolean quizIsOpen(int quizId){
	if(getQuiz(quizId) == null){
		return false;
	}
	return true;
}

/**
* Adds a Score (player ID and score) to a quiz.
*
* @param score the Score to add.
* @param quizId the id of the quiz.
*/
private void addQuizScore(Score score, int quizId){
	for(Quiz quiz : quizzes){
		if(quiz.getId() == quizId){
			quiz.addScore(score);
		}
	}
}

/**
* Gets a quiz by its unique ID number.
*
* @param quizId the id of the quiz to return.
* @return the requested Quiz.
* @return null if the quizId is invalid.
*/
private Quiz getQuiz(int quizId){
	for(Quiz quiz : quizzes){
		if(quiz.getId() == quizId){
			return quiz;
		}
	}
	return null;
}

/**
* Gets a copy of the list of quizzes as an Array.
*
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return an Array of the quiz list.
*/
@Override
public Quiz[] getQuizList() throws RemoteException{
	Quiz[] clonedQuizList = quizzes.toArray(new Quiz[0]);
	return clonedQuizList;
}

/**
* Checks if a player exists in the players list.
*
* @param playerId the players ID to check.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return boolean true if the player exists, false if not.
*/
@Override
public synchronized boolean checkPlayerId(int playerId) throws RemoteException{
	for(Player player : players){
		if(player.getId() == playerId){
			return true;
		}
	}
	return false;
}

/**
* Gets a unique ID number.
*
* @return int a unique ID number.
*/
private int getUniqueId(){
	int returnId = this.uniqueId + 1;
	this.uniqueId = returnId;
	return returnId;
}

/**
* Creates a player, sets their unique ID number, adds them to the players list, and returns their unique ID number.
*
* @param userName the players username.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @param the unique player ID assigned to the player.
*/
@Override
public synchronized int addPlayer(String userName) throws RemoteException{
	Player player = new PlayerImpl(userName);
	player.setId(getUniqueId());
	players.add(player);
	return player.getId();
}

/**
* Closes a quiz by removing it from the quizzes list. The QuizMaster.txt file is updated.
*
* @param quizId the unique ID number of the quiz to be closed.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @param a list containing the top score(s) with corresponding player details.
*/
@Override
public synchronized List<ScoreImpl> closeQuiz(int quizId) throws RemoteException{
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

/**
* Gets the players details by their unique ID number.
*
* @param the players unique ID number
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return a Sring containing the players user name, or a "details not found" message if not.
*/
@Override
public synchronized String getPlayerDetails(int playerId) throws RemoteException{
	for(Player player : players){
		if(player.getId() == playerId){
			return player.getUserName();
		}
	}
	return "Player details not found.";
}

/**
* Adds a Quiz to the list of quizzes and persists it to the QuizMaster.txt file.
*
* @param the Quiz to be added.
* @throws RemoteException if there is a problem with network connectivity to the client.
* @return the unique ID number for the quiz.
*/
@Override
public synchronized int addQuiz(Quiz quiz) throws RemoteException{
	quiz.setId(getUniqueId());
	quizzes.add(quiz);
	writeToFile();
	return quiz.getId();
}

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
@Override
public synchronized List<String> sendResult(Score score, int quizId) throws RemoteException, ClassCastException, UnsupportedOperationException{
	if(!quizIsOpen(quizId)){
		return null;
	}
	addQuizScore(score, quizId);
	List<String> topFive = getTopFiveScores(quizId);
	writeToFile();
	return topFive;
}

/**
* Launches the QuizGameImpl server.
*
* @exception MalformedURLException if the servers name is incorrect.
* @exception RemoteException if there is a problem with network connectivity.
*/
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


public static void main (String[] args){
	try{
		QuizGameImpl quizGame = new QuizGameImpl();
		quizGame.launch();	
	}catch(RemoteException ex){
		ex.printStackTrace();
	}
}	

}