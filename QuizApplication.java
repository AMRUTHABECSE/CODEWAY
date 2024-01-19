import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Quiz {
    private static final int QUESTION_DURATION_SECONDS = 15;

    private static final String[] questions = {
            "What is the capital of France?",
            "Which planet is known as the Red Planet?",
            "What is the largest mammal?",
            // Add more questions as needed
    };

    private static final String[][] options = {
            { "A) Paris", "B) London", "C) Berlin", "D) Rome" },
            { "A) Mars", "B) Jupiter", "C) Venus", "D) Saturn" },
            { "A) Elephant", "B) Whale Shark", "C) Blue Whale", "D) Giraffe" },
            // Add more options corresponding to each question
    };

    private static final int[] correctAnswers = { 0, 0, 2 }; // Index of the correct option for each question

    private int currentQuestionIndex;
    private int userScore;

    private Scanner scanner = new Scanner(System.in);
    private Timer timer = new Timer();

    public void startQuiz() {
        System.out.println("Welcome to the Quiz!");
        currentQuestionIndex = 0;
        userScore = 0;

        askQuestion();
    }

    private void askQuestion() {
        if (currentQuestionIndex < questions.length) {
            System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ": " + questions[currentQuestionIndex]);

            for (String option : options[currentQuestionIndex]) {
                System.out.println(option);
            }

            startTimer();
            int userAnswer = getUserAnswer();
            stopTimer();

            checkAnswer(userAnswer);
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            int seconds = QUESTION_DURATION_SECONDS;

            @Override
            public void run() {
                if (seconds > 0) {
                    System.out.println("Time remaining: " + seconds + " seconds");
                    seconds--;
                } else {
                    System.out.println("Time's up!");
                    stopTimer();
                    askQuestion();
                }
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    private int getUserAnswer() {
        System.out.print("Your answer (enter the option number): ");
        return scanner.nextInt();
    }

    private void checkAnswer(int userAnswer) {
        if (userAnswer == correctAnswers[currentQuestionIndex]) {
            System.out.println("Correct!");
            userScore++;
        } else {
            System.out.println("Incorrect! The correct answer is: "
                    + options[currentQuestionIndex][correctAnswers[currentQuestionIndex]]);
        }

        currentQuestionIndex++;
        askQuestion();
    }

    private void endQuiz() {
        System.out.println("\nQuiz ended!");
        System.out.println("Your final score: " + userScore + " out of " + questions.length);
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.startQuiz();
    }
}
