package ai.otto.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScrapedFieldUtilsTest {

    @Test
    public void testGetFirstName() {
        assertEquals("Arjay", ScrapedFieldUtils.getFirstName("Arjay Nacion"));
        assertEquals("Mary", ScrapedFieldUtils.getFirstName("Mary Grace Doe"));
    }

    @Test
    public void testGetCompanyName_noRoles() {
        String title = "Some title";
        String companyName = "RCG Global Services Full-time";
        boolean hasRoles = false;

        assertEquals("RCG Global Services", ScrapedFieldUtils.getCompanyName(companyName, title, hasRoles));

        companyName = "Upwork Freelance";

        assertEquals("Upwork", ScrapedFieldUtils.getCompanyName(companyName, title, hasRoles));

        companyName = "Some Company Inc. Part-time";

        assertEquals("Some Company", ScrapedFieldUtils.getCompanyName(companyName, title, hasRoles));

        title = "Accenture";
        hasRoles = true;

        assertEquals("Accenture", ScrapedFieldUtils.getCompanyName(companyName, title, hasRoles));
    }
}
