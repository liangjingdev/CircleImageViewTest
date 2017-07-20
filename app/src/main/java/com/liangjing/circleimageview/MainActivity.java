package com.liangjing.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * 功能：将照片弄成圆形头像
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void process(View v){

        //获取照片bitmap(src)
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.image);
        /**
         * 1、 mOut为那个遮罩层,dst 等下我们的美女图片会画在这个遮罩层上面 如果遮罩层是圆的,我们画出来的就是遮罩层圆的那部分
         *    如果遮罩层是圆角矩形的,我们画出来的就是圆角矩形的 就是说我们的src会在dst的所属区域内画画,超出dst区域范围内的裁剪掉
         * 2、设置为Config.ARGB_8888表明mOut该Bitmap的像素是可以被外界改变的，也就是说我们的图片可以覆盖在该Bitmap上
         * 3、现在在遮罩层上面创建画布,现在遮罩层是什么都没有的
         */
        Bitmap mOut = Bitmap.createBitmap(mBitmap.getWidth(),mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mOut);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //把遮罩层的区域画出来
        canvas.drawCircle(mBitmap.getWidth()/2,mBitmap.getHeight()/2,mBitmap.getWidth()/2,mPaint);
        /**
         * 这里就是使用SRC_IN的方法把美女图片画到dst上,不属于dst的区域内的部分裁剪掉
         */

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        mPaint.setXfermode(null);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        //显示圆形头像（注意：这里用于显示出来的Bitmap是Dst那张Bitmap）
        imageView.setImageBitmap(mOut);
    }
}
