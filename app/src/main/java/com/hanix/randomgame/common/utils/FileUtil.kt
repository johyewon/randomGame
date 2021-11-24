package com.hanix.randomgame.common.utils

import android.content.Context
import java.io.*

object FileUtil {
    /**
     * 텍스트 - > 파일
     * @param fileFullPath
     * @param msg
     * @param isAppend
     */
    fun saveTextToFile(fileFullPath: String, msg: String?, isAppend: Boolean) {
        try {
            val file = File(fileFullPath)
            if (!file.exists()) {
                file.createNewFile()
            }
            val fw = FileWriter(file.absoluteFile, isAppend)
            val bw = BufferedWriter(fw)

            bw.write(msg)
            bw.newLine()
            bw.flush()
            bw.close()
            fw.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * txt 파일 불러오기
     * @param fileFullPath
     * @return
     */
    fun loadTextFile(fileFullPath: String?): StringBuffer {
        val sb = StringBuffer()

        try {
            BufferedReader(FileReader(fileFullPath)).use { br ->
                var line = br.readLine()

                while (line != null) {
                    sb.append(line)
                    sb.append(System.lineSeparator())
                    line = br.readLine()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return sb
    }

    /**
     * 파일 삭제하기
     * @param fileFullPath
     * @return
     */
    fun delFile(fileFullPath: String): Boolean {
        try {
            val existFile = File(fileFullPath)

            if (existFile.exists()) {
                return existFile.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 캐시파일 있는지 확인
     * @param context
     * @param fileName
     * @return
     */
    fun isCacheFileExist(context: Context, fileName: String): Boolean {
        return try {
            val existFile = File(context.cacheDir, fileName)
            existFile.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 캐시파일 삭제
     * @param context
     * @param fileName
     */
    fun deleteCacheFile(context: Context, fileName: String) {
        try {
            val file = File.createTempFile(fileName, null, context.cacheDir)
            file.delete()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 캐시파일 전체 삭제
     * @param context
     */
    fun deleteAllCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 폴더 삭제
     * @param dir
     * @return
     */
    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()!!
            for (child in children) {
                val success = deleteDir(File(dir, child))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }
}