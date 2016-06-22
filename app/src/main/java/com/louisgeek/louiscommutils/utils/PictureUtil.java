package com.louisgeek.louiscommutils.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PictureUtil {


	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {
		int orientation = readPictureDegree(filePath);//获取旋转角度  
		Bitmap bm = getSmallBitmap(filePath);
		if(Math.abs(orientation) > 0){  
			bm =  rotaingImageView(orientation, bm);//旋转图片  
		}  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		
		return Base64.encodeToString(b, Base64.DEFAULT);
		
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
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
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
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
	 * 根据路径删除图片
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 添加到图库
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	/**
	 * 获取保存图片的目录
	 * 
	 * @return
	 */
	public static File getAlbumDir() {
		File dir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				getAlbumName());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 获取保存 隐患检查的图片文件夹名称
	 * 
	 * @return
	 */
	public static String getAlbumName() {
		return "sheguantong";
	}
	
	/**
	 * 获取图片角度
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {  
	    int degree  = 0;  
	    try {  
	        ExifInterface exifInterface = new ExifInterface(path);  
	        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
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
	 * 旋转图片
	 * @param angle
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {  
	    //旋转图片 动作  
	    Matrix matrix = new Matrix();;  
	    matrix.postRotate(angle);  
	    System.out.println("angle2=" + angle);  
	    // 创建新的图片  
	    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
	            bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
	    return resizedBitmap;  
	}  
}
