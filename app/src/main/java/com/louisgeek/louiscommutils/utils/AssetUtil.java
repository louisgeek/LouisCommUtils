package com.louisgeek.louiscommutils.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class AssetUtil {
	/**
	 * 打开Asset下的文件
	 * @param fileName 文件名
	 * @return InputStream
	 */
	public static InputStream openAssetFile(Context context, String fileName) {
		AssetManager am = context.getAssets();
		InputStream is = null;
		try {
			is = am.open(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

}
