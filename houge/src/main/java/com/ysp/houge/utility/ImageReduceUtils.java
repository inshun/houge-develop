package com.ysp.houge.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

/**
 * 描述： 图片压缩工具类
 *
 * @ClassName: ImageReduceUtils
 * 
 * @author: hx
 * 
 * @date: 2015年12月24日 下午3:12:58
 * 
 *        版本: 1.0
 */
public class ImageReduceUtils {
	/** 压缩图片，捕获压缩异常,出现异常返回原路径 */
	public static String compression(String filePath) {
        //这个会涉及内存溢出的时候的处理
        if (TextUtils.isEmpty(filePath)){
            return filePath;
        }
		try {
			filePath = ImageReduceUtils.bitmapToThumbnail(filePath);
		} catch (FileNotFoundException e) {
			// 压缩失败直接返回原路径
			LogUtil.setLogWithE("compression", "compression ERROR with the message:" + e.getMessage());
		}
		return filePath;
	}

	/** 压缩图片 */
	public static String bitmapToThumbnail(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		int orientation = readPictureDegree(filePath);
        Bitmap bm;
        try{
		    bm = createBitmap(filePath, 768, 1280);
        }catch (OutOfMemoryError e){
            return "";
        }catch (Exception e){
            return "";
        }
		if (bm == null) {
			return null;
		}
		bm = rotaingImageView(orientation, bm);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 80;// 个人喜欢从80开始,
		bm.compress(CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 200 && options > 15) {
			baos.reset();
			options -= 5;
			if (options < 15) {
				options = 15;
			}
			bm.compress(CompressFormat.JPEG, options, baos);
		}
		File fileOutPut = new File(FileUtil.getTmpFolder(), file.getName());
		FileOutputStream fos = new FileOutputStream(fileOutPut);
		bm.compress(CompressFormat.JPEG, options, fos);
		return fileOutPut.getPath();
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 根据宽度和长度进行缩放图片
	 * 
	 * @param path
	 *            图片的路径
	 * @param w
	 *            宽度
	 * @param h
	 *            长度
	 * @return
	 */
	public static Bitmap createBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		// 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存。
		BitmapFactory.decodeFile(path, opts);
		int srcWidth = opts.outWidth;// 获取图片的原始宽度
		int srcHeight = opts.outHeight;// 获取图片原始高度
		int destWidth = 0;
		int destHeight = 0;
		// 缩放的比例
		double ratio = 0.0;
		if (srcWidth < w || srcHeight < h) {
			ratio = 0.0;
			destWidth = srcWidth;
			destHeight = srcHeight;
		} else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长度
			ratio = (double) srcWidth / w;
			destWidth = w;
			destHeight = (int) (srcHeight / ratio);
		} else {
			ratio = (double) srcHeight / h;
			destHeight = h;
			destWidth = (int) (srcWidth / ratio);
		}
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
		newOpts.inSampleSize = (int) ratio + 1;
		// inJustDecodeBounds设为false表示把图片读进内存中
		newOpts.inJustDecodeBounds = false;
		// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
		newOpts.outHeight = destHeight;
		newOpts.outWidth = destWidth;
		// 获取缩放后图片
		return BitmapFactory.decodeFile(path, newOpts);
	}

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

}
