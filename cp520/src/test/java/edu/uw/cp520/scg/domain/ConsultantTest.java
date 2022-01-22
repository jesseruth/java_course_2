package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsultantTest {

    @Test
    void getName() {
        PersonalName name = new PersonalName("Jesse", "Ruth");
        Consultant consultant = new Consultant(name);
        assertEquals(name.getFirstName(), consultant.getName().getFirstName());
    }

    @Test
    void testToString() {
        PersonalName name = new PersonalName("Jesse", "Ruth");
        Consultant consultant = new Consultant(name);
        assertEquals("Ruth, Jesse", consultant.toString());
    }
}