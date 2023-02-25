package cn.lblbc.appstore.module.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.lblbc.appstore.R
import cn.lblbc.appstore.network.AppInfo
import cn.lblbc.appstore.network.NetworkRepository
import cn.lblbc.appstore.utils.EXTRA_KEY_APP_ID
import cn.lblbc.appstore.utils.Utils.downloadAndInstall
import cn.lblbc.lib.utils.launch
import cn.lblbc.lib.utils.loadImage
import kotlinx.android.synthetic.main.activity_app_detail.*
import kotlinx.android.synthetic.main.part_app_bottom.*


/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
class AppDetailActivity : AppCompatActivity() {
    private var appId = ""
    private var mAppInfo: AppInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_detail)
        appId = intent.getStringExtra(EXTRA_KEY_APP_ID) ?: ""
        initListeners()
        queryData()
    }

    private fun initListeners() {
        toolbar.setOnClickListener { finish() }
    }

    private fun queryData() {
        launch(
            action = { NetworkRepository.apiService.queryApp(appId) },
            onSuccess = { it?.data?.let { data -> processResponse(data) } }
        )
    }

    private fun processResponse(appInfo: AppInfo) {
        mAppInfo = appInfo
        descriptionTv.text = appInfo.description.replace("\\n", "\n")
        appNameTv.text = appInfo.name
        fileSizeTv.text = appInfo.fileSize
        downloadCountTv.text = appInfo.downloadCount
        loadImage(this, appIv, appInfo.logoUrl)
        initHorizontalRecyclerView(appInfo.screenshotUrls.split(","))
        installTv.setOnClickListener {
            downloadAndInstall(this, appInfo.apkUrl)
        }
    }

    private fun initHorizontalRecyclerView(screenShotUrls: List<String>) {
        horizontalImageViews.setData(screenShotUrls, 180f)
    }
}
