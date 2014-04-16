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

public class PlayerClientImpl implements Serializable, PlayerClient{
	
	public PlayerClientImpl(){

	}

//launch
private QuizGame launch() throws NotBoundException, MalformedURLException, RemoteException{
	if (System.getSecurityManager() == null){
		System.setSecurityManager(new RMISecurityManager());
	}
	Remote service = Naming.lookup("//127.0.0.1:1099/QuizMaster");		
	QuizGame quizGame = (QuizGame) service;
	return quizGame;
}

private boolean checkIdExists(QuizGame quizGame, int playerId) throws RemoteException{
	boolean idExists = quizGame.checkPlayerId(playerId);
		if(!idExists){
			System.out.println("This ID number does not exist. Please try again.");
		}
	return idExists;
}

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

private int getNewPlayerId(QuizGame quizGame, String userName) throws RemoteException{
	int playerId = quizGame.addPlayer(userName);
	System.out.println("");
	System.out.println("Your username is " + userName + " and your player ID number = " + playerId);
	System.out.println("**(Please keep this safe as you will need it to play future quizzes.)**");
	return playerId;
}

private String createUserName(){
	System.out.println("");
	System.out.println("To set up a player account please enter your username, followed by the return key.");
	return System.console().readLine();
}


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

private void printQuizList(Quiz[] quizzes){
	System.out.println("");
	System.out.println("***CURRENT QUIZ LIST***");
	System.out.println("");
		for(Quiz quiz : quizzes){
			System.out.println("Quiz Number " + quiz.getId() + "; " + quiz.getQuizName() + " (Total number of questions = " + quiz.getNumOfQuestions() + ")");
	}
}

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

private void printQuestion(Question question){
	System.out.println("");
	System.out.println(question.getQuestion());
	System.out.println("");
}

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

private void printResult(Quiz quizToPlay, int score){
	System.out.println("");
	System.out.println("***You have completed the quiz! You scored " + score + "/" + quizToPlay.getNumOfQuestions() + " ***");
}


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

private Quiz getQuizToPlay(Quiz[] quizzes, int quizNumber){
	for(Quiz quiz : quizzes){
		if(quiz.getId() == quizNumber){
			return quiz;
		}
	}
	return null;
}	

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









