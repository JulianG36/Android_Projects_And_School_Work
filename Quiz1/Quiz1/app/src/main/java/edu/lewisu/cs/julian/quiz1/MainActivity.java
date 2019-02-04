package edu.lewisu.cs.julian.quiz1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView label;
    private EditText textBox;
    private Button youTubeButton;
    private Button mapsButton;
    private ImageButton nextButton;
    private ImageButton backButton;
    private TextView questionTextView;
    private int currentIndex = 0;

    private Question[] questions = new Question[]{
            new Question(R.string.question1, true),
            new Question(R.string.question2, true),
            new Question(R.string.question3, true),
            new Question(R.string.question4, false),
    };

    private static final String TAG = "QuizApp";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("index");
        }

        youTubeButton = (Button) findViewById(R.id.false_button);
        mapsButton = (Button) findViewById(R.id.true_button);
        nextButton = (ImageButton) findViewById(R.id.next_button);
        backButton = (ImageButton) findViewById(R.id.back_button);
        textBox = (EditText) findViewById(R.id.textBox);
        label = (TextView) findViewById(R.id.textTitle);
        questionTextView = (TextView) findViewById(R.id.question_text);
        int question = questions[currentIndex].getTextResId();
        questionTextView.setText(question);


        youTubeButton.setOnClickListener(new YoutubeButtonListener());
        mapsButton.setOnClickListener(new MapsButtonListener());
        nextButton.setOnClickListener(new NextButtonClickListener());
        backButton.setOnClickListener(new BackButtonClickListener());
        textBox.setOnKeyListener(new TextBoxKey());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy Called");
    }

    private class NextButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            currentIndex = (currentIndex + 1) % questions.length;
            int question = questions[currentIndex].getTextResId();
            questionTextView.setText(question);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = questions[currentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    private class MapsButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, MapsApiActivity.class);
            startActivity(intent);
        }
    }

    privat
e class BackButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (currentIndex == 0) {
                currentIndex = questions.length - 1;
                int question = questions[currentIndex].getTextResId();
                questionTextView.setText(question);
            } else {
                currentIndex = (currentIndex - 1) % questions.length;
                int question = questions[currentIndex].getTextResId();
                questionTextView.setText(question);
            }

        }
    }

    private class TextBoxKey extends Activity implements View.OnKeyListener {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)) {
                String text = textBox.getText().toString();
                label.setText(text);
                return true;
            }
            return false;
        }
    }
    private class YoutubeButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
    }
}

