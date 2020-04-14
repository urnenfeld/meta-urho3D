inherit urhocomponent

URHO3D_GAME_LAUNCHER = "${URHO3D_COMPONENT_ID}-launcher"

python do_create_launcher () {
    filename = d.expand("${WORKDIR}/${URHO3D_GAME_LAUNCHER}")

    with open (filename,'w') as urhofest_file:
        urhofest_file.write("#!/bin/sh\n")

        if d.getVar("URHO3D_REUSE_CORE_ASSETS") == "1":
            urhofest_file.write(d.expand("export URHO3D_PREFIX_PATH=${URHO3D_ASSETS_BASE}/Resources\n"))
        else:
            urhofest_file.write(d.expand("export URHO3D_PREFIX_PATH=${dassetsdir}\n"))

        urhofest_file.write(d.expand("${URHO3D_COMPONENT_ID}\n"))

    os.chmod(filename, 0o644)
}

do_install_append() {
        install -m 544 ${WORKDIR}/${URHO3D_GAME_LAUNCHER} ${D}${bindir}
}

addtask create_launcher after do_compile before do_install
