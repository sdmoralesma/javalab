FROM ubuntu:14.04.3

MAINTAINER Sergio Morales "sdmoralesma@gmail.com"

#Install packages on ubuntu base image
RUN \
  export DEBIAN_FRONTEND=noninteractive && \
  apt-get update && \
  apt-get install -y unzip && \
  apt-get install -y software-properties-common python-software-properties

# Install Java 8, agree to oracle jdk license
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \ 
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Install WildFly to /opt
ENV WILDFLY_VERSION 9.0.1.Final
RUN cd /opt && wget http://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz && \
  tar xvf wildfly-$WILDFLY_VERSION.tar.gz && \
  ln -s /opt/wildfly-$WILDFLY_VERSION /opt/wildfly && \
  rm wildfly-$WILDFLY_VERSION.tar.gz

ENV JBOSS_HOME /opt/wildfly

# Create admin user for wildfly
RUN $JBOSS_HOME/bin/add-user.sh admin admin123 --silent

# dowload mysql connector
RUN mkdir $JBOSS_HOME/connector/ && \
  cd $JBOSS_HOME/connector/ && \
  wget http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.38/mysql-connector-java-5.1.38.jar

#add datasource to wildfly
ADD wildfly-config/scripts $JBOSS_HOME/scripts/
RUN $JBOSS_HOME/scripts/execute.sh

# Solves bug in history
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/current

# Install Gradle
ENV GRADLE_VERSION 2.7
WORKDIR /usr/bin
RUN wget https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip && \
  unzip gradle-$GRADLE_VERSION-bin.zip && \
  ln -s gradle-$GRADLE_VERSION gradle && \
  rm gradle-$GRADLE_VERSION-bin.zip

# Set Appropriate Environmental Variables
ENV GRADLE_HOME /usr/bin/gradle
ENV PATH $PATH:$GRADLE_HOME/bin

# Create the wildfly user and group
RUN groupadd -r wildfly-group -g 433 && \
  useradd -u 431 -r -g wildfly-group -s /bin/false wildfly -m

# Gradle workaround, will explode if TERM isn't declared
ENV TERM xterm

ENV USER_HOME /home/wildfly

# Add java project
ADD java-gradle $USER_HOME/java-gradle
RUN cd $USER_HOME/java-gradle && gradle run --quiet --refresh-dependencies

# Add scala project
ADD scala-gradle $USER_HOME/scala-gradle
RUN cd $USER_HOME/scala-gradle && gradle run --quiet --refresh-dependencies

# Add groovy project
ADD groovy-gradle $USER_HOME/groovy-gradle
RUN cd $USER_HOME/groovy-gradle && gradle run --quiet --refresh-dependencies

# Run everything below as the wildfly user
RUN chown -R wildfly:wildfly-group $JBOSS_HOME/* && \
  chown -R wildfly:wildfly-group $USER_HOME/* && \
  chmod -R 777 $JBOSS_HOME/* && \
  chmod -R 777 $USER_HOME/*

USER wildfly

# Expose the ports
EXPOSE 8080 9990 8787

# Boot WildFly in the standalone mode and bind to all interfaces
CMD ["sh", "-c", "${JBOSS_HOME}/bin/standalone.sh", "--debug", "8787", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
