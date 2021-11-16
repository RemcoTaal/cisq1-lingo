Feature: Start new game
  As a User:
  I want to start a new game
  So I can start playing lingo

  Scenario: I start a new game
    When I start a new game
    Then I should see that a new game has started

  Scenario: The game has started
    Given Game has started
    When I started a new game
    Then A round will start with the word to guess of length 5

Feature: Start new round
  As a User:
  I want to be able to start a round
  In order to start guessing the word of that round

  Scenario Outline: Start a new round
    Given I am playing a game
    And the round was won
    And the last word had "<previous length>" letters
    When I start a new round
    Then the word to guess has "<next length>" letters

    Examples:
    |previous length|next length|
    |5              |6          |
    |6              |7          |
    |7              |8          |

    # Failure path
    Given I am playing a game
    And the round was lost
    Then I cannot start a new round

  Scenario Outline: Guessing a word
    Given the word "<word>"
    When I guess "<guess>"
    Then I get the feedback "<feedback>"

    Examples:
    |word |guess  |feedback                                            |
    |lingo|linga  |CORRECT, CORRECT, CORRECT, CORRECT, ABSENT          |
    |lingo|lingoes|INVALID, INVALID, INVALID, INVALID, INVALID, INVALID|
    |lingo|bongi  |ABSENT, PRESENT, CORRECT, CORRECT, PRESENT          |

Feature: Guess 5 letter word
  As a User:
  I want to guess a 5 letter word
  To be able to make progress in the game
Feature: Guess 6 letter word
  As a User:
  I want to guess a 6 letter word
  To be able to make progress in the game
Feature: Guess 7 letter word
  As a User:
  I want to guess a 7 letter word
  To be able to make progress in the game
Feature: Feedback on guessed word
  As a User:
  I want to get feedback on the word I have guessed
  To be able to know what part of my guessed word is correct or wrong
Feature: Score round
  As a User:
  I want to know the score I got during the round
  In order to know how I performed
Feature: Hint
  As a User:
  I want to get a hint based on my previous guess
  In order to base my next guess on the provided hint

