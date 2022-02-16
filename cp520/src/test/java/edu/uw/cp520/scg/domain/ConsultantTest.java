package edu.uw.cp520.scg.domain;

import app.Util;
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
        assertEquals("Ruth, Jesse NMN", consultant.toString());
    }
    @Test
    void testSerializationProxy() {
        final String filename = "consultant_test.ser";
        PersonalName personalName = new PersonalName("Jesse", "Ruth");
        Consultant consultant = new Consultant(personalName);
        Util.writeObject(filename, consultant);
        Consultant newConsultant = (Consultant) Util.readObject(filename);
        assertEquals(consultant, newConsultant);
    }
}