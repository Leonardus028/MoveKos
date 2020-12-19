package com.example.movekos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class JoinUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        Element adsElement = new Element();
        adsElement.setTitle("Advertise Here");

        View joinUsPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(" ")
                .addGroup("Join our community below")
                .addEmail("lunnardolukias@gmail.com")
                .addWebsite("My Website")
                .addFacebook("MoveKos")
                .addTwitter("movekos")
                .addYoutube("My youtube")
                .addPlayStore("My Playstore")
                .addInstagram("movekos")
                .addGitHub("Leonardus028")
                .addItem(createCopyright())
                .create();
        setContentView(joinUsPage);
    }

    private Element createCopyright() {
        final  Element copyright = new Element();
        final String copyrightString = String.format("Copyright %d by MoveKos", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JoinUsActivity.this, copyrightString,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}