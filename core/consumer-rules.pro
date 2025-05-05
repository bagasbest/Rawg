##---------------Begin: proguard configuration for SQLCipher  ----------
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }


##---------------Begin: proguard configuration for Gson ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
@com.google.gson.annotations.SerializedName <fields>;
}


##---------------Begin: proguard configuration for Retrofit ----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
@retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

-dontwarn kotlinx.**


##---------------Begin: proguard configuration for Glide ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
<init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
**[] $VALUES;
public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
*** rewind();
}

# Uncomment for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


##---------------Begin: proguard configuration for RxJava ----------
# Uncomment if you use RxJava
#-dontwarn java.util.concurrent.Flow*

# 1) Keep your Retrofit service interfaces’ methods and annotations
-keepclasseswithmembers,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# 2) Preserve generic type signatures everywhere
-keepattributes Signature

# 3) Preserve Kotlin metadata so suspend/Flow signatures stay intact
-keep class kotlin.Metadata { *; }

# --- Keep Kotlin metadata so suspend/Flow methods stay intact ---
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata { *; }

# --- Keep ALL generic signatures so Retrofit can see <T> ---
-keepattributes Signature

# --- Keep parameter names (for Kotlin suspend & default args) ---
-keepattributes MethodParameters

# --- Keep annotations at runtime (for @GET, @POST, etc) ---
-keepattributes RuntimeVisibleAnnotations,
                RuntimeInvisibleAnnotations,
                RuntimeVisibleParameterAnnotations,
                RuntimeInvisibleParameterAnnotations,
                AnnotationDefault

# --- Keep nested‐class info, required for signature parsing ---
-keepattributes InnerClasses,EnclosingMethod

# --- (Optional) Keep local variable tables if you ever debug proxies ---
-keepattributes LocalVariableTable,LocalVariableTypeTable

# --- And finally, keep your Retrofit interfaces un-obfuscated: ---
-keep,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-keep class com.jakewharton.retrofit2.adapter.kotlin.coroutines.** { *; }
