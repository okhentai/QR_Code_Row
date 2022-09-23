package com.example.qr_code_row;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQRCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        // Ánh xạ
        ImageView imageView = (ImageView) findViewById(R.id.QR_Code);
        EditText editText = (EditText) findViewById(R.id.EdtData);
        Button button = (Button) findViewById(R.id.btn_generate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu đầu vào
                String text = editText.getText().toString().trim();
                // Multi format writer
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    // Ma trận Bit
                    BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 300 ,300);
                    // Barcode encoder
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    // Bitmap
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    // Đẩy mã QR lên imageView
                    imageView.setImageBitmap(bitmap);
                    // Input
                    InputMethodManager manager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
                    // Hide keyboard
                    manager.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}