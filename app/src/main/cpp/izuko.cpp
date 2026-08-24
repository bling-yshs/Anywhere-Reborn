//
// Created by Absinthe on 2019/11/22.
//

#include <jni.h>
#include <string>
#include <cstring>
#include "izuko.h"

extern "C"
const char *CIPHER_KEY = "absintheeeeeeeeeeeeeeeeeeeeeeeee";

/**
 * getApplication
 *
 * @param env
 * @return j_object
 */
static jobject getApplication(JNIEnv *env) {
    jobject application = nullptr;
    jclass activity_thread_clz = env->FindClass("android/app/ActivityThread");

    if (activity_thread_clz != nullptr) {
        jmethodID currentApplication = env->GetStaticMethodID(
                activity_thread_clz, "currentApplication", "()Landroid/app/Application;");
        if (currentApplication != nullptr) {
            application = env->CallStaticObjectMethod(activity_thread_clz, currentApplication);
        }
        env->DeleteLocalRef(activity_thread_clz);
    }
    return application;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_absinthe_anywhere_1_utils_manager_IzukoHelper_getCipherKey(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(CIPHER_KEY);
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_absinthe_anywhere_1_utils_manager_IzukoHelper_isHitagi(JNIEnv *env, jobject clazz,
                                                                jstring token) {
    jobject context = getApplication(env);
    // get Context object
    jclass cls = env->GetObjectClass(context);
    jmethodID method = env->GetMethodID(cls, "getContentResolver",
                                        "()Landroid/content/ContentResolver;");
    jobject resolverInstance = env->CallObjectMethod(context, method);

    // get android_id from android Settings$Secure
    jclass androidSettingsClass = env->FindClass("android/provider/Settings$Secure");
    jmethodID methodId = env->GetStaticMethodID(androidSettingsClass, "getString",
                                                "(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;");
    jstring param_android_id = env->NewStringUTF("android_id");
    auto android_id = (jstring) env->CallStaticObjectMethod(androidSettingsClass, methodId,
                                                            resolverInstance, param_android_id);

    jclass cipherClass = env->FindClass("com/absinthe/anywhere_/utils/CipherUtils");
    jmethodID encryptMethodId = env->GetStaticMethodID(cipherClass, "encrypt",
            "(Ljava/lang/String;)Ljava/lang/String;");
    auto encrypt_android_id = (jstring) env->CallStaticObjectMethod(cipherClass, encryptMethodId, android_id);

    char *nativeString1 = const_cast<char *>(env->GetStringUTFChars(token, JNI_FALSE));
    char *nativeString2 = const_cast<char *>(env->GetStringUTFChars(encrypt_android_id, JNI_FALSE));

    return static_cast<jboolean>(strcmp(nativeString1, nativeString2) == 0);
}