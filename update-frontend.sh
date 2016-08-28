#!/usr/bin/env bash

#
# transpile to js the frontend project
cd /home/sergio/WebstormProjects/javalab-ang2/

# create a "dist" folder with all js files required
source /home/sergio/WebstormProjects/javalab-ang2/build.sh

source_dir="/home/sergio/WebstormProjects/javalab-ang2/dist"
target_dir="/home/sergio/IdeaProjects/javalab/javalab/src/main/webapp"

cd $target_dir

#remove previous frontend
rm -rf app
rm -rf assets
rm -rf node_modules
rm index.html
rm systemjs.config.js

#copy new files
cp -a $source_dir"/app" $target_dir
cp -a $source_dir"/assets" $target_dir
cp -a $source_dir"/node_modules" $target_dir
cp $source_dir"/index.html" $target_dir"/index.html"
cp $source_dir"/bundle.min.js" $target_dir"/bundle.min.js"

#package in war file
cd /home/sergio/IdeaProjects/javalab/javalab/
mvn package
