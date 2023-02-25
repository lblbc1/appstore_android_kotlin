package cn.lblbc.appstore.module.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.lblbc.appstore.R
import cn.lblbc.appstore.module.detail.AppDetailActivity
import cn.lblbc.appstore.network.AppInfo
import cn.lblbc.appstore.network.NetworkRepository
import cn.lblbc.appstore.utils.EXTRA_KEY_APP_ID
import cn.lblbc.appstore.utils.Utils.downloadAndInstall
import cn.lblbc.lib.utils.launch
import cn.lblbc.lib.utils.loadImage
import cn.lblbc.lib.view.LblRecyclerView
import kotlinx.android.synthetic.main.item_app.view.*


/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
class AppListFragment(private var tabId: String) : Fragment() {
    private lateinit var lblRecyclerView: LblRecyclerView<AppInfo>
    private var downloadTaskId: Long? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_app_list, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        lblRecyclerView = view.findViewById(R.id.lblRecyclerView)
        lblRecyclerView.setLayoutResId { R.layout.item_app }
        lblRecyclerView.setOnBind { itemView, data ->
            itemView.appNameTv.text = data.name
            itemView.fileSizeTv.text = data.fileSize
            itemView.downloadCountTv.text = data.downloadCount
            loadImage(itemView.appIv, data.logoUrl)
            itemView.installTv.setOnClickListener {
                downloadAndInstall(requireContext(), data.apkUrl)
            }
        }

        lblRecyclerView.setOnItemClick { onItemClick(it) }
    }

    private fun onItemClick(it: AppInfo) {
        val intent = Intent(context, AppDetailActivity::class.java)
        intent.putExtra(EXTRA_KEY_APP_ID, it.id)
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        queryData()
    }

    private fun queryData() {
        launch(
            action = { NetworkRepository.apiService.queryByCategory(tabId) },
            onSuccess = { it?.data?.let { data -> processResponse(data) } }
        )
    }

    private fun processResponse(data: List<AppInfo>) {
        lblRecyclerView.setData(data)
    }

    companion object {
        fun newInstance(tabId: String): Fragment {
            return AppListFragment(tabId)
        }
    }
}