SUMMARY="Game Selector of the UrhoBox"
LICENSE = "MIT"

SRC_URI = "git://github.com/urnenfeld/theFin.git;protocol=https"

SRCREV="d1bd687dadcb03243f2b6cdb8c5900be46f042d5"

LIC_FILES_CHKSUM = "file://../git/LICENSE;md5=5d8bf837acaa37ac750d9fdf56fe9f54"

S = "${WORKDIR}/git"

DEPENDS = "urho3d"


EXTRA_OEMAKE += "'RECIPE_SYSROOT=${RECIPE_SYSROOT}'"
EXTRA_OEMAKE += "'CC=${CC}' 'CXX=${CXX}'"
EXTRA_OEMAKE += "'CFLAGS=${CFLAGS}' 'CXXFLAGS=${CXXFLAGS}'"
EXTRA_OEMAKE += "'LDFLAGS=${LDFLAGS}'"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/thefin2 ${D}${bindir}
}
