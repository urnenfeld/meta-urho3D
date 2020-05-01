require recipes-graphics/images/core-image-x11.bb

SUMMARY = "A very basic urho3d image"

SPLASH = "psplash-urho3d"

# Core
IMAGE_INSTALL += "urho3d thefin"
IMAGE_INSTALL += "urho3d-project-template"

# Local / Closed
IMAGE_INSTALL += "${LOCAL_GAMES}"

IMAGE_FEATURES += "ssh-server-dropbear"
# in theory helps psplash
# IMAGE_INSTALL += "kernel-modules"

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
