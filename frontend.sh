#!/usr/bin/env bash
set -e -x

#
# will copy all files related to the frontend project to javalab

# define paths
CURRENT=`pwd`
JAVALAB_FRONT=/home/sergio/WebstormProjects/javalab-ang2
TARGET_DIR=$CURRENT/javalab/src/main/webapp

# remove previous frontend copied
cd $TARGET_DIR
rm -rf app assets prod node_modules
rm index.html

# generates project's artifacts
cd $JAVALAB_FRONT
source $JAVALAB_FRONT/dist.sh

# copy new frontend files
cp -a $JAVALAB_FRONT/dist/app $TARGET_DIR
cp -a $JAVALAB_FRONT/dist/assets $TARGET_DIR
cp -a $JAVALAB_FRONT/dist/prod $TARGET_DIR
cp -a $JAVALAB_FRONT/dist/node_modules $TARGET_DIR
cp $JAVALAB_FRONT/dist/index.html $TARGET_DIR/index.html

# package in a war file
cd $CURRENT/javalab/
mvn package
