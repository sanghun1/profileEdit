package com.example.excamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    FragmentManager fragmentManager = getSupportFragmentManager();

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;

    GalleryFragment galleryFragment = new GalleryFragment();
    CameraFragment cameraFragment = new CameraFragment();

    ImageView border;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame, galleryFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.cam_btn).setOnClickListener(this);
        findViewById(R.id.img_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.cam_btn:
//                Animation borderLeft = new TranslateAnimation(0, 540, 0, 0);
//                borderLeft.setDuration(650);
//                borderLeft.setFillAfter(true);
//                border.setAnimation(borderLeft);
//                Toast.makeText(getApplicationContext(), "cam_btn 눌러짐",Toast.LENGTH_SHORT).show();
//                fragmentTransaction.replace(R.id.frame, cameraFragment).commit();
                cameraSlide(cameraFragment);
                break;

            case R.id.img_btn:
//                Toast.makeText(getApplicationContext(), "img_btn 눌러짐",Toast.LENGTH_SHORT).show();
//                fragmentTransaction.replace(R.id.frame, galleryFragment).commit();
                gallerySlide(galleryFragment);
                break;
        }
    }

    private void cameraSlide(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.camera_enter, R.anim.camera_exit, R.anim.camera_enter, R.anim.camera_exit);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void gallerySlide(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.gallery_enter, R.anim.gallery_exit, R.anim.gallery_enter, R.anim.gallery_exit);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    public int rotate(){
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0;
                break;

            case Surface.ROTATION_90: degrees = 90;
                break;

            case Surface.ROTATION_180: degrees = 180;
                break;

            case Surface.ROTATION_270: degrees = 270;
                break;
        }

        int result  = (90 - degrees + 360) % 360;

        return result;
    }


}