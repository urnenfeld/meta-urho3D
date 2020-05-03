#!/bin/sh

echo "Thefin loop"

# Taken from Launcher
export URHO3D_PREFIX_PATH="/usr/share/Urho3D/thefin-assets;/usr/share/Urho3D/Resources"
THEFIN_EXECUTABLE="/usr/bin/thefin"

while : ; do
    $THEFIN_EXECUTABLE
    [[ $? == 0 ]] || break
    /tmp/finNextLaunch
done

echo Parking system ...
# shutdown -h now
