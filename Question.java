/**
* An interface to implement questions. A question has one question and 4 possible multiple choice answers.
*/
public interface Question{

/**
* Adds the correct answer to the question object
*
*@param the answer to be added
*/
void addCorrectAnswer(String answer);

/**
* Adds an incorrect answer to the question object
*
*@param the answer to be added
*/
void addAnswer(String answer);	
}