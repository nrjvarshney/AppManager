package com.example.tester;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Gallery extends Activity {

    private static final int SELECTED_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PICTURE);
        ImageView im = (ImageView) findViewById(R.id.myimageView);
        Button b = (Button) findViewById(R.id.button3);
        b.setText("before");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK) {


//        switch (requestCode) {
//            case SELECTED_PICTURE:
//                if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String projection[] = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filepath = cursor.getString(columnIndex);
            cursor.close();
//
            yourSelectedImage = BitmapFactory.decodeFile(filepath);
            Log.e("bitmap",""+yourSelectedImage);
            Log.e("filepath",""+filepath);
//                    Drawable d = new BitmapDrawable(yourSelectedImage);
////                    im.setImageDrawable(d);
//                    //  im.setBackground(d);
//                    im.setImageBitmap(yourSelectedImage);
//                    Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
//                    Button bt = (Button) findViewById(R.id.button3);
//                    bt.setText("Haha");
            Intent intent = new Intent(Gallery.this, MainActivity.class);
//            if (yourSelectedImage != null) {
//                //Convert to byte array
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
                intent.putExtra("bitmap", filepath);
//            }
            startActivity(intent);
//
//                }
//                break;
//            default:
//                Toast.makeText(getApplicationContext(), "default", Toast.LENGTH_LONG).show();
//                break;
        }

        //b.setText("after");
    }

    Bitmap yourSelectedImage = null;
}
