package cn.lblbc.appstore.network

import retrofit2.http.*

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
interface ApiService {
    @GET("appstore/apps")
    suspend fun queryByCategory(@Query("categoryId") categoryId: String): QueryAppsByCategoryResp?

    @GET("appstore/categories")
    suspend fun queryCategory(): QueryCategoryResp?

    @GET("appstore/app/{id}")
    suspend fun queryApp(@Path("id") id: String): QueryAppResp?

    @GET("appstore/appsBySearch")
    suspend fun search(@Query("keyword") keyword: String): SearchAppsResp?
}