package com.fer.aula13_threads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView moq01, moq02, moq03, moq04, moq05, moq06, moq07, moq08;
    ProgressBar progressBar;
    TextView txtPts, txtPtsMaximo, txtTexto;
    Button btnIniciar;
    Random r;
    int pontos=0, fps=1000, ptsMax = 0, tempoStatus=0,
        tempoFinal=0, imgAtv=0, imgAtvSalva=0;
    AnimationDrawable an;
    SoundPool snd;
    int s_mosq, s_splat, s_fim;
    MediaPlayer musica;
    boolean tocar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIniciar = findViewById(R.id.button);
        txtPts = findViewById(R.id.textPts);
        txtPtsMaximo = findViewById(R.id.textRecord);
        txtTexto = findViewById(R.id.textoCentro);
        moq01 = findViewById(R.id.mosquito_01);
        moq02 = findViewById(R.id.mosquito_02);
        moq03 = findViewById(R.id.mosquito_03);
        moq04 = findViewById(R.id.mosquito_04);
        moq05 = findViewById(R.id.mosquito_05);
        moq06 = findViewById(R.id.mosquito_06);
        moq07 = findViewById(R.id.mosquito_07);
        moq08 = findViewById(R.id.mosquito_08);
        progressBar = findViewById(R.id.progressBar);

        //Efeitos Sonoros
        snd = new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        s_mosq = snd.load(MainActivity.this, R.raw.s_mosquito,3);
        s_splat = snd.load(MainActivity.this, R.raw.s_kill_the_fly,1);
        s_fim = snd.load(MainActivity.this,R.raw.s_end,1);

        //Música
        musica = MediaPlayer.create(MainActivity.this, R.raw.m_casino);

        //Persistência de Dados
        SharedPreferences prefs = getSharedPreferences("recorde",MODE_PRIVATE);
        ptsMax = prefs.getInt("recorde",0);
        txtPtsMaximo.setText("Recorde: "+ptsMax);

        //Jogo Mosquito
        r = new Random();
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pontos = 0;
                tempoStatus = 100;
                musica.start();
                txtTexto.setTextColor(Color.RED);
                txtPts.setText("Pontos: "+pontos);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        minhaEngine();
                    }
                },1000);
                btnIniciar.setEnabled(false);
                final Handler handler2 = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (tempoStatus > tempoFinal){
                            tempoStatus--;
                            if(tempoStatus<0) tempoStatus = 0;
                            SystemClock.sleep(250);
                            handler2.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(tempoStatus);
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        moq01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSet(moq01);
            }
        });
        moq02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSet(moq02);
            }
        });
        moq03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSet(moq03);
            }
        });
        moq04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSet(moq04);
            }
        });
        moq05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSet(moq05);
            }
        });
        moq06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSet(moq06);
            }
        });
        moq07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSet(moq07);
            }
        });
        moq08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSet(moq08);
            }
        });
    }

    private void clickSet(ImageView imgMoq) {
        imgMoq.setImageResource(R.drawable.red_splat);
        imgMoq.setEnabled(false);
        imgMoq.clearAnimation();
        snd.play(s_splat,1,1,1,0,1);
        snd.stop(s_mosq);
        pontos++;
        txtPts.setText("Pontos: "+pontos);
        tempoStatus += 2;
        if(tempoStatus>=100) tempoStatus =100;
    }

    private void minhaEngine() {
        if(pontos < 10) fps = 1000;
        else if (pontos <15) fps = 950;
        else if (pontos <20) fps = 900;
        else if (pontos <25) fps = 850;
        else if (pontos <30) fps = 800;
        else if (pontos <35) fps = 750;
        else if (pontos <40) fps = 700;
        else if (pontos <45) fps = 650;
        else if (pontos <50) fps = 600;
        else if (pontos <55) fps = 550;
        else fps = 500;

        an = (AnimationDrawable) ContextCompat.getDrawable(
                this, R.drawable.animacao);
        do{
            imgAtv = r.nextInt(8)+1;
        }while (imgAtvSalva == imgAtv);
        imgAtvSalva = imgAtv;
        if(imgAtv == 1){
            ativaImagem(moq01,fps);
        } else if(imgAtv == 2){
            ativaImagem(moq02,fps);
        } else if(imgAtv == 3){
            ativaImagem(moq03,fps);
        } else if(imgAtv == 4){
            ativaImagem(moq04,fps);
        } else if(imgAtv == 5){
            ativaImagem(moq05,fps);
        } else if(imgAtv == 6){
            ativaImagem(moq06,fps);
        } else if(imgAtv == 7){
            ativaImagem(moq07,fps);
        } else {
            ativaImagem(moq08,fps);
        }
        an.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moq01.setVisibility(View.INVISIBLE);
                moq02.setVisibility(View.INVISIBLE);
                moq03.setVisibility(View.INVISIBLE);
                moq04.setVisibility(View.INVISIBLE);
                moq05.setVisibility(View.INVISIBLE);
                moq06.setVisibility(View.INVISIBLE);
                moq07.setVisibility(View.INVISIBLE);
                moq08.setVisibility(View.INVISIBLE);
                moq01.setEnabled(false);
                moq02.setEnabled(false);
                moq03.setEnabled(false);
                moq04.setEnabled(false);
                moq05.setEnabled(false);
                moq06.setEnabled(false);
                moq07.setEnabled(false);
                moq08.setEnabled(false);
                if (tempoStatus < 1){
                    Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                    btnIniciar.setEnabled(true);
                    if(ptsMax < pontos) ptsMax = pontos;
                    txtPtsMaximo.setText("Recorde: "+ptsMax);
                    SharedPreferences prefs = getSharedPreferences("recorde",MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("recorde",ptsMax);
                    editor.apply();
                    txtTexto.setTextColor(Color.BLACK);
                    snd.play(s_fim,1,1,1,0,1);
                    musica.pause();
                    musica.seekTo(0);
                } else minhaEngine();
            }
        }, fps);
    }

    private void ativaImagem(ImageView v, int fps) {
        v.setImageDrawable(an);
        v.setVisibility(View.VISIBLE);
        v.setEnabled(true);
        snd.play(s_mosq, (float) 0.3, (float) 0.3,3,0,1);
        //animando em Círculo
        Animation anim = new CirculoRotateAnimation(v,10);
        anim.setDuration(fps);
        v.startAnimation(anim);
    }

    @Override
    protected void onPause() {
        super.onPause();
        musica.stop();
        snd.release();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        musica.stop();
        snd.release();
        finish();
    }
}
