#!/bin/bash

if [ $# -eq 0 ]; then
	
    echo No topic name supplied!

else

	echo Creating directory \"$1\" ...

	cp -r template $1

	cd $1

	echo Modifying file names for $1 ...

	mv notes/template.tex notes/$1.tex

	mv python/template.py python/$1.py

	mv cpp/template.hpp cpp/$1.hpp

	mv cpp/template.cpp cpp/$1.cpp

	mv java/template.java java/$1.java

	echo -e "Done \033[91m<3\033[0m"

fi

