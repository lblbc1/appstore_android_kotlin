package cn.lblbc.appstore.utils

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import cn.lblbc.appstore.BuildConfig
import cn.lblbc.lib.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object Utils {
    fun downloadAndInstall(context: Context, apkUrl: String) {
        ToastUtil.toast("下载中...")
        val downloadFiePath = Environment.getExternalStorageDirectory().path + "/Download/" + Utils.generateDownloadApkName()
        GlobalScope.launch(Dispatchers.IO) {
            Utils.download(apkUrl, File(downloadFiePath))
            install(context, downloadFiePath)
        }
    }

    fun download(urlStr: String, f: File): String? {
        var result: String? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(urlStr)
            connection = url.openConnection() as HttpURLConnection
            connection.setRequestMethod("GET")
            connection.setConnectTimeout(8000)
            connection.setReadTimeout(5000)
            val statusCode: Int = connection.getResponseCode()
            if (statusCode == 200) {
                val `in`: InputStream = connection.getInputStream()
                if (f.exists()) f.delete()
                f.createNewFile()
                val out = FileOutputStream(f)
                val buf = ByteArray(1024)
                var j = 0
                while (`in`.read(buf).also { j = it } != -1) out.write(buf, 0, j)
                out.flush()
                out.close()
                `in`.close()
                result = """
                Download OK.
                From: $urlStr
                To: ${f.getPath()}
                """.trimIndent()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (connection != null) connection.disconnect()
        }
        return result
    }

    fun install(context: Context, apkFilePath: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val file = File(apkFilePath)
        val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".FileProvider", file)
        val type = "application/vnd.android.package-archive"
        intent.setDataAndType(uri, type)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }

    fun generateDownloadApkName(): String {
        return getUUID() + ".apk"
    }

    fun getUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}