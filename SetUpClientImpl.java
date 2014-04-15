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

@Override
public void Options(SetUpClient newSetUpClient, QuizGame quizGame) throws RemoteException{
		int choice = 0, quizId = 0;
		String quizName = "", quizQuestion = "", answer = "", quizComplete = "Y";
		System.out.println("");
		System.out.println("If you want to CREATE a new quiz game then please press '1' followed by the return key.");
		System.out.println("");
		System.out.println("If you want to CLOSE a quiz game then please press '2' followed by the return key.");
		System.out.println("");
		System.out.println("If you want to QUIT QuizMaster then please press '3' followed by the return key.");
		choice = Integer.parseInt(System.console().readLine());
		System.out.println("");
		switch (choice){
			case 1:
			System.out.println("*****You have chosen to create a new quiz.*****");
			System.out.println("");
			System.out.println("Please enter the name of your quiz, followed by the return key;");
			quizName = System.console().readLine();
			Quiz quiz = new QuizImpl(quizName);
			System.out.println("");
			System.out.println("*****Your quiz name is " + quizName + "*****");
			System.out.println("");
			System.out.println("You will now be asked to type the questions and answers. Each question will have 4 multiple choice answers created by yourself."); 
			System.out.println("You will be asked to type in the correct answer first. Quiz Master will randomly mix up the order of the correct answer with the other answers.");
			for(int quNum = 1; quizComplete.equalsIgnoreCase("Y"); quNum++){
				System.out.println("");
				System.out.println("Please type a question;");
				quizQuestion = "Qu. " + quNum + ". " + System.console().readLine();
				Question question = new QuestionImpl(quizQuestion);
				System.out.println("Please type the CORRECT answer;");
				answer = System.console().readLine();
				question.addCorrectAnswer(answer);
				for(int count = 1; count < 4; count ++){
					System.out.println("Please type in fake answer; " + count);
					answer = System.console().readLine();
					question.addAnswer(answer);
				}
				quiz.addQuestion(question);
				System.out.println("");
				System.out.println("Do you want to add another question? Y/N ");
				quizComplete = System.console().readLine();
			}
			quiz.setNumOfQuestions();
			System.out.println("****Your quiz id number is; " + quizGame.addQuiz(quiz) + "****");
			newSetUpClient.Options(newSetUpClient, quizGame);
			break;
			
			case 2:
			System.out.println("*****You have chosen to close a quiz.*****");
			System.out.println("");
			System.out.println("Please enter the quiz ID number, followed by the return key;");
			quizId = Integer.parseInt(System.console().readLine());			
			List<ScoreImpl> topScores = quizGame.closeQuiz(quizId);
			if(topScores.isEmpty()){
				System.out.println("No players entered the quiz");
			}
			else{
				System.out.println("");
				System.out.println("The winner(s); ");
				for(ScoreImpl score : topScores){
					int playerId = score.getPlayerId();
					System.out.println("Player " + playerId + "  " + quizGame.getPlayerDetails(playerId) + " with a score of " + score.getPlayerScore() + ".");
				}
			}
			System.out.println("");
			System.out.println("*****Your quiz has now been closed.*****");
			newSetUpClient.Options(newSetUpClient, quizGame);
			break;

			case 3:
			System.out.println("*****THANKYOU - GOODBYE!*****");
			break;

			default:
			System.out.println("Sorry I didn't understand that. Please try again. ");
			newSetUpClient.Options(newSetUpClient, quizGame);
			break;
		}
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
		SetUpClientImpl newSetUpClient = new SetUpClientImpl();
		QuizGame quizGame = newSetUpClient.launch();
		newSetUpClient.getMenu();

		newSetUpClient.Options(newSetUpClient, quizGame);
		
	}



	
}