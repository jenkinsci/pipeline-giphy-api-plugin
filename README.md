# Note!
This plugin is not officially deployed (yet) to the official Jenkins repo.  
Therefore in order to use it you need to clone this repository, and use maven command:  
```
mvn hpi:run
```
# Table of Contents
- [Introduction](#introduction)
- [Configuration](#configuration)
- [Steps](#steps)
  - [Giphy Search API Steps](#giphy-search-api-steps)
    - [Variables](#variables)
    - [Example](#example)
      - [Declarative pipeline](#declarative-pipeline)
      - [Scripted pipeline](#scripted-pipeline)
  - [Giphy Translate API Steps](#giphy-translate-api-steps)
  - [Giphy Trending API Steps](#giphy-trending-api-steps)
  - [Giphy Random API Steps](#giphy-random-api-steps)
# Introduction
This plugin that expose giphy API within you Jenkins pipeline, both straight forward API and some customs.
# Configuration
Set your api key in credentials section on Jenkins.  
- ***TODO:*** *elaborate more including screenshots*
# Steps  
### Giphy Search API Steps
```
giphySearch - return list of urls matched to the keyword
giphySearchRandomByKeyword - return the url of random gif by the keyword
```
### Variables
- `credentialsId` *required* - The credential you saved to jenkins. See [here](#configuration).
- `keyword` *required* - The Keyword to search.
- `rating` *default - g* - MPAA rating filters of the images -  `Y`, `G`, `PG`, `PG-13` and `R`.
- `imageSize` *default - downsized_medium* - Image size from giphy. See more details [here](https://developers.giphy
.com/docs/#rendition-guide).
### Example
#### Declarative pipeline
```groovy
pipeline {
  agent none
  stages {
    stage("foo") {
      steps {
          echo giphySearch(credentialsId: 'giphy', keyword: 'success', rating: 'g').toString()
      }
    }
  }
}
```
Output is:
```
Started by user unknown or anonymous
Running in Durability level: MAX_SURVIVABILITY
[Pipeline] stage
[Pipeline] { (foo)
[Pipeline] giphySearch
[Pipeline] echo
[https://media0.giphy.com/media/l3q2BXqLMnzhVF720/giphy.gif, https://media0.giphy.com/media/3o7TKtsBMu4xzIV808/giphy.gif, https://media3.giphy.com/media/LWVn0cCgpRt8Q/giphy.gif, https://media0.giphy.com/media/V80Bk9rW9ZpVC/giphy.gif, https://media1.giphy.com/media/xNBcChLQt7s9a/giphy.gif, https://media1.giphy.com/media/l3q2Z6S6n38zjPswo/giphy.gif, https://media1.giphy.com/media/GS1VR900wmhJ6/giphy.gif, https://media1.giphy.com/media/yGlu7x5jfWGZi/giphy.gif, https://media1.giphy.com/media/2vA33ikUb0Qz6/giphy.gif, https://media1.giphy.com/media/3ov9jSmllAIKuthAe4/giphy.gif, https://media2.giphy.com/media/3ohhwo4PzDFaz2sADu/giphy.gif, https://media2.giphy.com/media/itVfItoFSikqQ/giphy.gif, https://media0.giphy.com/media/T0WzQ475t9Cw/giphy.gif, https://media3.giphy.com/media/nXxOjZrbnbRxS/giphy.gif, https://media3.giphy.com/media/111ebonMs90YLu/giphy.gif, https://media0.giphy.com/media/uudzUtVcsLAoo/giphy.gif, https://media0.giphy.com/media/4xpB3eE00FfBm/giphy-downsized-medium.gif, https://media1.giphy.com/media/OHZ1gSUThmEso/giphy.gif, https://media1.giphy.com/media/zaqclXyLz3Uoo/giphy.gif, https://media2.giphy.com/media/XreQmk7ETCak0/giphy.gif, https://media0.giphy.com/media/KEVNWkmWm6dm8/giphy.gif, https://media2.giphy.com/media/vtVpHbnPi9TLa/giphy.gif, https://media2.giphy.com/media/oGO1MPNUVbbk4/giphy.gif, https://media0.giphy.com/media/26gsobowozGM9umBi/giphy.gif, https://media3.giphy.com/media/aWRWTF27ilPzy/giphy.gif]
[Pipeline] }
[Pipeline] // stage
[Pipeline] End of Pipeline
Finished: SUCCESS
```
#### Scripted pipeline
```groovy
node {
    def gif = giphySearchRandomByKeyword(credentialsId: 'giphy', keyword: "keyword", rating: 'g', imageSize: 
    'downsized_medium')
    echo gif
}
```
Output is:
```
Started by user unknown or anonymous
Running in Durability level: MAX_SURVIVABILITY
[Pipeline] node
Running on Jenkins in D:\GitHub\giphy-plugin\work\jobs\test\workspace
[Pipeline] {
[Pipeline] giphySearchRandomByKeyword
[Pipeline] echo
https://media3.giphy.com/media/1tykcAaWUvIY/giphy-downsized-medium.gif
[Pipeline] }
[Pipeline] // node
[Pipeline] End of Pipeline
Finished: SUCCESS
```
### Giphy Translate API Steps
```
TODO
```
### Giphy Trending API Steps
```
TODO
```
### Giphy Random API Steps
```
TODO
```
