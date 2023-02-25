package cn.lblbc.appstore.module.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.lblbc.appstore.R
import cn.lblbc.lib.utils.launch
import cn.lblbc.lib.view.LblViewPager
import cn.lblbc.lib.view.TabBean
import cn.lblbc.appstore.module.search.SearchActivity
import cn.lblbc.appstore.network.CategoryInfo
import cn.lblbc.appstore.network.NetworkRepository

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
class HomeFragment : Fragment() {
    private lateinit var lblViewPager: LblViewPager
    private lateinit var topSearchLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        lblViewPager = view.findViewById(R.id.lblViewPager)
        topSearchLayout = view.findViewById(R.id.topSearchLayout)
        topSearchLayout.setOnClickListener { gotoSearch() }
    }

    private fun gotoSearch() {
        startActivity(Intent(context, SearchActivity::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        queryData()
    }

    private fun queryData() {
        launch(
            action = { NetworkRepository.apiService.queryCategory() },
            onSuccess = { it?.data?.let { data -> processResponse(data) } }
        )
    }

    private fun processResponse(data: List<CategoryInfo>) {
        val tabBeanList = data.map { TabBean(it.id, it.name) }
        lblViewPager.init(requireActivity(), tabBeanList, 0) {
            AppListFragment.newInstance(it)
        }
    }
}