package com.example.zlf.test2;

/**
 * Created by zlf on 24/02/18.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.widget.Toast;
import android.media.FaceDetector;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class PhotoHandler implements PictureCallback {

    private final Context context;

    public PhotoHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;

        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);

        System.out.println("filename is "+ filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();

            Bitmap tmpbm = firstStep(filename);
            float ff = secondStep(tmpbm);
            if(ff>130.0){
                Toast.makeText(context, "You are too close to the screen" + ff,
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception error) {
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "ServiceCamera");
    }

    public Bitmap firstStep(String s)
    {
        //取图片并转为bitmap格式

        Bitmap sampleBmp=BitmapFactory.decodeFile(s);
        //FaceDetecor只能读取RGB 565格式的Bitmap，所以在开始识别前，我们需要将上面得到的Bitmap进行一次格式转换。
        Bitmap tmpBmp = sampleBmp.copy(Bitmap.Config.RGB_565, true);

        Matrix matrix = new Matrix();

        matrix.postRotate(-90);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(tmpBmp,tmpBmp.getWidth(),tmpBmp.getHeight(),true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        return rotatedBitmap;
    }

    public float secondStep(Bitmap bitmap) {
        //设定最大可查的人脸数量
        float Distance = (float) 0.0;
        int MAX_FACES = 1;
        FaceDetector faceDet = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), MAX_FACES);
        //将人脸数据存储到facelist中
        FaceDetector.Face[] faceList = new FaceDetector.Face[MAX_FACES];
        faceDet.findFaces(bitmap, faceList);

        //  FaceDetector API文档我们发现，它查找人脸的原理是：找眼睛。
        //  它返回的人脸数据face，通过调用public float eyesDistance ()，public void getMidPoint (PointF point)，
        //  我们可以得到探测到的两眼间距，以及两眼中心点位置（MidPoint）。
        //  public float confidence () 可以返回该人脸数据的可信度(0~1)，这个值越大，该人脸数据的准确度也就越高。
        RectF[] faceRects = new RectF[faceList.length];
        for (int i = 0; i < faceList.length; i++) {
            FaceDetector.Face face = faceList[i];
            Log.d("FaceDet", "Face [" + face + "]");
            if (face != null) {
                //confidence标识一个匹配度，在0~1区间，越接近1，表示匹配越高。如果需要可以加上这个判断条件
                //这里不做判断

                    Log.d("FaceDet", "Face [" + i + "] - Confidence [" + face.confidence() + "]");
                    //获取两眼中心点的坐标位置
                    Distance = face.eyesDistance();
                Log.d("FaceDet", "Face ["+i+"] - Confidence ["+face.confidence()+"]");
                //获取两眼中心点的坐标位置
                PointF pf = new PointF();

                face.getMidPoint(pf);
                //这里的框，参数分别是：左上角的X,Y  右下角的X,Y
                //也就是左上角（r.left,r.top），右下角( r.right,r.bottom)。作为定位，确定这个框的格局。
                RectF r = new RectF();
                r.left = pf.x - face.eyesDistance() / 2;
                r.right = pf.x + face.eyesDistance() / 2;
                r.top = pf.y - face.eyesDistance() / 2;
                r.bottom = pf.y + face.eyesDistance() / 2;
                faceRects[i] = r;
                //画框:对原图进行处理，并在图上显示人脸框。
                Canvas canvas = new Canvas(bitmap);
                Paint p = new Paint();
                p.setAntiAlias(true);
                p.setStrokeWidth(2);
                p.setStyle(Paint.Style.STROKE);
                p.setColor(Color.BLUE);
                //画一个圈圈
                canvas.drawCircle(r.left, pf.y, 10, p);
                canvas.drawCircle(r.right, pf.y, 10, p);
                //画框
                canvas.drawRect(r, p);
                //图片显示
                File pictureFileDir = getDir();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = dateFormat.format(new Date());
                String photoFile = "Picture_" + date + ".png";

                String filename = pictureFileDir.getPath() + File.separator + photoFile;

                File pictureFile = new File(filename);

                System.out.println("filename is "+ filename);


                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90,fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  Distance;
        }
        return Distance;
    }

}
