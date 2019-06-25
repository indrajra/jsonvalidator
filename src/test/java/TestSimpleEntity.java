import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.*;

import java.io.InputStream;

import static org.junit.Assert.*;

public class TestSimpleEntity {
    private Schema schema;

    @Before
    public void setUp() throws Exception {
        InputStream schemaStream = TestSimpleEntity.class.getResourceAsStream("simple/Rectangle.json");
        JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
        SchemaLoader schemaLoader = SchemaLoader.builder().schemaJson(rawSchema).draftV7Support().build();
        schema = schemaLoader.load().build();
    }

    @Test
    public void simpleValidRectange_validationPasses() {
        InputStream inputStream = TestSimpleEntity.class.getResourceAsStream("simple/SimpleValidRectangle.json");
        JSONObject obj = new JSONObject(new JSONTokener(inputStream));
        schema.validate(obj); // throws a ValidationException if this object is invalid
        Assert.assertTrue(true == true);
    }

    @Test
    public void extraRectangleValuesSupplied_validationFails() {
        // Simple valid plus extra garbage is compliant
        boolean exceptionThrown = false;
        try {
            InputStream inputStream = TestSimpleEntity.class.getResourceAsStream("simple/ExtraValuesRectangle.json");
            JSONObject obj = new JSONObject(new JSONTokener(inputStream));
            schema.validate(obj); // throws a ValidationException if this object is invalid
        } catch (ValidationException ve) {
            exceptionThrown = true;
            System.out.println(ve.getMessage());
        }
        Assert.assertTrue(exceptionThrown == true);
    }

    @Test
    public void passTriangleForRectange_validationFails() {
        // Case 3 - wrong entity passed
        boolean exceptionThrown = false;
        try {
            InputStream inputStream = TestSimpleEntity.class.getResourceAsStream("simple/Triangle.json");
            JSONObject obj = new JSONObject(new JSONTokener(inputStream));
            schema.validate(obj); // throws a ValidationException if this object is invalid

        } catch (ValidationException e) {
            System.out.println("Wrong entity data - not compliant");
            System.out.println("--> " + e.getMessage());
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown == true);
    }

    @Test
    public void invalidValueTypes_validationFails() {
        // Case 4 - right entity but values different
        boolean exceptionThrown = false;
        try {
            InputStream inputStream = TestSimpleEntity.class.getResourceAsStream("simple/RectangleWithIncorrectTypeData.json");
            JSONObject obj = new JSONObject(new JSONTokener(inputStream));
            schema.validate(obj); // throws a ValidationException if this object is invalid

        } catch (ValidationException e) {
            exceptionThrown = true;
            System.out.println("Wrong scoping - not compliant");
            System.out.println("--> " + e.getMessage());
        }
        Assert.assertTrue(exceptionThrown == true);
    }


    @After
    public void tearDown() throws Exception {
    }
}