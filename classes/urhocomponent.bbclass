DEPENDS += "urho3d"

URHO3D_GAME_ID ?= ""
URHO3D_COMPONENT_ID ?= "${URHO3D_GAME_ID}"

URHO3D_ASSETS_BASE = "${datadir}/Urho3D"
URHO3D_ASSETS_PATHS ?= "Data CoreData"

dassetsdir = "${URHO3D_ASSETS_BASE}/${URHO3D_COMPONENT_ID}-assets"
sassetsdir ?= ""

# A way of separating the assets from the binary
# PACKAGES =+ "${PN}-assets"
# FILES_${PN}-assets += "${dassetsdir}/*"
FILES_${PN} += "${dassetsdir}/*"


do_install_append() {
#    if [ "${URHO3D_REUSE_CORE_ASSETS}" = "1" ] ; then

    install -d ${D}${dassetsdir}

    for path in ${URHO3D_ASSETS_PATHS}
    do
        install -d ${D}${dassetsdir}/$path
        cp --preserve=mode,timestamps -R ${S}${sassetsdir}/$path/* ${D}${dassetsdir}/$path
    done

}


python do_urhocomponent_qa () {

    if not d.expand("${URHO3D_COMPONENT_ID}"):
            bb.error("No URHO3D_COMPONENT_ID or URHO3D_GAME_ID specified in recipe")

    if d.getVar("URHO3D_REUSE_CORE_ASSETS") == "1":
        urhoassets = ['Data','CoreData']
        assets = d.getVar("URHO3D_ASSETS_PATHS").split(" ")

        if [path for path in urhoassets if path in assets]:
            bb.error("URHO3D_ASSETS_PATHS match with the standard provided by engine, but URHO3D_REUSE_CORE_ASSETS is set.")


}

addtask urhocomponent_qa after do_compile before do_install
