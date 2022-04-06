FROM openjdk:8

ENV APP_HOME=/user/local/service
ENV TZ='Asia/Shanghai'

COPY archive ${APP_HOME}/my_pulsar_homework

WORKDIR ${APP_HOME}/my_pulsar_homework
ENTRYPOINT ["/bin/sh", "-c","./bin/receive_then_send_to_pulsar.sh -u ${PULSAR_SERVICE_URL} -f ${FROM_TOPIC} -t ${TO_TOPIC} -n ${SUBSCRIPTION_NAME}"]