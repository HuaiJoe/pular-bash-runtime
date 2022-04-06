VERSION         :=      $(shell cat ./VERSION)
# App Name
APPLICATION         :=      my_pulsar_homework
# Docker Repository
REPO      :=      huaijoe
IMAGE_NAME       := ${REPO}/${APPLICATION}

#############################
# compile java source       #
#############################
.PHONY: compile
compile:
	mvn package -Dconsumer -s /Users/huaizhouliu/apache-maven-3.6.3/conf/settings_dev.xml
	mvn package -Dproducer -s /Users/huaizhouliu/apache-maven-3.6.3/conf/settings_dev.xml


#############################
# clean binary files        #
#############################
.PHONY: clean
clean:
	rm -rfv archive
	mvn clean

#############################
# exec unit case              #
#############################
.PHONY: test
test:
	mvn test -s /Users/huaizhouliu/apache-maven-3.6.3/conf/settings_dev.xml

############################
# Build package            #
############################
.PHONY: package
package:
	mkdir -p archive/{app,bin,log}
	cp target/pulsar-bash-consumer.jar archive/app || true
	cp target/pulsar-bash-producer.jar archive/app || true
	cp scripts/receive_then_send_to_pulsar.sh  archive/bin/ || true
	cp scripts/send_message_to_pulsar.sh  archive/bin/  || true


############################
# Build image              #
############################
.PHONY: image
image:
	docker build --network=host -t ${IMAGE_NAME}:$(VERSION) ./

############################
# push image               #
############################
.PHONY: push
push:
	docker push ${IMAGE_NAME}:$(VERSION)
