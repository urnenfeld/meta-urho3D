DEPENDS = "urho3d"

dassetsdir = "${datadir}/Urho3D/${URHO3D_GAME_ID}-assets"
sassetsdir ?= ""

MANIFEST_NAME_JSON ?= "urhofest.json"

# A way of separating the assets from the binary
# PACKAGES =+ "${PN}-assets"
# FILES_${PN}-assets += "${dassetsdir}/*"

FILES_${PN} += "${dassetsdir}/*"


python do_create_urhofest () {
    import json

    filename = d.expand("${WORKDIR}/${MANIFEST_NAME_JSON}")

    urhofest_dict =     {"Summary": d.getVar("SUMMARY"),
                         "Description": d.getVar("DESCRIPTION"),
                         "HomePage": d.getVar("HOMEPAGE"),
                         "Id": d.getVar("URHO3D_GAME_ID"),
                         "Genre": d.getVar("URHO3D_GAME_GENRE"),
                         "Subgenre": d.getVar("URHO3D_GAME_SUBGENRE"),
                         "Adult": d.getVar("URHO3D_GAME_ADULT_QUALIFICATION")}

    with open (filename,'w') as urhofest_file:
        json.dump(urhofest_dict, urhofest_file, indent=True)

    os.chmod(filename, 0o644)
}


python do_urhofest_qa () {

    checklist = [
                    ["SUMMARY", True],
                    ["URHO3D_GAME_ADULT_QUALIFICATION", False],
                    ["URHO3D_GAME_GENRE", True],
                    ["URHO3D_GAME_SUBGENRE", False],
                ]

    for var in checklist:
        if not d.getVar(var[0]):
            if var[1]:
                bb.error("No "+var[0]+" specified in recipe, urhofest cannot be created")
            else:
                bb.warn("No "+var[0]+" specified in recipe, theFin will not be happy")

}


do_install_append() {
         install -d ${D}${dassetsdir}
         install -d ${D}${dassetsdir}/CoreData
         install -d ${D}${dassetsdir}/Data

         install ${WORKDIR}/${MANIFEST_NAME_JSON} ${D}${dassetsdir}

         cp --preserve=mode,timestamps -R ${S}${sassetsdir}/CoreData/* ${D}${dassetsdir}/CoreData
         cp --preserve=mode,timestamps -R ${S}${sassetsdir}/Data/* ${D}${dassetsdir}/Data
}


addtask urhofest_qa after do_compile before create_urhofest
addtask create_urhofest after do_urhofest_qa before do_install
