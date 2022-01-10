# Archiving Weather Data

Create an application which periodically fetches weather data from [wttr.in](https://wttr.in/)'s JSON API and saves it in a database. 

You needn't save all available data, but at the very least, save the current temperature.

The application must provide a REST API to query for aggregated values over the saved data, e.g. getting the max/min/avg temperature of the past 24 hours.

The application must be runnable using Docker. The image needn't be uploaded to a registry, providing a Dockerfile is sufficient. You may also run provide a docker-compose file to start both your application and the database.

## Technology Choice

The technology choice is up to you. We expect to run only standard Docker(-Compose) commands to run it (e.g. `docker build`, `docker run`, `docker-compose up`).

## Final Remarks

If in doubt, ask!

