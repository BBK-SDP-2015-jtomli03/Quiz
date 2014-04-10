import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class QuizGameImpl extends UnicastRemoteObject implements QuizGame{
	private List<Quiz> quizzes = new ArrayList<Quiz>();

	public QuizGameImpl() throws RemoteException{
		super();
	}

@Override
public String echo(String s) throws RemoteException {
	System.out.println("Client connected.");
	return s;
}

//get quizzes
//@Override
public void closeQuiz(int quizId) throws RemoteException{
	for(Quiz quiz: quizzes){
		if(quiz.getId() == quizId){
			//get the highest score and winners details
			//return these details to the client
			//remove the quiz from quizzes
		}
		else{
			//quiz not found
		}
	}
}

//Adds a quiz to the Quiz Game Server
@Override
public int addQuiz(Quiz quiz) throws RemoteException{
	quiz.setId(getNewQuizId());
	quizzes.add(quiz);
	System.out.println("checking this doesnt show in client!!");
	return quiz.getId();
}

/**
* Returns a unique quiz ID
*
* @return int a unique quiz ID
*/
	private int getNewQuizId(){
		int max = 0;
		if (quizzes.isEmpty()){
			return 1;
		}
		else{
			for(Quiz quiz : quizzes){
				if(quiz.getId() > max){
					max = quiz.getId();
				}
			}
			return max + 1;
		}
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