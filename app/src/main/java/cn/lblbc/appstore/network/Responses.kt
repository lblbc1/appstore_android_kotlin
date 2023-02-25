package cn.lblbc.appstore.network

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
class DefaultResp(var code: Int, var msg: String, var data: String?)

class QueryAppsByCategoryResp(var code: Int, var msg: String, var data: List<AppInfo>?)

class AppInfo(
    var id: String = "",
    var name: String = "",
    var logoUrl: String = "",
    var screenshotUrls: String = "",
    var description: String = "",
    var apkUrl: String = "",
    var fileSize: String = "",
    var downloadCount: String = ""
)

class CategoryInfo(val id: String = "", val name: String = "")
class QueryCategoryResp(var code: Int, var msg: String, var data: List<CategoryInfo>?)

class QueryAppResp(var code: Int, var msg: String, var data: AppInfo?)

class SearchAppsResp(var code: Int, var msg: String, var data: List<AppInfo>?)