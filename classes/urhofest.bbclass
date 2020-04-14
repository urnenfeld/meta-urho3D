inherit urhocomponent

MANIFEST_NAME_JSON ?= "urhofest.json"

python do_create_urhofest () {
    import json

    filename = d.expand("${WORKDIR}/${MANIFEST_NAME_JSON}")

    urhofest_dict =     {"Summary": d.getVar("SUMMARY"),
                         "Description": d.getVar("DESCRIPTION"),
                         "HomePage": d.getVar("HOMEPAGE"),
                         "Id": d.getVar("URHO3D_COMPONENT_ID"),
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

    for req in checklist:
        if not d.getVar(req[0]):
            if req[1]:
                bb.error("No "+req[0]+" specified in recipe, urhofest cannot be created")
            else:
                bb.warn("No "+req[0]+" specified in recipe, theFin will not be happy")

}


do_install_append() {
         install ${WORKDIR}/${MANIFEST_NAME_JSON} ${D}${dassetsdir}
}


addtask urhofest_qa after do_compile before do_create_urhofest
addtask create_urhofest after do_compile before do_install
