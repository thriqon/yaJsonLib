
all: jsonoutputstream

test: jsonoutputstreamtest


jsonoutputstream: JsonOutputStream.class

JsonOutputStream.class: JsonOutputStream.java
	javac JsonOutputStream.java

JsonOutputStreamTest.class: JsonOutputStreamTest.java
	javac -cp /usr/share/java/junit.jar:. JsonOutputStreamTest.java



jsonoutputstreamtest: jsonoutputstream JsonOutputStreamTest.class
	@java -cp /usr/share/java/junit.jar:. org.junit.runner.JUnitCore JsonOutputStreamTest ; \
	if test "x$$?" != "x0" ; then  \
		echo $$'                \33[36m    +-----------------+'; \
		echo $$'                    |\33[5m\33[1;31m     FAILURES    \33[0m\33[36m|' ; \
		echo $$'                    +-----------------+\33[0m'; \
	else  \
		echo $$'                \33[36m    +-----------------+'; \
		echo $$'                    |\33[42m\33[37m        OK       \33[0m\33[36m|' ; \
		echo $$'                    +-----------------+\33[0m'; fi



