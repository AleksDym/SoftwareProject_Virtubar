FROM adoptopenjdk/openjdk13

RUN  apt-get update -y && \
     apt-get upgrade -y && \
     apt-get dist-upgrade -y && \
     apt-get -y autoremove && \
     apt-get clean

RUN apt-get install -y p7zip \
    wget \
    unzip \
  && rm -rf /var/lib/apt/lists/* \


RUN apt-get update \
  && apt-get install unzip

RUN wget https://services.gradle.org/distributions/gradle-6.2.2-bin.zip -P /tmp
RUN unzip -d /opt/gradle /tmp/gradle-*.zip


COPY settings.gradle build.gradle gradlew gradlew.bat ./
COPY src ./src/
COPY gradle ./gradle/

CMD ["/opt/gradle/gradle-6.2.2/bin/gradle","run"]
