package com.example.movekos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.movekos.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreditNumberActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_number);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");

        listView = (ExpandableListView) findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader, listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Bagaimana sistem pembayaran bekerja?");
        listDataHeader.add("Nomor Rekening");
        listDataHeader.add("Bagaimana Cara Membayar?");
        listDataHeader.add("Konfirmasi Pembayaran");

        List<String> sistemBayar = new ArrayList<>();
        sistemBayar.add("Sistem pembayaran bekerja dengan pelanggan melakukan transfer bank ke rekening yang tertera kemudian pelanggan melakukan konfirmasi ke kami. " +
                "Setelah pembayaran kami terima dan konfirmasi, pesanan akan langsung kami proses");

        List<String> noRekening = new ArrayList<>();
        noRekening.add("Nomor rekening yang perlu ditransfer adalah sebagai berikut :" +
                "BCA : 1806199890. ");
        noRekening.add("Diharapkan menggunakan bank yang sama yaitu Bank BCA ketika mentransfer dikarenakan transfer antar bank " +
                "akan dikenakan biaya tambahan oleh bank yang bersangkutan");


        List<String> caraBayar = new ArrayList<>();
        caraBayar.add("Pembayaran dapat dilakukan dengan melakukan transfer ke rekening yang tertera di atas");


        List<String> konfirmasi = new ArrayList<>();
        konfirmasi.add("Konfirmasi pembayaran dapat dilakukan dengan melakukan chat whatsapp ke nomor berikut yaitu 0812456789");

        listHash.put(listDataHeader.get(0),sistemBayar);
        listHash.put(listDataHeader.get(1),noRekening);
        listHash.put(listDataHeader.get(2),caraBayar);
        listHash.put(listDataHeader.get(3),konfirmasi);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}