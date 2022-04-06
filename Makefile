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
	mvn package -Dconsumer
	mvn package -Dproducer


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
	mvn test

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
