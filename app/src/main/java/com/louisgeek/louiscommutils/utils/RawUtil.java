package com.louisgeek.louiscommutils.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by louisgeek on 2016/5/31.
 */
public class RawUtil {
    public static String getStringFromRaw(Context context,int rawID) {
        String result = "";
        try {
            InputStream ssq_is = context.getResources().openRawResource(rawID);

            InputStreamReader inputReader = new InputStreamReader(ssq_is);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
