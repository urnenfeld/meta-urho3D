SUMMARY="Game Selector of the UrhoBox"
LICENSE = "MIT"

SRC_URI = "git://github.com/urnenfeld/theFin.git;protocol=https"

SRCREV="93abbb5fa93f77081d2734153f43641311e6f1ef"

LIC_FILES_CHKSUM = "file://../git/LICENSE;md5=5d8bf837acaa37ac750d9fdf56fe9f54"

S = "${WORKDIR}/git"

DEPENDS = "urho3d"


EXTRA_OEMAKE += "'RECIPE_SYSROOT=${RECIPE_SYSROOT}'"
EXTRA_OEMAKE += "'CC=${CC}' 'CXX=${CXX}'"
EXTRA_OEMAKE += "'CFLAGS=${CFLAGS}' 'CXXFLAGS=${CXXFLAGS}'"
EXTRA_OEMAKE += "'LDFLAGS=${LDFLAGS}'"


do_patch[postfuncs] += "set_makefile"
set_makefile () {
    mv ${S}/Makefile.yocto ${S}/Makefile
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/thefin2 ${D}${bindir}
}
