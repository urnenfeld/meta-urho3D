MACHINE="raspberrypi0-wifi"
IMAGE="core-image-urho3d"

echo sudo dd if=./tmp/deploy/images/$MACHINE/$IMAGE-$MACHINE.rpi-sdimg of=$1
echo Ensure invoking from build directory, review and press key or ^C ...
read something

sudo dd if=./tmp/deploy/images/$MACHINE/$IMAGE-$MACHINE.rpi-sdimg of=$1