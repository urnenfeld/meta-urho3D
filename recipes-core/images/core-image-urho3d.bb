require recipes-graphics/images/core-image-weston.bb

SUMMARY = "A very basic urho3d image"

# Setting this one, but this will have a fixed once among all machines
SPLASH = "psplash-raspberrypi"

IMAGE_INSTALL += "urho3d"
