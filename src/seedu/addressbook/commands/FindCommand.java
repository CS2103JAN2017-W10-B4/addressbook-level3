package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     * The command is case insensitive
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        Set<String> temp = new HashSet<String>();
        for (String each: keywords){
            temp.add(each.toLowerCase());
        }
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName1 = new HashSet<>(person.getName().getWordsInNameLowercase());
            if (!Collections.disjoint(wordsInName1, temp)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

}
