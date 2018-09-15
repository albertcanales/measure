package com.example.albert.measure.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import com.example.albert.measure.R;

import java.util.ArrayList;
import java.util.List;


public class DistanceActivity extends AppCompatActivity implements SurfaceHolder.Callback, Handler.Callback {

    static final int MY_PERMISSIONS_REQUEST_CAMERA = 1242;
    private static final String TAG = DistanceActivity.class.getSimpleName();
    private static final int MSG_CAMERA_OPENED = 1;
    private static final int MSG_SURFACE_READY = 2;
    private static final int CAMERA_ID = 0; // 0 -> Back camera -------- 1 -> Front camera
    private final Handler mHandler = new Handler(this);
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    CameraManager mCameraManager;
    String[] mCameraIDsList;
    CameraDevice.StateCallback mCameraStateCB;
    CameraDevice mCameraDevice;
    CameraCaptureSession mCaptureSession;
    boolean mSurfaceCreated = true;
    boolean mIsCameraConfigured = false;
    Context context;
    private Surface mCameraSurface = null;
    private int color_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        context = this;
        this.mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        this.mSurfaceHolder = this.mSurfaceView.getHolder();
        this.mSurfaceHolder.addCallback(this);
        this.mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);

        try {
            mCameraIDsList = this.mCameraManager.getCameraIdList();
            for (String id : mCameraIDsList) {
                Log.v(TAG, "CameraID: " + id);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        mCameraStateCB = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {
                mCameraDevice = camera;
                mHandler.sendEmptyMessage(MSG_CAMERA_OPENED);
            }

            @Override
            public void onDisconnected(CameraDevice camera) {
            }

            @Override
            public void onError(CameraDevice camera, int error) {
            }
        };

        final ImageButton ib = findViewById(R.id.iv_camera_focus);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = color_id == 0 ? R.color.colorPrimary : R.color.colorAccent;
                ib.setColorFilter(ContextCompat.getColor(context, color));
                color_id = (color_id + 1) % 2;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //requesting permission
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        } else {
            try {
                mCameraManager.openCamera(mCameraIDsList[CAMERA_ID], mCameraStateCB, new Handler());
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (mCaptureSession != null) {
                mCaptureSession.stopRepeating();
                mCaptureSession.close();
                mCaptureSession = null;
            }

            mIsCameraConfigured = false;
        } catch (final CameraAccessException | IllegalStateException e) {
            // Doesn't matter, cloising device anyway
            e.printStackTrace();
        } finally {
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
                mCaptureSession = null;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_calculate) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_CAMERA_OPENED:
            case MSG_SURFACE_READY:
                // if both surface is created and camera device is opened
                // - ready to set up preview and other things
                if (mSurfaceCreated && (mCameraDevice != null)
                        && !mIsCameraConfigured) {
                    configureCamera();
                }
                break;
        }

        return true;
    }

    private void configureCamera() {
        // prepare list of surfaces to be used in capture requests
        List<Surface> sfl = new ArrayList<Surface>();

        sfl.add(mCameraSurface); // surface for viewfinder preview

        // configure camera with all the surfaces to be ever used
        try {
            mCameraDevice.createCaptureSession(sfl,
                    new CaptureSessionListener(), null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        mIsCameraConfigured = true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    try {
                        mCameraManager.openCamera(mCameraIDsList[CAMERA_ID], mCameraStateCB, new Handler());
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCameraSurface = holder.getSurface();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCameraSurface = holder.getSurface();
        mSurfaceCreated = true;
        mHandler.sendEmptyMessage(MSG_SURFACE_READY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceCreated = false;
    }

    private class CaptureSessionListener extends
            CameraCaptureSession.StateCallback {
        @Override
        public void onConfigureFailed(final CameraCaptureSession session) {
            Log.d(TAG, "CaptureSessionConfigure failed");
        }

        @Override
        public void onConfigured(final CameraCaptureSession session) {
            Log.d(TAG, "CaptureSessionConfigure onConfigured");
            mCaptureSession = session;

            try {
                CaptureRequest.Builder previewRequestBuilder = mCameraDevice
                        .createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                previewRequestBuilder.addTarget(mCameraSurface);
                mCaptureSession.setRepeatingRequest(previewRequestBuilder.build(),
                        null, null);
            } catch (CameraAccessException e) {
                Log.d(TAG, "setting up preview failed");
                e.printStackTrace();
            }
        }
    }
}