# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\java\android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5          # 指定代码的压缩级别
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment

-dontwarn android.support.**


-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#pinyin4j
-keep class net.sourceforge.pinyin4j.**{*;}
 -keep class net.sourceforge.pinyin4j.format.**{*;}
 -keep class net.sourceforge.pinyin4j.format.exception.**{*;}

#反射
-keepattributes Signature
-keepattributes EnclosingMethod
#不混淆所有的com.czy.包下的类和这些类的所有成员变量
#-keep class czy.**{*;}

#不混淆Serializable接口的子类中指定的某些成员变量和方法
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#保持WEB接口不被混淆 此处xxx.xxx是自己接口的包名
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
#-keep class com.xxx.xxx.** { *; }

#不混淆所有com.czy包下的类，** 换成具体的类名则表示不混淆某个具体的类
#-keep class com.czy.**

#移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用，另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** i(...);
    public static *** d(...);
    public static *** w(...);
    public static *** e(...);
}
#titlebar工具
-keep class com.qz.app.utils.SystemBarTintManager{*;}

#OKHTTP
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
-keep public class * implements com.bumptech.glide.module.GlideModule


#友盟混淆
#-keepclassmembers class * {
#  public <init> (org.json.JSONObject);
#}

#sharedsdk混淆
	-keep class cn.sharesdk.**{*;}
	-keep class com.sina.**{*;}
	#-keep class **.R$* {*;}
	-keep class com.qz.app.wxapi.**{*;}
	-keep class **.R{*;}
	-keep class com.mob.**{*;}
	-dontwarn com.mob.**
	-dontwarn cn.sharesdk.**
	-dontwarn **.R$*

#-keep public class com.qz.app.R$*{
#public static final int *;
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}

#七牛混淆
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings





#腾讯tinker混淆
-keepattributes *Annotation*
-dontwarn com.tencent.tinker.anno.AnnotationProcessor
-keep @com.tencent.tinker.anno.DefaultLifeCycle public class *
-keep public class * extends android.app.Application {
    *;
}

-keep public class com.tencent.tinker.loader.app.ApplicationLifeCycle {
    *;
}
-keep public class * implements com.tencent.tinker.loader.app.ApplicationLifeCycle {
    *;
}

-keep public class com.tencent.tinker.loader.TinkerLoader {
    *;
}
-keep public class * extends com.tencent.tinker.loader.TinkerLoader {
    *;
}

-keep public class com.tencent.tinker.loader.TinkerTestDexLoad {
    *;
}
#your dex.loader pattern here
-keep class com.tencent.tinker.loader.**
-keep class tinker.sample.android.app.SampleApplication