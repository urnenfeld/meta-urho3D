SUMMARY="Game Selector of the UrhoBox"
LICENSE = "MIT"

SRC_URI = "git://github.com/urnenfeld/theFin.git;protocol=https"

SRCREV="e832fc11ccde63f987d7b59fdadd9076f5ad2536"

LIC_FILES_CHKSUM = "file://../git/LICENSE;md5=5d8bf837acaa37ac750d9fdf56fe9f54"

S = "${WORKDIR}/git"

URHO3D_COMPONENT_ID="theFin"

URHO3D_REUSE_CORE_ASSETS = "1"
URHO3D_ASSETS_PATHS = "Add1"

inherit urholauncher

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
    install -m 0755 ${S}/thefin ${D}${bindir}
}
