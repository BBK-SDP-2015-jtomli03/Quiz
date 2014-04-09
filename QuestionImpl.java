import java.io.Serializable;
import java.util.*;

public class QuestionImpl implements Question, Serializable{
	private String question = "";
	private int correctAnswer = 0;
	private String[] answers = new String[4];

public QuestionImpl(String question){
	this.question = question;
}

//Adds the correct answer randomly to the list of answers, 
//then adds one to the randomly obtained integer 'correctAnswer' so that this corresponds to the order that 
//the answers are then presented to the user
@Override
public void addCorrectAnswer(String answer){
	correctAnswer = (int)(Math.random()*4);
	answers[correctAnswer] = answer;
	correctAnswer = correctAnswer + 1;
}

//Adds the incorrect answers to the list
@Override
public void addAnswer(String answer){
	int count = 0;
	boolean answerAdded = false;
	while(count<4 && !answerAdded){
		if(answers[count] == null){
			answers[count] = answer;
			answerAdded = true;
		}
		else{
			count = count +1;
		}
	}
}
	
}