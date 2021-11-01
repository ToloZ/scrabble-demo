# Summary

Implement an HTTP REST service with JWT Auth that returns [Srabble](https://en.wikipedia.org/wiki/Scrabble_letter_distributions) words for a given set of letters.

Data source: Use the list of words: words.txt

#### API Specification

You must be logged in

- API: http://localhost:8080/api/auth/login 
- Request body
    ```json
    {
      "username": "pedro",
      "password": "1234"
    }
    ```
- Copy *access_token* and paste it on another request Authorization Header
    ```txt
    Example
  
    GET http://localhost:8080/api/user
    Headers:
            Key       |     Value
        ---------------------------------
        Authorization | Bearer eyJ0eXA...
    ```

The REST web service runs on port 8080 and responds to a URL of the pattern http://localhost:8080/api/scrabble/words/<letters>, where <letters> is a string of arbitrary
letters. There are two cases that need to be covered:
```txt
1. The dictionary contains words that can be spelled with the given letters.

  A JSON list of strings is returned, where each entry is a word. They are sorted by Scrabble score, from highest to lowest scoring. For example:

  Request URL:       http://localhost:8080/words/hat
  Expected response: ["hat","ah","ha","th","at","a"]

  The letters are like Scrabble tiles. Order is unimportant:

  Request URL:       http://localhost:8080/words/aht
  Expected response: ["hat","ah","ha","th","at","a"]

  The letters are not case-sensitive, so this returns the same results:

  Request URL:       http://localhost:8080/words/HAT
  Expected response: ["hat","ah","ha","th","at","a"]

  Request URL:       http://localhost:8080/words/Hat
  Expected response: ["hat","ah","ha","th","at","a"]

2. No dictionary words can be spelled with the given letters.

  An empty JSON list is returned.  For example:

  Request URL:       http://localhost:8080/words/zzz
  Expected response: []

The Scrabble score is calculated by adding up the point values of every letter in the word.

The point values are:

Points | Letters
-------+-----------------------------
   1   | A, E, I, L, N, O, R, S, T, U
   2   | D, G
   3   | B, C, M, P
   4   | F, H, V, W, Y
   5   | K
   8   | J, X
  10   | Q, Z

For example, the following words have these scores:

hat:  6
code: 7
antidisestablishmenatarianism: 39
```


## Usage.txt
```txt
Requirements:
  Requires Maven 3 or newer and Java JDK 11

To build:
  mvn clean package

To run:
  mvn spring-boot:run

URL format:
    http://localhost:8080/api/scrabble/words/<letters>

Example usage:
    > curl http://localhost:8080/api/scrabble/words/hat
    ["hat","ah","ha","th","at","a"]

    > curl http://localhost:8080/api/scrabble/words/zzz
    []
```

## Custom properties
- Trim: True-> Trim the word
- Quote: True-> Replace ' with blank
- WordPath: Custom Path to select word source
- Blank: True-> Accept \n as a word

Default values
```properties
scrabble.trim=true
scrabble.quote=true
scrabble.wordpath=classpath:words.txt
scrabble.blank=false
```

## Bench
JMeter Launch Options:
- Config File: Test_Plan_Scrabble.jmx
- Request: **/scrabble/words/antidisestablishmenatarianism**
- Number of Threads: 50
- Rump-up period: 60 seconds
- Loop count: Infinite
- During: 1:30 minutes

Summary Results in [Image Result](summary.png)

##### To run this test:
1. Run application: mvn spring-boot:run
2. Open JMeter
3. Go to File -> Open -> [test.jmx](Test_Plan_Scrabble.jmx)
4. Click 'Start'

## POSTMAN
Postman [file](Scrabble.postman_collection.json) with some examples use cases, was also provided for testing api manually

## Following versions
- Code coverage (jacoco)
- Swagger with auth
