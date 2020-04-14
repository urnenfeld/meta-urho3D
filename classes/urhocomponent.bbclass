DEPENDS += "urho3d"

URHO3D_GAME_ID ?= ""
URHO3D_COMPONENT_ID ?= "${URHO3D_GAME_ID}"

URHO3D_ASSETS_BASE = "${datadir}/Urho3D"

dassetsdir = "${URHO3D_ASSETS_BASE}/${URHO3D_COMPONENT_ID}-assets"
sassetsdir ?= ""

# A way of separating the assets from the binary
# PACKAGES =+ "${PN}-assets"
# FILES_${PN}-assets += "${dassetsdir}/*"
FILES_${PN} += "${dassetsdir}/*"


do_install_append() {

    if [ "${URHO3D_REUSE_CORE_ASSETS}" = "1" ] ; then
        # NOTE: This link breaks packaging rules
        # install -d ${D}${datadir}/Urho3D
        # ln -s Resources ${D}${dassetsdir}
        :
    else
        install -d ${D}${dassetsdir}
        install -d ${D}${dassetsdir}/CoreData
        install -d ${D}${dassetsdir}/Data

        cp --preserve=mode,timestamps -R ${S}${sassetsdir}/CoreData/* ${D}${dassetsdir}/CoreData
        cp --preserve=mode,timestamps -R ${S}${sassetsdir}/Data/* ${D}${dassetsdir}/Data
    fi

}


python do_urhocomponent_qa () {

    if not d.expand("${URHO3D_COMPONENT_ID}"):
            bb.error("No URHO3D_COMPONENT_ID or URHO3D_GAME_ID specified in recipe, ...")

}

addtask urhocomponent_qa after do_compile before do_install
