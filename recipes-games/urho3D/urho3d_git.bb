SUMMARY = "Cross-platform 2D and 3D game engine."
DESCRIPTION = "Urho3D is a free lightweight, cross-platform 2D and 3D game engine implemented in C++ and released under the MIT license. Greatly inspired by OGRE and Horde3D."
HOMEPAGE = "https://urho3d.github.io/"
LICENSE = "MIT"


SRC_URI = "git://github.com/urho3d/Urho3D.git;protocol=https"
# SRC_URI = "git://github.com/urho3d/Urho3D.git"
SRC_URI += "file://003_select_samples.patch"

SRC_URI_append_raspberrypi0-wifi += "file://000_trust_yocto_for_cpu_tunning.patch"

# On why -I -isystem ...
#   https://github.com/ponylang/ponyc/issues/797
#   https://github.com/ponylang/ponyc/issues/3016
#   https://github.com/ponylang/ponyc/pull/824
#   https://answers.opencv.org/question/172521/error-compiling-opencv-300-in-raspberry-pi-zero/
#   https://gcc.gnu.org/bugzilla/show_bug.cgi?id=70936
#   https://gcc.gnu.org/bugzilla/show_bug.cgi?id=71090
# On how yocto deals with it ...
#   Check poky/meta/recipes-core/glibc/glibc_2.25.bb#60 .... cmake does it!
#   BUILD_CPPFLAGS = "-I${STAGING_INCDIR_NATIVE}"

# On how can CMAKE deal with it ...
#   https://cmake.org/pipermail/cmake/2015-December/062166.html
#   https://gitlab.kitware.com/cmake/cmake/issues/17364
#   https://gitlab.kitware.com/cmake/cmake/issues/17348
# => list (APPEND TARGET_PROPERTIES NO_SYSTEM_FROM_IMPORTED true) @UrhoCommon.cmake#1755
SRC_URI_append_raspberrypi0-wifi += "${@bb.utils.contains("MACHINE_FEATURES", "vc4graphics", "file://005_do_not_search_videocore.patch", "file://001_magically_avoid_isystem.patch", d)}"

SRC_URI_append_raspberrypi0-wifi += "${@bb.utils.contains("MACHINE_FEATURES", "vc4graphics", "file://006_use_x11_instead_rpi_video.patch", "", d)}"

SRC_URI_append_raspberrypi0-wifi += "file://002_avoid_brcm_gl_libs.patch"
# TODO: not needed with vc4graphics
SRC_URI_append_raspberrypi0-wifi += "file://002a_search_vchostif_as_well.patch"
SRC_URI_append_raspberrypi0-wifi += "file://004_not_stripping_shared_lib.patch"


SRCREV="f1ca13db22e79d94003a11665ec27918220872b2"

LIC_FILES_CHKSUM = "file://LICENSE;md5=310c9a68fe03d6c6c8e20f238ef7e46d"
# License fetching ...
S = "${WORKDIR}/git"

DEPENDS = "virtual/libx11 libxext virtual/libgl"
DEPENDS_append_raspberrypi0-wifi = " virtual/libgles2"
#DEPENDS_append_raspberrypi0-wifi = " virtual/egl"

# Let yocto the stripping tasks(RelWithDebInfo, Release, Debug),
EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Debug"
# INSANE_SKIP_${PN} = "already-stripped"

# Check patch 000 & 004
EXTRA_OECMAKE += "-DYOCTO_USED=1 -DURHO3D_LIB_TYPE=SHARED"

# qemux86(Pentium II), given the lack of SSE support
# ref: https://www.yoctoproject.org/docs/2.6/mega-manual/mega-manual.html#qemu-kvm-cpu-compatibility
EXTRA_OECMAKE_append_qemux86 = " -DURHO3D_SSE=0"

# URHO3D_LUA=0 : Problems checking for native compiler in CMakeLists.txt:205 (check_native_compiler_exist)
# symt: SAVEC_CC are empty, however "/mnt/pyro/build-r0w/tmp/hosttools/gcc" is used, there typical arm options are passed => X
# URHO3D_TOOLS: Linking problems, not intention to use in target yet...
EXTRA_OECMAKE_append_raspberrypi0-wifi = " -DRPI=1 -DURHO3D_LUA=0 -DURHO3D_TOOLS=0"


do_install_append() {
    # Remove all stuff that we don't want to get packaged
    rm -rf ${D}${datadir}/Urho3D/Scripts/
    rm -rf ${D}${datadir}/Urho3D/CMake/

    # TODO: Avoid [pkgconfig] sanity check??
    rm -rf ${D}${libdir}/pkgconfig
}

# Samples take a lot of space in image, build for now the most helpful until removing them -DURHO3D_SAMPLES=0
# Check patch 003_select_samples
URHO3D_SELECTED_SAMPLES ?= "HelloWorld|HelloGUI|StaticScene|Physics|Sprites"

do_patch_append() {
    bb.build.exec_func('do_selected_samples', d)
}

do_selected_samples () {
	sed -i -e 's#OECORE_URHO3D_SELECTED_SAMPLES#${URHO3D_SELECTED_SAMPLES}#' ${S}/Source/Samples/CMakeLists.txt
}

# A guide on locations: http://wiki.koansoftware.com/index.php/Directories_and_installation_variables
# Assets
FILES_${PN} += "${datadir}/Urho3D/Resources"
# Samples
FILES_${PN} += "${bindir}"

# -DURHO3D_LIB_TYPE=SHARED
# Otherwise fails [dev-so]
FILES_${PN}-dev += "${libdir}"
# TODO: urho3d depends on urho3d-dev
INSANE_SKIP_${PN} += "dev-deps"

# Not -DURHO3D_LIB_TYPE=SHARED
#FILES_${PN}-staticdev += "${libdir}"

# Docs
FILES_${PN} += "${datadir}/Urho3D/Docs"

inherit cmake
