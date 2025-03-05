Feature: Core Product Tests

  Background: Given User lands on CoreProducts Home Page

    Scenario: TC1 Get All Jacket Prices
      When I close info pointer If Available
      And  I Click on 'Shop' Menu
      Then I click on Men's Item from Shop Menu
      And  Switch to new page for Men's shopping items
      And  I filter Jackets from the List
      And  I set page size as '96'
      And  I read Product details to file

    Scenario: TC2 Count Videos older than 3 days
      When I close info pointer If Available
      And  I hover over three dots in home page
      And  I click on NewsAndFeatures Item from menu
      And  I count number of videos older than '3' days