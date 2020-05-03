SUMMARY="Game Selector of the UrhoBox"
LICENSE = "MIT"

SRC_URI = "git://github.com/urnenfeld/theFin.git;protocol=https \
           file://thefinloop.sh"

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"


LIC_FILES_CHKSUM = "file://../git/LICENSE;md5=5d8bf837acaa37ac750d9fdf56fe9f54"

S = "${WORKDIR}/git"

URHO3D_COMPONENT_ID="thefin"

URHO3D_REUSE_CORE_ASSETS = "1"
URHO3D_ASSETS_PATHS = "Add1"

inherit urholauncher

EXTRA_OEMAKE += "'RECIPE_SYSROOT=${RECIPE_SYSROOT}'"
EXTRA_OEMAKE += "'CC=${CC}' 'CXX=${CXX}'"
EXTRA_OEMAKE += "'CFLAGS=${CFLAGS}' 'CXXFLAGS=${CXXFLAGS}'"
EXTRA_OEMAKE += "'LDFLAGS=${LDFLAGS}'"


set_makefile () {
    mv ${S}/Makefile.yocto ${S}/Makefile
}

do_patch_append () {
    bb.build.exec_func('set_makefile', d)
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/${URHO3D_COMPONENT_ID} ${D}${bindir}

    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/mini_x

    install -m 0755 ${WORKDIR}/thefinloop.sh ${D}${sysconfdir}/mini_x/session
}
