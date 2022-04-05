#!/bin/sh
APP_HOME=/home/pulsar
#########################################
# Help                                  #
#########################################
Help() {
  # Display Help
  echo "Tools help"
  echo
  echo "Syntax: receive_then_send_to_pulsar.sh [-u|f|t|n]"
  echo "options:"
  echo "    u     Set pulsar service url,like: -u pulsar://192.168.49.2:32579 "
  echo "    f     Set receive pulsar topic, like: -f oed/pulsar/my-topic1"
  echo "    t     Set send pulsar topic, like: -t oed/pulsar/my-topic2"
  echo "    n     Set subscriptionName , like: -n sub1"
  echo
  echo "Example:"
  echo "      ./receive_then_send_to_pulsar.sh -u pulsar://192.168.49.2:32579 -f my-topic -t my-topic -n sub1"
}

#########################################
# Main: Read Parameter                  #
#########################################
while getopts u:f:t:n: flag; do
  case "${flag}" in
  u) serviceUrl=${OPTARG} ;;
  f) from=${OPTARG} ;;
  t) to=${OPTARG} ;;
  n) subscription=${OPTARG} ;;
  *)
    Help
    exit 1
    ;;
  esac
done
echo "Pulsar Service Url: ${serviceUrl}"
echo "From Topic: ${from}"
echo "To Topic: ${to}"
echo "SubscriptionName: ${subscription}"
ÒÒ
#########################################
# Main: send message                    #
#########################################
java -jar "${APP_HOME}"/pulsar-bash-consumer.jar "${serviceUrl}" "${from}" "${to}" "${subscription}"
