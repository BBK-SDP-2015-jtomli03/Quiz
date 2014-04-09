
/**
* Allows you to set up a quiz
*/ 
public interface Quiz{
	
/**
* Returns a unique quiz ID number
*
* @return int a unique quiz ID number
*/
public int getId();

/**
* Adds a question to the quiz. Note; questions have answers.
*
* @param the question to be added
*/
public void addQuestion(Question question);
}