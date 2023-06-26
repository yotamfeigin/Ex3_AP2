package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.CursorWindow;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.activites.ChatsPage;
import com.example.myapplication.api.LoginAPI;
import com.example.myapplication.callback.LoginCallback;
import com.example.myapplication.entities.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView logoImageView;
    private RelativeLayout logoContainer;
    private String fireBaseToken;

    private User user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("UserLogin", Context.MODE_PRIVATE);
        user = new User("","","");
        progressBar = findViewById(R.id.progress_bar);
        logoImageView = findViewById(R.id.logo);
        logoContainer = findViewById(R.id.logo_container);

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage();
            }
        });

        Button register = findViewById(R.id.register_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterPage();
            }
        });
        FirebaseApp.initializeApp(this);
        requestNotificationPermission();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        Log.d("FCM Token", "Token: " + token);
                        fireBaseToken = token;
                        String savedUsername = sharedPreferences.getString("username", "");
                        String savedPassword = sharedPreferences.getString("password", "");
                        if(login == null) {
                            return;
                        }
                        LoginAPI loginApi = new LoginAPI(savedUsername, savedPassword, fireBaseToken);

                        loginApi.postLogin(user, new LoginCallback() {
                            @Override
                            public void onLoginSuccess(User user) {
                                // User has been updated after the login
                                Intent intent = new Intent(MainActivity.this, ChatsPage.class);
                                intent.putExtra("USER_OBJECT", user);
                                startActivity(intent);
                            }

                            @Override
                            public void onLoginFailure(Throwable throwable) {
                                // Handle login failure
                                throwable.printStackTrace();
                            }
                        });
                    } else {
                        Log.d("FCM Token", "Failed to retrieve token");
                    }
                });


        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAsyncTask(View v) {
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(100);
    }

    public void goToLoginPage() {
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        intent.putExtra("firebaseToken", fireBaseToken);
        startActivity(intent);
    }

    public void goToRegisterPage() {
        Intent intent = new Intent(MainActivity.this, RegisterPage.class);
        startActivity(intent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the notification channel with a unique ID and name
            String channelId = "channel_id";
            CharSequence channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            // Create the notification channel object
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

            // Get the notification manager and create the channel
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void requestNotificationPermission() {
        // Check if notification permission is granted
        if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            // Notifications are already enabled
            return;
        }

        // Create a notification to trigger the permission request
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("Permission Request")
                .setContentText("Please grant permission to receive notifications")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification to trigger the permission request
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        notificationManager.notify(0, builder.build());
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
            animation.start();

        }
    }
}
