FROM gradle:8.11.1-jdk17

RUN apt update && apt install -qq -y --no-install-recommends

ENV INSTALL_PATH /reactive-flashcards

RUN mkdir -p "$INSTALL_PATH"

WORKDIR "$INSTALL_PATH"

COPY . .