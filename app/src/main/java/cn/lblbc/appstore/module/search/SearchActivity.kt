package cn.lblbc.appstore.module.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.lblbc.appstore.R
import cn.lblbc.lib.utils.*
import cn.lblbc.lib.view.LblRecyclerView
import cn.lblbc.appstore.module.detail.AppDetailActivity
import cn.lblbc.appstore.network.AppInfo
import cn.lblbc.appstore.network.NetworkRepository
import cn.lblbc.appstore.utils.EXTRA_KEY_APP_ID
import cn.lblbc.appstore.utils.Utils
import kotlinx.android.synthetic.main.item_app.view.*
import kotlinx.android.synthetic.main.part_search_top.*

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
class SearchActivity : AppCompatActivity() {
    private lateinit var lblRecyclerView: LblRecyclerView<AppInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViews()
        initListeners()
    }

    private fun initViews() {
        lblRecyclerView = findViewById(R.id.lblRecyclerView)
        lblRecyclerView.setLayoutResId { R.layout.item_app }
        lblRecyclerView.setOnBind { itemView, data ->
            itemView.appNameTv.text = data.name
            itemView.fileSizeTv.text = data.fileSize
            itemView.downloadCountTv.text = data.downloadCount
            loadImage(this, itemView.appIv, data.logoUrl)
            itemView.installTv.setOnClickListener {
                Utils.downloadAndInstall(this, data.apkUrl)
            }
        }
        lblRecyclerView.setOnItemClick { onItemClick(it) }
    }

    private fun initListeners() {
        backIv.setOnClickListener { finish() }
        searchEt.onSearchKeyDown { search() }
        searchTv.setOnClickListener { search() }
    }

    private fun search() {
        hideSoftKeyboard(this)
        val keyword = searchEt.text.toString()

        launch(
            action = { NetworkRepository.apiService.search(keyword) },
            onSuccess = { it?.data?.let { data -> processResponse(data) } }
        )
    }

    private fun processResponse(data: List<AppInfo>) {
        lblRecyclerView.setData(data)
    }

    private fun onItemClick(appInfo: AppInfo) {
        val intent = Intent(this, AppDetailActivity::class.java)
        intent.putExtra(EXTRA_KEY_APP_ID, appInfo.id)
        startActivity(intent)
    }
}
