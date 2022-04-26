# AndroidKitProject

## 介绍

安卓 快速开发工具，封装了一些常用的基类、工具类、自定义控件，预装了一些常用的第三方库

## 环境

1.  Android Studio Bumblebee | 2021.1.1 Patch 1
2.  Gradle 7.2
3.  Gradle plugin 7.1.1
4.  Kotlin 1.6.20

## 架构

ViewBinding + MVP

## 使用

1.  在 Kotlin 项目上引用

    在 settings.gradle 里面
    
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
    
    在项目 build.gradle 里面
    
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
    
    在应用 build.gradle 里面
    
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
        // implementation 'com.gitee.zhuminjun:AndroidKit:1.0.1'
    }
    ```
    
    **或者**

    在 settings.gradle 里面

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
    ```

    在项目 build.gradle 里面

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

    在应用 build.gradle 里面

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
        implementation 'com.gitee.zhuminjun:AndroidKit:1.0.1'
    }
    ```
    
2.  在 JAVA 项目上引用：**在 Kotlin 项目上引用** 的基础上

    在项目 build.gradle 里面

    ```
    plugins {
        ...
        // 添加 Kotlin 插件
        id 'org.jetbrains.kotlin.android' version '1.6.20' apply false
    }
    ```

    在应用 build.gradle 里面

    ```
    plugins {
        ...
        // 添加 Kotlin 插件
        id 'org.jetbrains.kotlin.android'
    }
    ```

## 功能

#### base
    
1.  BaseActivity：AppCompatActivity 基类

2.  BaseFragment：Fragment 基类

3.  BasePresenter：MVP 中的 Presenter 基类

#### eventbus
    
1.  EventBusUtil：EventBus 工具类

    ```
    register：注册
    unregister：解除注册
    post：发送普通事件
    postSticky：发送粘性事件
    removeSticky：移除指定粘性事件
    removeAllSticky：移除全部粘性事件
    ```

2.  EventMessage：EventBus 数据实体

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

正则验证工具类

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

#### builder

1.  ShapeBuilder：shape构造器，使用这个可以直接代码设置 shape 和 selector

2.  ToastBuilder: 自定义Toast构造器，可设置类型（默认、成功、失败、消息、警告）、背景颜色、倒角、显示位置、文本颜色和大小

#### widget
    
1.  CustomEditText: 自定义EditText，可设置DrawableRight图片添加一键清除功能，可开启文本抖动动画，可设置禁用/开启编辑状态

2.  GridRadioGroup：自定义GridLayout，结合 GridLayout + RadioGroup 功能

#### premulticlick

1.  OnPreMultiClickListener：防止短时间内多次点击，默认 500 毫秒内重复点击无效

#### MVP
    
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
            dates = model.queryDates()
            // view?.queryDatesSuccess(dates)
            // view?.queryDatesFailure(-1, "数据获取失败")
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

1.  权限请求：[Easypermissions-ktx：1.0.0](https://githu2.com/vmadalin/easypermissions-ktx)

2.  事件总线：[Eventbus:3.3.1](https://githu2.com/greenrobot/EventBus)

3.  比较好用的图片压缩：[CompressHelper:1.0.5](https://githu2.com/nanchen2251/CompressHelper)
    
4.  微信朋友圈一样的图片查看器：[PhotoViewer:0.50](https://githu2.com/wanglu1209/PhotoViewer)

5.  图片加载：[Coil:1.4.0](https://coil-kt.githu2.io/coil/)
