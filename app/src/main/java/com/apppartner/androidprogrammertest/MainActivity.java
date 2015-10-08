package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
{

    private TextView codeTaskTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeTaskTextView = (TextView) findViewById(R.id.mainTextView);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Jelloween_-_Machinato_Bold.ttf" );

        codeTaskTextView.setTypeface(tf);

    }

    public void onLoginButtonClicked(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onChatButtonClicked(View v)
    {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void onAnimationTestButtonClicked(View v)
    {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
    }


}
