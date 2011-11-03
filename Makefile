

all: jsonoutputstream

test: jsonoutputstreamtest


jsonoutputstream: de/jonasw/yajsonlib/JsonOutputStream.class

de/jonasw/yajsonlib/JsonOutputStream.class: de/jonasw/yajsonlib/JsonOutputStream.java
	javac de/jonasw/yajsonlib/JsonOutputStream.java

test/JsonOutputStreamTest.class: test/JsonOutputStreamTest.java
	javac -cp /usr/share/java/junit.jar:. test/JsonOutputStreamTest.java



jsonoutputstreamtest: jsonoutputstream test/JsonOutputStreamTest.class
	@java -cp /usr/share/java/junit.jar:. org.junit.runner.JUnitCore test.JsonOutputStreamTest ; \
	if test "x$$?" != "x0" ; then  \
		echo $$'                \33[36m    +-----------------+'; \
		echo $$'                    |\33[5m\33[1;31m     FAILURES    \33[0m\33[36m|' ; \
		echo $$'                    +-----------------+\33[0m'; \
	else  \
		echo $$'                \33[36m    +-----------------+'; \
		echo $$'                    |\33[42m\33[37m        OK       \33[0m\33[36m|' ; \
		echo $$'                    +-----------------+\33[0m'; fi



