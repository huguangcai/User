package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nanchen.compresshelper.CompressHelper;

import java.io.File;

/**
 * create by Sincerly on 2019/1/16 0016
 **/
public class ImageUtils {
    /**
     * 压缩图片文件
     * @param context
     * @param name      输出文件名
     * @param oldFile  源文件
     * @param dirPath  输出路径
     * @return
     */
    public static String compress(Context context, String name, File oldFile,String dirPath) {
        String path = "";
        File newFile = new CompressHelper.Builder(context)
                .setQuality(100)    // 默认压缩质量为80
                .setFileName(name) // 设置你需要修改的文件名
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(dirPath)
                .build()
                .compressToFile(oldFile);
        path = newFile.getPath();
        return path;
    }
}
