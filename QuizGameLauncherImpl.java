import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.net.MalformedURLException;



public class QuizGameLauncherImpl{

	public QuizGameLauncherImpl(){

	}


//launch
public void launch(){
//1. If there is no security manager, start one
	if (System.getSecurityManager() == null) {
	System.setSecurityManager(new RMISecurityManager());
	}
	try {
// 2. Create the registry if there is not one
	LocateRegistry.createRegistry(1099);
// 3. Create the server object
QuizGameImpl server = new QuizGameImpl();
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

public static void main (String[] args){
	QuizGameLauncherImpl launchQuiz = new QuizGameLauncherImpl();
	launchQuiz.launch();
}

}