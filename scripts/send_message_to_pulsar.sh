#!/bin/sh
APP_HOME=/home/pulsar
#########################################
# Help                                  #
#########################################
Help()
{
   # Display Help
   echo "Tools help"
   echo
   echo "Syntax: send_message_to_pulsar.sh [-u|t|m]"
   echo "options:"
   echo "    u     Set pulsar service url,like: -u pulsar://192.168.49.2:32579 "
   echo "    t     Set pulsar topic, like: -t oed/pulsar/my-topic"
   echo "    m     Set message data, like: -m \"hello world\""
   echo
   echo "Example:"
   echo "      send_message_to_pulsar.sh -u pulsar://192.168.49.2:32579 -t my-topic -m \"hello world\""
}

#########################################
# Main: Read Parameter                  #
#########################################
while getopts u:t:m: flag
do
  case "${flag}" in
    u) serviceUrl=${OPTARG};;
    t) topic=${OPTARG};;
    m) msg=${OPTARG};;
    *) Help
       exit 1 ;;
  esac
done
echo "Pulsar Service Url: $serviceUrl";
echo "Topic: $topic";
echo "Message: $msg";

#########################################
# Main: send message                    #
#########################################
java -jar "${APP_HOME}"/pulsar-bash-producer.jar "${serviceUrl}"  "${topic}" "${msg}"