package com.example.cameraapptest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.FileChannel;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView = (ImageView) this.findViewById(R.id.imageView);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                    chooseFile.setType("application/pdf");
                    chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                    startActivityForResult(chooseFile, CAMERA_REQUEST);

//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_PERMISSION_CODE)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
//                chooseFile.setType("application/pdf");
//                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
//                startActivityForResult(chooseFile, CAMERA_REQUEST);
////                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            }
//            else
//            {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri photo = data.getData();
            Log.d("uriVal", "uri = " + data.getData());
            Log.d("uriVal", "path dir = " + Environment.getExternalStorageDirectory().getPath() + "/" + "TempPdf");

//            File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "TempMp3"+"usmannnn.pdf");
//            saveFile1(data.getData(),folder);
//
//            File file = new File(photo.getPath());

//            saveFile(MainActivity.this , data.getData() , Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "TempPdf" , "usman.pdf");
            Toast.makeText(this, "path = " + RealPathUtil.getRealPathFromURI_API19(MainActivity.this, photo), Toast.LENGTH_SHORT).show();
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);

        }
    }

    public String getRealPath(Context context, Uri contentUri) {

        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Files.FileColumns.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static String copyFileAndGetPath(Context context, Uri realUri, String id) {
        final String selection = "_id=?";
        final String[] selectionArgs = new String[]{id};
        String path = null;
        Cursor cursor = null;
        try {
            final String[] projection = {"_display_name"};
            cursor = context.getContentResolver().query(realUri, projection, selection, selectionArgs,
                    null);
            cursor.moveToFirst();
            final String fileName = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
            File file = new File(context.getCacheDir(), fileName);

            saveAnswerFileFromUri(realUri, file, context);
            path = file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return path;
    }

    fun saveAnswerFileFromUri(uri: Uri, destFile: File?, context: Context) {
        try {
            val pfd: ParcelFileDescriptor =
                    context.contentResolver.openFileDescriptor(uri, "r")!!
            if (pfd != null) {
                val fd: FileDescriptor = pfd.getFileDescriptor()
                val fileInputStream: InputStream = FileInputStream(fd)
                val fileOutputStream: OutputStream = FileOutputStream(destFile)
                val buffer = ByteArray(1024)
                var length: Int
                while (fileInputStream.read(buffer).also { length = it } > 0) {
                    fileOutputStream.write(buffer, 0, length)
                }

                fileOutputStream.flush()
                fileInputStream.close()
                fileOutputStream.close()
                pfd.close()
            }
        } catch (e: IOException) {
            Timber.w(e)
        }

    }
}

