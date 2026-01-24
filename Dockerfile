# syntax=docker/dockerfile:1

FROM ghcr.io/linuxserver/baseimage-ubuntu:noble

# set version label
ARG BUILD_DATE
ARG VERSION
ARG UNIFI_VERSION
LABEL build_version="Linuxserver.io version:- ${VERSION} Build-date:- ${BUILD_DATE}"
LABEL maintainer="thespad"

# environment settings
ENV DEBIAN_FRONTEND="noninteractive"
ENV ENVSUBST_VERSION=v1.4.3

RUN \
  echo "**** install packages ****" && \
  apt-get update && \
  apt-get install --no-install-recommends -y \
    jsvc \
    logrotate \
    openjdk-17-jre-headless \
    unzip \
    gettext && \
  curl -L https://github.com/a8m/envsubst/releases/download/${ENVSUBST_VERSION}/envsubst-`uname -s`-`uname -m` -o envsubst && \
  chmod +x envsubst && \
  mv envsubst /usr/local/bin && \
  echo "**** install unifi ****" && \
  if [ -z ${UNIFI_VERSION+x} ]; then \
    UNIFI_VERSION=$(curl -sX GET "https://fw-update.ubnt.com/api/firmware-latest?filter=eq~~product~~unifi-controller&filter=eq~~platform~~unix&filter=eq~~channel~~release" \
    | jq -r '._embedded.firmware[].version' \
    | awk -F '+' '{print $1}' \
    | tr -d 'v'); \
  fi && \
  UNIFI_DOWNLOAD=$(curl -sX GET "https://fw-update.ubnt.com/api/firmware?filter=eq~~product~~unifi-controller&filter=eq~~platform~~unix&filter=eq~~channel~~release&sort=-version" \
  | jq -r "._embedded.firmware[] | select(.version | test(\"v${UNIFI_VERSION}\")) | ._links.data.href") && \
  mkdir -p /app && \
  curl -o \
  /tmp/unifi.zip -L \
    "${UNIFI_DOWNLOAD}" && \
  unzip /tmp/unifi.zip -d /usr/lib && \
  mv /usr/lib/UniFi /usr/lib/unifi && \
  printf "Linuxserver.io version: ${VERSION}\nBuild-date: ${BUILD_DATE}" > /build_version && \
  echo "**** cleanup ****" && \
  apt-get clean && \
  rm -rf \
    /tmp/* \
    /var/lib/apt/lists/* \
    /var/tmp/*

# add local files
COPY root/ /

# Volumes and Ports
WORKDIR /usr/lib/unifi
VOLUME /config
EXPOSE 8080 8443 8843 8880
