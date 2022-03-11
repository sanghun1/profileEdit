package com.example.excamera;

//import static com.gun0912.tedpermission.TedPermissionUtil.getSharedPreferences;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.List;

public class CameraFragment extends Fragment implements SurfaceHolder.Callback{

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;

    ImageView border;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("카메라 권한을 거부하셨습니다.")
                .setPermissions(Manifest.permission.CAMERA)
                .check();


        Button buttonStopCameraPreview = (Button)view.findViewById(R.id.stopcamerapreview);

        border = view.findViewById(R.id.trans_border);

        getActivity().getWindow().setFormat(PixelFormat.UNKNOWN);

        buttonStopCameraPreview.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(camera != null && previewing){
                    camera.stopPreview();
                    camera.release();
                    camera = null;

                    previewing = false;
                }
            }});

        return view;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        camera = Camera.open();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null){

            camera.setDisplayOrientation(((MainActivity) getActivity()).rotate());

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show();
            surfaceView = (SurfaceView)getActivity().findViewById(R.id.surfaceview);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(CameraFragment.this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            camera = Camera.open();

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            camera.setDisplayOrientation(((MainActivity) getActivity()).rotate());
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getContext(), "권한이 거부됨\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };



}