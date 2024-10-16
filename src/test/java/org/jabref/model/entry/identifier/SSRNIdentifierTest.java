package org.jabref.model.entry.identifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SSRNIdentifierTest {
    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                // Basic string
                Arguments.of("4904445"),
                Arguments.of("  4904445   "),

                // URLs
                Arguments.of("https://ssrn.com/abstract=4904445"),
                Arguments.of("https://papers.ssrn.com/sol3/papers.cfm?abstract_id=4904445"),
                Arguments.of("  https://ssrn.com/abstract=4904445    "),
                Arguments.of("  https://papers.ssrn.com/sol3/papers.cfm?abstract_id=4904445     ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    public void acceptCorrectSSRNAbstracts(String input) {
        assertEquals("4904445", new SSRNIdentifier(input).getNormalized());
    }

    @Test
    public void testIdentifierNormalisation() {
        assertEquals("123456", new SSRNIdentifier(123456).getNormalized());
    }

    @Test
    public void testIdentifierExternalUrl() {
        SSRNIdentifier ssrnIdentifier = new SSRNIdentifier(123456);
        URI uri = URI.create("https://ssrn.com/abstract=123456");
        assertEquals(Optional.of(uri), ssrnIdentifier.getExternalURI());
    }
}
