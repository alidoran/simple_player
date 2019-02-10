package alidoran.ir.scrollviewmytest;

import android.Manifest;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    Button btn;
    SeekBar seekbar;
    Handler handler;
    Runnable runnable;
    RadioButton rbtn1;
    RadioButton rbtn2;
    String path;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        ActivityCompat.requestPermissions ( MainActivity.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , 0 );
        mPlayer = new MediaPlayer ( );
        btn = findViewById ( R.id.btn );
        rbtn1 = findViewById ( R.id.radiobtn1 );
        rbtn2 = findViewById ( R.id.radiobtn2 );

        path = "/sdcard/Music/b.mp3";

        rbtn1.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                path = "/sdcard/Music/a.mp3";
            }
        } );
        rbtn2.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                path="/sdcard/Music/b.mp3";
            }
        } );
        btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                playmusic ();
            }
        } );


        seekbar = findViewById ( R.id.seekbar );
        seekbar.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener ( ) {
            @Override
            public void onProgressChanged ( SeekBar seekBar , int progress , boolean fromUser ) {


            }

            @Override
            public void onStartTrackingTouch ( SeekBar seekBar ) {

            }

            @Override
            public void onStopTrackingTouch ( SeekBar seekBar ) {
                int duration = (( int ) mPlayer.getDuration ( ));
                handler.postDelayed ( runnable , 1000 );
                mPlayer.seekTo ( ((seekBar.getProgress ( ) * duration) / 100) );
            }
        } );


        handler = new Handler ( );
        runnable = new Runnable ( ) {
            @Override
            public void run ( ) {
                updateSeekbar ( );
            }
        };
    }

    private void updateSeekbar ( ) {
        float seekprogress = (( float ) mPlayer.getCurrentPosition ( ) / mPlayer.getDuration ( ));  //find current progress position of mediaplayer
        seekbar.setProgress ( ( int ) (seekprogress * 100) ); //set this progress to seekbar
        handler.postDelayed ( runnable , 1000 ); //run handler again after 1 second
    }


    private void playmusic ( ) {
        if (!mPlayer.isPlaying ( )) {
            try {
                mPlayer.setDataSource ( path );
                mPlayer.prepare ( );
            } catch (IOException e) {
                e.printStackTrace ( );
            }
            mPlayer.start ( );
            updateSeekbar ( );
        } else if (mPlayer.isPlaying ( )) {
            mPlayer.stop ( );
            mPlayer.reset ();
            Toast.makeText ( MainActivity.this , mPlayer.isPlaying ( ) + "" , Toast.LENGTH_SHORT ).show ( );

        }
    }


}

