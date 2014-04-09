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
private void launch(){
// 1. If there is no security manager, start one
	if (System.getSecurityManager() == null) {
	System.setSecurityManager(new RMISecurityManager());
	}
	try{
	Remote service = Naming.lookup("//127.0.0.1:1099/QuizMaster");		
	QuizGame quizGame = (QuizGame) service;
	String receivedEcho = quizGame.echo("Hello!");
	System.out.println(receivedEcho);
	}catch(NotBoundException ex){
		ex.printStackTrace();
	}catch(MalformedURLException ex){
		ex.printStackTrace();
	}catch(RemoteException ex){
		ex.printStackTrace();
	}
}

	public static void main (String[] args){
		SetUpClientImpl newClient = new SetUpClientImpl();
		newClient.launch();
		
		int choice = 0;
		String quizName = "";
		System.out.println("");
		System.out.println("*****Welcome to Quiz Master!*****");
		System.out.println("");
		System.out.println("If you want to CREATE a new quiz game then please press '1' followed by the return key.");
		System.out.println("");
		System.out.println("If you want to CLOSE a quiz game then please press '2' followed by the return key.");
		choice = Integer.parseInt(System.console().readLine());
		System.out.println("");
		if(choice == 1){
			System.out.println("Please enter the name of your quiz, followed by the return key;");
			quizName = System.console().readLine();
			

		}


	}
}