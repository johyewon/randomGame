package com.hanix.randomgame.common.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

/**
 * 카메라 정보 저장하면서 생성
 * 절대경로 가져오고 저장하기
 */
object RealPathUtil {
    /**
     * 절대경로 취득
     * @param context
     * @param uri
     * @return
     */
    fun getRealPath(context: Context, uri: Uri): String? {
        val realPath: String? =
            // SDK > 19 (Android 4.4)
            getRealPathFromUriApi19(context, uri)
        return realPath
    }

    /****  절대경로 api 버전별로 가져오기 시작  */
    @SuppressLint("NewApi")
    fun getRealPathFromUriApi19(context: Context, uri: Uri): String? {
        val isKitkat = true
        if (isKitkat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    id.toLong()
                )

                return getDataColumn(context, contentUri, null, null)

            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null

                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }

        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return if (isGooglePhotoUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )

        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }

        return null
    }

    /****  절대경로 api 버전별로 가져오기 시작  */
    private fun getDataColumn(
        context: Context,
        uri: Uri?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor =
                context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /****     uri 확인 시작      */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotoUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    } //****     uri 확인 끝     *****/
}