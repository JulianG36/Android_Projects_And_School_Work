package edu.lewisu.cs.julian.quiz1;
import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import android.app.Activity;
import android.widget.Toast;

import static edu.lewisu.cs.julian.quiz1.R.id.back_button;

/**
 * Created by Julian on 2/6/2018.
 */

public class SecondActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private Button ExitButton;
    private YouTubePlayerView youtubeView;
    private static final int RECOVERY_REQUEST = 1;

@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.second_activity);
    ExitButton = (Button) findViewById(back_button);
    ExitButton.setOnClickListener(new exitButton());

    youtubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
    youtubeView.initialize(Config.YOUTUBE_API_KEY,this);
}
public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored){
    if (!wasRestored){
        player.cueVideo("fhWaJi1Hsfo");
    }
}

public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
    if (errorReason.isUserRecoverableError()){
        errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
    }else{
        String error = String.format(getString(R.string.player_error),errorReason.toString());
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
protected void onActivityResult(int requestCode, int resultCode, Intent data){
    if (requestCode == RECOVERY_REQUEST){
        getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY,this);
    }
}
protected Provider getYouTubePlayerProvider(){
    return youtubeView;
}



private class exitButton implements View.OnClickListener{
        @Override
                public void onClick(View view){
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);

        }
    }


}

