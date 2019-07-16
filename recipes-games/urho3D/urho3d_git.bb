SUMMARY = "Cross-platform 2D and 3D game engine."
DESCRIPTION = "Urho3D is a free lightweight, cross-platform 2D and 3D game engine implemented in C++ and released under the MIT license. Greatly inspired by OGRE and Horde3D."
HOMEPAGE = "https://urho3d.github.io/"
LICENSE = "MIT"


SRC_URI = "git://github.com/urho3d/Urho3D.git;protocol=https"
# SRC_URI = "git://github.com/urho3d/Urho3D.git"
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
SRC_URI_append_raspberrypi0-wifi += "file://001_magically_avoid_isystem.patch"

SRCREV="f1ca13db22e79d94003a11665ec27918220872b2"

LIC_FILES_CHKSUM = "file://LICENSE;md5=310c9a68fe03d6c6c8e20f238ef7e46d"
# License fetching ...
S = "${WORKDIR}/git"

DEPENDS = "virtual/libx11 libxext virtual/libgl"
DEPENDS_append_raspberrypi0-wifi = " virtual/libgles2 virtual/egl"

# Let yocto the stripping tasks(RelWithDebInfo, Release, Debug),
EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Debug"
# INSANE_SKIP_${PN} = "already-stripped"

# Check patch 000
EXTRA_OECMAKE += "-DYOCTO_USED=1"

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

# A guide on locations: http://wiki.koansoftware.com/index.php/Directories_and_installation_variables
# Assets
FILES_${PN} += "${datadir}/Urho3D/Resources"
# Samples
FILES_${PN} += "${bindir}"
# libs
FILES_${PN}-staticdev += "${libdir}"
# Docs
FILES_${PN} += "${datadir}/Urho3D/Docs"

inherit cmake
