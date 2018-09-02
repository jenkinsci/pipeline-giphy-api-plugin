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
giphyGetRandomByKeyword - return the url of random gif by the keyword
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
    def gif = giphyGetRandomByKeyword(credentialsId: 'credential-id-from-jenkins', keyword: "keyword", rating: 'g', imageSize: 'downsized_medium')
    echo gif
}
```
#### Scripted pipeline
```groovy
TODO
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
