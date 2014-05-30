
LOCAL_PATH := $(call my-dir)

include $(LOCAL_PATH)/minibpm/Android.mk

$(call import-module,android/cpufeatures)

