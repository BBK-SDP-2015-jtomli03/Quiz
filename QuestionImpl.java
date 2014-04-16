import java.io.Serializable;
import java.util.*;

/**
* Represents a quiz question and it's multiple choice answers.
* It stores the value of the correct answer as an int corresponding to its position in the Array of answers.
*
* @author Jo Tomlinson
*/
public class QuestionImpl implements Question, Serializable{
	private static final long serialVersionUID = 1;
	private String question = "";
	private int correctAnswer = 0;
	private String[] answers = new String[4];


/**
* Creates a QuestionImpl object and sets the question.
*
* @param question the question 
*/
public QuestionImpl(String question){
	this.question = question;
}

/**
* Adds the correct answer at a random position in the Array of answers.
* Note that once the answer has been added to the Array of answers, int correctAnswer = correctAnswer + 1. 
* This makes it's value correspond to the order that the answer will be viewed on the user interface, making
* scoring easier.
*
* @param answer the correct answer to be added
*/
@Override
public void addCorrectAnswer(String answer){
	correctAnswer = (int)(Math.random()*4);
	answers[correctAnswer] = answer;
	correctAnswer = correctAnswer + 1; 
}

/**
* Adds an incorrect answer to Array answers. This will be assigned a position depending 
* on the position of the other answers, to allow randomisation of the correct answer.
*
*@param answer the answer to be added
*/
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

/**
* Gets the position in Array answers of the correct answer.
*
*@return int the position of the correct answer
*/
@Override
public int getCorrectAnswer(){
	return correctAnswer;
}

/**
* Gets the question
*
*@return String the question
*/
@Override
public String getQuestion(){
	return question;
}

/**
* Prints the multiple choice answers to the question
*/
@Override
public void printAnswers(){
	int  count = 1;
	for(String answer: answers){
			System.out.println(count + ") " + answer);
			count = count + 1;
	}
}
	
}