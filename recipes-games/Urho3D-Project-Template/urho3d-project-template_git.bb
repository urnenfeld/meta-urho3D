SUMMARY = "Urho3D Project Template"
DESCRIPTION = "Urho3D application template with the base functionality which is required for most projects."
HOMEPAGE = "https://github.com/ArnisLielturks/Urho3D-Project-Template"
LICENSE = "MIT"

SRC_URI = "git://github.com/ArnisLielturks/Urho3D-Project-Template.git;protocol=https"
SRCREV="c3ed4e0f4733361d082208ec9dcd5fbb7853327b"

SRC_URI[md5sum] = "dec044f04ebe030afbc62c54ff90e653"
SRC_URI[sha256sum] = "2b445426cf650dc09109a2b8c9696472cff86facad5b9034caa295339c8c5473"

LIC_FILES_CHKSUM = "file://LICENSE;md5=30b83a9c563c75e79b183f051ba08911"
# License fetching ...
S = "${WORKDIR}/git"

DEPENDS = "urho3d"

export URHO3D_HOME = "${SYSROOTS}"

inherit cmake
