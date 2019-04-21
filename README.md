# Dictionary Application
Light and fast online dictionary app that allows to use custom made dictionaries.

## Running
First, you need to build the frontend. Go to `src/main/resources/frontend` folder and run (npm and node are required):
```
make dependencies
```

While working on the frontend it is better to turn on the development mode, since it will refresh your browser after
each change in the source code:
```
make run_webui_dev_server
```

If you need to add a new dependency, you should modify `src/main/resources/frontend/package.json`. After that remove
old dependencies by removing `src/main/resources/frontend/webui/node_modules` folder. Rerun:
```
make dependencies
```

To run the backend use the following options (Java 8 is required):
```
server dictionary.yaml
```

## Configuration
Configuration options are stored in `dictionary.yaml`

| Option            | Description |
| ----------------- | ------- |
| version | version number |
| dbFullPath | path to the database file |
| dbUser   | database user name |
| dbPassword | database password |

## API endpoint

| URL            | Method | Description | 
| ----------------- | ------ | ----------- |
| /info | GET | information about the API |
| /dictionary/{id} | GET | information about a dictionary |
| /query | POST | start a search query |
| /query/{id} | GET | request a query status |
| /query/{id}/matches | GET | list query matches for a given query |