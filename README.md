Example run

$ cd /tmp
$ mkdir test
$ cd test/
$ git clone https://github.com/mothlight/VTUF-3D-Java.v2.git
$ cd VTUF-3D-Java.v2/
$ unzip example.zip
$ cd src/
$ javac VTUF3D/*.java
$ java VTUF3D.TUFreg3D ../example/PrestonBase8/
$ find . -mindepth 1 -maxdepth 1 -iname '*.out' | zip -@ out.zip
$ #rm *.out
