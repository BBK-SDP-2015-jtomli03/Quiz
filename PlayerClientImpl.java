import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.lang.NumberFormatException;

/**
* Allows players to set up player accounts and play quizzes.
*
* @author Jo Tomlinson
*/
public class PlayerClientImpl implements Serializable, PlayerClient{
	
public PlayerClientImpl(){

}

/**
* Launches the PlayerClientImpl.
*
* @throws NotBoundException if the registry cannot find the QuizGame server.
* @throws MalformedURLException if the servers name is incorrect.
* @throws RemoteException if there is a problem with network connectivity to the QuizGame server.
* @return the stub to the QuizGame server.
*/
private QuizGame launch() throws NotBoundException, MalformedURLException, RemoteException{
	if (System.getSecurityManager() == null){
		System.setSecurityManager(new RMISecurityManager());
	}
	Remote service = Naming.lookup("//127.0.0.1:1099/QuizMaster");		
	QuizGame quizGame = (QuizGame) service;
	return quizGame;
}

/**
* Checks that a players ID number exists.
*
* @param quizGame the stub to the QuizGame server.
* @throws playerId the players unique ID number.
* @throws RemoteException if there is a problem with network connectivity to the QuizGame server.
* @return boolean true if the ID exists, false if not.
*/
private boolean checkIdExists(QuizGame quizGame, int playerId) throws RemoteException{
	boolean idExists = quizGame.checkPlayerId(playerId);
		if(!idExists){
			System.out.println("This ID number does not exist. Please try again.");
		}
	return idExists;
}

/**
* Takes a player ID number as inputted by the player, and returns this ID only if the input doesn't throw a NumberFormatException
*
* @exception NumberFormatException caught if the player keys in anything other than an int. They will be asked to try again.
* @return the keyed in int value which may or may not be a valid player ID number.
*/
private int idFromPlayer(){
	int id = 0;
	boolean proceed = false;
	while(!proceed){
		System.out.println("");
		System.out.println("Please key in your player ID number.");
		try{
			id = Integer.parseInt(System.console().readLine());
			proceed = true;
		}catch(NumberFormatException ex){
			System.out.println("You didn't enter a number. Please try again.");
		}
	}
	return id;
}

/**
* Adds a new Player to the players list on the QuizGame server, and gets the players unique ID number.
*
* @param quizGame the stub to the QuizGame server.
* @param userName the players chosen username.
* @throws RemoteException if there is a problem with network connectivity to the QuizGame server.
* @return int the players unique ID number.
*/
private int getNewPlayerId(QuizGame quizGame, String userName) throws RemoteException{
	int playerId = quizGame.addPlayer(userName);
	System.out.println("");
	System.out.println("Your username is " + userName + " and your player ID number = " + playerId);
	System.out.println("**(Please keep this safe as you will need it to play future quizzes.)**");
	return playerId;
}

/**
* Takes the players chosen username
*
* @return String the players chosen username
*/
private String createUserName(){
	System.out.println("");
	System.out.println("To set up a player account please enter your username, followed by the return key.");
	return System.console().readLine();
}

/**
* The player set up menu to check if a player is new or returning.
* If a player is new then they will be directed to set up an account, after which their ID number is returned.
* If they are returning then they have to key in their player ID number, which is then checked against
* the players list held on the Quizgame server. If correct the ID is returned. If incorrect or the player keys in
* anything other than an int value they will be directed back to the menu.
*
* @param quizGame the stub to the QuizGame server.
* @throws RemoteException if there is a problem with network connectivity to the QuizGame server.
* @return int the players unique ID number.
*/
private int playerSetUp(QuizGame quizGame) throws RemoteException{
	int playerId = 0;
	boolean optionChosen = false;
	System.out.println("*****Welcome to Quiz Master!*****");
	System.out.println("");
	while(!optionChosen){
		System.out.println("Are you a returning player? Y or N.");
		String choice = System.console().readLine();
		switch (choice.toUpperCase()){
			case "Y":
					playerId = idFromPlayer();
					optionChosen = checkIdExists(quizGame, playerId);
					break;

			case "N":
					String userName = createUserName();
					playerId = getNewPlayerId(quizGame, userName);
					optionChosen = true;
					break;

			default:
					System.out.println("Sorry I didn't understand that.");
					break;
		}
	}
	return playerId;
}

/**
* Shows on the user interface the list of available quizzes to play.
*
* @param quizzes an Array of available quizzes.
*/
private void printQuizList(Quiz[] quizzes){
	System.out.println("");
	System.out.println("***CURRENT QUIZ LIST***");
	System.out.println("");
		for(Quiz quiz : quizzes){
			System.out.println("Quiz Number " + quiz.getId() + "; " + quiz.getQuizName() + " (Total number of questions = " + quiz.getNumOfQuestions() + ")");
	}
}

/**
* Shows the player the list of available quizzes and returns their choice.
*
* @param quizzes the list of available quizzes.
* @exception NumberFormatException caught if the player keys in anything other than an int for the quiz number. 
* They will be asked to try again.
* @return the chosen quiz.
*/
private Quiz chooseQuiz(Quiz[] quizzes){
	printQuizList(quizzes);
	Quiz quizToPlay = null;
	while(quizToPlay == null){
		System.out.println("");
		System.out.println("***Please choose a quiz to play by keying in its QUIZ NUMBER;***");
		try{
			int quizNumber = Integer.parseInt(System.console().readLine());
			quizToPlay = getQuizToPlay(quizzes, quizNumber);
		}catch(NumberFormatException ex){
			System.out.println("You didn't enter a number. Please try again.");
		}
	}
	System.out.println("");
	System.out.println("You have chosen to play " + quizToPlay.getQuizName() + "........GOOD LUCK!!");
	return quizToPlay;
}

/**
* Shows on the user interface a quiz question.
*
* @param question the question to be printed.
*/
private void printQuestion(Question question){
	System.out.println("");
	System.out.println(question.getQuestion());
	System.out.println("");
}

/**
* Takes the players answer to set of multiple choice answers. The game will not proceed unless a valid answer is entered.
* The player will be told if their answer is invalid, ie is not an int between 1-4.
*
* @exception NumberFormatException caught if the player keys in anything other than an int for the answer. 
* They will be asked to try again.
* @return int the chosen answer.
*/
private int getPlayersAnswer(){
	boolean tryAgain = true;
	int answer = 0;
	System.out.println("");
	while(tryAgain){
		System.out.print("Please key in your answer; ");
		try{
			answer = Integer.parseInt(System.console().readLine());
			if(answer < 1 || answer > 4){
				System.out.println("That is not a valid answer.");
			}
			else{
				tryAgain = false; 
			}
		}catch(NumberFormatException ex){
			System.out.println("You didn't enter a number.");
		}
	}
	return answer;
}

/**
* Shows on the user interface the players overall quiz score in the format; x/y, where x = player score, y = total questions. 
*
* @param quizToPlay the quiz that the score refers to.
* @param score the players score.
*/
private void printResult(Quiz quizToPlay, int score){
	System.out.println("");
	System.out.println("***You have completed the quiz! You scored " + score + "/" + quizToPlay.getNumOfQuestions() + " ***");
}

/**
* Allows a player to play a quiz; shows on the user interface the questions and possible answers, 
* takes a players inputted answer, calculates the players score, and shows on the user interface the players score. 
*
* @param quizToPlay the quiz to play.
* @return int the players score.
*/
private int playQuiz(Quiz quizToPlay){
	int score = 0;
	for(Question question : quizToPlay.getQuestions()){
		printQuestion(question);
		question.printAnswers();	
		int answer = getPlayersAnswer();	
		if(answer == question.getCorrectAnswer()){
			score = score + 1;
		}
	}
	printResult(quizToPlay, score);		
	return score;
}

/**
* Shows on the user interface the top 5 scores for a quiz. If a quiz has been closed while a player has been playing it,
* a message explaining this will show instead.
*
* @param topFive the list of top 5 scores.
*/		
private void printTopFive(List<String> topFive){
	if(topFive == null){
		System.out.println("");
		System.out.println("Sorry, the quiz was closed while you were playing.");
	}
	else{
		System.out.println("");
		System.out.println("The top scores are; ");
		System.out.println("");	
		for(String result : topFive){
			System.out.println(result);
		}
	}
}

/**
* Gets a quiz.
*
* @param quizzes an Array of available quizzes.
* @param quizNumber the ID number of the quiz to be returned.
* @return the requested Quiz.
*/	
private Quiz getQuizToPlay(Quiz[] quizzes, int quizNumber){
	for(Quiz quiz : quizzes){
		if(quiz.getId() == quizNumber){
			return quiz;
		}
	}
	return null;
}	

/**
* Checks if a player wants to play another quiz. If an invalid input is keyed, the player will be asked again.
*
* @return boolean true if the player wants to play another quiz; false if not.
*/	
private boolean playAgain(){
	boolean answer = false, play = false;
	while(!answer){
		System.out.println("");
		System.out.println("Do you want to play another quiz? Y/N ");
		String choice = System.console().readLine();
		if(choice.equalsIgnoreCase("Y")){
			answer = true;
			play = true;
		}
		else if(choice.equalsIgnoreCase("N")){
			System.out.println("*****GOODBYE!*****");
			answer = true;
			play = false;
		}
		else{
			System.out.println("Sorry I didn't understand that.");
		}
	}
	return play;
}

/**
* The main method from which PlayerClientImpl is launched, and the QuizGame server stub is returned to.
* Verifys existing players and adds new players to the list of players on the QuizGame server.
* Allows a player to choose and play a quiz.
* Adds a playerScore to the quiz in the quizzes list on the QuizGameServer.
* Asks if a player wants to play another quiz or quit.
*/	
public static void main (String[] args) throws NotBoundException, MalformedURLException, RemoteException{
	PlayerClientImpl newPlayerClient = new PlayerClientImpl();
	QuizGame quizGame = newPlayerClient.launch();
	int playerId = newPlayerClient.playerSetUp(quizGame);
	Quiz[] quizzes = quizGame.getQuizList();
	boolean play = true;
	while(play){
		Quiz quizToPlay = newPlayerClient.chooseQuiz(quizzes);
		int score = newPlayerClient.playQuiz(quizToPlay);		
		Score playerScore = new ScoreImpl(playerId, score);
		List<String> topFive = quizGame.sendResult(playerScore, quizToPlay.getId());
		newPlayerClient.printTopFive(topFive);
		play = newPlayerClient.playAgain();
	}
}

}









