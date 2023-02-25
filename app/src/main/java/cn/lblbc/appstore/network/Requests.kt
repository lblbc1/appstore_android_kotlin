package cn.lblbc.appstore.network

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
class LoginRequest(var name: String = "", var password: String = "")
class SimpleCartItem(var goodsId: String = "", var quantity: Int = 0)

class AddToCartRequest(var goodsId: String = "")
class ModifyCartRequest(var quantity: Int)

class CreateOrderRequest(var simpleCartItemList: List<SimpleCartItem>, var addressId: String)

