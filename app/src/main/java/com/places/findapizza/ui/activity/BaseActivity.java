package com.places.findapizza.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.places.findapizza.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Override this method to customize the appearance of the {@link ActionBar}
     * .
     */
    protected void setupActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorOrange)));

    }

    protected void setActionBarTitle(String title)
    {
        if(actionBar != null)
        {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void startActivity(Intent intent) {

        super.startActivity(intent);
    }

    /**
     * Use this whenever you need to start an Activity for result. To provide a
     * single point of change.
     *
     * @param intent      the intent
     * @param requestCode the request code
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {

        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}
