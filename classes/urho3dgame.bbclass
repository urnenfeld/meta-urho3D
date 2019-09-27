DEPENDS = "urho3d"

dassetsdir = "${datadir}/Urho3D/${URHO3D_GAME_NAME}-assets"
sassetsdir ?= ""

do_install_append() {
         install -d ${D}${dassetsdir}
         install -d ${D}${dassetsdir}/CoreData
         install -d ${D}${dassetsdir}/Data

         cp --preserve=mode,timestamps -R ${S}${sassetsdir}/CoreData/* ${D}${dassetsdir}/CoreData
         cp --preserve=mode,timestamps -R ${S}${sassetsdir}/Data/* ${D}${dassetsdir}/Data
}

# A way of separating the assets from the binary
# PACKAGES =+ "${PN}-assets"
# FILES_${PN}-assets += "${dassetsdir}/*"

FILES_${PN} += "${dassetsdir}/*"
