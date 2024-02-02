package pingmebot;

import pingmebot.task.Deadline;
import pingmebot.task.Events;
import pingmebot.task.ToDos;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    protected String userInput;
    protected ArrayList<String> words;

    public Parser (String userInput) {
        this.userInput = userInput;
        this.words = new ArrayList<>(Arrays.asList(userInput.split(" ")));
    }

    /*
     * Returns a todo object with the task description
     * Handles errors where user fails to include task description
     */
    public ToDos todoParser() throws myBotException {
        try {
            if (!this.words.get(1).isEmpty()) {
                StringBuilder description = new StringBuilder(this.words.get(1));
                for (int i = 2; i < words.size(); i++) {
                    description.append(" ").append(this.words.get(i));
                }
                return new ToDos(description.toString());
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new myBotException("OOPS! The command is incomplete. Please provide a task description!");
        }
    }


    /*
     * Return a deadline object with task description and date/time to complete the task
     * Handles error where user forgets to include task description, a deadline,
     * or when there is an incorrect syntax (e.g. never include /by)
     */
    public Deadline deadlineParser() throws myBotException {
        StringBuilder description = new StringBuilder();
        StringBuilder by  = new StringBuilder();
        int index = this.words.indexOf("/by");
        if (index != -1) {
            if (index != 1) {
                // This is to ensure that /by would not become part of the description field
                // if the user forgets to include description field
                description = new StringBuilder(this.words.get(1));
            }
        } else {
            throw new myBotException("I don't understand your command. Try writing: deadline (task description) /by (d/m/yyyy HHmm format)");
        }

        for (int i = 2; i < words.size(); i++) {
            if (i < index) {
                description.append(" ").append(words.get(i));
            } else if (i > index) {
                by.append(" ").append(words.get(i));
            }
        }
        if (!(by.toString().isEmpty() || description.toString().isEmpty())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime parsedDateTime;
            try {
                parsedDateTime = LocalDateTime.parse(by.toString().trim(), formatter);
            } catch (DateTimeParseException e) {
                throw new myBotException("I don't understand your command. Try writing: deadline (task description) /by (d/m/yyyy HHmm format)");
            }
            return new Deadline(description.toString(), parsedDateTime);
        } else {
            throw new myBotException("You have missing fields! You need a task description & a deadline to finish your task, try again!");
        }


    }

    /*
     * Returns an event object with task description, a from and to date/time
     * Handles errors where users forget to include task description, a from or to date/time or
     * incorrect syntax (e.g. never include /from, /to)
     */
    public Events eventsParser() throws myBotException {
        StringBuilder description = new StringBuilder();
        StringBuilder start = new StringBuilder();
        StringBuilder end = new StringBuilder();
        int indexOfFrom = this.words.indexOf("/from");
        int indexOfTo = this.words.indexOf("/to");

        if (indexOfFrom == -1 || indexOfTo == -1) {
            throw new myBotException("I don't understand your command. Try writing: event (task description) /from (date/time) /to (date/time)");
        } else {
            if (indexOfFrom == 1 || indexOfTo == 1) {} else {
                description = new StringBuilder(this.words.get(1));
            }
        }

        for (int i = 2; i < words.size(); i++) {
            if (i < indexOfFrom) {
                description.append(" ").append(words.get(i));
            } else if (i > indexOfFrom && i < indexOfTo) {
                start.append(" ").append(words.get(i));
            } else if (i > indexOfTo){
                end.append(" ").append(words.get(i));
            }
        }
        if (description.toString().isEmpty() || start.toString().isEmpty() || end.toString().isEmpty()) {
            throw new myBotException("You having missing fields! You need a task description, start and end date/time for your task, try again!");
        } else {
            return new Events(description.toString(), start.toString(), end.toString());
        }
    }

    /*
     * Returns an integer to know which task the user wishes to mark
     * Deals with error when the user accidentally tries to mark a task which does not belong in the list of task
     * or when the user fails to include which task they want to mark as completed
     */
    public int markParser(int currentNumOfTask) throws myBotException {
        try {
            if (!this.words.get(1).isEmpty()) {
                if (Integer.parseInt(this.words.get(1)) > currentNumOfTask || Integer.parseInt(this.words.get(1)) <= 0) {
                    throw new myBotException("You have currently " + currentNumOfTask + " tasks. You cannot mark task larger or smaller than this!");
                } else {
                    return Integer.parseInt(this.words.get(1)) - 1;
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new myBotException("I'm not sure which task you wish to mark. Please specify the task you wish to mark and try again!");
        }
    }

    /*
     * Returns an integer to know which task the user wishes to un-mark
     * Deals with error when the user accidentally tries to un-mark a task which does not belong in the list of task
     * or when the user fails to include which task they want to un-mark
     */
    public int unmarkParser(int currentNumOfTask) throws myBotException {
        try {
            if (!this.words.get(1).isEmpty()) {
                if (Integer.parseInt(this.words.get(1)) > currentNumOfTask || Integer.parseInt(this.words.get(1)) <= 0) {
                    throw new myBotException("You have currently " + currentNumOfTask + " tasks. You cannot un-mark task larger or smaller than this!");
                } else {
                    return Integer.parseInt(this.words.get(1)) - 1;
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new myBotException("I'm not sure which task you wish to un-mark. Please specify the task you wish to un-mark and try again!");
        }
    }

    /*
     * Returns an integer to know which task the user wishes to delete
     * Deals with error when the user accidentally tries to delete a task which does not belong in the list of task
     * or when the user fails to include which task they want to delete
     */
    public int deleteParser(int currentNumOfTask) throws myBotException {
        try {
            if (!this.words.get(1).isEmpty()) {
                if (Integer.parseInt(this.words.get(1)) > currentNumOfTask || Integer.parseInt(this.words.get(1)) <= 0) {
                    throw new myBotException("You have currently " + currentNumOfTask + " tasks. You cannot delete task larger or smaller than this!");
                } else {
                    return Integer.parseInt(this.words.get(1)) - 1;
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new myBotException("I'm not sure which task you wish to delete. Please specify the task you want to delete and try again!");
        }
    }

    /**
     * Returns the keyword of what the user is trying to find in his/her tasklist.
     *
     * @return Keyword specified by the user.
     * @throws myBotException If the user forgets to input keyword to find.
     */
    public String parseFindCommand() throws  myBotException {
        try {
            if (!this.words.get(1).isEmpty()) {
                return this.words.get(1);
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new myBotException("I'm not sure what you are trying to find. Please specify a keyword and try again!");
        }
    }
}
