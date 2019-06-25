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

public class TestConnectedEntity {
    private Schema schema;

    @Before
    public void setUp() throws Exception {
        InputStream schemaStream = TestConnectedEntity.class.getResourceAsStream("/connectedEntity/Rectangle.json");
        JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
        SchemaLoader schemaLoader = SchemaLoader.builder().schemaJson(rawSchema)
                .resolutionScope("http://localhost:8080/_schemas/").build();
        schema = schemaLoader.load().build();
    }

    @Test
    public void color_validationPasses() {
        InputStream schemaStream = Main.class.getResourceAsStream("connectedEntity/Color.json");
        JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
        SchemaLoader schemaLoader = SchemaLoader.builder().schemaJson(rawSchema).
                draftV7Support().build();
        Schema colorschema = schemaLoader.load().build();

        InputStream inputStream = Main.class.getResourceAsStream("connectedEntity/ValidSampleColor.json");
        JSONObject obj = new JSONObject(new JSONTokener(inputStream));
        colorschema.validate(obj); // throws a ValidationException if this object is invalid
        System.out.println(colorschema.getSchemaLocation());

        Assert.assertTrue(true); // no exception, so good.
    }

    @Test
    public void connectedEntityValidRectangle_validationPasses() {
        InputStream inputStream = TestConnectedEntity.class.getResourceAsStream("/connectedEntity/ValidRectangle.json");
        JSONObject obj = new JSONObject(new JSONTokener(inputStream));
        schema.validate(obj); // throws a ValidationException if this object is invalid

        Assert.assertTrue(true); // no exception, so good.
    }

    @Test (expected = ValidationException.class)
    public void connectedEntityInvalidColorRectangle_validationPasses() {
        InputStream inputStream = TestConnectedEntity.class.getResourceAsStream("/connectedEntity/InvalidRectangle.json");
        JSONObject obj = new JSONObject(new JSONTokener(inputStream));
        schema.validate(obj); // throws a ValidationException if this object is invalid
    }

    @After
    public void tearDown() throws Exception {
    }
}