docker run --rm --name demoDb -e POSTGRES_PASSWORD=pwd -e POSTGRES_USER=usr -e POSTGRES_DB=demoDb -p 5441:5432 postgres:16