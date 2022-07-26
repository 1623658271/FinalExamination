# FinalExamination  
## OpenEyes App介绍  
### 页面布局  
由六个Activity和七个Fragment组成  
1.MainActivity：设置了toorbar、FragmentContainerView和BottomNavigationView用于切换首页、发现和我的三个fragment以及使用搜索功能  
2.VideoPlayActivity：点击视频后进入的页面、其中包含CommentFragment(评论页面)和DetailsFragment(视频兮姐)两个小界面，用的TabLayout进行切换  
3.SearchActivity：搜索界面，包含两个RecyclerView(用于热搜关键词和搜索结果)和SearchView(用于搜索)  
4.ClassInActivity：分类具体界面，点击一个分类的item后进入，包含一个RecyclerView和一个toolbar(便于返回和提醒当前分类)  
5.PersonMessageActivity：个人信息界面，用于查看用户信息，在任何区域点击一个头像都会进入该界面  
6.PicWatchActivity：查看大图界面，可以长按保存图片，可从有图片的界面点击进入    

### 功能页介绍  
1.首页  
<img src=https://user-images.githubusercontent.com/89245928/180635825-890137f0-9955-4f45-8340-160f1e9d5074.jpg width="200px">  
通过自定义的ViewPager和VagerAdapter实现了自动轮播图以及其中的小红点，视频其实不是视频，是图片加了个播放图标，点进去才会进入视频播放活动  
为一个RecyclerView，上拉加载更多是用了脚布局，同时判断上拉状态和数据源状态，动态进行更新数据    
2.发现  
<img src=https://user-images.githubusercontent.com/89245928/180635980-63039ce6-d8f0-44c7-8bb1-0c619e1648e8.jpg width="200px">  
<img src=https://user-images.githubusercontent.com/89245928/180635996-369ec4e8-252c-432e-baa3-422832480964.jpg width="200px">  <img src=https://user-images.githubusercontent.com/89245928/180636176-1f49ddb4-078f-4aba-9523-efe256411b53.jpg width="200px">  

包含推荐和分类页面  
推荐页面使用了RV的瀑布流布局  
分类页面使用了帧布局实现文字和图片共存，CardView美化视觉    
3.我的  
<img src=https://user-images.githubusercontent.com/89245928/180636062-b3b0cb7e-a0b5-4471-9f95-dd3fa56fcaae.jpg width="200px">   
暂时还啥都没实现    
4.视频播放  
<img src=https://user-images.githubusercontent.com/89245928/180636093-f48ddf7f-fa93-4f93-9f28-c9c90db01b6d.jpg width="200px">  
<img src=https://user-images.githubusercontent.com/89245928/180636098-c99f922c-448c-45f7-a5d0-3ff68393c2c2.jpg width="200px">  
<img src=https://user-images.githubusercontent.com/89245928/180636457-fe9e8465-500e-477e-9b38-2bba0f97f0c3.jpg width="200px">  
使用了第三方库饺子播放器'cn.jzvd:jiaozivideoplayer:7.5.0'  
5.搜索  
<img src=https://user-images.githubusercontent.com/89245928/180636142-7ced769f-8059-43ce-8f53-32cb42df38e4.jpg width="200px">  
<img src=https://user-images.githubusercontent.com/89245928/180636137-1f7a3b0f-0b2d-4f98-abf9-37f7f066d7b0.jpg width="200px">  
6.用户信息  
<img src=https://user-images.githubusercontent.com/89245928/180636160-b9466835-bdba-45ca-af16-c3563aa01d7d.jpg width="200px">  
一个普通的布局  
7.查看大图  
<img src=https://user-images.githubusercontent.com/89245928/180636188-722c0742-c038-4398-94ad-fab92b889c95.jpg width="200px">  
一个activity+viewpagerAdapter实现  
### 知识点  
retrofit、rxjava3、mvvm、databinding、viewmodel、navigation、LiveData  
### 心得体会  
认真做了十天，这次考核还是收获挺大的，对网络请求的理解加深了一些，  
但是做的不太好，毕竟我自己掌握的知识还是少了，上学期确实比较马虎。  
不过无论成功与否，我这次留校的选择是没错的，因为我希望在这个暑假能好好规划一下自己的未来，  
养成一个良好的作息习惯和学习规律，为以后的学习生活打好思想基础和行为基础。  
最后真诚地感谢红岩组织和兢兢业业的学长学姐们！
