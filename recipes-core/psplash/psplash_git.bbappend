FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SPLASH_IMAGES_append = " file://psplash-urho3d-img.png;outsuffix=urho3d"
ALTERNATIVE_PRIORITY_psplash-urho3d[psplash] = "10"

# Seen in some platformn S00 is too early to have /dev/fb0
INITSCRIPT_PARAMS = "start 10 S . stop 20 0 1 6 ."
