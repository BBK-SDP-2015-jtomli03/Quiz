import java.util.*;

/**
* A Question object has one question and 4 possible multiple choice answers. 
* It stores the value of the correct answer as an int corresponding to its position in the Array of answers.
*/
public interface Question{

/**
* Adds the correct answer to the question.
*
*@param answer the answer to be added
*/
void addCorrectAnswer(String answer);

/**
* Adds an incorrect answer to the question object.
*
*@param answer the answer to be added
*/
void addAnswer(String answer);	

/**
* Gets the position in Array answers of the correct answer.
*
*@return int the position of the correct answer
*/
int getCorrectAnswer();

/**
* Prints the multiple choice answers to the question
*/
void printAnswers();

/**
* Gets the question
*
*@return String the question
*/
String getQuestion();
}