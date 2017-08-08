package com.example.alpin.bottomnavigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
/**
 * Created by alpin on 07/08/17.
 */

public class CameraFragment extends Fragment {


    /**
     * Created by Gebruiker on 29-10-2014.
     */

        View rootView;
        ImageButton cameraShootButton;
        ImageButton cameraChangeButton;
        Boolean switching = false;
        View.OnClickListener tapper = null;
        SurfaceView cameraPreview = null;
        SurfaceHolder cameraPreviewHolder = null;
        Camera camera = null;
        boolean inPreview = false;
        boolean cameraConfigured = false;
        Integer currentCamera;

        public static CameraFragment newInstance(String text) {

            CameraFragment f = new CameraFragment();
            Bundle b = new Bundle();
            b.putString("msg", text);

            f.setArguments(b);

            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.second_frag, container, false);


            cameraShootButton = (ImageButton) rootView.findViewById(R.id.captureB);
            cameraChangeButton = (ImageButton) rootView.findViewById(R.id.changeCameraB);
            cameraPreview = (SurfaceView) rootView.findViewById(R.id.cameraView);

            cameraPreviewHolder = cameraPreview.getHolder();
            cameraPreviewHolder.addCallback(surfaceCallback);
            cameraPreviewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


            tapper = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.captureB) {
                        camera.takePicture(ShutterCallback, PictureCallbackRaw, null, PictureCallbackJpeg);
                    } else if (v.getId() == R.id.changeCameraB) {
                        if (switching == true){
                            return;
                        }
                        changeCamera();
                    }
                }
            };

            cameraShootButton.setOnClickListener(tapper);
            cameraChangeButton.setOnClickListener(tapper);
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            camera = getCamera("back");
            currentCamera = 1;
            startPreview();
        }

       /* @Override
        public void onPause() {
            if (inPreview) {
                camera.stopPreview();
            }
            camera.release();
            camera = null;
            inPreview = false;

            super.onPause();
        }*/

        private Camera.Size getBestPreviewSize(int width, int height,
                                               Camera.Parameters parameters) {
            Camera.Size result = null;

            for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
                if (size.width <= width && size.height <= height) {
                    if (result == null) {
                        result = size;
                    } else {
                        int resultArea = result.width * result.height;
                        int newArea = size.width * size.height;

                        if (newArea > resultArea) {
                            result = size;
                        }
                    }
                }
            }

            return (result);
        }

        private void initPreview(int width, int height) {
            if (camera != null && cameraPreviewHolder.getSurface() != null) {
                try {
                    camera.setPreviewDisplay(cameraPreviewHolder);
                } catch (Throwable t) {
                    Log.e("PreviewDemo-surfaceCallback",
                            "Exception in setPreviewDisplay()", t);
                }

                if (!cameraConfigured) {
                    Camera.Parameters parameters = camera.getParameters();
                    Log.v("CAMERA", parameters.toString());
                    Camera.Size size = getBestPreviewSize(width, height,
                            parameters);

                    if (size != null) {
                        Log.v("CameraPreviewHeight", ""+cameraPreview.getMeasuredHeight());
                        Log.v("CameraRES", size.width + " " + size.height);
                        parameters.setPreviewSize(size.width, size.height);
                        camera.setParameters(parameters);
                        cameraConfigured = true;
                    }
                }
            }
        }

        private void startPreview() {
            if (cameraConfigured && camera != null) {
                camera.startPreview();
                inPreview = true;
                camera.setDisplayOrientation(90);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    camera.enableShutterSound(false);
                }
            }
        }

        private void changeCamera() {
            switching = true;
            if (inPreview) {
                camera.stopPreview();
                camera.setPreviewCallback(null);
            }
            camera.release();
            camera = null;
            inPreview = false;
            if (currentCamera==1){
                camera = getCamera("front");
                currentCamera =2;
            }
            else{
                camera = getCamera("back");
                currentCamera = 1;
            }
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(cameraPreviewHolder);
                cameraPreviewHolder.addCallback(surfaceCallback);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inPreview = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                camera.enableShutterSound(false);
            }
            switching = false;

        }

        SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                // no-op -- wait until surfaceChanged()
            }

            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width,
                                       int height) {
                initPreview(width, height);
                startPreview();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                // no-op
            }
        };

        Camera.ShutterCallback ShutterCallback = new Camera.ShutterCallback() {
            public void surfaceCreated(SurfaceHolder holder) {
                // no-op -- wait until surfaceChanged()
            }

            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width,
                                       int height) {
                initPreview(width, height);
                startPreview();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                // no-op
            }

            @Override
            public void onShutter() {

            }
        };

        Camera.PictureCallback PictureCallbackRaw = new Camera.PictureCallback() {
            public void surfaceCreated(SurfaceHolder holder) {
                // no-op -- wait until surfaceChanged()
            }

            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width,
                                       int height) {
                initPreview(width, height);
                startPreview();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                // no-op
            }

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

            }
        };

        Camera.PictureCallback PictureCallbackJpeg = new Camera.PictureCallback() {
            public void surfaceCreated(SurfaceHolder holder) {
                // no-op -- wait until surfaceChanged()
            }

            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width,
                                       int height) {
                initPreview(width, height);
                startPreview();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                // no-op
            }

            @Override
            public void onPictureTaken(byte[] byteData, Camera camera) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 5;
                OutputStream bos = null;
                Bitmap m = BitmapFactory.decodeByteArray(byteData, 0, byteData.length, options);

                //m.compress(Bitmap.CompressFormat.PNG, 75, bos);
                //Log.v("CAPTURED", ""+bos);
            }
        };

        private Camera getCamera(String getCamera) {
            int cameraCount = 0;
            Camera cam = null;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if ((cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) && (getCamera == "front")) {
                    try {
                        cam = Camera.open(camIdx);
                    } catch (RuntimeException e) {
                        Log.e("TEST", "Camera failed to open: " + e.getLocalizedMessage());
                    }
                }
                else if ((cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) && (getCamera == "back")) {
                    try {
                        cam = Camera.open(camIdx);
                    } catch (RuntimeException e) {
                        Log.e("TEST", "Camera failed to open: " + e.getLocalizedMessage());
                    }
                }
            }
            return cam;
        }


    }

