SUMMARY="Game Selector of the UrhoBox"
LICENSE = "MIT"

SRC_URI = "git://github.com/urnenfeld/theFin.git;protocol=https"
SRCREV="8b9aee8b984e8eeefe97b010210b3811521a72fe"

LIC_FILES_CHKSUM = "file://../git/LICENSE;md5=5d8bf837acaa37ac750d9fdf56fe9f54"

S = "${WORKDIR}/git"

DEPENDS = "urho3d"

# CFLAGS_prepend = "-I ${RECIPE_SYSROOT}/usr/include "
