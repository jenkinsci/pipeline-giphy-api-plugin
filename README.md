# Table of Contents
- [Introduction](#introduction)
- [Configuration](#configuration)
- [Steps](#steps)
  - [Giphy Search API Steps](#giphy-search-api-steps)
    - [Variables](#search-variables)
    - [Example](#search-example)
      - [Declarative pipeline](#search-declarative-pipeline)
      - [Scripted pipeline](#search-scripted-pipeline)
  - [Giphy Translate API Steps](#giphy-translate-api-steps)
    - [Variables](#translate-variables)
    - [Example](#translate-example)
      - [Declarative pipeline](#translate-declarative-pipeline)
      - [Scripted pipeline](#translate-scripted-pipeline)
  - [Giphy Trending API Steps](#giphy-trending-api-steps)
  - [Giphy Random API Steps](#giphy-random-api-steps)
    - [Variables](#random-variables)
    - [Example](#random-example)
      - [Declarative pipeline](#random-declarative-pipeline)
      - [Scripted pipeline](#random-scripted-pipeline)
# Introduction
This plugin expose giphy API within your Jenkins pipeline, both straight forward APIs and some customs.  
For more information look here: [Giphy for Developers](https://developers.giphy.com/docs/#technical-documentation)
# Configuration
Set your api key in credentials section on Jenkins.  
- ***TODO:*** *elaborate more including screenshots*
# Steps  
### Giphy Search API Steps

`giphySearch` - return list of urls matched to the keyword  
`giphySearchRandomByKeyword` - return the url of random gif by the keyword. my custom implementation of [Giphy Random API](#giphy-random-api-steps)

### Search Variables
- `credentialsId` *required* - The credential you saved to jenkins. See [here](#configuration).
- `keyword` *required* - The Keyword to search.
- `rating` *default - g* - MPAA rating filters of the images -  `Y`, `G`, `PG`, `PG-13` and `R`.
- `imageSize` *default - downsized_medium* - Image size from giphy. See more details [here](https://developers.giphy.com/docs/#rendition-guide).
### Search Example
#### Search Declarative pipeline
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
#### Search Scripted pipeline
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

`giphyTranslate` - return url matched to the keyword  

### Translate Variables
- `credentialsId` *required* - The credential you saved to jenkins. See [here](#configuration).
- `keyword` *required* - The Keyword to search.
- `imageSize` *default - downsized_medium* - Image size from giphy. See more details [here](https://developers.giphy.com/docs/#rendition-guide).
### Translate Example
#### Translate Declarative pipeline
```groovy
pipeline {
  agent none
  stages {
    stage("foo") {
      steps {
          echo giphyTranslate(credentialsId: 'giphy', keyword: 'success').toString()
      }
    }
  }
}
```
Output is:
```
Started by user admin
Running in Durability level: MAX_SURVIVABILITY
[Pipeline] stage
[Pipeline] { (foo)
[Pipeline] giphyTranslate
[Pipeline] echo
https://media2.giphy.com/media/2cZlphZGMk4RW/giphy.gif
[Pipeline] }
[Pipeline] // stage
[Pipeline] End of Pipeline
Finished: SUCCESS
```
#### Translate Scripted pipeline
```groovy
node {
    def gif = giphyTranslate(credentialsId: 'giphy', keyword: "keyword", imageSize: 
    'downsized_medium')
    echo gif
}
```
Output is:
```
Started by user admin
Running in Durability level: MAX_SURVIVABILITY
[Pipeline] node
Running on Jenkins in /var/jenkins_home/workspace/test
[Pipeline] {
[Pipeline] giphyTranslate
[Pipeline] echo
https://media1.giphy.com/media/2t9ASqRNjQTNNXv5ob/giphy-downsized-medium.gif
[Pipeline] }
[Pipeline] // node
[Pipeline] End of Pipeline
Finished: SUCCESS
```
### Giphy Trending API Steps
```
TODO
```
### Giphy Random API Steps

`giphyRandom` - return random url matched to the keyword  

### Random Variables
- `credentialsId` *required* - The credential you saved to jenkins. See [here](#configuration).
- `tag` *required* - The tag to search.
- `imageSize` *default - downsized_medium* - Image size from giphy. See more details [here](https://developers.giphy.com/docs/#rendition-guide).
### Random Example
#### Random Declarative pipeline
```groovy
pipeline {
  agent none
  stages {
    stage("foo") {
      steps {
          echo giphyRandom(credentialsId: 'giphy', tag: 'success').toString()
      }
    }
  }
}
```
Output is:
```
Started by user admin
Running in Durability level: MAX_SURVIVABILITY
[Pipeline] stage
[Pipeline] { (foo)
[Pipeline] giphyRandom
[Pipeline] echo
https://media2.giphy.com/media/cN1RJVaYCht96/giphy.gif
[Pipeline] }
[Pipeline] // stage
[Pipeline] End of Pipeline
Finished: SUCCESS
```
#### Random Scripted pipeline
```groovy
node {
    def gif = giphyRandom(credentialsId: 'giphy', tag: "tag", rating: 'pg-13', imageSize: 
    'downsized_medium')
    echo gif
}
```
Output is:
```
Started by user admin
Running in Durability level: MAX_SURVIVABILITY
[Pipeline] node
Running on Jenkins in /var/jenkins_home/workspace/test
[Pipeline] {
[Pipeline] giphyRandom
[Pipeline] echo
https://media0.giphy.com/media/6pipAdjEVrVBK/giphy.gif
[Pipeline] }
[Pipeline] // node
[Pipeline] End of Pipeline
Finished: SUCCESS
```
