require recipes-graphics/images/core-image-x11.bb

SUMMARY = "A very basic urho3d image"

SPLASH = "psplash-urho3d"

# Core
IMAGE_INSTALL += "urho3d"
IMAGE_INSTALL += "urho3d-project-template"
