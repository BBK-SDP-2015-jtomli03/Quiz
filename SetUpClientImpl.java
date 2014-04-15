import java.util.List;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.io.Serializable;

public class SetUpClientImpl implements Serializable, SetUpClient{

	public SetUpClientImpl(){
		super();
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

private void createQuiz(QuizGame quizGame) throws RemoteException{
	Quiz quiz = nameQuiz();
	addQuestions(quiz);
	quiz.setNumOfQuestions();
	System.out.println("****YOUR QUIZ ID NUMBER IS; " + quizGame.addQuiz(quiz) + "****");
}

private Quiz nameQuiz() throws RemoteException{
	System.out.println("");
	System.out.println("*****You have chosen to create a new quiz.*****");
	System.out.println("");
	System.out.println("Please enter the name of your quiz, followed by the return key;");
	String quizName = System.console().readLine();
	Quiz quiz = new QuizImpl(quizName);
	System.out.println("");
	System.out.println("*****Your quiz name is " + quizName + "*****");
	return quiz;
}

private void addQuestions(Quiz quiz){
	String quizComplete = "Y";
	System.out.println("");
	System.out.println("You will now be asked to type the questions and answers. Each question will have 4 multiple choice answers created by yourself."); 
	System.out.println("You will be asked to type in the correct answer first. Quiz Master will randomly mix up the order of the correct answer with the other answers.");
	for(int quNum = 1; quizComplete.equalsIgnoreCase("Y"); quNum++){
		Question question = createQuestion(quNum);
		addAnswers(question);
		quiz.addQuestion(question);
		System.out.println("");
		System.out.println("Enter 'Y' if you want to add another question. Any other key to complete the quiz.");
		quizComplete = System.console().readLine();
	}
}

private Question createQuestion(int quNum){
	System.out.println("");
	System.out.println("Please type a question;");
	String quizQuestion = "Qu. " + quNum + ". " + System.console().readLine();
	Question question = new QuestionImpl(quizQuestion);
	return question;
}

private void addAnswers(Question question){
	System.out.println("Please type the CORRECT answer;");
	String answer = System.console().readLine();
	question.addCorrectAnswer(answer);
	for(int count = 1; count < 4; count ++){
		System.out.println("Please type in fake answer; " + count);
		answer = System.console().readLine();
		question.addAnswer(answer);
	}
}

private void closeQuiz(QuizGame quizGame) throws RemoteException{
	int quizId = getQuizId();
	List<ScoreImpl> topScores = quizGame.closeQuiz(quizId);
	try{
		printTopScores(topScores, quizGame);
		System.out.println("*****YOUR QUIZ HAS NOW BEEN CLOSED.*****");
	}catch(NullPointerException ex){
		System.out.println("The quiz ID entered is not valid. Please start again.");
	}
}

private int getQuizId(){
	int choice = 0;
	boolean tryAgain = true;
	while(tryAgain){
		try{
			System.out.println("*****You have chosen to close a quiz.*****");
			System.out.println("");
			System.out.println("Please enter the quiz ID number, followed by the return key;");
			choice = Integer.parseInt(System.console().readLine());
			tryAgain = false;
		}catch(NumberFormatException ex){
			System.out.println("You didn't enter a number.");
		}
	}
	return choice;	
}

private void printTopScores(List<ScoreImpl> topScores, QuizGame quizGame) throws RemoteException{
	if(topScores.isEmpty()){
		System.out.println("No players entered the quiz");
	}
	else{
		System.out.println("__________________________________________________________________");
		System.out.println("The winner(s); ");
		for(ScoreImpl score : topScores){
			int playerId = score.getPlayerId();
			System.out.println("Player " + playerId + "  " + quizGame.getPlayerDetails(playerId) + " with a score of " + score.getPlayerScore() + ".");
		}
	}
	System.out.println("------------------------------------------------------------------");
}


private int getMenu(){
	int choice = 0;
	boolean tryAgain = true;
	while(tryAgain){
		try{
			System.out.println("");
			System.out.println("                    MENU");
			System.out.println("                   -------");
			System.out.println("To CREATE a quiz press '1' followed by the return key.");
			System.out.println("");
			System.out.println("To CLOSE a quiz press '2' followed by the return key.");
			System.out.println("");
			System.out.println("To QUIT QuizMaster press '3' followed by the return key.");
			choice = Integer.parseInt(System.console().readLine());
			tryAgain = false;
		}catch(NumberFormatException ex){
			System.out.println("You didn't enter a number.");
		}
	}
	return choice;
}

private void startSetUp(QuizGame quizGame) throws RemoteException{
	boolean finished = false;
	while(!finished){
		int choice = getMenu();		
		switch (choice){
			case 1:
				createQuiz(quizGame);
			break;

			case 2:
				closeQuiz(quizGame);
			break;

			case 3:
				System.out.println("*****THANKYOU - GOODBYE!*****");
				finished = true;
			break;

			default:
				System.out.println("Sorry I didn't understand that. Please try again. ");
			break;
		}
	}
}

public static void main (String[] args) throws NotBoundException, MalformedURLException, RemoteException{
	SetUpClientImpl newSetUpClient = new SetUpClientImpl();
	QuizGame quizGame = newSetUpClient.launch();
	newSetUpClient.startSetUp(quizGame);
}
	
}