@XIP-6
@REQ_XIP-4
Feature: Create Task Feature
    Verify if user is able to create the task

    @TEST_XIP-10 @adminUserLoggedIn
    Scenario Outline: Create a task
        Given user clicks on new task button on Dashboard Page
        When user enters new task name "<Task Name>"
        And user selects new task type"<Task Type>"
        And user clicks on button Create and continue
        Then verify that a card with new task "<Task Name>" is visible
        And delete the new task "<Task Name>"

        Examples:
        | Task Name                 | Task Type     |
        | Test Login Functionality1 | Urgent task   |

