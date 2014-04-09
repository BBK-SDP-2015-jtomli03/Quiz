import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class QuizGameImpl extends UnicastRemoteObject implements QuizGame{
	private List<Quiz> quizzes = new ArrayList<Quiz>();

	public QuizGameImpl() throws RemoteException{
		super();
	}

@Override
public String echo(String s) {
	System.out.println("Replied to some client saying ’" + s + "’");
	return s;
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
	

}