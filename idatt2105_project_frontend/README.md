# SnagIt frontend

SnagIt is a web-application for selling and buying items. This project contains the code for the client.


## Members
- Therese Synnøve Rondeel
- Carine Margrethe Rondeel
## Recommended IDE Setup

- Reccomend using IntelliJ. 


## Prerequisites (and reccomended versions)

- Needs the SnagIt backend to get items from the database
- Node.js (v22+)

## Project Setup

```sh
npm install
```

### Compile and start client

```sh
npm run dev
```

### Run Unit Tests with [Vitest](https://vitest.dev/)

Tests are somewhat limited due to the way the code is set up and being a group of two which led to more hours than expected, and not enough to make those methods more efficient.

```sh
npm run test:unit
```

### Run End-to-End Tests with [Cypress](https://www.cypress.io/)
Tests are somewhat limited due to the way the code is set up and being a group of two which led to more hours than expected, and not enough to make those methods more efficient.


```sh
npx cypress open
```

## Test user credentials
When using the application you may create your own user, or use one of the existing ones. Below are their credentials:
```
#User: Ola Nordmann
email: olaNord@snagmail.com
password: olaErBest!

#User: Kari Norddame
email: kari1989@snagmail.com
password: ef912hfs12

#User: Navn Navnesen
email: navn@navnesen.no
password: Passord123
```

## Acknowledgements
For getting coordinates from norwegian zip codes, [OpenStreetMap](https://www.openstreetmap.org/copyright) has been used.