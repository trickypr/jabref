package net.sf.jabref.model.groups;

import java.util.Optional;

import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.FieldName;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExplicitGroupTest {

    private ExplicitGroup group;
    private ExplicitGroup group2;

    private BibEntry entry;

    @Before
    public void setUp() {
        group = new ExplicitGroup("myExplicitGroup", GroupHierarchyType.INDEPENDENT, ',');
        group2 = new ExplicitGroup("myExplicitGroup2", GroupHierarchyType.INCLUDING, ',');
        entry = new BibEntry();
    }

    @Test
    public void addSingleGroupToEmptyBibEntryChangesGroupsField() {
        group.add(entry);

        assertEquals(Optional.of("myExplicitGroup"), entry.getField(FieldName.GROUPS));
    }

    @Test
    public void addSingleGroupToNonemptyBibEntryAppendsToGroupsField() {
        entry.setField(FieldName.GROUPS, "some thing");
        group.add(entry);

        assertEquals(Optional.of("some thing, myExplicitGroup"), entry.getField(FieldName.GROUPS));
    }

    @Test
    public void addTwoGroupsToBibEntryChangesGroupsField() {
        group.add(entry);
        group2.add(entry);

        assertEquals(Optional.of("myExplicitGroup, myExplicitGroup2"), entry.getField(FieldName.GROUPS));
    }

    @Test
    public void addDuplicateGroupDoesNotChangeGroupsField() throws Exception {
        entry.setField(FieldName.GROUPS, "myExplicitGroup");
        group.add(entry);

        assertEquals(Optional.of("myExplicitGroup"), entry.getField(FieldName.GROUPS));
    }

    @Test
    // For https://github.com/JabRef/jabref/issues/2334
    public void removeDoesNotChangeFieldIfContainsNameAsPart() throws Exception {
        entry.setField(FieldName.GROUPS, "myExplicitGroup_alternative");
        group.remove(entry);

        assertEquals(Optional.of("myExplicitGroup_alternative"), entry.getField(FieldName.GROUPS));
    }

    @Test
    // For https://github.com/JabRef/jabref/issues/2334
    public void removeDoesNotChangeFieldIfContainsNameAsWord() throws Exception {
        entry.setField(FieldName.GROUPS, "myExplicitGroup alternative");
        group.remove(entry);

        assertEquals(Optional.of("myExplicitGroup alternative"), entry.getField(FieldName.GROUPS));
    }
}
