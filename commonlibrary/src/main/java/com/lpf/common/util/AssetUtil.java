package com.lpf.common.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by liupengfei on 17/2/7.
 * <p>
 * read file from asset folder
 */

public class AssetUtil {

    Context mContext;

    public AssetUtil(Context context) {
        this.mContext = context;
    }

    public String getDataFromAssets(String fileName) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(mContext.getAssets().open(fileName));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
