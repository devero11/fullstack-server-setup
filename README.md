# Full-Stack Template 


## Instalation

```bash

git clone https://github.com/devero11/fullstack-server-setup.git


```

## Starting the server

The project is made of 3 components:
    1. Angular
    2. Spring-Boot
    3. Postgres

All 3 components need to be running for everything to work:

1. Start-up Postgres using Docker-Compose:

```bash

    # Navigate to postgres directory
    cd postgres
    
    # Run docker-compose
    # The -d flag runs docker-compose in detached mode (runs in background, lets you use the terminal after)
    docker-compose up -d   

    # Check the status of the database server
    sudo docker ps

    #Status should be Up + some amount of time

```
