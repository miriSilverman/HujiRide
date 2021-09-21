package huji.postpc.year2021.hujiride.Onboarding;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.List;

import huji.postpc.year2021.hujiride.R;

public class CameraActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2{
    private static final String TAG="MainActivity";

    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;
    private ImageView read_but;
    private ImageView cam_but;
    private String txt_read="";
    private String barcode_read="";
    private Boolean barcode_find=false;
    private Boolean text_find=false;

    private Boolean isStud=false;



    private ImageView current_img;
    private TextView current_txt;
    private Boolean tookpic=false;

    private Toast compareToast;

    private TextRecognizer textRecognizer;
    private boolean cam_time=true;

    private Bitmap bitmap=null;
    BarcodeScannerOptions options;


    private BaseLoaderCallback mLoaderCallback =new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface
                        .SUCCESS:{
                    Log.i(TAG,"OpenCv Is loaded");

                    mOpenCvCameraView.enableView();
//                    mOpenCvCameraView.enableFpsMeter();

                }
                default:
                {
                    super.onManagerConnected(status);

                }
                break;
            }
        }
    };

    public CameraActivity(){
        Log.i(TAG,"Instantiated new "+this.getClass());
    }

    private void scan()
    {

        InputImage image=InputImage.fromBitmap(bitmap,0);
        //setting up to read barcodes

        options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_CODE_128)
                        .build();

        BarcodeScanner scanner = BarcodeScanning.getClient();

        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        if (!barcodes.isEmpty()) {
                            barcode_find=true;
                            barcode_read= barcodes.get(0).getRawValue();

//                                            String rawValue =
//                                            String all = " id read =" + rawValue;
//                                            current_txt.setText(all);
//                            Toast toast =Toast.makeText(getApplicationContext(),"found barcode...",Toast.LENGTH_SHORT);
//                            toast.show();


                            //setting up to read text
                            Task<Text> results= textRecognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess( Text text) {
                                    //showing the text that was returned
                                    text_find=true;
                                    txt_read=text.getText();
//                                String all = current_txt.getText()+"\n" +text.getText();
//                                current_txt.setText(all);
                                    Log.d("read",text.getText());
//                                    compareToast =Toast.makeText(getApplicationContext(),"comparing text...",Toast.LENGTH_SHORT);
//                                    compareToast.show();


                                    //comparing barcode to id, and searching for key words
                                    if (text_find && barcode_find)
                                    {
                                        if (txt_read.contains(barcode_read) && txt_read.contains("THE HEBREW UNIVERSITY OF JERUSALEM") && txt_read.contains("STUDENT CARD")){
                                              isStud=true;
                                              foundStudCard();
//                                            Toast toaster =Toast.makeText(getApplicationContext(),"VERTIFIED STUDENT!",Toast.LENGTH_SHORT);
//                                            toaster.show();
//                                            setResult(RESULT_OK);
//                                            finish();

                                        }
                                        else {
                                            no_Student_card_detected();
                                        }

                                    }
                                    else {
                                        no_Student_card_detected();
                                    }



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    text_find=false;
                                    txt_read=null;
                                    Log.d("read","here");
                                    no_Student_card_detected();

                                }
                            });



                        }
                        else {

                            barcode_find=false;

                            Log.e("NOAM","couldntRead");
                            no_Student_card_detected();

                            //    current_txt.setText("No Barcode Found!!!!");

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                                        barcode_read="NO";
                        barcode_find=false;
                        no_Student_card_detected();
                        // current_txt.setText("not a card!!!");



                    }
                });


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int MY_PERMISSIONS_REQUEST_CAMERA=0;
        // if camera permission is not given it will ask for it on device
        if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){

            Toast toast = Toast.makeText(this, "denied", Toast.LENGTH_LONG);
            toast.show();
            ActivityCompat.requestPermissions(CameraActivity.this, new String[] {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        setContentView(R.layout.activity_camera);

        mOpenCvCameraView=(CameraBridgeViewBase) findViewById(R.id.frame_Surface);
        mOpenCvCameraView.setCameraPermissionGranted();

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);


//        current_txt=findViewById(R.id.textView);
//        current_txt.setVisibility(View.GONE);

        current_img=findViewById(R.id.cuttrnt_image);
        current_img.setVisibility(View.GONE);

        //loading the textReco
        this.textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        this.cam_but =findViewById(R.id.camera_button);
        cam_but.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    return true;
                }
                if (event.getAction()==MotionEvent.ACTION_UP)
                {
                    if (cam_time)
                    {
                        tookpic=true;
                        //this parts convert currnet frame to bitmap and stop showing camera live
                        cam_but.setColorFilter(Color.DKGRAY);
                        Mat a= mRgba.t();
                        Core.flip(a,mRgba,1);
                        a.release();
                        bitmap=Bitmap.createBitmap(mRgba.cols(),mRgba.rows(),Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(mRgba,bitmap);
                        mOpenCvCameraView.disableView();
                        cam_time=false;
                        scan();
                    }
                    return true;
                }
                return false;
            }
        });



    }

    @Override
    public void onBackPressed() {
        //when pressing back after photo was taken -> user can take pic again
        if (isStud)
        {
            setResult(RESULT_OK);
        }
        else {
            setResult(RESULT_CANCELED);
        }
        finish();


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            //if load success
            Log.d(TAG,"Opencv initialization is done");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            //if not loaded
            Log.d(TAG,"Opencv is not loaded. try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this,mLoaderCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }

    }

    public void onCameraViewStarted(int width ,int height){
        mGray =new Mat(height,width,CvType.CV_8UC1);
        mRgba=new Mat(height,width, CvType.CV_8UC4);


    }
    public void onCameraViewStopped(){
        mRgba.release();
    }
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mGray=inputFrame.gray();
        mRgba=inputFrame.rgba();

        return mRgba;

    }

    private void foundStudCard(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.found_card_dialog, null)).setTitle("Verified student!")
                .setMessage("\n\nFound a student card of The Hebrew University of Jerusalem\n\nid: "+  barcode_read.toString())
                .setPositiveButton("Thanks, Lets continue!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setResult(RESULT_OK);
                        finish();


                    }
                });


        AlertDialog alert = builder.create();

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {}
        });

        alert.show();

    }

    private void no_Student_card_detected()
    {
//        //this functions show pop-up when no student card is found
//        if (compareToast!=null)
//        {
//            compareToast.cancel();
//
//        }
////        Toast toaster =Toast.makeText(getApplicationContext(),"DID NOT DETECT A STUDENT CARD, PLEASE TRY AGAIN!",Toast.LENGTH_LONG);
////        toaster.show();
//        // inflate the layout of the popup window
//        LayoutInflater inflater = (LayoutInflater)
//                getSystemService(LAYOUT_INFLATER_SERVICE);
//        View popupView = inflater.inflate(R.layout.popup_notstud, null);
//
//        // create the popup window
//        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        boolean focusable = true; // lets taps outside the popup also dismiss it
//        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//
//        // dismiss the popup window when touched
//        popupView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                popupWindow.dismiss();
//                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
//                finish();
//                //todo: go back to before activity as not student
//
//                return true;
//            }
//        });

//        setResult(RESULT_CANCELED);
//        finish();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.found_card_dialog, null)).setTitle("No Student Card Found")
                .setMessage("\n\nSorry, the picture does not match a student id card. It might have been too blurry...")
                .setPositiveButton("Try Again!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recreate();

                    }
                })
                .setNegativeButton("Give up:(", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {}
                });

        AlertDialog alert = builder.create();

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {}
        });

        alert.show();


    }

}
