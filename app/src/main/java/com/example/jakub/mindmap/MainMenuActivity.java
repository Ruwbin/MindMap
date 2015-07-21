package com.example.jakub.mindmap;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainMenuActivity extends ActionBarActivity {

    ImageView newimg,newimgclck;
    LinearLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        newimg = (ImageView) findViewById(R.id.imageView);
        newimgclck = (ImageView) findViewById(R.id.imageView5);
        View.OnTouchListener kuuupa = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView dotkniety = (ImageView) findViewById(R.id.imageView);
                ImageView wyswietl = (ImageView) findViewById(R.id.imageView5);
                switch (v.getId()) {
                    case R.id.imageView:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            dotkniety.setVisibility(View.GONE);
                            wyswietl.setVisibility(View.VISIBLE);
                        }
                        else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE){
                            dotkniety.setVisibility(View.VISIBLE);
                            wyswietl.setVisibility(View.GONE);
                        }

                        break;
                    case R.id.imageView5:
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            dotkniety.setVisibility(View.VISIBLE);
                            wyswietl.setVisibility(View.GONE);
                        }

                        break;
                    }

                return true;
                }
        };
        newimg.setOnTouchListener(kuuupa);
        newimgclck.setOnTouchListener(kuuupa);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void ClickedNewButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void ClickedBrowseButton(View view){


    }
}
