April 2014
Version 1.0

***QUIZ***
==========
Quiz is a quiz game system consisting of a central Quiz server (QuizGameImpl) and two separate client programs 
(SetUpClientImpl and PlayerClientImpl);

ABOUT:
------
*SetUpClientImpl; allows clients to create their own multiple choice quizzes (4 answers per question). The software will mix up the answers randomly for you, and add complete quizzes to the Quiz server for players to enjoy. It also allows you to close a quiz and returns the top score(s) along with the player details.

*PlayerClientImpl; allows you to set up a player account, and then play any of the quizzes held on the Quiz server. At the end of each quiz you will see your own score, along with the top 5 results for that quiz.

*QuizGameImpl; runs the quiz game system. It stores the list containing all open quizzes (along with their score 
lists), and the list containing all player details. Each time a quiz, player, or new score are added to the lists they are saved to a txt file called QuizMaster.txt. Also when a quiz is closed by a client it is removed from the file. On the first launch of the server the program creates a QuizMaster.txt file.

HOW TO RUN:
-----------
*Ensure all files for the Quiz system are saved in the same file so that they have the same class path. Set this class path on the command line.

*To start the server (QuizGameImpl);
----------------------
1) Create the stub to be sent to clients by keying in on the command line; 
    rmic QuizGameImpl

2) Launch the server on the command line by keying in; 
    java -Djava.security.policy=server.policy QuizGameImpl

3) To close the server from the command line key; ctrl+c

If the server is unexpectedly closed, all the data already in the servers lists will already be saved to the QuizMaster.txt file. On restarting the server this data will be retrieved from the file.


*To start SetUpClientImpl;
---------------------------
1) Launch on a separate command line by keying in; 
    java -Djava.security.policy=client.policy SetUpClientImpl

2) Note; the program has a 'quit' option to close the program.

*To start PlayerClientImpl;
---------------------------
1) Launch on a separate command line by keying in; 
    java -Djava.security.policy=client.policy PlayerClientImpl

2) Note; the program has a 'quit' option to close the program.




