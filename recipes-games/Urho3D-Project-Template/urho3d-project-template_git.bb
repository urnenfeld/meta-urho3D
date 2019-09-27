SUMMARY = "Urho3D Project Template"
DESCRIPTION = "Urho3D application template with the base functionality which is required for most projects."
HOMEPAGE = "https://github.com/ArnisLielturks/Urho3D-Project-Template"
LICENSE = "MIT"

SRC_URI = "git://github.com/ArnisLielturks/Urho3D-Project-Template.git;protocol=https"
SRC_URI_append += "file://000_remove_lua.patch"
SRCREV="c3ed4e0f4733361d082208ec9dcd5fbb7853327b"

LIC_FILES_CHKSUM = "file://LICENSE;md5=30b83a9c563c75e79b183f051ba08911"
# License fetching ...
S = "${WORKDIR}/git"

URHO3D_GAME_NAME = "ArnisTemplate"

inherit arnistemplate

inherit urho3dgame
