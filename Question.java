/**
* An interface to implement questions. A question has one question and 4 possible multiple choice answers.
*/
public interface Question{

/**
* Adds the correct answer to the question object
*
*@param String the answer to be added
*/
void addCorrectAnswer(String answer);

/**
* Adds an incorrect answer to the question object
*
*@param String the answer to be added
*/
void addAnswer(String answer);	

/**
* Shows which answer from the list of choices is correct
*
*@return int the numbered position of the correct answer as printed on screen
*/
int getCorrectAnswer();

/**
* Prints the possible answers to the question
*/
void printAnswers();

/**
* Shows the question
*
*@return String the question
*/
String getQuestion();
}