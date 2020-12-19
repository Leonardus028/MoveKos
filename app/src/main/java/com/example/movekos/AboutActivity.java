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

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        Element adsElement = new Element();
        adsElement.setTitle("Advertise Here");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo)
                .setDescription("MoveKos adalah aplikasi yang diciptakan untuk membantu mengirimkan barang para mahasiswa yang barangnya  " +
                        "tertinggal di kosan. Situasi pandemi menciptakan keresahan dan kepanikan yang menyebabkan banyak mahasiswa yang meninggalkan kosan mereka secara terburu-buru.   " +
                        "Akibatnya banyak mahasiswa yang meninggalkan barangnya di kosannya. Harapan kami dengan adanya MoveKos para mahasiswa ini dapat bertemu kembali dengan barang milik mereka")
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
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
                Toast.makeText(AboutActivity.this, copyrightString,Toast.LENGTH_SHORT).show();
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