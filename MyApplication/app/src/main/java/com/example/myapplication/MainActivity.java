package com.example.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView logoImageView;
    private RelativeLayout logoContainer;
    private Button loginButton;
    private Button registerButton;
    private Button settingsButton;

    public String BaseUrl;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseUrl = getString(R.string.BaseUrl);
        saveUrl(); // Saving the default Url to SharedPreferences

        progressBar = findViewById(R.id.progress_bar);
        logoImageView = findViewById(R.id.logo);
        logoContainer = findViewById(R.id.logo_container);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        settingsButton = findViewById(R.id.settings_button);

        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(100);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterPage();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
                Log.d("SETTINGS", "onClick: ");
            }
        });


    }

    public void startAsyncTask(View v) {
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(100);
    }

    public void saveUrl() {
        SharedPreferences SharedPreferences = getSharedPreferences(String.valueOf(R.string.SharedPrefs), MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPreferences.edit();
        editor.putString("BaseUrl",BaseUrl);
        editor.apply();
    }

    public void goToLoginPage() {
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
    }

    public void goToRegisterPage() {
        Intent intent = new Intent(MainActivity.this, RegisterPage.class);
        startActivity(intent);
    }

    public void goToSettings() {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    private static class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<MainActivity> activityWeakReference;

        ExampleAsyncTask(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Welcome!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);

            ObjectAnimator animation = ObjectAnimator.ofFloat(activity.logoContainer, "translationY", -1000f);
            animation.setDuration(2000);
            animation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    // Fade in buttons after logo animation finishes
                    activity.loginButton.setVisibility(View.VISIBLE);
                    activity.loginButton.setAlpha(0f);
                    activity.loginButton.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .start();

                    activity.registerButton.setVisibility(View.VISIBLE);
                    activity.registerButton.setAlpha(0f);
                    activity.registerButton.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .start();


                    activity.settingsButton.setVisibility(View.VISIBLE);
                    activity.settingsButton.setAlpha(0f);

                    activity.settingsButton.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .start();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            animation.start();
        }
    }
}
