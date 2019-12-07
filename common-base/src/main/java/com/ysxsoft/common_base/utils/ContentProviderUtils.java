package com.ysxsoft.common_base.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

public class ContentProviderUtils{
    private static final String TAG = "ContentProviderUtils";
    public static ArrayList<FileInfo> getAllVideo(Context context) {
        ArrayList<FileInfo> videos = new ArrayList<FileInfo>();
        String[] mediaColumns = new String[]{
                MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.DISPLAY_NAME,
                MediaStore.Video.VideoColumns.DURATION
        };
        ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);
        if (cursor == null) return videos;
        if (cursor.moveToFirst()) {
            do {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                File file = new File(path);
                boolean canRead = file.canRead();
                long length = file.length();
                if (!canRead || length == 0) {
                    continue;
                }
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));

                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                if (duration < 0) {
                    duration = 0;
                }
                FileInfo fileItem = new FileInfo();
                fileItem.setFilePath(path);
                fileItem.setFileName(name);
                fileItem.setDuration(duration);
                fileItem.setFileType(FileInfo.FILE_TYPE_VIDEO);
                if (fileItem.getFileName() != null && fileItem.getFileName().endsWith(".mp4")) {
                    videos.add(fileItem);
                }
                LogUtils.d(TAG, "fileItem = " + fileItem.toString());
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return videos;
    }

    public static ArrayList<FileInfo> getAllPictrue(Context context) {
        ArrayList<FileInfo> pictureList = new ArrayList<FileInfo>();
        String[] mediaColumns = new String[]{
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DESCRIPTION
        };
        ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
        while (cursor.moveToNext()){
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            File file = new File(path);
            boolean canRead = file.canRead();
            long length = file.length();
            if (!canRead || length == 0) {
                continue;
            }
            FileInfo fileItem = new FileInfo();
            fileItem.setFilePath(path);
            fileItem.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
            fileItem.setFileType(FileInfo.FILE_TYPE_PICTURE);
            pictureList.add(fileItem);
        }
        if (cursor != null) {
            cursor.close();
        }
        return pictureList;
    }

    public static class FileInfo{
        public static final int FILE_TYPE_VIDEO = 0;
        public static final int FILE_TYPE_PICTURE = 1;
        private String filePath;
        private String fileName;
        private int fileType;
        private long duration;
        private boolean isSelected = false;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getFilePath() {
            return filePath == null ? "" : filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFileName() {
            return fileName == null ? "" : fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }
    }
}
