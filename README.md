# MD版的花瓣网App
#介绍

 - 作为一个花瓣网用户实在受不了Android版的崩溃而且已经一年多没有更新。工作之余网络抓包和反编译，要自己写一个App，因为毕竟是别人的项目也不指望能上架，就直接开源放到github上，如果侵权请联系我及时删除。
 - 源代码放在GitHub：[项目地址](https://github.com/LiCola/huabanDemo)
 - 下面介绍目前的工作内容

#更新记录
 - 现在兼容包升级导致API-20以下Fresco使用异常，目前已经统一使用[CompatUtils工具类](https://github.com/LiCola/huabanDemo/blob/master/app/src/main/java/licola/demo/com/huabandemo/Util/CompatUtils.java)，内部使用[VectorDrawableCompat](https://developer.android.com/reference/android/support/graphics/drawable/VectorDrawableCompat.html)获取VectorDrawable资源，保证低版本的运行。

#UI


![Main](https://github.com/LiCola/huabanDemo/blob/master/ScreenCapture/Main.jpg) ![Drawer](https://github.com/LiCola/huabanDemo/blob/master/ScreenCapture/Drawer.jpg)

![Image](https://github.com/LiCola/huabanDemo/blob/master/ScreenCapture/Image.jpg) ![User](https://github.com/LiCola/huabanDemo/blob/master/ScreenCapture/User.jpg)


#架构
 这个项目在写在很久之前，当时MVP架构网络上各种分析描述，但是感觉都是各说各的。不确定项目是否采用MVP架构就先动手写代码，随时准备重构项目。就在不久前Google发布[Android Architecture Blueprints \[beta\]](https://github.com/googlesamples/android-architecture)，终于感觉这事有点靠谱了，我现在已经在动手重构项目了。
 分析博文在这里：[MVP架构-官方MVP项目和MVP-RxJava项目架构对比分析解读](http://blog.csdn.net/card361401376/article/details/51518605)


##目前的架构：

 - 基本思想是采用模板方法模式，父类控制代码结构，子类实现，部分具有相同功能的再提供父类实现。
 例如`BaseSwipeViewPagerActivity`是所有具有上拉刷新SwipeRefreshLayout和左右滑动ViewPager的子Activity类的父类。
 `BaseRecyclerHeadFragment`是所有具有能够添加头尾View的RecyclerView的父类Fragment，内部主要实现向下滑动自动加载数据。
 
 - 每个Activity负责逻辑控制，其中会包含都会有一至多个Fragment负责UI显示，尽可能的不在Activity有网络操作。

#技术点
由于是个人项目所以全部采用目前最新的和最热门技术。

##RxJava
很多基本的逻辑使用[RxJava/RxAndroid](https://github.com/ReactiveX/RxAndroid)来实现异步响应，简化了很多异步回调的代码。比如欢迎界面的实现，详解点这里[RxAndroid项目实践-使用RxJava响应式编码实现知乎日报的欢迎界面](http://blog.csdn.net/card361401376/article/details/51115047)

##Retrofit
网络模块使用Retrofit，搭配RxJava实在是爽。
同时还很简洁的实现了Https的网络访问。功能强大。Retrofit整体框架主要采用代理模式，使得简化我们的网络调用操作，真正的内部还是采用OkHttp，这就导致实现下载上传进度监听会有点麻烦。目前也已经实现不会有内存泄露，具体参考看[DownloadService.java](https://github.com/LiCola/huabanDemo/blob/master/app/src/main/java/licola/demo/com/huabandemo/Service/DownloadService.java)后台服务单线程图片下载实现。

##Fresco
图片加载框架使用的是Fresco，同样的功能强大，但是使用复杂。我包装了一个类`ImageLoadFresco`，采用生成器模式，配置很多的默认实现，同时能够实现复杂操作，具体代码和介绍[Fresco的封装和使用说明以及获取缓存中的Bitmap对象](http://blog.csdn.net/card361401376/article/details/50965241)。

##其他
其他方面，每个功能模块都会提供工具类或者包装类，分化代码使得相同功能代码能够重用，减少代码量。







