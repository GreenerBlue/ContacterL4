package com.testing.atul.contacterl4;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ContactFrag.OnFragmentInteractionListener, FilterFrag.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FilterFrag f = new FilterFrag();
        f.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.frame1,f).commit();
    }

    @Override
    public void onFragmentInteraction(String uri) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
