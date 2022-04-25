# AndroidKitProject

#### 介绍

安卓 快速开发工具，封装了一些常用的基类、工具类、自定义控件，预装了一些常用的第三方库

#### 环境

1.  Android Studio Bumblebee | 2021.1.1 Patch 1
2.  Gradle 7.2
3.  Gradle plugin 7.1.1
4.  Kotlin 1.6.20

#### 架构

ViewBinding + MVP

#### 使用

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
    }
    ```
    
2.  在 JAVA 项目上引用：[在 Kotlin 项目上引用] 的基础上

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

#### 功能

1.  base
    
    a.  BaseActivity：AppCompatActivity 基类
    
    b.  BaseFragment：Fragment 基类
    
    c.  BasePresenter：MVP 中的 Presenter 基类

2.  eventbus
    
    a.  EventBusUtil：EventBus 工具类

    b.  EventMessage：EventBus 数据实体

3.  utils

    a.  Base64Util：Base64 编解码相关工具类，可对字符串、文件、Bitmap进行编解码

    b.  AppUtil：App 相关工具类，可获取APP版本、名称等，可获取APK安装路径、可检查APK是否安装  
    
    c.  DateTimeUtil：日期/时间 工具类  
    
    d.  DeviceUtil：设备相关工具类

    e.  SPUtil：SharedPreferences工具类
    
    f.  ValidatorUtil：正则验证工具类

4.  builder

    a.  ShapeBuilder：shape构造器，使用这个可以直接代码设置 shape 和 selector

    b.  ToastBuilder: 自定义Toast构造器，可设置类型（默认、成功、失败、消息、警告）、背景颜色、倒角、显示位置、文本颜色和大小

5.  widget
    
    a.  ClearEditText: 自定义EditText，可设置DrawableRight图片添加一键清除功能，可开启文本抖动动画，可设置禁用/开启编辑状态

6.  MVP
    
    a.  MainActivity
        
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
    
    b.  MainPresenter

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

    c.  MainContract

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

    d.  MainModel

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

#### 第三方库

1.  权限请求：[Easypermissions-ktx：1.0.0]{https://github.com/vmadalin/easypermissions-ktx}

2.  事件总线：[Eventbus:3.3.1]{https://github.com/greenrobot/EventBus}

3.  比较好用的图片压缩：[CompressHelper:1.0.5]{https://github.com/nanchen2251/CompressHelper}
    
4.  微信朋友圈一样的图片查看器：[PhotoViewer:0.50]{https://github.com/wanglu1209/PhotoViewer}

5.  图片加载：[Coil:1.4.0]{https://coil-kt.github.io/coil/}
