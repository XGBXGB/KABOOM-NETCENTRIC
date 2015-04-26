package com.example.christiangabriel.training;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class SleepActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
//
//        ArrayList<String> dummy = new ArrayList<String>(
//                Arrays.asList("Monday something", "Tuesday something", "Wednesday something", "Thursday something", "Friday something", "Saturday something", "Sunday something"));
//        ListView weekly = (ListView) findViewById(R.id.sleep_listview);
//        ArrayAdapter adapter = new MyAdapter(this, dummy);
//        weekly.setAdapter(adapter);




        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void sleepWeekBack(View view){
        Intent i = new Intent(SleepActivity.this, MainActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sleep, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static String DEFAULT = "";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sleep, container, false);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Profile", getActivity().MODE_PRIVATE);
            ArrayList<String> dummy = new ArrayList<String>(
                    Arrays.asList("Sunday\n"+sharedPreferences.getString("1", DEFAULT),
                                  "Monday\n"+sharedPreferences.getString("2", DEFAULT),
                                  "Tuesday\n"+sharedPreferences.getString("3", DEFAULT),
                                  "Wednesday\n"+sharedPreferences.getString("4", DEFAULT),
                                  "Thursday\n"+sharedPreferences.getString("5", DEFAULT),
                                  "Friday\n"+sharedPreferences.getString("6", DEFAULT),
                                  "Saturday\n"+sharedPreferences.getString("7", DEFAULT)));
            ListView weekly = (ListView) rootView.findViewById(R.id.sleep_listview);
            ArrayAdapter adapter = new MyAdapter(getActivity(), dummy);
            weekly.setAdapter(adapter);
            weekly.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DialogFragment myFragment = new AlarmDialog();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("position", position+1);
                    myFragment.setArguments(bundle);
                    myFragment.show(getActivity().getFragmentManager(), "DialogManager");
                }
            });
            return rootView;
        }
    }
}
