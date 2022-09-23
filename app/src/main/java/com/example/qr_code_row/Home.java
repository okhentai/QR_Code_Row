package com.example.qr_code_row;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ánh xạ
        Button generate = (Button) findViewById(R.id.generateQR);
        Button scan = (Button) findViewById(R.id.scanQR);

        // Tạo mã QR button
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,GenerateQRCode.class));
            }
        });

        // Quét mã QR button
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
    }
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Made by TamRow");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        dh20lt.launch(options);
    }
    ActivityResultLauncher<ScanOptions> dh20lt = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents() != null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setTitle("Kết quả");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData data = ClipData.newPlainText("Ket qua:", result.getContents());
                    manager.setPrimaryClip(data);
                    Toast.makeText(Home.this, "Copied!", Toast.LENGTH_SHORT).show();
                }
            }).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
    });
}