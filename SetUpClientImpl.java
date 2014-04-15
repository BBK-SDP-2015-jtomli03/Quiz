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
	System.out.println("****Your quiz id number is; " + quizGame.addQuiz(quiz) + "****");
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
		System.out.println("Do you want to add another question? Y/N ");
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
	printTopScores(topScores, quizGame);
	System.out.println("*****YOUR QUIZ HAS NOW BEEN CLOSED.*****");
}

private int getQuizId(){
	System.out.println("*****You have chosen to close a quiz.*****");
	System.out.println("");
	System.out.println("Please enter the quiz ID number, followed by the return key;");
	return Integer.parseInt(System.console().readLine());	
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
	System.out.println("");
	System.out.println("To CREATE a quiz press '1' followed by the return key.");
	System.out.println("");
	System.out.println("To CLOSE a quiz press '2' followed by the return key.");
	System.out.println("");
	System.out.println("To QUIT QuizMaster press '3' followed by the return key.");
	return Integer.parseInt(System.console().readLine());
}


public static void main (String[] args) throws NotBoundException, MalformedURLException, RemoteException{
	boolean finished = false;
	SetUpClientImpl newSetUpClient = new SetUpClientImpl();
	QuizGame quizGame = newSetUpClient.launch();
	while(!finished){
		int choice = newSetUpClient.getMenu();
		switch (choice){
			case 1:
				newSetUpClient.createQuiz(quizGame);
			break;

			case 2:
				newSetUpClient.closeQuiz(quizGame);
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
	
}