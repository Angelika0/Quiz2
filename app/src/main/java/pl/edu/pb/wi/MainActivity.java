package pl.edu.pb.wi;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button hintButton;
    private TextView questionTextView;
    public static final String KEY_EXTRA_ANSWER = "pl.edu.wi.quiz.correct";
    private static final int REQUEST_CODE_PROMPT = 0;

    // Zmienna do śledzenia, czy odpowiedź została pokazana
    private boolean answerWasShown = false;

    private int currentIndex = 0;
    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version, false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() jest wywołana");
        setContentView(R.layout.activity_main);

        // Przywracanie stanu po obrocie ekranu
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        EdgeToEdge.enable(this);

        // Inicjalizacja widoków
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        hintButton = findViewById(R.id.podpowiedz);
        questionTextView = findViewById(R.id.question_text_view);

        // Ustawienia wcięć
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Przycisk podpowiedzi
        hintButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });

        // Przycisk prawda
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(true);
            }
        });

        // Przycisk fałsz
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);
            }
        });

        // Przycisk następne pytanie
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });

        // Ustawienie pierwszego pytania
        setNextQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState jest wywołana");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() jest wywołana");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() jest wywołana");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() jest wywołana");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() jest wywołana");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() jest wywołana");
    }

    // Obsługuje wynik zwrócony z PromptActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data != null) {
                boolean answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOW, false);
                // Zrób coś z wynikiem (np. pokaż powiadomienie)
                Log.d(TAG, "Answer was shown: " + answerWasShown);
            }
        }
    }


    // Sprawdzenie poprawności odpowiedzi
    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageID = userAnswer == correctAnswer ? R.string.correct_answer : R.string.incorrect_answer;
        Toast.makeText(this, resultMessageID, Toast.LENGTH_SHORT).show();
    }

    // Ustawienie następnego pytania
    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionID());
    }

    // Klasa reprezentująca pytanie
    public class Question {
        private int questionID;
        private boolean trueAnswer;

        public Question(int questionID, boolean trueAnswer) {
            this.questionID = questionID;
            this.trueAnswer = trueAnswer;
        }

        public int getQuestionID() {
            return questionID;
        }

        public boolean isTrueAnswer() {
            return trueAnswer;
        }
    }
}
