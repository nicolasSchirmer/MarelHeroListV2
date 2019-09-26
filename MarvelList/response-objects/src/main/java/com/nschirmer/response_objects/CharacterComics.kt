package com.nschirmer.response_objects

/**
 * Response Pattern:

"comics": {
    "available": 12,
    "collectionURI": "http://gateway.marvel.com/v1/public/characters/1011334/comics",
    "items": [
        {
        "resourceURI": "http://gateway.marvel.com/v1/public/comics/21366",
        "name": "Avengers: The Initiative (2007) #14"
        }
    ...
    ],
    "returned": 12
}

 * **/

class CharacterComics(val available: Int)