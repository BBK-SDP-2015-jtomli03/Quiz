
/**
* Allows you to set up a quiz
*/ 
public interface Quiz{
	
/**
* Returns a unique quiz ID number
*
* @return int a unique quiz ID number
*/
int getId();

/**
*Sets the quiz ID number
*
* @param the id number to be set for this quiz
*/
void setId(int id);

/**
* Adds a question to the quiz. Note; questions have answers.
*
* @param the question to be added
*/
void addQuestion(Question question);
}