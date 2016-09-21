/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 * 
 */
package com.ysp.houge.utility;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ysp.houge.app.Constants;

/**
 * This class is used for 把图片转化成string
 * 
 * @author tyn
 * @version 1.0, 2014-9-22 下午7:03:06
 */

public class PictureUtil {
	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;// 个人喜欢从80开始,
		bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 300) {
			baos.reset();
			options -= 10;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {
		int orientation = readPictureDegree(filePath);
		Bitmap bm = createBitmap(filePath, 800, 1280);
		if (bm == null) {
			return null;
		}
		bm = rotaingImageView(orientation, bm);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;// 个人喜欢从80开始,
		bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 300) {
			baos.reset();
			options -= 10;
			bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		byte[] b = baos.toByteArray();
		bm.recycle();
		bm = null;
		return Base64.encodeToString(b, Base64.DEFAULT);

	}

	public static String bitmapToThumbnail(Bitmap bm) throws FileNotFoundException {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		File fileOutPut = new File(Constants.DB_PATH, "wechatimg");
		FileOutputStream fos = new FileOutputStream(fileOutPut);
		bm.compress(Bitmap.CompressFormat.JPEG, options, fos);
		return fileOutPut.getPath();
	}

	/**
	 * @描述:压缩图片 @方法名: bitmapToThumbnail @param filePath @return
	 *          压缩后图片在SDCard路径 @throws FileNotFoundException @返回类型 String @创建人
	 *          wanliang @创建时间 2015年5月8日上午11:20:49 @修改人 wanliang @修改时间
	 *          2015年5月8日上午11:20:49 @修改备注 @since @throws
	 */
	public static String bitmapToThumbnail(String filePath) throws FileNotFoundException {
		File file = new File(filePath);

		int orientation = readPictureDegree(filePath);
		Bitmap bm = createBitmap(filePath, 800, 1280);
		if (bm == null) {
			return null;
		}
		bm = rotaingImageView(orientation, bm);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;// 个人喜欢从80开始,
		bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 300) {
			baos.reset();
			options -= 10;
			bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		File fileOutPut = new File(FileUtil.getTmpFolder(), file.getName());
		FileOutputStream fos = new FileOutputStream(fileOutPut);
		bm.compress(Bitmap.CompressFormat.JPEG, options, fos);
		return fileOutPut.getPath();
	}

	/**
	 * @描述:创建指定大小的图片 @方法名: createBitmapByPath @param path @param w @param
	 *               h @return @throws FileNotFoundException @返回类型 String @创建人
	 *               wanliang @创建时间 2015年5月8日上午11:29:16 @修改人 wanliang @修改时间
	 *               2015年5月8日上午11:29:16 @修改备注 @since @throws
	 */
	public static String createBitmapByPath(String path, int w, int h) throws FileNotFoundException {
		File file = new File(path);
		Bitmap bitmap = createBitmap(path, w, h);
		if (bitmap == null) {
			return null;
		}
		int orientation = readPictureDegree(path);
		bitmap = rotaingImageView(orientation, bitmap);
		File fileOutPut = new File(FileUtil.getTmpFolder(), file.getName());
		FileOutputStream fos = new FileOutputStream(fileOutPut);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		return fileOutPut.getPath();
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath, String compressionPath, String compressionName) {
		int orientation = readPictureDegree(filePath);
		Bitmap bm = createBitmap(filePath, 600, 1000);
		if (bm == null) {
			return null;
		}
		bm = rotaingImageView(orientation, bm);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;// 个人喜欢从80开始,
		bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 150) {
			baos.reset();
			options -= 20;
			bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		byte[] b = baos.toByteArray();
		getFile(b, compressionPath, compressionName);
		bm.recycle();
		bm = null;
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath, fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
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

	/**
	 * 根据路径获得图片并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap1(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * 获取裁剪后的圆形图片
	 * 
	 * @param radius
	 *            半径
	 */
	public static Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;
		int diameter = radius * 2;

		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图片
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);

		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2, scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap回收(recycle导致在布局文件XML看不到效果)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
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

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		System.out.println(angle + "");
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/** 保存方法 */
	public static String saveBitmap(Bitmap bm, String picName) {
		File f = new File(Constants.DB_PATH + File.separator, picName);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Constants.DB_PATH + File.separator + picName;
	}

	/**
	 * @描述: @方法名: saveImageToGallery @param context @param bmp @return @返回类型
	 *      String @创建人 tyn @创建时间 2015年5月19日上午9:22:57 @修改人 tyn @修改时间
	 *      2015年5月19日上午9:22:57 @修改备注 @since @throws
	 */
	public static String saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(FileUtil.getImageSaveFolder());
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		// 最后通知图库更新
		context.sendBroadcast(
				new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
		return file.getAbsolutePath();
	}

	/**
	 * @描述:获取相册的图片的路径 @方法名: getPictureStorePath @param context @param
	 *                selectedImage @return @返回类型 String @创建人 tyn @创建时间
	 *                2015年5月19日上午9:29:19 @修改人 tyn @修改时间
	 *                2015年5月19日上午9:29:19 @修改备注 @since @throws
	 */
	public static String getPictureStorePath(Context context, Uri selectedImage) {
		// 干什么用的，为什么环信的不用此数组
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex("_data");
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;
			if (picturePath == null || picturePath.equals("null")) {
				Toaster.getInstance().displayToast("找不到图片", -1);
				return null;
			} else {
				return picturePath;
			}
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				Toaster.getInstance().displayToast("找不到图片", -1);
				return null;
			}
			Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
			if (drawable == null) {
				Toaster.getInstance().displayToast("图片格式错误！", -1);
				return null;
			}
			return file.getAbsolutePath();
		}
	}

	public static Bitmap createQRImage(Context context, String code,int size) {
		Bitmap bitmap = null;
		try {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 0);
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(code, BarcodeFormat.QR_CODE,300,
                    300, hints);
			int[] pixels = new int[300 * 300];
			for (int y = 0; y < 300; y++) {
				for (int x = 0; x < 300; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * 300 + x] = 0xff000000;
					} else {
						pixels[y * 300 + x] = 0xffffffff;
					}
				}
			}
			// 生成二维码图片的格式，使用ARGB_8888
			bitmap = Bitmap.createBitmap(300, 300,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, 300, 0, 0, 300,
                    300);
		} catch (Exception e) {
			LogUtil.setLogWithE("createQRImage", e.getMessage());
		}
		return bitmap;
	}
}
