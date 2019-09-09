package com.ayannah.asira.util;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ayannah.asira.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraTakeBeforeM extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final String TAG = "AndroidCameraApi";
    TextureView mcamera_container;
    LinearLayout mcamera_frame;
    Camera mCamera;
    FrameLayout mBtnCapture;
    private CameraView mPreview;
    public static int count = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    int TAKE_PHOTO_CODE = 0;
    protected CameraDevice cameraDevice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera_ktp);

        Intent intent = getIntent();
//        simulationCodeResponse = intent.getExtras().getParcelable("simresponse");
        mCamera = getCameraInstance();
        mCamera.setDisplayOrientation(90);

        mPreview = new CameraView(this, mCamera);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        LinearLayout frame = (LinearLayout) findViewById(R.id.camera_id);
        TextureView textureView = (TextureView) findViewById(R.id.camera_container);
        textureView.setVisibility(View.GONE);
        frame.addView(mPreview);

        final FrameLayout capture = (FrameLayout) findViewById(R.id.btnCapture);
        capture.setEnabled(true);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture);
                capture.setEnabled(false);
//                mCamera.release();
//                Intent intent = new Intent();
//                mCamera.release();
//                setResult(RESULT_OK,intent);
//                finish();
            }
        });
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            Camera.Parameters params = c.getParameters();
//            params.setPictureSize(1080,720);
            c.setParameters(params);
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
//                SPFromActivity sessionFrom;
//                sessionFrom = new SPFromActivity(getApplication());
//                sessionFrom.createFromSession("camera");
//                Intent intent = new Intent(TakeKTPCameraActivity.this, DocumentUploadActivity.class);
//                startActivity(intent);
                Intent intent = new Intent();
                mCamera.release();
                setResult(RESULT_OK,intent);
                finish();
            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            }
        }
    };

    private File getOutputMediaFile() {
        File mediaStorageDir = new File (Environment.getExternalStorageDirectory() + File.separator + "picTemp.jpg");
//        if (!mediaStorageDir.exists()) {
//            Log.d("MyCameraApp", "failed to create directory");
//            return null;
//        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaStorageDir;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            mCamera.release();
        }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void setMyPreviewSize(int width, int height) {
        // Get the set dimensions
        float newProportion = (float) width / (float) height;

        // Get the width of the screen
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        float screenProportion = (float) screenWidth / (float) screenHeight;

        // Get the SurfaceView layout parameters
        android.view.ViewGroup.LayoutParams lp = mcamera_container.getLayoutParams();
        if (newProportion > screenProportion) {
            lp.width = screenWidth;
            lp.height = (int) ((float) screenWidth / newProportion );
        } else {
            lp.width = (int) (newProportion * (float) screenHeight);
            lp.height = screenHeight;
        }
        // Commit the layout parameters
        mcamera_container.setLayoutParams(lp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCamera.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
