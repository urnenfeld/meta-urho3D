SUMMARY = "Cross-platform 2D and 3D game engine."
DESCRIPTION = "Urho3D is a free lightweight, cross-platform 2D and 3D game engine implemented in C++ and released under the MIT license. Greatly inspired by OGRE and Horde3D."
HOMEPAGE = "https://urho3d.github.io/"
LICENSE = "MIT"


SRC_URI = "git://github.com/urho3d/Urho3D.git;protocol=https"
# SRC_URI = "git://github.com/urho3d/Urho3D.git"
SRC_URI += "file://000_trust_yocto_for_cpu_tunning.patch"

SRCREV="f1ca13db22e79d94003a11665ec27918220872b2"

LIC_FILES_CHKSUM = "file://LICENSE;md5=310c9a68fe03d6c6c8e20f238ef7e46d"
# License fetching ...
S = "${WORKDIR}/git"

DEPENDS = "virtual/libx11 libxext virtual/libgl"

# Let yocto the stripping tasks(RelWithDebInfo, Release, Debug),
# Check patch 000
EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Debug -DYOCTO_USED=1"
# INSANE_SKIP_${PN} = "already-stripped"

# qemux86(Pentium II), given the lack of SSE support
# ref: https://www.yoctoproject.org/docs/2.6/mega-manual/mega-manual.html#qemu-kvm-cpu-compatibility
EXTRA_OECMAKE_append_qemux86 = " -DURHO3D_SSE=0"

# URHO3D_LUA=0 : Problems checking for native compiler in CMakeLists.txt:205 (check_native_compiler_exist)
# symt: SAVEC_CC are empty, however "/mnt/pyro/build-r0w/tmp/hosttools/gcc" is used, there typical arm options are passed => X
# URHO3D_ANGELSCRIPT: Problems finding essential headers...
EXTRA_OECMAKE_append_raspberrypi0-wifi = " -DRPI=1 -DURHO3D_LUA=0 -DURHO3D_ANGELSCRIPT=0"
DEPENDS_append_raspberrypi0-wifi = " virtual/libgles2 virtual/egl"

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
