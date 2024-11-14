package pl.edu.pb.wi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PromptActivity extends AppCompatActivity {
    public static final String KEY_EXTRA_ANSWER_SHOW = "PB.EDU.WI.QUIZ.ANSWERSHOW";
    private boolean correctAnswer;
    private Button showCorrectAnswerButton;
    private TextView answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prompt);

        // Odczytanie wartości z MainActivity
        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);

        // Inicjalizacja widoków
        showCorrectAnswerButton = findViewById(R.id.button); // Upewnij się, że w XML ma id "button"
        answerTextView = findViewById(R.id.textView3); // Upewnij się, że w XML ma id "textView3"

        // Ustawienia wcięć
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer); // Poprawiona metoda setText
            }
        });
    }


    private void setAnswerShowResult(boolean answerWasShow) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOW, answerWasShow);
        setResult(RESULT_OK, resultIntent);
    }
}
