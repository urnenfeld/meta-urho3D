require recipes-graphics/images/core-image-x11.bb

SUMMARY = "A very basic urho3d image"

SPLASH = "psplash-urho3d"

IMAGE_INSTALL += "urho3d"
# in theory helps psplash
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
