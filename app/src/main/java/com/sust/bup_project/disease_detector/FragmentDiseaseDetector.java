package com.sust.bup_project.disease_detector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.sust.bup_project.BuildConfig;
import com.sust.bup_project.R;

import java.io.File;
import java.io.IOException;

public class FragmentDiseaseDetector extends Fragment {

    public interface DiseaseDetectorListeners {
        void detect(Bitmap bitmap,View view);
    }
    DiseaseDetectorListeners diseaseDetector;


    private static final int PHOTO_REQUEST_CAMERA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final String PHOTO_FILE_NAME = "temp_pic.jpg";

    private static String[] SELECT_BUTTON_TEXT = {"Choose Photo","Take Photo"};
    private static int GALLERY = 0;
    private static int CAMERA = 1;
    private Button selectPhotoButton;
    private Button detectDiseaseButton;
    private ImageView selectedImageView;
    private RadioGroup selectModeRadioGroup;

    private int radioButtonState = 1;
    private File tempFile;
    private Bitmap bitmap;

    public int getRadioButtonState() {
        return radioButtonState;
    }

    public void setRadioButtonState(int radioButtonState) {
        this.radioButtonState = radioButtonState;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        diseaseDetector = (DiseaseDetectorListeners) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disease_detector_layout,container,false);
        initialiazeViews(view);
        setUpRadioGroup();

        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getRadioButtonState() == CAMERA) {
                    camera(view);
                }
                else if(getRadioButtonState() == GALLERY) {
                    gallery(view);
                }
            }
        });

        detectDiseaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap != null)
                diseaseDetector.detect(bitmap,view);
                else Snackbar.make(view,"ERROR: NO PHOTOS WERE SELECTED",Snackbar.LENGTH_LONG).show();
            }
        });
        return view;
    }


    private void gallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private void camera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            try {
                tempFile = new File(getActivity().getApplicationContext().getExternalFilesDir("files").getAbsolutePath(), PHOTO_FILE_NAME);
                Uri uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", tempFile);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                } catch (Exception e) {
                e.printStackTrace();
            }
            startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_CAMERA) {
                Uri uri = Uri.fromFile(tempFile);
                bitmap = BitmapFactory.decodeFile(uri.getPath());
                selectedImageView.setImageBitmap(bitmap);
        }
        else if (requestCode == PHOTO_REQUEST_GALLERY){
            if (data!=null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectedImageView.setImageBitmap(bitmap);
            }
            }
        else if (requestCode == PHOTO_REQUEST_CUT){
            if(data!=null){
                Bitmap bitmap = data.getParcelableExtra("data");
                selectedImageView.setImageBitmap(bitmap);
            }
            try {
                tempFile.delete();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpRadioGroup() {
        selectModeRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.select_camera_radio_button) {
                    setViewText(selectPhotoButton, SELECT_BUTTON_TEXT[CAMERA]);
                    setRadioButtonState(CAMERA);
                }
                else if (checkedId == R.id.select_gallery_radio_button) {
                    setViewText(selectPhotoButton, SELECT_BUTTON_TEXT[GALLERY]);
                    setRadioButtonState(GALLERY);
                }
            }
        });
    }

    private void setViewText(TextView view, String text) {
        view.setText(text);
    }

    private void initialiazeViews(View view) {
        detectDiseaseButton = view.findViewById(R.id.detect_disease_button);
        selectPhotoButton = view.findViewById(R.id.select_photo_button);
        selectedImageView = view.findViewById(R.id.selected_image_view);
        selectModeRadioGroup = view.findViewById(R.id.select_mode_radio_group);
    }
}
