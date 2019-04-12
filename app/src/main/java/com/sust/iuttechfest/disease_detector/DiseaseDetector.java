package com.sust.iuttechfest.disease_detector;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.List;

import tensorflow.Classifier;
import tensorflow.TensorFlowImageClassifier;

public class DiseaseDetector implements Runnable {

    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 118;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "final_result";


    private static final String MODEL_FILE = "file:///android_asset/retrained_graph.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/retrained_labels.txt";

    public interface DiseaseDetectorListener {
        void onThreadComplete(List<Classifier.Recognition> results);
    }
    private DiseaseDetectorListener context;
    private AssetManager assetManager;
    private Bitmap bitmap;

    public DiseaseDetector(Context context,Bitmap bitmap) {
        this.context = (DiseaseDetectorListener) context;
        this.assetManager = context.getAssets();
        this.bitmap = bitmap;
    }

    public void detectDisease() {
        Classifier classifier = TensorFlowImageClassifier.create(assetManager,MODEL_FILE,LABEL_FILE,INPUT_SIZE,IMAGE_MEAN,IMAGE_STD,INPUT_NAME,OUTPUT_NAME);
        final List<Classifier.Recognition> results  = (classifier.recognizeImage(scaleImage(bitmap)));
        context.onThreadComplete(results);
    }

    public Bitmap scaleImage(Bitmap bitmap){
        int orignalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float scaleWidth = ((float)INPUT_SIZE)/orignalWidth;
        float scaleHeight = ((float)INPUT_SIZE)/originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap,0,0,orignalWidth,originalHeight,matrix,true);
        return scaledBitmap;
    }

    @Override
    public void run() {
        detectDisease();
    }
}
