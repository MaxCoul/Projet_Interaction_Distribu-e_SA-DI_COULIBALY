SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")

rm -rf $SCRIPTPATH/example/lib/
mkdir $SCRIPTPATH/example/lib
cp $SCRIPTPATH/ingescape/jar/*.jar $SCRIPTPATH/example/lib/
cp $SCRIPTPATH/ingescape/libs/*.jar $SCRIPTPATH/example/lib/

echo "building ..."
javac $SCRIPTPATH/example/src/Example.java $SCRIPTPATH/example/src/agents/cb/* -cp "$SCRIPTPATH/example/lib/*" -d "$SCRIPTPATH/example/bin"

echo "executing ..."
java -cp "$SCRIPTPATH/example/lib/*:$SCRIPTPATH/example/bin" Example   