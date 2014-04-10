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

	public static void main (String[] args) throws NotBoundException, MalformedURLException, RemoteException{
		SetUpClientImpl newSetUpClient = new SetUpClientImpl();
		QuizGame quizGame = newSetUpClient.launch();


		int choice = 0, quizId = 0;
		String quizName = "", quizQuestion = "", answer = "", quizComplete = "Y";
		System.out.println("");
		//System.out.println("*****Welcome to Quiz Master!*****");
		System.out.println("");
		System.out.println("If you want to CREATE a new quiz game then please press '1' followed by the return key.");
		System.out.println("");
		System.out.println("If you want to CLOSE a quiz game then please press '2' followed by the return key.");
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
			while(quizComplete.equalsIgnoreCase("Y")){
				System.out.println("");
				System.out.println("Please type a question;");
				quizQuestion = System.console().readLine();
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
				System.out.println("To add another question type 'Y'.");
				System.out.println("To finish creating the quiz type 'N'.");
				quizComplete = System.console().readLine();
			}
			System.out.println("Your quiz id number is; " + quizGame.addQuiz(quiz));
			break;
			
			case 2:
			System.out.println("*****You have chosen to close a quiz.*****");
			System.out.println("");
			System.out.println("Please enter the quiz ID number, followed by the return key;");
			quizId = Integer.parseInt(System.console().readLine());
			quizGame.closeQuiz(quizId);
			break;
			
			default:
			//code to ask again


		}
	}



	
}