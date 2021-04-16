# gameboard-API

# Demo

Swagger URL: http://104.236.210.85:8080/swagger-ui.html \
Play: http://boardgame-env.eba-2usnnj5p.us-east-2.elasticbeanstalk.com:8080/ \

# Guidelines

The objective is an API to be able to play minesweeper.

For this, I designed and implement an extensible API also for other board games (like chess or sudoku), applying SOLID principles and design patterns: Factory and Strategy

# Technical

Java 11, Spring Boot 2.4.4, Node 14.15.5, React 17.0.1, 
Gradle 6.4.1, Web pack 5.21.2, MongoDB 4.2.1

# Unit Test

SpringTests, Mockito, Junit4

# Improvements

Graphic design of the page \
User authentication

# Functionality

- API for the game
- API client library for the API designed above.
- When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat)
- Ability to 'flag' a cell with a question mark or red flag
- Detect when game is over
- Persistence
- Time tracking
- Ability to start a new game and preserve/resume the old ones
- Ability to select the game parameters: number of rows, columns, and mines
- Ability to support multiple users/accounts

# Endpoints and Examples

### Create new Minesweeper Game

POST: /api/game/new 

Request: 
```
{
    "columns": 8,
    "gameName": "minesweeper",
    "mines": 10,
    "rows": 8,
    "user": "e"
}
```

Possible game names: minesweeper 

Response: 

```
{
   "gameName":"minesweeper",
   "user":"e",
   "rows":8,
   "columns":8,
   "gameId":"4e9ff054-e6c1-494b-8708-4cfa76abad46",
   "board":{
      "squares":[
         [
            {
               "row":0,
               "column":0
            },
            {
               "row":0,
               "column":1
            },
            ...
         ]
      ],
      "rows":8,
      "columns":8
   },
   "status":{
      "status":"PLAYING",
      "ended":false
   },
   "timeMin":0,
   "timeSec":0
}
```

### Action

POST: /api/game/action

Request: 

```
{
  "action": "TURN",
  "column": 0,
  "gameId": "4e9ff054-e6c1-494b-8708-4cfa76abad46",
  "row": 2,
  "timeMin": 0,
  "timeSec": 2
}
```

Possible action: TURN|FLAG|QUESTION 

Response: 

```
{
  "gameId": "4e9ff054-e6c1-494b-8708-4cfa76abad46",
  "boardResult": [
    {
      "row": 1,
      "column": 0,
      "flag": false,
      "mine": false,
      "adjacentMines": 1,
      "turned": true,
      "question": false
    },
    {
      "row": 0,
      "column": 0,
      "flag": false,
      "mine": false,
      "adjacentMines": 0,
      "turned": true,
      "question": false
    },
    ...
  ],
  "status": {
    "status": "PLAYING",
    "ended": false
  }
}
```

### Game By Id

GET: /api/game/4e9ff054-e6c1-494b-8708-4cfa76abad46/load

Response: 

```
{
  "gameName": "minesweeper",
  "user": "e",
  "rows": 8,
  "columns": 8,
  "gameId": "4e9ff054-e6c1-494b-8708-4cfa76abad46",
  "board": {
    "squares": [
      [
        ...
        {
          "row": 0,
          "column": 2,
          "flag": false,
          "mine": false,
          "adjacentMines": 2,
          "turned": true,
          "question": false
        },
        {
          "row": 0,
          "column": 3
        },
        ...
      ]
    ],
    "rows": 8,
    "columns": 8
  },
  "status": {
    "status": "PLAYING",
    "ended": false
  },
  "timeMin": 0,
  "timeSec": 0
}
```
### Games By User

POST: /api/games/byUser

Request: 

```
{
  "userName": "e"
}
```

Response 

```
{
  "gamesIds": [
    "4e9ff054-e6c1-494b-8708-4cfa76abad46"
  ]
}
```
