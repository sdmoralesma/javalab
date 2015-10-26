#!/usr/bin/jjs -fv

print("hello duke!");
var wildflyScript = "/opt/wildfly/bin/standalone.sh ";
var wildflyParams = "--debug 8787 -b 0.0.0.0 -bmanagement 0.0.0.0";

print("Running command: " + wildflyScript + wildflyParams);
$EXEC(wildflyScript, wildflyParams);
print($OUT)
