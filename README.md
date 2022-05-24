# AndroidKitProject

[![](https://jitpack.io/v/com.gitee.zhuminjun/AndroidKit.svg)](https://jitpack.io/#com.gitee.zhuminjun/AndroidKit)

## 介绍

安卓 快速开发工具，封装了一些常用的基类、工具类、自定义控件，预装了一些常用的第三方库

## 环境

1.  Android Studio Bumblebee | 2021.1.1 Patch 1
2.  Gradle 7.3.3
3.  Gradle plugin 7.2.0
4.  Kotlin 1.6.21

## 架构

ViewBinding + MVP

## 使用

###### 在 Kotlin 项目上引用

1.  在 settings.gradle 里面
    
    ```
    pluginManagement {
        repositories {
            ...
        }
    }
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            ...
            // CompressHelper 和 PhotoViewer 这两个第三方需要添加下面这行配置
            maven { url 'https://jitpack.io' }
        }
    }
    rootProject.name = "AndroidKitProject"
    include ':app'
    
    // 添加这个
    include ':AndroidKit'
    ```
    
2.  在项目 build.gradle 里面
    
    ```
    plugins {
        ...
    }
    
    // 添加这一行启用 config.gradle 公共配置
    apply from: './AndroidKit/config.gradle'
    
    task clean(type: Delete) {
        delete rootProject.buildDir
    }
    ```
    
3.  在应用 build.gradle 里面
    
    ```
    android {
        // AndroidKit 使用的 ViewBinding，应用也应使用 ViewBinding
        buildFeatures {
            viewBinding true
        }
    }
    dependencies {
        // 最好把其余的删掉只保留这一行，因为 AndroidKit 已经使用 api 把需要的基础包添加进来了，
        // 依赖了 AndroidKit 相当于项目配置了那些基础包
        implementation project(':AndroidKit')
    }
    ```
    
**或者**

1.  在 settings.gradle 里面

    ```
    pluginManagement {
        repositories {
            ...
        }
    }
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            ...
            // AndroidKit、CompressHelper 和 PhotoViewer 这几个第三方需要添加下面这行配置
            maven { url 'https://jitpack.io' }
        }
    }
    rootProject.name = "AndroidKitProject"
    include ':app'
    ```

2.  在项目 build.gradle 里面

    ```
    plugins {
        ...
    }
    
    // 添加这一行启用 config.gradle 公共配置
    apply from: './AndroidKit/config.gradle'
    
    task clean(type: Delete) {
        delete rootProject.buildDir
    }
    ```

3.  在应用 build.gradle 里面

    ```
    android {
        // AndroidKit 使用的 ViewBinding，应用也应使用 ViewBinding
        buildFeatures {
            viewBinding true
        }
    }
    dependencies {
        // 最好把其余的删掉只保留这一行，因为 AndroidKit 已经使用 api 把需要的基础包添加进来了，
        // 依赖了 AndroidKit 相当于项目配置了那些基础包
        implementation 'com.gitee.zhuminjun:AndroidKit:1.0.2'
    }
    ```

###### 在 JAVA 项目上引用

    **在 Kotlin 项目上引用** 的基础上

1.  在项目 build.gradle 里面

    ```
    plugins {
        ...
        // 添加 Kotlin 插件
        id 'org.jetbrains.kotlin.android' version '1.6.20' apply false
    }
    ```

2.  在应用 build.gradle 里面

    ```
    plugins {
        ...
        // 添加 Kotlin 插件
        id 'org.jetbrains.kotlin.android'
    }
    ```

## 功能

#### base

###### BaseActivity

AppCompatActivity 基类

```
protected val binding: ViewBinding
protected var presenter: BasePresenter

initToolBar(toolbar: Toolbar?, isShowBack: Boolean/*是否显示返回键*/, isShowTitle: Boolean/*是否显示标题*/): 加载工具栏

protected abstract fun getViewBinding(): T //加载ViewBinding
protected abstract fun obtainPresenter(): P? //加载Presenter

/**
 * 是否注册事件分发
 * @return true 注册；false 不注册，默认不注册
 */
open fun isRegisteredEventBus(): Boolean = false

/**
 * 接收到Eventbus分发的事件
 * @param event 事件
 */
@Subscribe(threadMode = ThreadMode.MAIN)
open fun onReceiveEvent(event: EventMessage<*>) {
}

/**
 * 接收到Eventbus分发的粘性事件
 * @param event 粘性事件
 */
@Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
open fun onReceiveStickyEvent(event: EventMessage<*>) {
}
```

###### BaseFragment

Fragment 基类

```
protected val binding: ViewBinding
protected var presenter: BasePresenter

initToolBar(toolbar: Toolbar?, isShowBack: Boolean/*是否显示返回键*/, isShowTitle: Boolean/*是否显示标题*/): 加载工具栏

protected abstract fun getViewBinding(): T //加载ViewBinding
protected abstract fun obtainPresenter(): P? //加载Presenter

/**
 * 是否注册事件分发
 * @return true 注册；false 不注册，默认不注册
 */
open fun isRegisteredEventBus(): Boolean = false

/**
 * 接收到Eventbus分发的事件
 * @param event 事件
 */
@Subscribe(threadMode = ThreadMode.MAIN)
open fun onReceiveEvent(event: EventMessage<*>) {
}

/**
 * 接收到Eventbus分发的粘性事件
 * @param event 粘性事件
 */
@Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
open fun onReceiveStickyEvent(event: EventMessage<*>) {
}

//销毁 Fragment 时，可能需要做些 解除广播绑定，服务之类的鬼东西
protected abstract fun onDestroyViewAndThing()
```

###### BasePresenter

MVP 中的 Presenter 基类

#### builder

###### ShapeBuilder

shape构造器，使用这个可以直接代码设置 shape 和 selector

```
setShapeType：设置 Shape 样式
    enum class ShapeType { RECTANGLE, OVAL, LINE, RING }
setShapeSolidColor：设置底色
setShapeCornersRadius：设置四角倒圆角半径
setShapeCornersTopLeftRadius：设置左上角倒圆角半径
setShapeCornersTopRightRadius：设置右上角倒圆角半径
setShapeCornersBottomRightRadius：设置右下角倒圆角半径
setShapeCornersBottomLeftRadius：设置左下角倒圆角半径
setShapeStrokeWidth：设置边线宽
setShapeStrokeColor：设置边线颜色
setShapeStrokeDashWidth：设置虚线宽度
setShapeStrokeDashGap：设置虚线间隙
setShapeUseSelector：设置是否启用Selector
setShapeSelectorPressedColor：设置点击状态颜色
setShapeSelectorNormalColor：设置默认状态颜色
setShapeSelectorDisableColor：设置禁用状态颜色
setShapeSizeWidth：设置宽度
setShapeSizeHeight：设置高度
setShapeGradientAngle：设置渐变角度
setShapeGradientCenterX：设置渐变中心X
setShapeGradientCenterY：设置渐变中心Y
setShapeGradientRadius：设置渐变半径/范围
setShapeGradientStartColor：设置渐变开始颜色
setShapeGradientCenterColor：设置渐变中心颜色
setShapeGradientEndColor：设置渐变结束颜色
setShapeGradientType：设置渐变样式
    enum class GradientType { LINEAR, RADIAL, SWEEP }
setShapeGradientUseLevel：设置渐变等级

into(view: View)：对 view 启用以上设置
```

###### ToastBuilder

自定义Toast构造器，可设置类型（默认、成功、失败、消息、警告）、背景颜色、倒角、显示位置、文本颜色和大小
**@Deprecated(message = "Android 11（API 30）之后 Toast 不能自定义了，请使用 SnackBarExt 里面的 Snackbar.showToast 方法代替")**

```
setToastType：设置类型，可看 ToastType
    enum class ToastType { NORMAL, SUCCESS, INFO, WARNING, ERROR }
setBgColor：设置背景颜色
setMessage：设置文本
setTextColor：设置文本颜色
setTextSize：设置文字大小
setIconId：设置图标资源ID
setIconWidth：设置图标宽度
setIconHeight：设置图标高度
setRadius：设置四角倒圆角半径
setGravity：设置显示位置
setDuration：设置显示时间

create()：启用以上设置并生成Toast
```

#### eventbus

###### EventBusUtil

EventBus 工具类

```
register：注册
unregister：解除注册
post：发送普通事件
postSticky：发送粘性事件
removeSticky：移除指定粘性事件
removeAllSticky：移除全部粘性事件
```

###### EventMessage

EventBus 数据实体

#### extend

###### SnackBarExt

SnackBar 扩展，新增显示 Toast 样式的 SnackBar 的方法

```
make：创建 SnackBar
showToast：显示 Toast 样式的 SnackBar，调用这个方法前最好是先调用 SnackBarExt 下面的 make 方法，因为正常的 SnackBar.make() 方法会有警告，强迫症者慎用
```

使用

```
SnackBarExt.make(view, "SnackBar Test", Snackbar.LENGTH_SHORT)
    .showToast(
        toastType = SnackBarExt.ToastType.SUCCESS,
        gravity = Gravity.CENTER// 自定义位置：居中显示
    )
```

#### holder

###### BaseViewHolder

ViewHolder 基类

###### BaseViewBindingHolder

继承 BaseViewHolder，方便 ViewBinding 使用

#### premulticlick

###### OnPreMultiClickListener

防止短时间内多次点击，默认 500 毫秒内重复点击无效

```
View.setOnClickClictener(onPreMultiClickClictener)

private val onPreMultiClickClictener = object: OnPreMultiClickClictener(1000) {
    override fun onValidClick(view: View) {
        // 有效的点击事件回调
    }

    override fun onInvalidClick(view: View) {
        // 无效的点击事件回调
    }
}
```

#### singleton

###### SingletonHolder

Kotlin 单例辅助类，T：需要实现单例的类，A：传递的参数，如 Context

```
class MyManager private constructor(context: Context) {

    fun doSomething() {
        ...
    }

    companion object : SingletonHolder<MyManager, Context>(::MyManager)
}

MyManager.getInstance(context).doSomething()
```

#### utils

###### Base64Util

Base64 编解码相关工具类，可对字符串、文件、Bitmap进行编解码

```
encodeToString：最原始的Base64编码
decode：最原始的Base64解码
stringEncode：对字符串进行Base64编码
stringDecode：对字符串进行Base64解码
fileEncode：对文件进行Base64编码
fileDecode：对文件进行Base64解码
bitmapEncode：对图片进行Base64编码
bitmapDecode：对图片进行Base64解码
```

###### AppUtil

App 相关工具类，可获取APP版本、名称等，可获取APK安装路径、可检查APK是否安装

```
getAppName：获取应用程序名称
getVersionName：取应用程序版本名称信息
getVersionCode：获取应用程序的版本Code信息
getSelfApkPath：获取APK安装路径
isPkgInstalled：检查APK是否安装
```

###### DateTimeUtil

日期/时间 工具类

```
getNowLocalDateTime：获取当前日期和时间，如：12小时制 en: April 19, 2022, 1:30 PM   zh: 2022年4月19日 下午1:30
getNowLocalDate：获取当前日期，如：en: April 19, 2022   zh: 2022年4月19日
getNowLocalTime：获取当前时间，如：12小时制 en: 1:30 PM   zh: 下午 1:30
getDefaultTimeZone：获取默认时区
getDefaultTimeZoneName：获取默认时区名称，如：GMT+08:00 中国标准时间
getTimeZoneName：获取时区名称，如：GMT+08:00 中国标准时间
getTimeZoneShortName：获取时区短名称，如：GMT+08:00
getTimeZoneLongName：获取时区长名称，如：中国标准时间
isAM：判断是上午还是下午
is24HourFormat：判断系统使用的是24小时制还是12小时制
getNowDateFormat：根据指定格式，获取现在时间
getDateFormat：把String日期转成制定格式
getAgeByBirthday：根据生日计算年龄
```

###### DeviceUtil

设备相关工具类

```
getDeviceId：获取设备的唯一标识，deviceId
getPhoneBrand：获取手机品牌
getPhoneModel：获取手机型号
getBuildLevel：获取手机Android API等级（22、23 ...）
getBuildVersion：获取手机Android 版本（4.4、5.0、5.1 ...）
```

###### SPUtil

SharedPreferences工具类

```
put：保存数据的方法
get：得到保存的数据的方法
remove：移除某个key值对应的值
clear：清除所有数据
contains：查询某个key是否已经存在
getAll：获取所有已保存的键值对
```

###### ValidatorUtil

正则验证工具类

```
isUserName：校验用户名，不包含中文和特殊字符，5-17位
isPassword：验证密码，不包含特殊字符, 至少需要一个字母+数字，6-20位
isMobile：验证手机号
isEmail：验证邮箱
isChinese：验证汉字
```

###### ScreenUtil

屏幕相关工具类

```
getScreenWidth：获得屏幕宽度
getScreenHeight：获得屏幕高度
getStatusBarHeight、getStatusHeight：获取状态栏高度
```

###### DensityUtil

分辨率相关

```
dp2px：dp转px
px2dp：px转dp
sp2px：sp转px
px2sp：px转sp
```

###### ArithmeticUtil

运算相关

```
add(v1: String, v2: String)：提供精确的加法运算
add(v1: String, v2: String, scale: Int)：提供精确的加法运算，scale 保留小数位数
sub(v1: String, v2: String)：提供精确的减法运算
sub(v1: String, v2: String, scale: Int)：提供精确的减法运算，scale 保留小数位数
mul(v1: String, v2: String)：提供精确的乘法运算
mul(v1: String, v2: String, scale: Int)：提供精确的乘法运算，scale 保留小数位数
p(v1: String, v2: String)：提供精确的除法运算
p(v1: String, v2: String, scale: Int)：提供精确的除法运算，scale 保留小数位数
remainder(v1: String, v2: String, scale: Int)：取余数，scale 保留小数位数
round(b: BigDecimal, scale: Int)：提供精确的小数位四舍五入处理，scale 保留小数位数
compare(v1: String, v2: String)：比较大小，如果v1 大于v2 则 返回true 否则false
```

###### LocationUtil

位置相关

```
interface OnGetLocationListener{
    fun isLocationDisable() { }
    fun onLocationCallBack(location: Location?) { }
    fun onAddressCallBack(address: Address?) { }
}

isLocationEnabled：判断位置信息开关是否打开
getAddress：获取地址信息，协程使用回调OnGetLocationListener返回数据
address：获取地址信息
getLocation：获取位置信息，协程使用回调OnGetLocationListener返回数据
locale：获取位置信息，协程
gotoLocationSettings：进入“位置信息”设置界面
```

使用

```
val locationUtil = LocationUtil(this)
locationUtil.getAddress(lifecycleScope, object : LocationUtil.OnGetLocationListener {
    // 位置信息开关处于禁用状态
    override fun isLocationDisable() {
        
    }
    
    override fun onAddressCallBack(address: Address?) {
        validPhone(address)
    }
})

/*
locationUtil.getLocation(lifecycleScope, object : LocationUtil.OnGetLocationListener {
    override fun isLocationDisable() {
    
    }
    
    override fun onLocationCallBack(location: Location?) {
        
    }
})
*/

/*
lifecycleScope.launch {
    locationUtil.isLocationEnabled().also {
        if (it) {
            locationUtil.locale().also { location: Location? ->
                locationUtil.address(location).also { address: Address? ->
                    
                }
            }
        }
    }
}
*/
```

###### ClipboardUtil

剪切板相关工具类

```
copyText：复制字符串到剪切板
copyHtmlText：复制 Html 字符串到剪切板
copyUri：复制 Uri 到剪切板
copyRawUri：复制 RawUri 到剪切板
copyIntent：复制 Intent 到剪切板
getClipboardContent：获取剪切板上的第一条内容，不管什么类型都强制转成String
getClipType：获取第一条内容类型

enum class ClipType { UNKNOWN, TEXT, INTENT, URI, HTML, }
```

#### widget

###### CustomEditText

自定义EditText，可设置DrawableRight图片添加一键清除功能，可开启文本抖动动画，可设置禁用/开启编辑状态

```
startShakeAnimation：晃动动画
setTextContent：设置文本，当 isEditable = false 时，则自动改为可编辑状态
getEditable：判断 EditText 是否处于可编辑状态
setEditable：设置 EditText 是否可编辑
```

###### GridRadioGroup

自定义GridLayout，结合 GridLayout + RadioGroup 功能

###### ZAlertDialog

自定义 AlertDialog，旨在统一 Dialog 风格

```
changeRootViewParam：修改最底层背景CardView参数
changeTitleViewParam：修改标题TextView参数
changeMessageViewParam：修改默认内容文本TextView参数
setCustomContentView：设置自定义内容，装在内容容器FrameLayout里面
changeButtonRootViewParam：修改按钮容器参数，LinearLayout
changeCancelButtonParam：修改取消按钮参数
changeCompleteButtonParam：修改取消按钮参数

interface OnZAlertDialogButtonClickListener {
    fun onDialogButtonClick(dialog: ZAlertDialog, view: View)
}：按钮监听回调
```

#### MVP

使用    

1.  MainActivity
        
    ```
    class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.View {
    
        override fun getViewBinding(): ActivityMainBinding {
            return ActivityMainBinding.inflate(layoutInflater)
        }
    
        override fun obtainPresenter(): MainPresenter {
            return MainPresenter(this)
        }
    
        override fun onResume() {
            super.onResume()
            // MVP 获取数据
            presenter?.queryDates()
        }
    
        override fun queryDatesSuccess(dates: List<Int>) {
            ToastBuilder(this)
                .setMessage("数据获取成功")
                .setToastType(ToastType.SUCCESS)
                .create()
                .show()
        }
    
        override fun queryDatesFailure(errCode: Int, errMsg: String) {
            ToastBuilder(this)
                .setMessage("数据获取失败")
                .setToastType(ToastType.ERROR)
                .create()
                .show()
        }
    
    }
    ```

2.  MainPresenter

    ```
    /**
     * 这里是 MVP 中对逻辑进行处理的地方，通过 View 回调告诉 Activity 如何更新 UI
     */
    class MainPresenter(view: MainContract.View): BasePresenter<MainContract.View>(view), MainContract.Presenter {
    
        private val model = MainModel()
        var dates: List<Int> = ArrayList()
    
        override fun queryDates() {
            dates = model.queryDates().also {
            if (it.isEmpty()) {
                view?.queryDatesFailure(-1, "数据获取失败")
            } else {
                view?.queryDatesSuccess(dates)
            }
        }
    
    }
    ```

3.  MainContract

    ```
    /**
     * 这里是 MVP 定义接口的地方
     */
    interface MainContract {
        interface Model {
            fun queryDates(): List<Int>
        }
    
        interface View {
            fun queryDatesSuccess(dates: List<Int>)
            fun queryDatesFailure(errCode: Int, errMsg: String)
        }
    
        interface Presenter {
            fun queryDates()
        }
    }
    ```

4.  MainModel

    ```
    /**
     * 这里在 MVP 中是真正执行数据请求的地方
     */
    class MainModel: MainContract.Model {
    
        override fun queryDates(): List<Int> {
            return arrayListOf(0, 1, 2, 3, 4)
        }
    
    }
    ```

## 第三方库

1.  权限请求：[Easypermissions-ktx：1.0.0](https://github.com/vmadalin/easypermissions-ktx)

2.  事件总线：[Eventbus:3.3.1](https://github.com/greenrobot/EventBus)

3.  比较好用的图片压缩：[CompressHelper:1.0.5](https://github.com/nanchen2251/CompressHelper)
    
4.  微信朋友圈一样的图片查看器：[PhotoViewer:0.50](https://github.com/wanglu1209/PhotoViewer)

5.  图片加载：[Coil:2.0.0-rc03](https://coil-kt.github.io/coil/)

6.  google 开源手机号码相关库：[libphonenumber:8.12.47](https://github.com/google/libphonenumber)
    
7.  下拉刷新、上拉加载库：[SmartRefreshLayout:2.0.5](https://github.com/scwang90/SmartRefreshLayout)

## 版本

#### v1.0.1

#### v1.0.2

1.  新增 Kotlin 单例辅助类: SingletonHolder
2.  新增位置相关工具类: LocationUtil
3.  新增运算相关工具类: ArithmeticUtil
4.  新增分辨率工具类: DensityUtil 
5.  新增防止短时间内多次点击: OnPreMultiClickListener 
6.  新增自定义View: GridRadioGroup
7.  EventBusUtil: 新增 移除指定粘性事件 和 移除所有粘性事件 方法 
8.  ValidatorUtil: 更新 密码验证 正则表达式
9.  新增google 开源手机号码相关库：libphonenumber:8.12.47

#### v1.0.3

1. 新增剪切板相关工具类: ClipboardUtil
2. ToastBuilder 添加过时标志: Android 11(API 30)之后 Toast.setView() 不能自定义了，请使用 SnackBarExt 里面的 Snackbar.showToast 方法代替
3. 新增 SnackBar 扩展: SnackBarExt，自定义显示 Toast 样式的 SnackBar 的方法
4. 升级第三方库 Coil 到 2.0.0-rc03 版本
5. 新增 ViewHolder 基类 BaseViewHolder 和 继承于 BaseViewHolder 的方便 ViewBinding 使用的 BaseViewBindingHolder
6. 新增下拉刷新、上拉加载库：SmartRefreshLayout：2.0.5 及相应的 header-classics:2.0.5、footer-classics:2.0.5'
7. 新增自定义AlertDialog: ZAlertDialog，旨在统一 Dialog 风格