SUMMARY = "Mad Tunnel"
DESCRIPTION = "Mad Tunnel is a remake of one of the 4 subgames composed by Silmarils' Mad Show from 1988"
HOMEPAGE = ""
LICENSE = "CLOSED"

# MADTUNNEL_PATH is an absolute local path locating to the source code.
SRC_URI = "file://${MADTUNNEL_PATH}"
S = "${WORKDIR}${MADTUNNEL_PATH}"

URHO3D_GAME_ID = "madtunnel"
URHO3D_GAME_GENRE = "action"
# URHO3D_GAME_SUBGENRE = "misc"

URHO3D_REUSE_CORE_ASSETS = "1"
URHO3D_ASSETS_PATHS = "Assets"

# Inheriting from urho3dgame performs 3 main tasks:
#   - Ensure copying the assets defined URHO3D_ASSETS_PATHS in to the package
#   - Generates the urhofest taking into account URHO3D_GAME_* variables
#   - Generate the launching script placing the assets in the environment variables
#   - QA tasks to avoid misconfigurations.
inherit urho3dgame

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
    install -m 0755 ${S}/${URHO3D_GAME_ID} ${D}${bindir}
}
