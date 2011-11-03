
all: jsonoutputstream

test: jsonoutputstreamtest


jsonoutputstream: de/jonasw/yajsonlib/JsonOutputStream.class

de/jonasw/yajsonlib/JsonOutputStream.class: de/jonasw/yajsonlib/JsonOutputStream.java
	javac de/jonasw/yajsonlib/JsonOutputStream.java

de/jonasw/yajsonlib/JsonOutputStreamTest.class: de/jonasw/yajsonlib/JsonOutputStreamTest.java
	javac -Xlint:unchecked -cp /usr/share/java/junit.jar:. de/jonasw/yajsonlib/JsonOutputStreamTest.java



jsonoutputstreamtest: jsonoutputstream de/jonasw/yajsonlib/JsonOutputStreamTest.class
	@java -cp /usr/share/java/junit.jar:. org.junit.runner.JUnitCore de.jonasw.yajsonlib.JsonOutputStreamTest ; \
	if test "x$$?" != "x0" ; then  \
		echo $$'                \33[36m    +-----------------+'; \
		echo $$'                    |\33[5m\33[1;31m     FAILURES    \33[0m\33[36m|' ; \
		echo $$'                    +-----------------+\33[0m'; \
	else  \
		echo $$'                \33[36m    +-----------------+'; \
		echo $$'                    |\33[42m\33[37m        OK       \33[0m\33[36m|' ; \
		echo $$'                    +-----------------+\33[0m'; fi



