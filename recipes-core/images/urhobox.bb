require recipes-core/images/core-image-urho3d.bb

SUMMARY = "UrhoBox complete image"


IMAGE_CODENAME = "hernan"
IMAGE_VERSION_SUFFIX = "-${IMAGE_CODENAME}-${DATETIME}"

# Core
IMAGE_INSTALL += "thefin"

# Local / Closed
LOCAL_GAMES ?= ""
IMAGE_INSTALL += "${LOCAL_GAMES}"

IMAGE_FEATURES += "ssh-server-dropbear"
# Bluetooth and other potential modules
IMAGE_INSTALL += "kernel-modules"

IMAGE_INSTALL += "${@bb.utils.contains("MACHINE_FEATURES", "wifi", "wpa-supplicant", "", d)}"
# IMAGE_INSTALL += "${@bb.utils.contains("MACHINE_FEATURES", "bluetooth", "bluez5", "", d)}"

# OTA packages
#IMAGE_INSTALL += " \
#	e2fsprogs-mke2fs \
#	parted \
#	curl \
#	wget \
#	"

# NAS Packages
# IMAGE_INSTALL += " \
# 	minidlna \
# 	transmission-web \
# 	udev-extraconf \
# 	"

# while theFin is not yet ready ...
# xsession_thefin() {
#   mkdir ${IMAGE_ROOTFS}/etc/mini_x
#   ln -s ../../usr/bin/theFin-launcher ${IMAGE_ROOTFS}/etc/mini_x/session
# }

# ROOTFS_POSTPROCESS_COMMAND += "xsession_thefin;"
