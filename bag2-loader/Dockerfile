# Copyright (C) 2021 B3Partners B.V.
#
# SPDX-License-Identifier: MIT

FROM openjdk:11-jre-slim

LABEL org.opencontainers.image.source=https://github.com/b3partners/brmo/bag2-loader

ARG BRMO_VERSION=2.2.1
ARG DEBIAN_FRONTEND="noninteractive"
ARG TZ="Europe/Amsterdam"

LABEL org.opencontainers.image.authors="support@b3partners.nl" \
      org.opencontainers.image.description="BRMO BAG Loader voor laden van BAG v2" \
      org.opencontainers.image.vendor="B3Partners BV" \
      org.opencontainers.image.licenses="MIT" \
      org.opencontainers.image.title="BRMO BAG Loader" \
      org.opencontainers.image.documentation="https://github.com/B3Partners/brmo/blob/master/bag2-loader/README.md" \
      org.opencontainers.image.version=$BRMO_VERSION

RUN set -eux; \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone \
    && apt-get update \
    && apt-get install -y --no-install-recommends locales tzdata \
    && apt-get install -y dumb-init \
    && apt-get -y --purge autoremove \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* \
    && sed -i 's/# nl_NL.UTF-8 UTF-8/nl_NL.UTF-8 UTF-8/' /etc/locale.gen \
    && locale-gen "nl_NL.UTF-8"

ENV LANG=nl_NL.UTF-8 LANGUAGE=nl_NL:nl LC_ALL=nl_NL.UTF-8

RUN useradd --user-group --system user -s /bin/false

COPY target/lib/ /app/lib/
COPY target/bag2-loader-*.jar /app

WORKDIR /app
RUN chown -R user:user /app
USER user

RUN ln -s bag2-loader-*.jar bag2-loader.jar

ENTRYPOINT ["dumb-init", "java","-jar","/app/bag2-loader.jar"]

