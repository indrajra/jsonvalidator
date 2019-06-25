import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class TestDateTime {
    private Schema schema;

    @Before
    public void setUp() throws Exception {
        InputStream schemaStream = TestDateTime.class.getResourceAsStream("date_time/History.json");
        JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
        SchemaLoader schemaLoader = SchemaLoader.builder().schemaJson(rawSchema).draftV7Support().build();
        schema = schemaLoader.load().build();
    }

    @Test
    public void simpleValidHistoricalItem_validationPasses() {
        InputStream inputStream = TestDateTime.class.getResourceAsStream("date_time/valid.json");
        JSONObject obj = new JSONObject(new JSONTokener(inputStream));
        schema.validate(obj); // throws a ValidationException if this object is invalid
        Assert.assertTrue(true == true);
    }

    @Test
    public void badIRI_validationFails() {
        // Simple valid plus extra garbage is compliant
        boolean exceptionThrown = false;
        try {
            InputStream inputStream = TestDateTime.class.getResourceAsStream("iri_test/invalid.json");
            JSONObject obj = new JSONObject(new JSONTokener(inputStream));
            schema.validate(obj); // throws a ValidationException if this object is invalid
            Assert.assertTrue(true == true);
        } catch (ValidationException ve) {
            exceptionThrown = true;
            System.out.println(ve.getMessage());
        }
        Assert.assertTrue(exceptionThrown == true);
    }


    @After
    public void tearDown() throws Exception {
    }
}