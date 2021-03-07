# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepclassmembers class com.bik.telefood.model.database.* { *;}
-keepclassmembers class com.bik.telefood.model.entity.auth.LoginResponse { *; }
-keepclassmembers class com.bik.telefood.model.entity.Autherntication.*{ *;}
-keepclassmembers class com.bik.telefood.model.entity.Autherntication.vendors.*{ *;}
-keepclassmembers class com.bik.telefood.model.entity.*{ *;}
-keepclassmembers class com.bik.telefood.model.entity.general.*{ *;}
-keepclassmembers class com.bik.telefood.model.entity.general.services.*{ *;}
-keepclassmembers class com.bik.telefood.model.entity.general.singleservices.*{ *;}
-keepclassmembers class com.bik.telefood.model.entity.support.*{ *;}
