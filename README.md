# Table of Contents
- [Introduction](#introduction)
- [Configuration](#Configuration)
- [Steps](#Steps)
  - [Giphy Search API Steps](#Giphy-Search-API-Steps)
    - [Variables](#Variables)
    - [Example](#Example)
      - [Declarative pipeline](#Declarative-pipeline)
      - [Scripted pipeline](#Scripted-pipeline)
  - [Giphy Translate API Steps](#Giphy-Translate-API-Steps)
  - [Giphy Trending API Steps](#Giphy-Trending-API-Steps)
  - [Giphy Random API Steps](#Giphy-Random-API-Steps)
# Introduction
This plugin that expose giphy API within you Jenkins pipeline, both straight forward API and some customs.
#Configuration
Set your api key in credentials section on Jenkins.  
- ***TODO:*** *elaborate more including screenshots*
# Steps  
### Giphy Search API Steps
```
giphyGetRandomByKeyword
```
###Variables
- `credentialsId` - The credential you saved to jenkins. See [here](#Configuration).
- `keyword` - The Keyword to search.
- `rating` - MPAA rating filters of the images -  `Y`, `G`, `PG`, `PG-13` and `R`.
- `imageSize` - Image size from giphy. See more details [here](https://developers.giphy.com/docs/#rendition-guide).
###Example
##### Declarative pipeline
```groovy
pipeline {
    def gif = giphyGetRandomByKeyword(credentialsId: 'credential-id-from-jenkins', keyword: "keyword", rating: 'g', imageSize: 'downsized_medium')
    echo gif
}
```
##### Scripted pipeline
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
