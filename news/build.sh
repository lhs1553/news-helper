#!/bin/sh

git reset --hard
git pull

rm -rf target

gradle clean bootjar 
gradle bootjar --stacktrace --debug

mkdir target
cp build/libs/*.jar target/
