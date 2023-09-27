# UIService

UI Client of the PuLS Platform

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Use Docker
Run `docker build . -t uiservice:dev` to build Docker container and `docker run -v ${PWD}:/app -v /app/node_modules -p 4201:4200 --rm example:dev` to start the docker container.

## User Docker Compose
Run `docker-compose up -d --build` to build and start the container

`docker-compose exec uiservice ng test --watch=false`
`docker-compose exec uiservice ng e2e --port 4202`

Run `docker-compose stop` to stop the running container